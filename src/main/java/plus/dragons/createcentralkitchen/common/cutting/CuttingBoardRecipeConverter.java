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

package plus.dragons.createcentralkitchen.common.cutting;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerRecipeSearchEvent;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeManager.CachedCheck;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipeInput;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

@EventBusSubscriber
public class CuttingBoardRecipeConverter {
    private static final LoadingCache<Object, CachedCheck<CuttingBoardRecipeInput, CuttingBoardRecipe>> CHECK_CACHE = CacheBuilder
            .newBuilder()
            .weakKeys()
            .build(new CacheLoader<>() {
                @Override
                public CachedCheck<CuttingBoardRecipeInput, CuttingBoardRecipe> load(Object key) {
                    return RecipeManager.createCheck(ModRecipeTypes.CUTTING.get());
                }
            });

    public static RecipeHolder<CuttingRecipe> asSawing(RecipeHolder<CuttingBoardRecipe> holder) {
        CuttingBoardRecipe recipe = holder.value();
        ResourceLocation id = holder.id().withSuffix("_using_saw");
        var builder = new ProcessingRecipeBuilder<>(CuttingRecipe::new, id)
                .require(recipe.getIngredients().getFirst());
        for (var result : recipe.getRollableResults())
            builder.output(result.chance(), result.stack());
        return new RecipeHolder<>(id, builder.build());
    }

    public static RecipeHolder<DeployerApplicationRecipe> asDeploying(RecipeHolder<CuttingBoardRecipe> holder) {
        CuttingBoardRecipe recipe = holder.value();
        ResourceLocation id = holder.id().withSuffix("_using_deployer");
        var builder = new ProcessingRecipeBuilder<>(DeployerApplicationRecipe::new, id)
                .require(recipe.getIngredients().getFirst())
                .require(recipe.getTool());
        for (var result : recipe.getRollableResults())
            builder.output(result.chance(), result.stack());
        return new RecipeHolder<>(id, builder.build());
    }

    public static Optional<RecipeHolder<CuttingBoardRecipe>> findRecipe(BlockEntity blockEntity, CuttingBoardRecipeInput input) {
        var level = blockEntity.getLevel();
        assert level != null;
        return CHECK_CACHE.getUnchecked(blockEntity).getRecipeFor(input, level);
    }

    @SubscribeEvent
    public static void onDeployerRecipeSearch(final DeployerRecipeSearchEvent event) {
        if (CCKConfig.recipes().convertCuttingBoardRecipesToDeployingRecipes.get()) {
            var deployer = event.getBlockEntity();
            var inventory = event.getInventory();
            var input = new CuttingBoardRecipeInput(inventory.getItem(0), inventory.getItem(1));
            event.addRecipe(() -> findRecipe(deployer, input)
                    .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                    .map(CuttingBoardRecipeConverter::asDeploying), 50);
        }
    }
}
