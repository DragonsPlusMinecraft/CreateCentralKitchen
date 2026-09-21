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
import alabaster.hearthandharvest.common.crafting.CaskRecipe;
import alabaster.hearthandharvest.common.registry.HHModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.common.packager.ShapelessUnpackingHandler;

public class CaskUnpackingHandler extends ShapelessUnpackingHandler {
    public CaskUnpackingHandler() {
        super(0, 4);
    }

    @Override
    protected @Nullable IItemHandler getInventory(Level level, BlockPos pos, Direction side) {
        return level.getBlockEntity(pos) instanceof CaskBlockEntity cask ? cask.getInventory() : null;
    }

    @Override
    protected RecipeType<?> getRecipeType() {
        return HHModRecipeTypes.AGING.get();
    }

    @Override
    protected boolean acceptsRecipe(Level level, BlockPos pos, Recipe<?> recipe) {
        if (!(level.getBlockEntity(pos) instanceof CaskBlockEntity cask))
            return false;
        var inventory = cask.getInventory();
        var output = ((CaskRecipe) recipe).getOutput();
        if (output.isEmpty())
            return false;
        var existing = inventory.getStackInSlot(4);
        int limit = Math.min(inventory.getSlotLimit(4), output.getMaxStackSize());
        return (existing.isEmpty() || ItemStack.isSameItemSameComponents(existing, output))
                && (long) existing.getCount() + output.getCount() <= limit;
    }
}
