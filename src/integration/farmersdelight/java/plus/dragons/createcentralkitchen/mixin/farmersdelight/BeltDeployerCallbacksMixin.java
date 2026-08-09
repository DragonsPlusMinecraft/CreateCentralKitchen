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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.deployer.BeltDeployerCallbacks;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.farmersdelight.recipe.CuttingBoardDeployerRecipe;

@Restriction(require = @Condition(ModIntegration.Mods.FARMERSDELIGHT))
@Mixin(BeltDeployerCallbacks.class)
public class BeltDeployerCallbacksMixin {
    @ModifyExpressionValue(method = "activate", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/deployer/ItemApplicationRecipe;shouldKeepHeldItem()Z"))
    private static boolean keepNonDamageableCuttingTool(
            boolean original,
            @Local(argsOnly = true) Recipe<?> recipe,
            @Local(name = "heldItem") ItemStack heldItem) {
        return recipe instanceof CuttingBoardDeployerRecipe cuttingRecipe
                ? cuttingRecipe.shouldKeepHeldTool(heldItem)
                : original;
    }
}
