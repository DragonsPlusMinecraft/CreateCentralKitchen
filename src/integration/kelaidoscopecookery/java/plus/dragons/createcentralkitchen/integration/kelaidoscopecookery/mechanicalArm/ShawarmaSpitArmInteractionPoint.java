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

package plus.dragons.createcentralkitchen.integration.kelaidoscopecookery.mechanicalArm;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.ShawarmaSpitBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ShawarmaSpitArmInteractionPoint extends ArmInteractionPoint {
    public static RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> QUICK_CHECK;

    public ShawarmaSpitArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
        if (QUICK_CHECK == null) QUICK_CHECK = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
    }

    @Override
    public ItemStack extract(ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        if (level.getBlockEntity(pos) instanceof ShawarmaSpitBlockEntity interaction) {
            if (interaction.cookTime == 0 && !interaction.cookedItem.isEmpty()) {
                var result = interaction.cookedItem.copy();
                if (!simulate) {
                    interaction.cookingItem = ItemStack.EMPTY;
                    interaction.cookedItem = ItemStack.EMPTY;
                    interaction.cookTime = 0;
                    interaction.refresh();
                }
                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotCount(ArmBlockEntity armBlockEntity) {
        return 1;
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        if (level.getBlockEntity(pos) instanceof ShawarmaSpitBlockEntity interaction) {
            if (interaction.cookingItem.isEmpty() && interaction.cookedItem.isEmpty()) {
                if (QUICK_CHECK.getRecipeFor(new SingleRecipeInput(stack), armBlockEntity.getLevel()).isPresent()) {
                    if (simulate) {
                        var result = stack.copy();
                        result.shrink(8);
                        return result;
                    }
                    interaction.onPutCookingItem(level, stack);
                    return stack;
                }
            }
        }
        return stack;
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, 1 / 2f);
    }

    public static class Type extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof ShawarmaSpitBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new ShawarmaSpitArmInteractionPoint(this, level, pos, state);
        }
    }
}
