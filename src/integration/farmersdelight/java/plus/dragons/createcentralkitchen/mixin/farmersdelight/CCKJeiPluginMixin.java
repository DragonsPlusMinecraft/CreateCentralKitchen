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

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.DeployingCategory;
import com.simibubi.create.compat.jei.category.SawingCategory;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.farmersdelight.recipe.CuttingBoardRecipeConverters;
import plus.dragons.createcentralkitchen.integration.jei.CCKJeiPlugin;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

@Restriction(require = @Condition(ModIntegration.Mods.FARMERSDELIGHT))
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

    @Unique
    private final static RecipeType<RecipeHolder<CuttingRecipe>> SAWING = RecipeType
            .createRecipeHolderType(CCKCommon.asResource("farmersdelight.automatic_food_sawing"));

    @Unique
    private final static RecipeType<RecipeHolder<DeployerApplicationRecipe>> DEPLOYING = RecipeType
            .createRecipeHolderType(CCKCommon.asResource("farmersdelight.automatic_food_cutting"));

    @Inject(method = "loadCategories", at = @At("RETURN"))
    private void loadCategories$extradelight(CallbackInfo ci) {
        CreateRecipeCategory<?> automaticFoodSawing = ((CCKJeiPlugin) (Object) this).builder(CuttingRecipe.class)
                .addTypedRecipes(AllRecipeTypes.CUTTING)
                .catalyst(AllBlocks.MECHANICAL_SAW::get)
                .doubleItemIcon(AllBlocks.MECHANICAL_SAW.get(), ModItems.CUTTING_BOARD.get())
                .emptyBackground(177, 70)
                .build(CCKCommon.asResource("farmersdelight.automatic_food_sawing"), SawingCategory::new);

        CreateRecipeCategory<?> automaticFoodCutting = ((CCKJeiPlugin) (Object) this).builder(DeployerApplicationRecipe.class)
                .addTypedRecipes(AllRecipeTypes.DEPLOYING)
                .removeNonAutomation()
                .catalyst(AllBlocks.DEPLOYER::get)
                .catalyst(AllBlocks.DEPOT::get)
                .catalyst(AllItems.BELT_CONNECTOR::get)
                .doubleItemIcon(AllBlocks.DEPLOYER.get(), ModItems.CUTTING_BOARD.get())
                .emptyBackground(177, 70)
                .build(CCKCommon.asResource("farmersdelight.automatic_food_cutting"), DeployingCategory::new);
    }

    @Inject(method = "registerRecipes", at = @At("HEAD"))
    private void registerRecipes$farmersdelight(IRecipeRegistration registration, CallbackInfo ci) {
        Level level = getLevel();
        RecipeManager recipeManager = getRecipeManager();
        var cuttingBoardRecipes = recipeManager.getAllRecipesFor(ModRecipeTypes.CUTTING.get());
        if (CCKConfig.recipes().convertCuttingBoardRecipesToSawingRecipes.get()) {
            registration.addRecipes(SAWING, cuttingBoardRecipes.stream()
                    .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                    .filter(CuttingBoardRecipeConverters::canSaw)
                    .map(CuttingBoardRecipeConverters.SAWING)
                    .toList());
        }
        if (CCKConfig.recipes().convertCuttingBoardRecipesToDeployingRecipes.get()) {
            registration.addRecipes(DEPLOYING, cuttingBoardRecipes.stream()
                    .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                    .map(CuttingBoardRecipeConverters.DEPLOYING)
                    .toList());
        }
    }
}
