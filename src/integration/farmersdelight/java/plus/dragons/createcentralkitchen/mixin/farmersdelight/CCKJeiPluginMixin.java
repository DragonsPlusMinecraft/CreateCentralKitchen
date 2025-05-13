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

package plus.dragons.createcentralkitchen.mixin.farmersdelight;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.farmersdelight.recipe.CuttingBoardRecipeConverters;
import plus.dragons.createcentralkitchen.integration.jei.CCKJeiPlugin;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

@Mixin(CCKJeiPlugin.class)
public abstract class CCKJeiPluginMixin {
    @Shadow
    public static Level getLevel() {
        throw new AssertionError();
    }

    @Shadow
    public static RecipeManager getRecipeManager() {
        throw new AssertionError();
    }

    @Shadow
    @Final
    public static RecipeType<CuttingRecipe> SAWING;

    @Shadow
    @Final
    public static RecipeType<DeployerApplicationRecipe> DEPLOYING;

    @Inject(method = "registerRecipes", at = @At("HEAD"))
    private void registerRecipes$farmersdelight(IRecipeRegistration registration, CallbackInfo ci) {
        Level level = getLevel();
        RecipeManager recipeManager = getRecipeManager();
        var cuttingBoardRecipes = recipeManager.getAllRecipesFor(ModRecipeTypes.CUTTING.get());
        if (CCKConfig.recipes().convertCuttingBoardRecipesToSawingRecipes.get()) {
            ItemStack knife = new ItemStack(ModItems.IRON_KNIFE.get());
            registration.addRecipes(SAWING, cuttingBoardRecipes.stream()
                    .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                    .filter(holder -> holder.value().getTool().test(knife))
                    .map(CuttingBoardRecipeConverters.SAWING)
                    .map(RecipeHolder::value)
                    .toList());
        }
        if (CCKConfig.recipes().convertCuttingBoardRecipesToDeployingRecipes.get()) {
            registration.addRecipes(DEPLOYING, cuttingBoardRecipes.stream()
                    .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                    .map(CuttingBoardRecipeConverters.DEPLOYING)
                    .map(RecipeHolder::value)
                    .toList());
        }
    }
}
