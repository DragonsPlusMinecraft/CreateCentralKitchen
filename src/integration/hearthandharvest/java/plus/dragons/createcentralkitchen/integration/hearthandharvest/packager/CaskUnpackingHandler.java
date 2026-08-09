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

package plus.dragons.createcentralkitchen.integration.hearthandharvest.packager;

import alabaster.hearthandharvest.common.block.entity.CaskBlockEntity;
import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import com.simibubi.create.content.logistics.stockTicker.PackageOrderWithCrafts;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.integration.hearthandharvest.cask.CaskRecipeSelection;

public class CaskUnpackingHandler implements UnpackingHandler {
    @Override
    public boolean unpack(
            Level level,
            BlockPos pos,
            BlockState state,
            Direction side,
            List<ItemStack> items,
            @Nullable PackageOrderWithCrafts orderContext,
            boolean simulate) {
        if (!(level.getBlockEntity(pos) instanceof CaskBlockEntity cask))
            return false;
        var inventory = cask.getInventory();
        var plan = CaskRecipeSelection.findUnpacking(level, inventory, items);
        if (plan.isEmpty())
            return false;

        var ingredients = plan.get().ingredients();
        for (int slot = 0; slot < ingredients.size(); slot++) {
            if (!inventory.insertItem(slot, ingredients.get(slot), true).isEmpty())
                return false;
        }
        if (!simulate) {
            for (int slot = 0; slot < ingredients.size(); slot++)
                inventory.setStackInSlot(slot, ingredients.get(slot).copy());
        }
        return true;
    }
}
