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

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.brewinandchewin.recipe.KegPouringRecipeConverters;

@Restriction(require = @Condition(ModIntegration.Mods.BREWINANDCHEWIN))
@Mixin(GenericItemFilling.class)
public class GenericItemFillingMixin {
    @Inject(method = "canItemBeFilled", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getCapability(Lnet/neoforged/neoforge/capabilities/ItemCapability;)Ljava/lang/Object;"), cancellable = true)
    private static void canItemBeFilledByKegPouring(Level level, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (CCKConfig.recipes().convertKegPouringRecipesToFillingRecipes.get())
            KegPouringRecipeConverters.getKegFillingRecipes(level)
                    .map(RecipeHolder::value)
                    .filter(recipe -> recipe.getIngredients().getFirst().test(stack))
                    .findAny()
                    .ifPresent(it -> cir.setReturnValue(true));
    }

    @Inject(method = "getRequiredAmountForItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getCapability(Lnet/neoforged/neoforge/capabilities/ItemCapability;)Ljava/lang/Object;"), cancellable = true)
    private static void getRequiredAmountForKegPouring(Level level, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Integer> cir) {
        if (CCKConfig.recipes().convertKegPouringRecipesToFillingRecipes.get())
            KegPouringRecipeConverters.getKegFillingRecipes(level)
                    .map(RecipeHolder::value)
                    .filter(recipe -> recipe.getFluidIngredients().getFirst().test(availableFluid))
                    .filter(recipe -> recipe.getIngredients().getFirst().test(stack))
                    .findFirst()
                    .ifPresent(recipe -> cir.setReturnValue(recipe.getFluidIngredients().getFirst().amount()));
    }

    @Inject(method = "fillItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getCapability(Lnet/neoforged/neoforge/capabilities/ItemCapability;)Ljava/lang/Object;"), cancellable = true)
    private static void fillItemByKegPouring(Level level, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 1) FluidStack toFill) {
        if (CCKConfig.recipes().convertKegPouringRecipesToFillingRecipes.get())
            KegPouringRecipeConverters.getKegFillingRecipes(level)
                    .map(RecipeHolder::value)
                    .filter(recipe -> recipe.getFluidIngredients().getFirst().test(toFill))
                    .filter(recipe -> recipe.getIngredients().getFirst().test(stack))
                    .findFirst()
                    .ifPresent(recipe -> {
                        stack.shrink(1);
                        cir.setReturnValue(recipe.getRollableResultsAsItemStacks().getFirst());
                    });
    }
}
