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

import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModSounds;

public class PortionableFoodArmInteractionPoint extends ArmInteractionPoint {
    public PortionableFoodArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        var state = level.getBlockState(pos);
        var block = state.getBlock();
        if (block instanceof FeastBlock feast)
            return replaceLastFeast(feast, state, stack, simulate);
        if (block instanceof PieBlock pie)
            return replaceLastPie(pie, state, stack, simulate);
        return stack;
    }

    @Override
    public ItemStack extract(ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        var state = level.getBlockState(pos);
        var block = state.getBlock();
        if (block instanceof FeastBlock feast)
            return extractFeast(feast, state, amount, simulate);
        if (block instanceof PieBlock pie)
            return extractPie(pie, state, amount, simulate);
        return ItemStack.EMPTY;
    }

    private ItemStack extractFeast(FeastBlock feast, BlockState state, int amount, boolean simulate) {
        var servings = feast.getServingsProperty();
        if (!state.hasProperty(servings) || state.getValue(servings) <= 1)
            return ItemStack.EMPTY;
        var serving = feast.getServingItem(state).copy();
        if (!canExtract(serving, amount))
            return ItemStack.EMPTY;
        if (!simulate) {
            int remainingServings = state.getValue(servings) - 1;
            level.setBlock(pos, state.setValue(servings, remainingServings), 3);
            level.playSound(null, pos, ModSounds.BLOCK_FOOD_TAKE_PORTION.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return serving;
    }

    private ItemStack extractPie(PieBlock pie, BlockState state, int amount, boolean simulate) {
        if (!state.hasProperty(PieBlock.BITES) || state.getValue(PieBlock.BITES) >= pie.getMaxBites() - 1)
            return ItemStack.EMPTY;
        var serving = pie.getPieSliceItem().copy();
        if (!canExtract(serving, amount))
            return ItemStack.EMPTY;
        if (!simulate) {
            int bites = state.getValue(PieBlock.BITES) + 1;
            level.setBlock(pos, state.setValue(PieBlock.BITES, bites), 3);
            level.playSound(null, pos, ModSounds.BLOCK_FOOD_SLICE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return serving;
    }

    private ItemStack replaceLastFeast(FeastBlock feast, BlockState state, ItemStack stack, boolean simulate) {
        var servings = feast.getServingsProperty();
        if (!state.hasProperty(servings) || state.getValue(servings) != 1)
            return stack;
        var replacementBlock = getReplacementBlock(stack);
        if (!(replacementBlock instanceof FeastBlock newFeast))
            return stack;
        var replacementState = copySharedPlacementProperties(state, newFeast.defaultBlockState());
        if (!replacementState.canSurvive(level, pos))
            return stack;
        return replaceAndReturnLastServing(stack, feast.getServingItem(state).copy(), replacementState, ModSounds.BLOCK_FOOD_TAKE_PORTION.get(), simulate);
    }

    private ItemStack replaceLastPie(PieBlock pie, BlockState state, ItemStack stack, boolean simulate) {
        if (!state.hasProperty(PieBlock.BITES) || state.getValue(PieBlock.BITES) != pie.getMaxBites() - 1)
            return stack;
        var replacementBlock = getReplacementBlock(stack);
        if (!(replacementBlock instanceof PieBlock newPie))
            return stack;
        var replacementState = copySharedPlacementProperties(state, newPie.defaultBlockState());
        if (!replacementState.canSurvive(level, pos))
            return stack;
        return replaceAndReturnLastServing(stack, pie.getPieSliceItem().copy(), replacementState, ModSounds.BLOCK_FOOD_SLICE.get(), simulate);
    }

    private ItemStack replaceAndReturnLastServing(ItemStack stack, ItemStack serving, BlockState replacementState, SoundEvent sound, boolean simulate) {
        if (serving.isEmpty())
            return stack;
        if (stack.getCount() > 1)
            return stack;
        if (simulate)
            return serving;
        if (!level.setBlock(pos, replacementState, 3))
            return stack;
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
        return serving;
    }

    @Nullable
    private static Block getReplacementBlock(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem)
            return blockItem.getBlock();
        return null;
    }

    private static BlockState copySharedPlacementProperties(BlockState oldState, BlockState newState) {
        for (var property : oldState.getProperties()) {
            if (property == FeastBlock.SERVINGS || property == PieBlock.BITES || !newState.hasProperty(property))
                continue;
            newState = copyProperty(oldState, newState, property);
        }
        return newState;
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState oldState, BlockState newState, Property<T> property) {
        return newState.setValue(property, oldState.getValue(property));
    }

    private static boolean canExtract(ItemStack stack, int amount) {
        return !stack.isEmpty() && amount >= stack.getCount();
    }

    @Override
    public int getSlotCount(ArmBlockEntity armBlockEntity) {
        return 1;
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, 0.5f);
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return state.getBlock() instanceof FeastBlock || state.getBlock() instanceof PieBlock;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new PortionableFoodArmInteractionPoint(this, level, pos, state);
        }
    }
}
