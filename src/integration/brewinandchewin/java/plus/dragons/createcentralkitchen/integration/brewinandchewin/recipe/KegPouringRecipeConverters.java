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

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import plus.dragons.createdragonsplus.common.recipe.RecipeConverter;
import umpaz.brewinandchewin.common.crafting.CreatePotionPouringRecipe;
import umpaz.brewinandchewin.common.crafting.KegPouringRecipe;

public class KegPouringRecipeConverters {
    public static final RecipeConverter<KegPouringRecipe, FillingRecipe> FILLING = RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_as_filling");
                var container = recipe.getContainer();
                var output = recipe.getOutput();
                var fluid = (FluidStack) recipe.getFluid(output).loaderSpecific();
                var builder = new StandardProcessingRecipe.Builder<>(FillingRecipe::new, id)
                        .require(Ingredient.of(container))
                        .require(SizedFluidIngredient.of(fluid))
                        .output(output);
                return new RecipeHolder<>(id, builder.build());
            });
    public static final RecipeConverter<KegPouringRecipe, EmptyingRecipe> EMPTYING = RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_as_emptying");
                var container = recipe.getContainer();
                var output = recipe.getOutput();
                var fluid = (FluidStack) recipe.getFluid(output).loaderSpecific();
                var builder = new StandardProcessingRecipe.Builder<>(EmptyingRecipe::new, id)
                        .require(Ingredient.of(output))
                        .output(fluid);
                if (!container.isEmpty())
                    builder.output(container);
                return new RecipeHolder<>(id, builder.build());
            });

    private static final LoadingCache<RecipeManager, ConvertedRecipes> RECIPES = CacheBuilder.newBuilder()
            .weakKeys()
            .build(CacheLoader.from(KegPouringRecipeConverters::buildRecipes));

    public static Stream<RecipeHolder<FillingRecipe>> getKegFillingRecipes(Level level) {
        return getRecipes(level.getRecipeManager()).filling().stream();
    }

    public static Stream<RecipeHolder<EmptyingRecipe>> getKegEmptyingRecipes(Level level) {
        return getRecipes(level.getRecipeManager()).emptying().stream();
    }

    public static void invalidateCaches() {
        RECIPES.invalidateAll();
        RecipeConverter.CACHE_INVALIDATORS.get(FILLING).run();
        RecipeConverter.CACHE_INVALIDATORS.get(EMPTYING).run();
    }

    static ConvertedRecipes getRecipes(RecipeManager manager) {
        return RECIPES.getUnchecked(manager);
    }

    @SuppressWarnings("unchecked")
    private static ConvertedRecipes buildRecipes(RecipeManager manager) {
        var nativeFilling = new ArrayList<FillingRecipe>();
        var nativeEmptying = new ArrayList<EmptyingRecipe>();
        var kegRecipes = new ArrayList<RecipeHolder<KegPouringRecipe>>();
        // Partition once per recipe manager/reload, including when there are no Keg fallbacks.
        for (var holder : manager.getRecipes()) {
            if (holder.value() instanceof FillingRecipe recipe)
                nativeFilling.add(recipe);
            if (holder.value() instanceof EmptyingRecipe recipe)
                nativeEmptying.add(recipe);
            if (holder.value() instanceof KegPouringRecipe)
                kegRecipes.add((RecipeHolder<KegPouringRecipe>) holder);
        }

        var filling = new ArrayList<RecipeHolder<FillingRecipe>>();
        var emptying = new ArrayList<RecipeHolder<EmptyingRecipe>>();
        for (var holder : kegRecipes) {
            if (canConvertToFilling(holder) && nativeFilling.stream()
                    .noneMatch(nativeRecipe -> isEquivalentFilling(nativeRecipe, holder.value())))
                filling.add(FILLING.apply(holder));
            if (canConvertToEmptying(holder) && nativeEmptying.stream()
                    .noneMatch(nativeRecipe -> isEquivalentEmptying(nativeRecipe, holder.value())))
                emptying.add(EMPTYING.apply(holder));
        }
        return new ConvertedRecipes(List.copyOf(filling), List.copyOf(emptying));
    }

    record ConvertedRecipes(List<RecipeHolder<FillingRecipe>> filling, List<RecipeHolder<EmptyingRecipe>> emptying) {}

    static boolean isEquivalentFilling(FillingRecipe nativeRecipe, KegPouringRecipe kegRecipe) {
        if (nativeRecipe.getIngredients().size() != 1 || nativeRecipe.getFluidIngredients().size() != 1 ||
                nativeRecipe.getRollableResultsAsItemStacks().size() != 1)
            return false;
        var kegOutput = kegRecipe.getOutput();
        var kegFluid = (FluidStack) kegRecipe.getFluid(kegOutput).loaderSpecific();
        return nativeRecipe.getIngredients().getFirst().test(kegRecipe.getContainer()) &&
                ItemStack.isSameItemSameComponents(
                        nativeRecipe.getRollableResultsAsItemStacks().getFirst(), kegOutput)
                &&
                nativeRecipe.getRequiredFluid().ingredient().test(kegFluid);
    }

    static boolean isEquivalentEmptying(EmptyingRecipe nativeRecipe, KegPouringRecipe kegRecipe) {
        var nativeOutputs = nativeRecipe.getRollableResultsAsItemStacks();
        if (nativeRecipe.getIngredients().size() != 1 || nativeRecipe.getFluidResults().size() != 1 ||
                nativeOutputs.size() > 1)
            return false;
        var kegOutput = kegRecipe.getOutput();
        var kegFluid = (FluidStack) kegRecipe.getFluid(kegOutput).loaderSpecific();
        var nativeFluid = nativeRecipe.getResultingFluid();
        var nativeContainer = nativeOutputs.isEmpty() ? ItemStack.EMPTY : nativeOutputs.getFirst();
        return nativeRecipe.getIngredients().getFirst().test(kegOutput) &&
                FluidStack.isSameFluidSameComponents(nativeFluid, kegFluid) &&
                nativeFluid.getAmount() == kegFluid.getAmount() &&
                ItemStack.matches(nativeContainer, kegRecipe.getContainer());
    }

    private static boolean canConvertToFilling(RecipeHolder<KegPouringRecipe> holder) {
        if (!AllRecipeTypes.CAN_BE_AUTOMATED.test(holder))
            return false;
        var recipe = holder.value();
        if (recipe instanceof CreatePotionPouringRecipe)
            return false;
        var filled = recipe.getOutput();
        if (filled.getCapability(FluidHandler.ITEM) != null)
            return false;
        // Ignore integration recipes to avoid duplicates
        return holder.id().getNamespace()
                .equals(RegisteredObjectsHelper.getKeyOrThrow(filled.getItem()).getNamespace());
    }

    private static boolean canConvertToEmptying(RecipeHolder<KegPouringRecipe> holder) {
        if (!AllRecipeTypes.CAN_BE_AUTOMATED.test(holder))
            return false;
        KegPouringRecipe recipe = holder.value();
        if (recipe instanceof CreatePotionPouringRecipe || !recipe.canFill())
            return false;
        var filled = recipe.getOutput();
        if (filled.getCapability(FluidHandler.ITEM) != null)
            return false;
        // Ignore integration recipes to avoid duplicates
        return holder.id().getNamespace()
                .equals(RegisteredObjectsHelper.getKeyOrThrow(filled.getItem()).getNamespace());
    }
}
