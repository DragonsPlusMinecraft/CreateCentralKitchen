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

package plus.dragons.createcentralkitchen.integration.culturaldelights.mechanicalArm;

import com.baisylia.culturaldelights.block.ModBlocks;
import com.baisylia.culturaldelights.block.custom.EggplantFeastBlock;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.common.mechanicalarm.StatefulBlockArmInteractionPoint;

public class EggplantParmesanArmInteractionPoint extends StatefulBlockArmInteractionPoint {
    public EggplantParmesanArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected @Nullable Insertion planInsertion(BlockState state, ItemStack stack) {
        if (!(state.getBlock() instanceof EggplantFeastBlock feast) || state.getBlock() != ModBlocks.EGGPLANT_PARMESAN_BLOCK.get())
            return null;
        var servings = feast.getServingsProperty();
        if (state.getValue(servings) != 1 || stack.getCount() != 1 || !(stack.getItem() instanceof BlockItem blockItem) || blockItem.getBlock() != state.getBlock())
            return null;
        var replacement = copySharedProperties(state, feast.defaultBlockState(), servings);
        if (!replacement.canSurvive(level, pos))
            return null;
        return insertion(feast.getServingItem(state), replacement, SoundEvents.ARMOR_EQUIP_GENERIC.value());
    }

    @Override
    protected @Nullable Extraction planExtraction(BlockState state) {
        if (!(state.getBlock() instanceof EggplantFeastBlock feast) || state.getBlock() != ModBlocks.EGGPLANT_PARMESAN_BLOCK.get())
            return null;
        var servings = feast.getServingsProperty();
        int count = state.getValue(servings);
        if (count <= 1)
            return null;
        return extraction(feast.getServingItem(state), state.setValue(servings, count - 1), SoundEvents.ARMOR_EQUIP_GENERIC.value());
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return state.getBlock() == ModBlocks.EGGPLANT_PARMESAN_BLOCK.get();
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new EggplantParmesanArmInteractionPoint(this, level, pos, state);
        }
    }
}
