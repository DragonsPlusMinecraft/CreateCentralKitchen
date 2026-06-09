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

package plus.dragons.createcentralkitchen.integration.mynethersdelight.mechanicalArm;

import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.soytutta.mynethersdelight.common.block.BlazierBlock;
import com.soytutta.mynethersdelight.common.block.entity.BlazierBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BlazierArmInteractionPoint extends DepositOnlyArmInteractionPoint {
    public BlazierArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        var state = level.getBlockState(pos);
        if (!state.hasProperty(BlazierBlock.LIT) || !state.getValue(BlazierBlock.LIT))
            return stack;
        if (!state.hasProperty(BlazierBlock.HEAT))
            return stack;
        if (level.getBlockEntity(pos) instanceof BlazierBlockEntity blazier && blazier.hasFreeSlot()) {
            var recipe = blazier.getCookableRecipe(stack);
            if (recipe.isEmpty())
                return stack;
            var remainder = stack.copy();
            if (simulate) {
                remainder.shrink(1);
                return remainder;
            }
            var heat = state.getValue(BlazierBlock.HEAT);
            int cookingTime = blazier.getCookTimeForRecipe(recipe.get(), heat);
            if (blazier.placeFood(null, remainder, cookingTime))
                return remainder;
        }
        return stack;
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, 0.5f);
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof BlazierBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new BlazierArmInteractionPoint(this, level, pos, state);
        }
    }
}
