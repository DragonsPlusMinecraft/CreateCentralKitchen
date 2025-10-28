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
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import java.util.stream.Stream;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
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

    @SuppressWarnings("unchecked")
    public static Stream<RecipeHolder<FillingRecipe>> getKegFillingRecipes(Level level) {
        return RecipeFinder
                .get(FILLING, level, holder -> holder.value() instanceof KegPouringRecipe &&
                        canConvertToFilling((RecipeHolder<KegPouringRecipe>) holder))
                .stream()
                .map(holder -> (RecipeHolder<KegPouringRecipe>) holder)
                .map(FILLING);
    }

    @SuppressWarnings("unchecked")
    public static Stream<RecipeHolder<EmptyingRecipe>> getKegEmptyingRecipes(Level level) {
        return RecipeFinder
                .get(EMPTYING, level, holder -> holder.value() instanceof KegPouringRecipe &&
                        canConvertToEmptying((RecipeHolder<KegPouringRecipe>) holder))
                .stream()
                .map(holder -> (RecipeHolder<KegPouringRecipe>) holder)
                .map(EMPTYING);
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
