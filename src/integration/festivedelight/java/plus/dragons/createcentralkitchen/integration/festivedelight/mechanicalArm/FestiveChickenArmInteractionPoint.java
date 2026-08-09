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

package plus.dragons.createcentralkitchen.integration.festivedelight.mechanicalArm;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.toopa.festivedelight.init.FestiveDelightModBlocks;
import net.toopa.festivedelight.init.FestiveDelightModItems;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.common.mechanicalarm.StatefulBlockArmInteractionPoint;
import vectorwing.farmersdelight.common.registry.ModSounds;

public class FestiveChickenArmInteractionPoint extends StatefulBlockArmInteractionPoint {
    public FestiveChickenArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected @Nullable Insertion planInsertion(BlockState state, ItemStack stack) {
        return null;
    }

    @Override
    protected @Nullable Extraction planExtraction(BlockState state) {
        var next = nextBlock(state.getBlock());
        if (next == null)
            return null;
        var nextState = copySharedProperties(state, next.defaultBlockState());
        return extraction(
                new ItemStack(FestiveDelightModItems.FESTIVE_CHIKEN.get()), nextState, ModSounds.BLOCK_FOOD_TAKE_PORTION.get());
    }

    private static @Nullable Block nextBlock(Block block) {
        if (block == FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_0.get())
            return FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_1.get();
        if (block == FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_1.get())
            return FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_2.get();
        if (block == FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_2.get())
            return FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_3.get();
        if (block == FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_3.get())
            return FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_LEFTOVER.get();
        return null;
    }

    public static boolean isFestiveChicken(Block block) {
        return block == FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_0.get()
                || block == FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_1.get()
                || block == FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_2.get()
                || block == FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_3.get()
                || block == FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_LEFTOVER.get();
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return isFestiveChicken(state.getBlock());
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new FestiveChickenArmInteractionPoint(this, level, pos, state);
        }
    }
}
