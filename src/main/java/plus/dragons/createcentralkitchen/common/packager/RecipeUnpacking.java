/*
 * Copyright (C) 2025  DragonsPlus
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package plus.dragons.createcentralkitchen.common.packager;

import com.simibubi.create.content.logistics.stockTicker.PackageOrderWithCrafts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

/** Plans complete, balanced recipes without changing either inventory or package. */
final class RecipeUnpacking {
    private RecipeUnpacking() {}

    interface Device {
        IItemHandler inventory();

        int slotStart();

        int slotCount();

        List<Candidate> recipes();

        Predicate<Recipe<?>> acceptsRecipe();
    }

    record Candidate(Recipe<?> recipe, List<Ingredient> ingredients) {
        Candidate(Recipe<?> recipe) {
            // Keg recipes can contain empty placeholders for unused input slots.
            this(recipe, recipe.getIngredients().stream().filter(ingredient -> !ingredient.isEmpty()).toList());
        }
    }

    static @Nullable Plan plan(Device device, List<ItemStack> items, @Nullable PackageOrderWithCrafts context) {
        if (device.slotCount() < 1 || device.slotCount() > 9
                || device.slotStart() < 0 || device.slotStart() + device.slotCount() > device.inventory().getSlots())
            return null;
        var contents = aggregate(items);
        long total = contents.stream().mapToLong(ItemStack::getCount).sum();
        if (total == 0 || total > Integer.MAX_VALUE || contents.size() > device.slotCount())
            return null;

        var before = new ArrayList<ItemStack>();
        var occupied = new ArrayList<Integer>();
        var existing = new ArrayList<ItemStack>();
        int storedServings = 0;
        for (int slot = 0; slot < device.slotCount(); slot++) {
            var stack = device.inventory().getStackInSlot(device.slotStart() + slot).copy();
            before.add(stack);
            if (stack.isEmpty())
                continue;
            if (storedServings != 0 && storedServings != stack.getCount())
                return null;
            storedServings = stack.getCount();
            occupied.add(slot);
            existing.add(stack.copyWithCount(1));
        }

        // A context describes a recipe layout, not necessarily the number of crafts in this package.
        var pattern = orderPattern(context, device.slotCount());
        Candidate contextualRecipe = null;
        if (!pattern.isEmpty() && multiplier(contents, pattern) > 0) {
            for (var recipe : device.recipes()) {
                if (assign(recipe.ingredients(), pattern) != null) {
                    contextualRecipe = recipe;
                    break;
                }
            }
        }

        if (!existing.isEmpty()) {
            int servings = multiplier(contents, existing);
            if (servings == 0)
                return null;
            for (var recipe : device.recipes()) {
                if (contextualRecipe != null && recipe != contextualRecipe)
                    continue;
                if (assign(recipe.ingredients(), existing) != null)
                    return finish(device, recipe, before, occupied, existing, servings);
            }
            return null;
        }

        if (contextualRecipe != null)
            return finish(device, contextualRecipe, before, null, pattern, multiplier(contents, pattern));

        // Preserve the old one-item-per-slot interpretation when it is a complete recipe.
        var single = units(items.stream().filter(stack -> !stack.isEmpty()).toList(), 1, device.slotCount());
        if (single != null) {
            for (var recipe : device.recipes()) {
                if (assign(recipe.ingredients(), single) != null)
                    return finish(device, recipe, before, null, single, 1);
            }
        }
        for (var recipe : device.recipes()) {
            int size = recipe.ingredients().size();
            if (size == 0 || size > device.slotCount() || total % size != 0)
                continue;
            int servings = (int) (total / size);
            var units = units(contents, servings, device.slotCount());
            var ordered = units == null ? null : assign(recipe.ingredients(), units);
            if (ordered != null)
                return finish(device, recipe, before, null, ordered, servings);
        }
        return null;
    }

    private static @Nullable Plan finish(Device device, Candidate recipe, List<ItemStack> before,
            @Nullable List<Integer> slots, List<ItemStack> layout, int servings) {
        // Selection is final: never try another recipe or layout to work around a full slot.
        if (!device.acceptsRecipe().test(recipe.recipe()))
            return null;
        var additions = new ArrayList<ItemStack>();
        for (int i = 0; i < before.size(); i++)
            additions.add(ItemStack.EMPTY);
        for (int i = 0; i < layout.size(); i++)
            additions.set(slots == null ? i : slots.get(i), layout.get(i).copyWithCount(servings));
        return new Plan(device.slotStart(), before, additions);
    }

