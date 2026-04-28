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
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createdragonsplus.common.processing.freeze.BlockFreezer;
import umpaz.brewinandchewin.common.block.entity.KegBlockEntity;
import umpaz.brewinandchewin.common.tag.BnCTags;
import vectorwing.farmersdelight.common.tag.ModTags;

@Restriction(require = @Condition(ModIntegration.Mods.BREWINANDCHEWIN))
@Mixin(KegBlockEntity.class)
public class KegBlockEntityMixin {
    @WrapOperation(method = "updateTemperature", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState computeBoilerHeatAndBlockFreeze(Level level, BlockPos pos, Operation<BlockState> original,
            @Share("passiveHeat") LocalIntRef passiveHeat, @Share("activeHeat") LocalIntRef activeHeat,
            @Share("passiveFreeze") LocalIntRef passiveFreeze, @Share("activeFreeze") LocalIntRef activeFreeze) {
        var state = original.call(level, pos);
        float heat = BoilerHeater.findHeat(level, pos, state);
        if (heat >= 0 && !state.is(ModTags.Blocks.HEAT_SOURCES))
            passiveHeat.set(passiveHeat.get() + 1);
        if (heat > 0)
            activeHeat.set(activeHeat.get() + (int) heat);
        float freeze = BlockFreezer.findFreeze(level, pos, state);
        if (freeze >= 0 && !state.is(BnCTags.Blocks.FREEZE_SOURCES))
            passiveFreeze.set(passiveFreeze.get() + 1);
        if (freeze > 0)
            activeFreeze.set(activeFreeze.get() + (int) freeze);
        return state;
    }

    @ModifyExpressionValue(method = "updateTemperature", at = @At(value = "INVOKE", target = "Ljava/util/stream/IntStream;sum()I", ordinal = 1))
    private int addBoilerHeat(int heat, @Share("passiveHeat") LocalIntRef passiveHeat, @Share("activeHeat") LocalIntRef activeHeat) {
        return heat + passiveHeat.get() + activeHeat.get();
    }

    @ModifyExpressionValue(method = "updateTemperature", at = @At(value = "INVOKE", target = "Ljava/util/stream/IntStream;sum()I", ordinal = 3))
    private int addBlockFreeze(int freeze, @Share("passiveFreeze") LocalIntRef passiveFreeze, @Share("activeFreeze") LocalIntRef activeFreeze) {
        return freeze + passiveFreeze.get() + activeFreeze.get();
    }

    @ModifyReturnValue(method = "lambda$updateTemperature$10", at = @At("RETURN"))
    private static boolean checkEmptyBlazeBurner(boolean original, BlockState state) {
        if (original && state.hasProperty(BlazeBurnerBlock.HEAT_LEVEL)) {
            return state.getValue(BlazeBurnerBlock.HEAT_LEVEL) != HeatLevel.NONE;
        }
        return original;
    }
}
