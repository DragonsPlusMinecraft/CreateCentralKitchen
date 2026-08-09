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

package plus.dragons.createcentralkitchen.integration.hearthandharvest.cask;

import alabaster.hearthandharvest.common.crafting.CaskRecipe;
import alabaster.hearthandharvest.common.registry.HHModRecipeTypes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

/** Stateless, deterministic recipe selection shared by Cask automation entry points. */
public final class CaskRecipeSelection {
    public static final int INPUT_SLOTS = 4;
    public static final int OUTPUT_SLOT = 4;

    private CaskRecipeSelection() {}

    public static Optional<InsertionPlan> findInsertion(Level level, IItemHandler inventory, ItemStack stack) {
        return findInsertion(level.getRecipeManager().getAllRecipesFor(HHModRecipeTypes.AGING.get()), inventory, stack);
    }

    public static Optional<InsertionPlan> findInsertion(
            List<RecipeHolder<CaskRecipe>> recipes, IItemHandler inventory, ItemStack stack) {
        if (stack.isEmpty() || stack.getCount() <= 0)
            return Optional.empty();

        var current = new ArrayList<ItemStack>(INPUT_SLOTS);
        var emptySlots = new ArrayList<Integer>(INPUT_SLOTS);
        for (int slot = 0; slot < INPUT_SLOTS; slot++) {
            var existing = inventory.getStackInSlot(slot);
            if (existing.isEmpty())
                emptySlots.add(slot);
            else current.add(existing);
        }
        if (emptySlots.isEmpty())
            return Optional.empty();

        for (var holder : sorted(recipes)) {
            var recipe = holder.value();
            var ingredients = recipe.getIngredients();
            if (current.size() >= ingredients.size() || !canAcceptOutput(inventory, recipe.getOutput()))
                continue;

            int maximum = Math.min(
                    Math.min(stack.getCount(), emptySlots.size()), ingredients.size() - current.size());
            for (int amount = maximum; amount > 0; amount--) {
                var proposed = new ArrayList<>(current);
                for (int count = 0; count < amount; count++)
                    proposed.add(stack.copyWithCount(1));
                if (canAssign(proposed, ingredients))
                    return Optional.of(new InsertionPlan(
                            holder, List.copyOf(emptySlots.subList(0, amount))));
            }
        }
        return Optional.empty();
    }

    public static Optional<UnpackingPlan> findUnpacking(
            Level level, IItemHandler inventory, List<ItemStack> packagedItems) {
        return findUnpacking(
                level.getRecipeManager().getAllRecipesFor(HHModRecipeTypes.AGING.get()),
                inventory,
                packagedItems);
    }

    public static Optional<UnpackingPlan> findUnpacking(
            List<RecipeHolder<CaskRecipe>> recipes,
            IItemHandler inventory,
            List<ItemStack> packagedItems) {
        for (int slot = 0; slot < INPUT_SLOTS; slot++) {
            if (!inventory.getStackInSlot(slot).isEmpty())
                return Optional.empty();
        }

        var units = expand(packagedItems);
        if (units.isEmpty())
            return Optional.empty();

        for (var holder : sorted(recipes)) {
            var recipe = holder.value();
            if (recipe.getIngredients().size() != units.size()
                    || !canAcceptOutput(inventory, recipe.getOutput()))
                continue;
            var ordered = assignInRecipeOrder(units, recipe.getIngredients());
            if (ordered.isPresent())
                return Optional.of(new UnpackingPlan(holder, ordered.get()));
        }
        return Optional.empty();
    }

    private static List<RecipeHolder<CaskRecipe>> sorted(List<RecipeHolder<CaskRecipe>> recipes) {
        return recipes.stream()
                .sorted(Comparator.comparing(holder -> holder.id().toString()))
                .toList();
    }

    private static List<ItemStack> expand(List<ItemStack> stacks) {
        var units = new ArrayList<ItemStack>(INPUT_SLOTS);
        for (var stack : stacks) {
            if (stack.isEmpty())
                continue;
            if (stack.getCount() > INPUT_SLOTS - units.size())
                return List.of();
            for (int count = 0; count < stack.getCount(); count++)
                units.add(stack.copyWithCount(1));
        }
        return units;
    }

    private static boolean canAssign(List<ItemStack> stacks, List<Ingredient> ingredients) {
        return assign(stacks, ingredients, 0, new boolean[ingredients.size()]);
    }

    private static boolean assign(
            List<ItemStack> stacks, List<Ingredient> ingredients, int stackIndex, boolean[] usedIngredients) {
        if (stackIndex == stacks.size())
            return true;
        var stack = stacks.get(stackIndex);
        for (int ingredientIndex = 0; ingredientIndex < ingredients.size(); ingredientIndex++) {
            if (usedIngredients[ingredientIndex] || !ingredients.get(ingredientIndex).test(stack))
                continue;
            usedIngredients[ingredientIndex] = true;
            if (assign(stacks, ingredients, stackIndex + 1, usedIngredients))
                return true;
            usedIngredients[ingredientIndex] = false;
        }
        return false;
    }

    private static Optional<List<ItemStack>> assignInRecipeOrder(
            List<ItemStack> stacks, List<Ingredient> ingredients) {
        var ordered = new ItemStack[ingredients.size()];
        if (!assignInRecipeOrder(stacks, ingredients, 0, new boolean[stacks.size()], ordered))
            return Optional.empty();
        return Optional.of(Arrays.stream(ordered).map(ItemStack::copy).toList());
    }

    private static boolean assignInRecipeOrder(
            List<ItemStack> stacks,
            List<Ingredient> ingredients,
            int ingredientIndex,
            boolean[] usedStacks,
            ItemStack[] ordered) {
        if (ingredientIndex == ingredients.size())
            return true;
        var ingredient = ingredients.get(ingredientIndex);
        for (int stackIndex = 0; stackIndex < stacks.size(); stackIndex++) {
            if (usedStacks[stackIndex] || !ingredient.test(stacks.get(stackIndex)))
                continue;
            usedStacks[stackIndex] = true;
            ordered[ingredientIndex] = stacks.get(stackIndex);
            if (assignInRecipeOrder(stacks, ingredients, ingredientIndex + 1, usedStacks, ordered))
                return true;
            usedStacks[stackIndex] = false;
        }
        return false;
    }

    private static boolean canAcceptOutput(IItemHandler inventory, ItemStack output) {
        if (output.isEmpty())
            return false;
        var existing = inventory.getStackInSlot(OUTPUT_SLOT);
        int limit = Math.min(inventory.getSlotLimit(OUTPUT_SLOT), output.getMaxStackSize());
        if (existing.isEmpty())
            return output.getCount() <= limit;
        return ItemStack.isSameItemSameComponents(existing, output)
                && existing.getCount() + output.getCount() <= limit;
    }

    public record InsertionPlan(RecipeHolder<CaskRecipe> recipe, List<Integer> targetSlots) {}

    public record UnpackingPlan(RecipeHolder<CaskRecipe> recipe, List<ItemStack> ingredients) {}
}
