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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import java.util.Optional;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.brewinandchewin.recipe.KegPouringRecipeConverters;

@Restriction(require = @Condition(ModIntegration.Mods.BREWINANDCHEWIN))
@Mixin(GenericItemEmptying.class)
public class GenericItemEmptyingMixin {
    @ModifyExpressionValue(method = "canItemBeEmptied", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/AllRecipeTypes;find(Lnet/minecraft/world/item/crafting/RecipeInput;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private static Optional<RecipeHolder<EmptyingRecipe>> canItemBeEmptiedByKegPouring(Optional<RecipeHolder<EmptyingRecipe>> original, Level level, ItemStack stack) {
        if (original.isEmpty() && CCKConfig.recipes().convertKegPouringRecipesToEmptyingRecipes.get())
            return KegPouringRecipeConverters.getKegEmptyingRecipes(level)
                    .filter(holder -> holder.value().getIngredients().getFirst().test(stack))
                    .findFirst();
        return original;
    }

    @ModifyExpressionValue(method = "emptyItem", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/AllRecipeTypes;find(Lnet/minecraft/world/item/crafting/RecipeInput;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private static Optional<RecipeHolder<EmptyingRecipe>> getRequiredAmountForKegPouring(Optional<RecipeHolder<EmptyingRecipe>> original, Level level, ItemStack stack) {
        if (original.isEmpty() && CCKConfig.recipes().convertKegPouringRecipesToEmptyingRecipes.get())
            return KegPouringRecipeConverters.getKegEmptyingRecipes(level)
                    .filter(holder -> holder.value().getIngredients().getFirst().test(stack))
                    .findFirst();
        return original;
    }
}
