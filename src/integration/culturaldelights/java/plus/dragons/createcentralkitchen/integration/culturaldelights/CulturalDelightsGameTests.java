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

package plus.dragons.createcentralkitchen.integration.culturaldelights;

import com.baisylia.culturaldelights.block.ModBlocks;
import com.baisylia.culturaldelights.block.custom.EggplantFeastBlock;
import com.baisylia.culturaldelights.item.ModItems;
import com.simibubi.create.Create;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.culturaldelights.mechanicalArm.EggplantParmesanArmInteractionPoint;

@GameTestHolder(CCKCommon.ID)
@PrefixGameTestTemplate(false)
public class CulturalDelightsGameTests {
    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void eggplantParmesanTransactionsAreAtomic(GameTestHelper helper) {
        var position = new BlockPos(1, 2, 1);
        var block = (EggplantFeastBlock) ModBlocks.EGGPLANT_PARMESAN_BLOCK.get();
        var servings = block.getServingsProperty();
        var state = block.defaultBlockState().setValue(EggplantFeastBlock.FACING, Direction.EAST).setValue(servings, 3);
        helper.setBlock(position, state);
        var point = new EggplantParmesanArmInteractionPoint.Type()
                .createPoint(helper.getLevel(), helper.absolutePos(position), state);
        helper.assertTrue(point != null, "Eggplant Parmesan must create an arm interaction point");

        helper.assertTrue(point.extract(null, 0, 0, false).isEmpty(), "A zero-sized extraction must be rejected");
        var simulated = point.extract(null, 0, 1, true);
        helper.assertTrue(simulated.is(ModItems.EGGPLANT_PARMESAN.get()), "Simulation must report one plated serving");
        helper.assertValueEqual(helper.getBlockState(position).getValue(servings), 3, "Simulation changed the feast");

        var extracted = point.extract(null, 0, 1, false);
        helper.assertTrue(extracted.is(ModItems.EGGPLANT_PARMESAN.get()), "The arm must extract a plated serving");
        helper.assertValueEqual(helper.getBlockState(position).getValue(servings), 2, "Extraction did not decrement servings");
        helper.assertValueEqual(helper.getBlockState(position).getValue(EggplantFeastBlock.FACING), Direction.EAST, "Extraction changed facing");

        helper.setBlock(position, state.setValue(servings, 1));
        helper.assertTrue(point.extract(null, 0, 1, false).isEmpty(), "The final serving must stay in place");
        var oversized = new ItemStack(block, 2);
        helper.assertTrue(ItemStack.matches(point.insert(null, oversized, false), oversized), "An oversized replacement must be rejected");
        helper.assertValueEqual(helper.getBlockState(position).getValue(servings), 1, "Rejected insertion changed servings");

        var replacement = new ItemStack(block);
        var returned = point.insert(null, replacement, false);
        helper.assertTrue(returned.is(ModItems.EGGPLANT_PARMESAN.get()), "A fresh feast must return the final serving");
        helper.assertValueEqual(helper.getBlockState(position).getValue(servings), block.getMaxServings(), "Fresh feast was not restored");
        helper.assertValueEqual(helper.getBlockState(position).getValue(EggplantFeastBlock.FACING), Direction.EAST, "Replacement changed facing");

        helper.setBlock(position, state.setValue(servings, 0));
        helper.assertTrue(point.extract(null, 0, 1, false).isEmpty(), "Leftovers must be terminal");
        helper.assertTrue(ItemStack.matches(point.insert(null, replacement, false), replacement), "Leftovers must reject replacement");
        helper.assertTrue(helper.getBlockState(position).is(block), "Terminal leftovers were removed");
        helper.succeed();
    }
}
