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

package plus.dragons.createcentralkitchen.integration.farmersdelight.mechanicalArm;

import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.AbstractStoveBlock;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;

public class StoveArmInteractionPoint extends DepositOnlyArmInteractionPoint {
    public StoveArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        if (level.getBlockEntity(pos) instanceof AbstractStoveBlockEntity stove) {
            int slot = stove.getNextEmptySlot();
            if (slot < 0 || slot >= stove.getItems().getSlots() || isStoveBlockedAbove(stove)) {
                return stack;
            }
            var recipe = stove.getCookingRecipe(stack);
            if (recipe.isEmpty())
                return stack;
            var remainder = stack.copy();
            if (simulate) {
                remainder.shrink(1);
                return remainder;
            }
            stove.placeFood(null, remainder, recipe.get());
            return remainder;
        }
        return stack;
    }

    private boolean isStoveBlockedAbove(AbstractStoveBlockEntity stove) {
        return AbstractStoveBlock.isStoveTopCovered(level, stove.getBlockPos(), stove.getBlockState());
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, 1f);
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof AbstractStoveBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new StoveArmInteractionPoint(this, level, pos, state);
        }
    }
}
