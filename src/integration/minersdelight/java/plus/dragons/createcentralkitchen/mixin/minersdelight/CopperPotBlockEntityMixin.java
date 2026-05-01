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

package plus.dragons.createcentralkitchen.mixin.minersdelight;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.sammy.minersdelight.content.block.copper_pot.CopperPotBlockEntity;
import com.simibubi.create.api.boiler.BoilerHeater;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.tag.ModTags;

@Restriction(require = @Condition(ModIntegration.Mods.MINERSDELIGHT))
@Mixin(CopperPotBlockEntity.class)
public class CopperPotBlockEntityMixin {
    @ModifyExpressionValue(method = "processCooking", at = @At(value = "FIELD", target = "Lcom/sammy/minersdelight/content/block/copper_pot/CopperPotBlockEntity;cookTime:I", ordinal = 0))
    private int speedUpCooking(int cookTime, RecipeHolder<CookingPotRecipe> recipe, CopperPotBlockEntity copperPot) {
        var level = copperPot.getLevel();
        assert level != null;
        var pos = copperPot.getBlockPos();
        var heaterPos = pos.below();
        var heaterState = level.getBlockState(heaterPos);
        BoilerHeater heater = BoilerHeater.REGISTRY.get(heaterState);
        if (heater == null && !copperPot.requiresDirectHeat() && heaterState.is(ModTags.Blocks.HEAT_CONDUCTORS)) {
            heaterPos = pos.below(2);
            heaterState = level.getBlockState(pos.below(2));
            heater = BoilerHeater.REGISTRY.get(heaterState);
        }
        if (!heaterState.is(ModTags.Blocks.HEAT_SOURCES))
            return cookTime;
        if (heater != null) {
            float heat = heater.getHeat(level, heaterPos, heaterState);
            if (heat > 0)
                cookTime = cookTime + (int) heat;
        }
        return cookTime;
    }
}
