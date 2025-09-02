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

import com.lance5057.extradelight.ExtraDelightBlocks;
import com.lance5057.extradelight.ExtraDelightRecipes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.MixingCategory;
import com.simibubi.create.compat.jei.category.PackingCategory;
import com.simibubi.create.compat.jei.category.PressingCategory;
import com.simibubi.create.content.processing.basin.BasinRecipe;
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
import plus.dragons.createcentralkitchen.integration.extradelight.recipe.ExtraDelightRecipeConverters;
import plus.dragons.createcentralkitchen.integration.jei.CCKJeiPlugin;

@Mixin(CCKJeiPlugin.class)
public abstract class CCKJeiPluginMixin {

    @Unique
    private static final RecipeType<RecipeHolder<BasinRecipe>> AUTOMATIC_MORTAR_GRINDING = RecipeType
            .createRecipeHolderType(CCKCommon.asResource("automatic_mortar_grinding"));

    @Shadow
    public static Level getLevel() {
        throw new AssertionError();
    }

    @Shadow
    public static RecipeManager getRecipeManager() {
        throw new AssertionError();
    }


    @Inject(method = "loadCategories", at = @At("RETURN"))
    private void loadCategories$extradelight(CallbackInfo ci) {
        CreateRecipeCategory<?> automaticMortarGrinding = ((CCKJeiPlugin) (Object) this).builder(BasinRecipe.class)
                .addTypedRecipes(AllRecipeTypes.PRESSING)
                .catalyst(AllBlocks.MECHANICAL_PRESS::get)
                .catalyst(AllBlocks.BASIN::get)
                .doubleItemIcon(AllBlocks.MECHANICAL_PRESS.get(), ExtraDelightBlocks.MORTAR_STONE.get())
                .emptyBackground(177, 103)
                .build(CCKCommon.asResource("automatic_mortar_grinding"), PackingCategory::standard);
    }

    @Inject(method = "registerRecipes", at = @At("HEAD"))
    private void registerRecipes$extradelight(IRecipeRegistration registration, CallbackInfo ci) {
        Level level = getLevel();
        RecipeManager recipeManager = getRecipeManager();
        var mortarRecipes = recipeManager.getAllRecipesFor(ExtraDelightRecipes.MORTAR.get());
        if (CCKConfig.recipes().convertMortarGrindingRecipesToCompactingRecipes.get()) {
            registration.addRecipes(AUTOMATIC_MORTAR_GRINDING, mortarRecipes.stream()
                    .map(ExtraDelightRecipeConverters.AUTOMATIC_MORTAR_GRINDING.apply(level.registryAccess()))
                    .toList());
        }
    }
}
