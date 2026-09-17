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

package plus.dragons.createcentralkitchen.integration.brewinandchewin.recipe;

import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.spout.FillingBySpout;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import umpaz.brewinandchewin.common.crafting.KegPouringRecipe;

@GameTestHolder(CCKCommon.ID)
@PrefixGameTestTemplate(false)
public class KegPouringRecipeConvertersGameTests {
    private static final ResourceLocation HNH_KEG_RECIPE = ResourceLocation.fromNamespaceAndPath("hearthandharvest", "integration/brewinandchewin/pouring/blueberry_wine");
    private static final ResourceLocation HNH_FILLING_RECIPE = ResourceLocation.fromNamespaceAndPath("hearthandharvest", "integration/create/filling/blueberry_wine");
    private static final ResourceLocation BNC_KEG_RECIPE = ResourceLocation.fromNamespaceAndPath("brewinandchewin", "pouring/beer");

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void nativeFillingRecipesSuppressKegFallbacks(GameTestHelper helper) {
        if (!ModList.get().isLoaded("hearthandharvest")) {
            helper.succeed();
            return;
        }
        var level = helper.getLevel();
        var kegRecipe = getRecipe(helper, HNH_KEG_RECIPE, KegPouringRecipe.class);
        var nativeRecipe = getRecipe(helper, HNH_FILLING_RECIPE, FillingRecipe.class);
        var kegFluid = (FluidStack) kegRecipe.getFluid(kegRecipe.getOutput()).loaderSpecific();
        int nativeAmount = nativeRecipe.getRequiredFluid().amount();
        helper.assertTrue(nativeAmount != kegFluid.getAmount(), "The regression pair must use different fluid amounts");
        assertFillingFallbackHidden(helper, HNH_KEG_RECIPE);

        var availableFluid = kegFluid.copy();
        availableFluid.setAmount(1000);
        var container = kegRecipe.getContainer().copy();
        int requiredAmount = FillingBySpout.getRequiredAmountForItem(level, container, availableFluid);
        helper.assertValueEqual(requiredAmount, nativeAmount, "The Spout did not prefer the native fluid amount");
        var filled = FillingBySpout.fillItem(level, requiredAmount, container, availableFluid);
        helper.assertTrue(
                ItemStack.isSameItemSameComponents(filled, kegRecipe.getOutput()),
                "The native Filling recipe produced the wrong item");
        helper.assertValueEqual(
                availableFluid.getAmount(), 1000 - nativeAmount, "The Spout consumed the Keg fallback amount");

        var genericFluid = kegFluid.copy();
        genericFluid.setAmount(1000);
        helper.assertValueEqual(
                GenericItemFilling.getRequiredAmountForItem(
                        level, kegRecipe.getContainer().copy(), genericFluid),
                -1,
                "Generic filling still exposed the suppressed Keg fallback");

        KegPouringRecipeConverters.invalidateCaches();
        assertFillingFallbackHidden(helper, HNH_KEG_RECIPE);
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void emptyingEquivalenceIncludesAmountAndContainer(GameTestHelper helper) {
        var kegRecipe = getRecipe(helper, BNC_KEG_RECIPE, KegPouringRecipe.class);
        var kegOutput = kegRecipe.getOutput();
        var kegFluid = (FluidStack) kegRecipe.getFluid(kegOutput).loaderSpecific();
        var matching = emptyingRecipe("matching_emptying", kegOutput, kegFluid, kegRecipe.getContainer());
        helper.assertTrue(
                KegPouringRecipeConverters.isEquivalentEmptying(matching, kegRecipe),
                "An equivalent native Emptying recipe was not recognized");

        var wrongAmount = kegFluid.copy();
        wrongAmount.grow(1);
        helper.assertTrue(
                !KegPouringRecipeConverters.isEquivalentEmptying(
                        emptyingRecipe("wrong_amount", kegOutput, wrongAmount, kegRecipe.getContainer()), kegRecipe),
                "An Emptying recipe with a different fluid amount was treated as equivalent");
        helper.assertTrue(
                !KegPouringRecipeConverters.isEquivalentEmptying(
                        emptyingRecipe("wrong_container", kegOutput, kegFluid, ItemStack.EMPTY), kegRecipe),
                "An Emptying recipe with a different container result was treated as equivalent");
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void repeatedLookupsReuseCompleteRecipeSnapshot(GameTestHelper helper) {
        var manager = new CountingRecipeManager(helper.getLevel().registryAccess());
        manager.replaceRecipes(List.of(helper.getLevel().getRecipeManager().byKey(BNC_KEG_RECIPE).orElseThrow()));
        for (int i = 0; i < 1000; i++) {
            var recipes = KegPouringRecipeConverters.getRecipes(manager);
            helper.assertValueEqual(recipes.filling().size(), 1, "The beer Filling fallback disappeared");
            helper.assertValueEqual(recipes.emptying().size(), 1, "The beer Emptying fallback disappeared");
        }
        helper.assertValueEqual(manager.reads, 1, "Repeated lookups rescanned the recipe manager");
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void cachedKegFallbackPreservesFillingAndEmptyingResults(GameTestHelper helper) {
        var level = helper.getLevel();
        var keg = getRecipe(helper, BNC_KEG_RECIPE, KegPouringRecipe.class);
        var expectedFluid = (FluidStack) keg.getFluid(keg.getOutput()).loaderSpecific();
        for (int i = 0; i < 2; i++) {
            var containers = keg.getContainer().copyWithCount(2);
            var availableFluid = expectedFluid.copy();
            availableFluid.grow(123);
            int amount = GenericItemFilling.getRequiredAmountForItem(level, containers, availableFluid);
            helper.assertValueEqual(amount, expectedFluid.getAmount(), "The cached Filling fallback changed the required fluid amount");
            var filled = GenericItemFilling.fillItem(level, amount, containers, availableFluid);
            helper.assertTrue(ItemStack.matches(filled, keg.getOutput()), "The cached Filling fallback changed the output or its components");
            helper.assertValueEqual(containers.getCount(), 1, "Filling did not consume exactly one container");
            helper.assertValueEqual(availableFluid.getAmount(), 123, "Filling consumed the wrong fluid amount");

            var input = filled.copyWithCount(2);
            helper.assertTrue(GenericItemEmptying.canItemBeEmptied(level, input), "The cached Emptying fallback was unavailable");
            for (boolean simulate : List.of(true, false)) {
                var emptied = GenericItemEmptying.emptyItem(level, input, simulate);
                helper.assertTrue(FluidStack.matches(emptied.getFirst(), expectedFluid), "Emptying changed the fluid, amount, or components");
                helper.assertTrue(ItemStack.matches(emptied.getSecond(), keg.getContainer()), "Emptying returned the wrong container");
                helper.assertValueEqual(input.getCount(), simulate ? 2 : 1, "Emptying changed the input count incorrectly");
            }
        }
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void emptyResultsAreCachedAndManagersAreIsolated(GameTestHelper helper) {
        var emptyManager = new CountingRecipeManager(helper.getLevel().registryAccess());
        var beerManager = new CountingRecipeManager(helper.getLevel().registryAccess());
        beerManager.replaceRecipes(List.of(helper.getLevel().getRecipeManager().byKey(BNC_KEG_RECIPE).orElseThrow()));
        for (int i = 0; i < 1000; i++) {
            var empty = KegPouringRecipeConverters.getRecipes(emptyManager);
            helper.assertTrue(empty.filling().isEmpty() && empty.emptying().isEmpty(), "Another manager's recipes leaked into an empty manager");
            helper.assertValueEqual(KegPouringRecipeConverters.getRecipes(beerManager).filling().size(), 1, "The nonempty manager reused an empty result");
        }
        helper.assertValueEqual(emptyManager.reads, 1, "An empty recipe snapshot was repeatedly rebuilt");
        helper.assertValueEqual(beerManager.reads, 1, "The nonempty manager was repeatedly scanned");
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void invalidationReevaluatesNativeFillingAndEmptyingRecipes(GameTestHelper helper) {
        var holder = helper.getLevel().getRecipeManager().byKey(BNC_KEG_RECIPE).orElseThrow();
        var keg = (KegPouringRecipe) holder.value();
        var manager = new CountingRecipeManager(helper.getLevel().registryAccess());
        manager.replaceRecipes(List.of(holder));
        helper.assertValueEqual(KegPouringRecipeConverters.getRecipes(manager).filling().size(), 1, "Missing initial Filling fallback");
        var fluid = (FluidStack) keg.getFluid(keg.getOutput()).loaderSpecific();
        var fillingId = ResourceLocation.fromNamespaceAndPath(CCKCommon.ID, "cache_test_filling");
        var nativeFluid = fluid.copy();
        nativeFluid.grow(1);
        var filling = new StandardProcessingRecipe.Builder<>(FillingRecipe::new, fillingId)
                .require(Ingredient.of(keg.getContainer()))
                .require(SizedFluidIngredient.of(nativeFluid))
                .output(keg.getOutput()).build();
        var emptyingId = ResourceLocation.fromNamespaceAndPath(CCKCommon.ID, "cache_test_emptying");
        manager.replaceRecipes(List.of(holder, new RecipeHolder<>(fillingId, filling),
                new RecipeHolder<>(emptyingId, emptyingRecipe("cache_test_emptying", keg.getOutput(), fluid, keg.getContainer()))));
        KegPouringRecipeConverters.invalidateCaches();
        var suppressed = KegPouringRecipeConverters.getRecipes(manager);
        helper.assertTrue(suppressed.filling().isEmpty(), "Reload did not suppress the Filling fallback with a different native amount");
        helper.assertTrue(suppressed.emptying().isEmpty(), "Reload did not suppress the equivalent Emptying fallback");
        helper.assertValueEqual(manager.reads, 2, "Reload did not rebuild the snapshot exactly once");

        manager.replaceRecipes(List.of(holder));
        KegPouringRecipeConverters.invalidateCaches();
        var restored = KegPouringRecipeConverters.getRecipes(manager);
        helper.assertValueEqual(restored.filling().size(), 1, "Removing the native Filling recipe did not restore the fallback");
        helper.assertValueEqual(restored.emptying().size(), 1, "Removing the native Emptying recipe did not restore the fallback");
        helper.assertValueEqual(manager.reads, 3, "Removing native recipes did not rebuild the snapshot exactly once");
        helper.succeed();
    }

    private static class CountingRecipeManager extends RecipeManager {
        private int reads;

        private CountingRecipeManager(HolderLookup.Provider registries) {
            super(registries);
        }

        @Override
        public Collection<RecipeHolder<?>> getRecipes() {
            reads++;
            return super.getRecipes();
        }
    }

    private static EmptyingRecipe emptyingRecipe(
            String path, ItemStack input, FluidStack fluid, ItemStack container) {
        var builder = new StandardProcessingRecipe.Builder<>(
                EmptyingRecipe::new, ResourceLocation.fromNamespaceAndPath(CCKCommon.ID, path))
                        .require(Ingredient.of(input))
                        .output(fluid);
        if (!container.isEmpty())
            builder.output(container);
        return builder.build();
    }

    private static void assertFillingFallbackHidden(GameTestHelper helper, ResourceLocation kegRecipeId) {
        helper.assertTrue(
                KegPouringRecipeConverters.getKegFillingRecipes(helper.getLevel())
                        .noneMatch(holder -> holder.id().equals(kegRecipeId.withSuffix("_as_filling"))),
                "An equivalent Keg Filling fallback remained visible to GenericItem Filling and JEI");
    }

    private static <T> T getRecipe(GameTestHelper helper, ResourceLocation id, Class<T> type) {
        RecipeHolder<?> holder = helper.getLevel()
                .getRecipeManager()
                .byKey(id)
                .orElseThrow(() -> new IllegalStateException("Missing test recipe " + id));
        helper.assertTrue(type.isInstance(holder.value()), "Test recipe has the wrong type: " + id);
        return type.cast(holder.value());
    }
}
