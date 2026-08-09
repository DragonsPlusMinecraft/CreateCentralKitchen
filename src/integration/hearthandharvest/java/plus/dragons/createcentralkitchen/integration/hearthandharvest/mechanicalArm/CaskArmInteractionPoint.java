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

package plus.dragons.createcentralkitchen.integration.hearthandharvest.mechanicalArm;

import alabaster.hearthandharvest.common.block.entity.CaskBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.integration.hearthandharvest.cask.CaskRecipeSelection;

public class CaskArmInteractionPoint extends ArmInteractionPoint {
    public CaskArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        if (!(level.getBlockEntity(pos) instanceof CaskBlockEntity cask))
            return stack;
        var inventory = cask.getInventory();
        var plan = CaskRecipeSelection.findInsertion(level, inventory, stack);
        if (plan.isEmpty())
            return stack;

        int accepted = plan.get().targetSlots().size();
        if (!simulate) {
            for (int slot : plan.get().targetSlots())
                inventory.setStackInSlot(slot, stack.copyWithCount(1));
        }
        return stack.copyWithCount(stack.getCount() - accepted);
    }

    @Override
    public ItemStack extract(ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        if (amount <= 0 || !(level.getBlockEntity(pos) instanceof CaskBlockEntity cask))
            return ItemStack.EMPTY;
        return cask.getInventory().extractItem(CaskRecipeSelection.OUTPUT_SLOT, amount, simulate);
    }

    @Override
    public int getSlotCount(ArmBlockEntity armBlockEntity) {
        return 1;
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof CaskBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CaskArmInteractionPoint(this, level, pos, state);
        }
    }
}
