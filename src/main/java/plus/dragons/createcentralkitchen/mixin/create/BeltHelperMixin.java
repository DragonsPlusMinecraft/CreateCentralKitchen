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

package plus.dragons.createcentralkitchen.mixin.create;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BeltHelper.class, priority = 2000)
public abstract class BeltHelperMixin {
    @WrapMethod(method = "lambda$isItemUpright$1")
    private static Boolean isItemUprightForRemainder(ItemStack stack, Item item, Operation<Boolean> original) {
        if (original.call(stack, item))
            return true;
        ItemStack remainder;
        remainder = stack.getCraftingRemainingItem();
        if (!remainder.isEmpty())
            return original.call(remainder, remainder.getItem());
        FoodProperties food = stack.getFoodProperties(null);
        if (food != null && food.usingConvertsTo().isPresent()) {
            remainder = food.usingConvertsTo().get();
            return !remainder.isEmpty() && original.call(remainder, remainder.getItem());
        }
        return false;
    }
}
