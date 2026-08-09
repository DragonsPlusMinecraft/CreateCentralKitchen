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
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createdragonsplus.common.recipe.RecipeConverter;
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

        RecipeFinder.LISTENER.onResourceManagerReload(null);
        var invalidator = RecipeConverter.CACHE_INVALIDATORS.get(KegPouringRecipeConverters.FILLING);
        if (invalidator != null)
            invalidator.run();
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
