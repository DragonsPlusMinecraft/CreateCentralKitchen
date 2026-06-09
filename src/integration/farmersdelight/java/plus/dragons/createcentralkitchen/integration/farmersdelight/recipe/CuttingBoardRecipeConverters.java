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

package plus.dragons.createcentralkitchen.integration.farmersdelight.recipe;

import com.google.common.cache.CacheBuilder;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerRecipeSearchEvent;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createdragonsplus.common.recipe.RecipeConverter;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipeInput;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

public class CuttingBoardRecipeConverters {
    public static final RecipeConverter<CuttingBoardRecipe, CuttingRecipe> SAWING = RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_using_saw");
                var builder = new StandardProcessingRecipe.Builder<>(CuttingRecipe::new, id)
                        .require(recipe.getIngredients().getFirst());
                for (var result : recipe.getRollableResults())
                    builder.output(result.chance(), result.stack());
                return new RecipeHolder<>(id, builder.build());
            });
    public static final RecipeConverter<CuttingBoardRecipe, DeployerApplicationRecipe> DEPLOYING = RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_using_deployer");
                var builder = new ItemApplicationRecipe.Builder<>(DeployerApplicationRecipe::new, id)
                        .require(recipe.getIngredients().getFirst())
                        .require(recipe.getTool());
                for (var result : recipe.getRollableResults())
                    builder.output(result.chance(), result.stack());
                return new RecipeHolder<>(id, builder.build());
            });

    public static ItemStack sawAsKnife() {
        return new ItemStack(ModItems.IRON_KNIFE.get());
    }

    public static CuttingBoardRecipeInput sawInput(ItemStack input) {
        return new CuttingBoardRecipeInput(input, sawAsKnife());
    }

    public static boolean canSaw(RecipeHolder<CuttingBoardRecipe> holder) {
        return holder.value().getTool().test(sawAsKnife());
    }

    @SubscribeEvent
    public static void onDeployerRecipeSearch(final DeployerRecipeSearchEvent event) {
        if (CCKConfig.recipes().convertCuttingBoardRecipesToDeployingRecipes.get()) {
            var deployer = event.getBlockEntity();
            var inventory = event.getInventory();
            var level = deployer.getLevel();
            assert level != null;
            event.addRecipe(() -> level.getRecipeManager()
                    .getAllRecipesFor(ModRecipeTypes.CUTTING.get())
                    .stream()
                    .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                    .map(CuttingBoardRecipeConverters.DEPLOYING)
                    .filter(holder -> holder.value().matches(inventory, level))
                    .findFirst(), 50);
        }
    }
}
