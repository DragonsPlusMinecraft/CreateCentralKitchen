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

package plus.dragons.createcentralkitchen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.tag.ModTags;

@Mixin(BlazeBurnerBlockEntity.class)
public abstract class BlazeBurnerBlockEntityMixin extends SmartBlockEntity {
    private BlazeBurnerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @ModifyReturnValue(method = "isValidBlockAbove", at = @At("TAIL"))
    private boolean isHeatableBlockEntityAbove(boolean original, @Local BlockState aboveState) {
        if (original)
            return true;
        assert level != null;
        if (level.getBlockEntity(worldPosition.above()) instanceof HeatableBlockEntity)
            return true;
        if (aboveState.is(ModTags.HEAT_CONDUCTORS))
            return level.getBlockEntity(worldPosition.above(2)) instanceof HeatableBlockEntity;
        return false;
    }
}
