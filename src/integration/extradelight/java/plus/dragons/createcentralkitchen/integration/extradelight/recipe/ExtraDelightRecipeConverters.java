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

package plus.dragons.createcentralkitchen.integration.extradelight.recipe;

import com.google.common.cache.CacheBuilder;
import com.lance5057.extradelight.workstations.juicer.JuicerRecipe;
import com.lance5057.extradelight.workstations.mortar.recipes.MortarRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.RecipeHolder;
import plus.dragons.createdragonsplus.common.recipe.RecipeConverter;

import java.util.function.Function;

public class ExtraDelightRecipeConverters {
    public static final Function<HolderLookup.Provider, RecipeConverter<MortarRecipe, BasinRecipe>> AUTOMATIC_MORTAR_GRINDING = registry -> RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_using_press");
                var builder = new StandardProcessingRecipe.Builder<>(BasinRecipe::new, id)
                        .require(recipe.getIngredients().getFirst());
                var result = recipe.getResultItem(registry);
                if(!result.isEmpty())
                    builder.output(result);
                var fluid = recipe.getFluid();
                if(!fluid.isEmpty())
                    builder.output(fluid);
                return new RecipeHolder<>(id, builder.build());
            });

    // WIP TODO
    public static final Function<HolderLookup.Provider, RecipeConverter<JuicerRecipe, BasinRecipe>> JUICING = registry -> RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_using_press");
                var builder = new StandardProcessingRecipe.Builder<>(BasinRecipe::new, id)
                        .require(recipe.getIngredients().getFirst());
                var result = recipe.getResultItem(registry);
                if(!result.isEmpty())
                    builder.output(result);
                var fluid = recipe.getFluid();
                if(!fluid.isEmpty())
                    builder.output(fluid);
                return new RecipeHolder<>(id, builder.build());
            });
}
