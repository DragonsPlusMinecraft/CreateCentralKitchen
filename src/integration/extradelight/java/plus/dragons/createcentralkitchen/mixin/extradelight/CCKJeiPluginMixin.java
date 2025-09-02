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

package plus.dragons.createcentralkitchen.mixin.extradelight;

import com.lance5057.extradelight.ExtraDelightRecipes;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
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
import plus.dragons.createcentralkitchen.integration.extradelight.recipe.ExtraDelightRecipeConverters;
import plus.dragons.createcentralkitchen.integration.jei.CCKJeiPlugin;

@Mixin(CCKJeiPlugin.class)
public abstract class CCKJeiPluginMixin {
    @Shadow
    @Final
    public static RecipeType<RecipeHolder<BasinRecipe>> COMPACTING;

    @Shadow
    public static Level getLevel() {
        throw new AssertionError();
    }

    @Shadow
    public static RecipeManager getRecipeManager() {
        throw new AssertionError();
    }



    @Inject(method = "registerRecipes", at = @At("HEAD"))
    private void registerRecipes$extradelight(IRecipeRegistration registration, CallbackInfo ci) {
        Level level = getLevel();
        RecipeManager recipeManager = getRecipeManager();
        var mortarRecipes = recipeManager.getAllRecipesFor(ExtraDelightRecipes.MORTAR.get());
        if (CCKConfig.recipes().convertMortarGrindingRecipesToCompactingRecipes.get()) {
            registration.addRecipes(COMPACTING, mortarRecipes.stream()
                    .map(ExtraDelightRecipeConverters.GRINDING.apply(level.registryAccess()))
                    .toList());
        }
    }
}
