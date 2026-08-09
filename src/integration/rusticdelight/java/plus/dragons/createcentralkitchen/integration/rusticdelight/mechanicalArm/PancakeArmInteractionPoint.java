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

package plus.dragons.createcentralkitchen.integration.rusticdelight.mechanicalArm;

import com.phantomwing.rusticdelight.block.custom.PancakeBlock;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.common.mechanicalarm.StatefulBlockArmInteractionPoint;

public class PancakeArmInteractionPoint extends StatefulBlockArmInteractionPoint {
    public PancakeArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected @Nullable Insertion planInsertion(BlockState state, ItemStack stack) {
        if (!(state.getBlock() instanceof PancakeBlock pancakes))
            return null;
        var serving = pancakes.getServingItem();
        if (!ItemStack.isSameItemSameComponents(stack, serving))
            return null;
        int present = PancakeBlock.getPancakesPresent(state);
        int inserted = Math.min(stack.getCount(), PancakeBlock.MAX_TOTAL_SERVINGS - present);
        if (inserted <= 0)
            return null;
        var remainder = stack.copy();
        remainder.shrink(inserted);
        return insertion(remainder, stateForCount(state, present + inserted), SoundEvents.WOOL_PLACE);
    }

    @Override
    protected @Nullable Extraction planExtraction(BlockState state) {
        if (!(state.getBlock() instanceof PancakeBlock pancakes))
            return null;
        int present = PancakeBlock.getPancakesPresent(state);
        if (present <= 0)
            return null;
        var nextState = present == 1 ? null : stateForCount(state, present - 1);
        return extraction(pancakes.getServingItem(), nextState, SoundEvents.WOOL_BREAK);
    }

    /** Resolves Rustic Delight's non-linear public serving encoding without calling its private inverse helper. */
    public static BlockState stateForCount(BlockState state, int count) {
        for (int encoded : PancakeBlock.SERVINGS.getPossibleValues()) {
            var candidate = state.setValue(PancakeBlock.SERVINGS, encoded);
            if (PancakeBlock.getPancakesPresent(candidate) == count)
                return candidate;
        }
        throw new IllegalArgumentException("Unsupported pancake count: " + count);
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return state.getBlock() instanceof PancakeBlock;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new PancakeArmInteractionPoint(this, level, pos, state);
        }
    }
}
