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

package plus.dragons.createcentralkitchen.common.mechanicalarm;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * A mechanical-arm target whose operation is an atomic transition of a block state.
 *
 * <p>Plans are always created from the live state. Simulation follows the same path as a real operation but never
 * changes the level or plays a sound.</p>
 */
public abstract class StatefulBlockArmInteractionPoint extends ArmInteractionPoint {
    protected StatefulBlockArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public final ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        var transaction = planInsertion(level.getBlockState(pos), stack);
        if (transaction == null)
            return stack;
        if (simulate)
            return transaction.remainder().copy();
        if (!apply(transaction.nextState()))
            return stack;
        playSound(transaction.sound());
        return transaction.remainder().copy();
    }

    @Override
    public final ItemStack extract(ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        if (amount <= 0)
            return ItemStack.EMPTY;
        var transaction = planExtraction(level.getBlockState(pos));
        if (transaction == null || transaction.extracted().isEmpty() || amount < transaction.extracted().getCount())
            return ItemStack.EMPTY;
        if (!simulate) {
            if (!apply(transaction.nextState()))
                return ItemStack.EMPTY;
            playSound(transaction.sound());
        }
        return transaction.extracted().copy();
    }

    protected abstract @Nullable Insertion planInsertion(BlockState state, ItemStack stack);

    protected abstract @Nullable Extraction planExtraction(BlockState state);

    protected static Insertion insertion(ItemStack remainder, BlockState nextState, SoundEvent sound) {
        return new Insertion(remainder, nextState, sound);
    }

    protected static Extraction extraction(ItemStack extracted, @Nullable BlockState nextState, SoundEvent sound) {
        return new Extraction(extracted, nextState, sound);
    }

    private boolean apply(@Nullable BlockState nextState) {
        return nextState == null ? level.removeBlock(pos, false) : level.setBlock(pos, nextState, 3);
    }

    private void playSound(SoundEvent sound) {
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    protected static BlockState copySharedProperties(BlockState oldState, BlockState newState, Property<?>... excluded) {
        for (var property : oldState.getProperties()) {
            if (Arrays.asList(excluded).contains(property) || !newState.hasProperty(property))
                continue;
            newState = copyProperty(oldState, newState, property);
        }
        return newState;
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState oldState, BlockState newState, Property<T> property) {
        return newState.setValue(property, oldState.getValue(property));
    }

    @Override
    public final int getSlotCount(ArmBlockEntity armBlockEntity) {
        return 1;
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, 0.5F);
    }

    protected record Insertion(ItemStack remainder, BlockState nextState, SoundEvent sound) {}

    protected record Extraction(ItemStack extracted, @Nullable BlockState nextState, SoundEvent sound) {}
}