    private static List<ItemStack> aggregate(List<ItemStack> items) {
        var result = new ArrayList<ItemStack>();
        for (var stack : items) {
            if (stack.isEmpty())
                continue;
            var match = result.stream().filter(other -> ItemStack.isSameItemSameComponents(stack, other)).findFirst();
            if (match.isPresent()) {
                if ((long) match.get().getCount() + stack.getCount() > Integer.MAX_VALUE)
                    return List.of();
                match.get().grow(stack.getCount());
            } else result.add(stack.copy());
        }
        return result;
    }

    private static List<ItemStack> orderPattern(@Nullable PackageOrderWithCrafts context, int limit) {
        if (!PackageOrderWithCrafts.hasCraftingInformation(context))
            return List.of();
        var pattern = new ArrayList<ItemStack>();
        for (var entry : context.getCraftingInformation()) {
            if (entry.stack.isEmpty())
                continue;
            if (entry.count <= 0 || entry.count > limit - pattern.size())
                return List.of();
            for (int i = 0; i < entry.count; i++)
                pattern.add(entry.stack.copyWithCount(1));
        }
        return pattern;
    }

    private static int multiplier(List<ItemStack> contents, List<ItemStack> layout) {
        var required = aggregate(layout);
        if (required.isEmpty() || required.size() != contents.size())
            return 0;
        int multiplier = 0;
        for (var ingredient : required) {
            var stack = contents.stream().filter(item -> ItemStack.isSameItemSameComponents(item, ingredient)).findFirst();
            if (stack.isEmpty() || stack.get().getCount() % ingredient.getCount() != 0)
                return 0;
            int count = stack.get().getCount() / ingredient.getCount();
            if (multiplier != 0 && multiplier != count)
                return 0;
            multiplier = count;
        }
        return multiplier;
    }

    private static @Nullable List<ItemStack> units(List<ItemStack> contents, int servings, int limit) {
        var units = new ArrayList<ItemStack>();
        for (var stack : contents) {
            if (stack.getCount() % servings != 0 || stack.getCount() / servings > limit - units.size())
                return null;
            for (int i = 0; i < stack.getCount() / servings; i++)
                units.add(stack.copyWithCount(1));
        }
        return units;
    }

    private static @Nullable List<ItemStack> assign(List<Ingredient> ingredients, List<ItemStack> units) {
        if (ingredients.isEmpty() || ingredients.size() != units.size())
            return null;
        var ordered = new ItemStack[units.size()];
        // At most nine slots: memoizing used-slot subsets bounds overlapping-tag backtracking to 512 states.
        return assign(ingredients, units, ordered, 0, new boolean[1 << units.size()]) ? Arrays.asList(ordered) : null;
    }

    private static boolean assign(List<Ingredient> ingredients, List<ItemStack> units, ItemStack[] ordered,
            int used, boolean[] failed) {
        int index = Integer.bitCount(used);
        if (index == units.size())
            return true;
        if (failed[used])
            return false;
        for (int i = 0; i < units.size(); i++) {
            if ((used & (1 << i)) != 0 || !ingredients.get(index).test(units.get(i)))
                continue;
            ordered[index] = units.get(i);
            if (assign(ingredients, units, ordered, used | (1 << i), failed))
                return true;
        }
        failed[used] = true;
        return false;
    }

    /** Owns its snapshots; no mutable stack or list is exposed to callers. */
    static final class Plan {
        private final int start;
        private final List<ItemStack> before;
        private final List<ItemStack> additions;

        private Plan(int start, List<ItemStack> before, List<ItemStack> additions) {
            this.start = start;
            this.before = before.stream().map(ItemStack::copy).toList();
            this.additions = additions.stream().map(ItemStack::copy).toList();
        }

        boolean apply(IItemHandler inventory, boolean simulate) {
            for (int i = 0; i < before.size(); i++) {
                var existing = inventory.getStackInSlot(start + i);
                var addition = additions.get(i);
                if (!ItemStack.matches(existing, before.get(i)))
                    return false;
                if (addition.isEmpty())
                    continue;
                int limit = Math.min(inventory.getSlotLimit(start + i), addition.getMaxStackSize());
                if ((!existing.isEmpty() && !ItemStack.isSameItemSameComponents(existing, addition))
                        || (long) existing.getCount() + addition.getCount() > limit
                        || !inventory.isItemValid(start + i, addition)
                        || !inventory.insertItem(start + i, addition.copy(), true).isEmpty())
                    return false;
            }
            if (!simulate) {
                for (int i = 0; i < additions.size(); i++) {
                    if (!additions.get(i).isEmpty())
                        inventory.insertItem(start + i, additions.get(i).copy(), false);
                }
            }
            return true;
        }
    }
}
