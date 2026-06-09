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

import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

public class CuttingBoardArmInteractionPoint extends DepositOnlyArmInteractionPoint {
    public CuttingBoardArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        if (level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity board && board.canAddItem(stack) && hasCuttingRecipe(stack)) {
            if (simulate)
                return board.getInventory().insertItem(0, stack, true);
            return board.addItem(stack.copy());
        }
        return stack;
    }

    private boolean hasCuttingRecipe(ItemStack stack) {
        if (stack.isEmpty())
            return false;
        var recipeManager = level.getRecipeManager();
        return recipeManager.getAllRecipesFor(ModRecipeTypes.CUTTING.get())
                .stream()
                .anyMatch(holder -> holder.value().getIngredients().getFirst().test(stack));
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, 1 / 16f);
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CuttingBoardArmInteractionPoint(this, level, pos, state);
        }
    }
}
