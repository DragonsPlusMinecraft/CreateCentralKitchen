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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.simibubi.create.api.boiler.BoilerHeater;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import umpaz.brewinandchewin.common.block.entity.KegBlockEntity;
import vectorwing.farmersdelight.common.tag.ModTags;

@Restriction(require = @Condition(ModIntegration.Constants.BREWINANDCHEWIN))
@Debug(export = true)
@Mixin(KegBlockEntity.class)
public class KegBlockEntityMixin {
    @WrapOperation(method = "updateTemperature", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState computeBoilerHeat(Level level, BlockPos pos, Operation<BlockState> original, @Share("passiveHeat") LocalIntRef passiveHeat, @Share("activeHeat") LocalFloatRef activeHeat) {
        var state = original.call(level, pos);
        var heater = BoilerHeater.REGISTRY.get(state);
        if (heater != null) {
            float heat = heater.getHeat(level, pos, state);
            if (heat >= 0 && !state.is(ModTags.HEAT_SOURCES))
                passiveHeat.set(passiveHeat.get() + 1);
            if (heat > 0)
                activeHeat.set(activeHeat.get() + heat);
        }
        return state;
    }

    @ModifyVariable(method = "updateTemperature", at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;stream()Ljava/util/stream/Stream;", ordinal = 2), ordinal = 0)
    private int addBoilerHeat(int heat, @Share("passiveHeat") LocalIntRef passiveHeat, @Share("activeHeat") LocalFloatRef activeHeat) {
        return heat + passiveHeat.get() + (int) activeHeat.get();
    }
}
