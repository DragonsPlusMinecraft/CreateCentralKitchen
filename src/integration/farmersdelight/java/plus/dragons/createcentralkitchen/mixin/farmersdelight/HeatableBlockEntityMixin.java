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

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.api.boiler.BoilerHeater;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;

@Restriction(require = @Condition(ModIntegration.Mods.FARMERSDELIGHT))
@Mixin(value = HeatableBlockEntity.class, remap = false)
public interface HeatableBlockEntityMixin {
    @Inject(method = "isHeated", at = @At(value = "FIELD", target = "Lvectorwing/farmersdelight/common/tag/ModTags$Blocks;HEAT_SOURCES:Lnet/minecraft/tags/TagKey;", ordinal = 0), cancellable = true)
    private void isHeatedByBoilerHeaterBelow(Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) BlockState stateBelow) {
        BoilerHeater heater = BoilerHeater.REGISTRY.get(stateBelow);
        if (heater != null)
            cir.setReturnValue(heater.getHeat(level, pos.below(), stateBelow) >= 0);
    }

    @Inject(method = "isHeated", at = @At(value = "FIELD", target = "Lvectorwing/farmersdelight/common/tag/ModTags$Blocks;HEAT_SOURCES:Lnet/minecraft/tags/TagKey;", ordinal = 1), cancellable = true)
    private void isHeatedByBoilerHeaterFurtherBelow(Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 1) BlockState stateFurtherBelow) {
        BoilerHeater heater = BoilerHeater.REGISTRY.get(stateFurtherBelow);
        if (heater != null)
            cir.setReturnValue(heater.getHeat(level, pos.below(2), stateFurtherBelow) >= 0);
    }
}
