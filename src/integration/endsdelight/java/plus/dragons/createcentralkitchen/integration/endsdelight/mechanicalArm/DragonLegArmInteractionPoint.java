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

package plus.dragons.createcentralkitchen.integration.endsdelight.mechanicalArm;

import cn.foggyhillside.ends_delight.block.DragonLegBlock;
import cn.foggyhillside.ends_delight.registry.ModItems;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class DragonLegArmInteractionPoint extends ArmInteractionPoint {
    public DragonLegArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        if (!stack.is(Items.BOWL) || stack.getCount() != 1)
            return stack;
        var state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof DragonLegBlock) || !state.hasProperty(DragonLegBlock.SERVINGS))
            return stack;
        int servings = state.getValue(DragonLegBlock.SERVINGS);
        if (servings <= 1)
            return stack;
        var otherPos = findOtherHalf(state);
        if (otherPos == null)
            return stack;
        var otherState = level.getBlockState(otherPos);
        var serving = new ItemStack(ModItems.DRAGON_LEG_WITH_SAUCE.get());
        if (simulate)
            return serving;
        var newState = state.setValue(DragonLegBlock.SERVINGS, servings - 1);
        var newOtherState = otherState.setValue(DragonLegBlock.SERVINGS, servings - 1);
        if (!level.setBlock(otherPos, newOtherState, 3))
            return stack;
        if (!level.setBlock(pos, newState, 3)) {
            level.setBlock(otherPos, otherState, 3);
            return stack;
        }
        level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
        return serving;
    }

    @Override
    public ItemStack extract(ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotCount(ArmBlockEntity armBlockEntity) {
        return 1;
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, 0.5f);
    }

    @Nullable
    private BlockPos findOtherHalf(BlockState state) {
        if (!state.hasProperty(DragonLegBlock.PART) || !state.hasProperty(DragonLegBlock.FACING))
            return null;
        var part = state.getValue(DragonLegBlock.PART);
        var facing = state.getValue(DragonLegBlock.FACING);
        var expected = pos.relative(part == BedPart.FOOT ? facing : facing.getOpposite());
        if (isOtherHalf(expected, state, part))
            return expected;
        var opposite = pos.relative(part == BedPart.FOOT ? facing.getOpposite() : facing);
        if (isOtherHalf(opposite, state, part))
            return opposite;
        return null;
    }

    private boolean isOtherHalf(BlockPos candidate, BlockState state, BedPart part) {
        var otherState = level.getBlockState(candidate);
        if (otherState.getBlock() != state.getBlock())
            return false;
        if (!otherState.hasProperty(DragonLegBlock.PART) || !otherState.hasProperty(DragonLegBlock.SERVINGS))
            return false;
        if (otherState.getValue(DragonLegBlock.PART) == part)
            return false;
        return otherState.getValue(DragonLegBlock.SERVINGS).equals(state.getValue(DragonLegBlock.SERVINGS));
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return state.getBlock() instanceof DragonLegBlock;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new DragonLegArmInteractionPoint(this, level, pos, state);
        }
    }
}
