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

package plus.dragons.createcentralkitchen.common.packager;

import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import com.simibubi.create.content.logistics.stockTicker.PackageOrderWithCrafts;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class ShapelessUnpackingHandler implements UnpackingHandler {
    protected final int slotStart;
    protected final int slotCount;

    protected ShapelessUnpackingHandler(int slotStart, int slotCount) {
        this.slotStart = slotStart;
        this.slotCount = slotCount;
    }

    @Override
    public boolean unpack(Level level, BlockPos pos, BlockState state, Direction side, List<ItemStack> items, @Nullable PackageOrderWithCrafts orderContext, boolean simulate) {
        var inventory = getInventory(level, pos, side);
        if (inventory == null)
            return false;
        for (int slot = slotStart; slot < slotStart + slotCount; slot++) {
            var item = items.getFirst();
            if (inventory.getStackInSlot(slot).isEmpty() && inventory.insertItem(slot, item.split(1), simulate).isEmpty()) {
                if (item.isEmpty()) {
                    items.removeFirst();
                    if (items.isEmpty())
                        return true;
                }
            } else return false;
        }
        return false;
    }

    protected abstract @Nullable IItemHandler getInventory(Level level, BlockPos pos, Direction side);
}
