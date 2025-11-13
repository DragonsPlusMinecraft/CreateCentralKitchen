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

package plus.dragons.createcentralkitchen.mixin.brewinandchewin;

import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.brewinandchewin.recipe.KegPouringRecipeConverters;
import plus.dragons.createcentralkitchen.integration.jei.CCKJeiPlugin;

@Restriction(require = @Condition(ModIntegration.Mods.BREWINANDCHEWIN))
@Mixin(CCKJeiPlugin.class)
public abstract class CCKJeiPluginMixin {
    @Shadow
    @Final
    public static RecipeType<RecipeHolder<FillingRecipe>> SPOUT_FILLING;

    @Shadow
    @Final
    public static RecipeType<RecipeHolder<EmptyingRecipe>> DRAINING;

    @Shadow
    public static Level getLevel() {
        throw new AssertionError();
    }

    @Inject(method = "registerRecipes", at = @At("HEAD"))
    private void registerRecipes$brewinandchewin(IRecipeRegistration registration, CallbackInfo ci) {
        Level level = getLevel();
        if (CCKConfig.recipes().convertKegPouringRecipesToFillingRecipes.get()) {
            registration.addRecipes(SPOUT_FILLING, KegPouringRecipeConverters
                    .getKegFillingRecipes(level)
                    .toList());
        }
        if (CCKConfig.recipes().convertKegPouringRecipesToEmptyingRecipes.get()) {
            registration.addRecipes(DRAINING, KegPouringRecipeConverters
                    .getKegEmptyingRecipes(level)
                    .toList());
        }
    }
}
