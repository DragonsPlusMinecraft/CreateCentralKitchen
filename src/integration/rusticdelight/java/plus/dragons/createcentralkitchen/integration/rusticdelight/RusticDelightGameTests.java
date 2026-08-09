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

package plus.dragons.createcentralkitchen.integration.rusticdelight;

import com.phantomwing.rusticdelight.block.ModBlocks;
import com.phantomwing.rusticdelight.block.custom.PancakeBlock;
import com.simibubi.create.Create;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.rusticdelight.mechanicalArm.PancakeArmInteractionPoint;

@GameTestHolder(CCKCommon.ID)
@PrefixGameTestTemplate(false)
public class RusticDelightGameTests {
    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void pancakeTransactionsHonorPublicEncoding(GameTestHelper helper) {
        var position = new BlockPos(1, 2, 1);
        var flavors = pancakeBlocks();
        for (int index = 0; index < flavors.size(); index++) {
            var pancakes = flavors.get(index);
            var six = PancakeArmInteractionPoint.stateForCount(
                    pancakes.defaultBlockState().setValue(PancakeBlock.FACING, Direction.EAST), 6);
            helper.assertValueEqual(six.getValue(PancakeBlock.SERVINGS), 0, "Six pancakes must use the upstream encoded value");
            helper.assertValueEqual(
                    PancakeArmInteractionPoint.stateForCount(six, 1).getValue(PancakeBlock.SERVINGS), 5, "One-pancake encoding changed");
            helper.assertValueEqual(
                    PancakeArmInteractionPoint.stateForCount(six, 7).getValue(PancakeBlock.SERVINGS), 6, "Seven-pancake encoding changed");
            helper.assertValueEqual(
                    PancakeArmInteractionPoint.stateForCount(six, 12).getValue(PancakeBlock.SERVINGS), 11, "Twelve-pancake encoding changed");

            helper.setBlock(position, six);
            var point = new PancakeArmInteractionPoint.Type()
                    .createPoint(helper.getLevel(), helper.absolutePos(position), six);
            helper.assertTrue(point != null, "Every PancakeBlock must create an arm interaction point");
            var serving = pancakes.getServingItem();

            var simulatedExtract = point.extract(null, 0, 1, true);
            helper.assertTrue(ItemStack.isSameItemSameComponents(simulatedExtract, serving), "Simulation returned the wrong pancake flavor");
            assertCount(helper, position, 6, "Extraction simulation changed the stack");
            helper.assertTrue(point.extract(null, 0, 0, false).isEmpty(), "Zero-sized extraction must be rejected");
            helper.assertTrue(ItemStack.isSameItemSameComponents(point.extract(null, 0, 1, false), serving), "Extraction returned the wrong flavor");
            assertCount(helper, position, 5, "Extraction did not cross the lower encoding branch");
            helper.assertValueEqual(helper.getBlockState(position).getValue(PancakeBlock.FACING), Direction.EAST, "Extraction changed facing");

            var seven = PancakeArmInteractionPoint.stateForCount(six, 7);
            helper.setBlock(position, seven);
            var three = serving.copyWithCount(3);
            helper.assertTrue(point.insert(null, three, true).isEmpty(), "A simulated legal insertion should accept all three pancakes");
            assertCount(helper, position, 7, "Insertion simulation changed the stack");
            helper.assertTrue(point.insert(null, three, false).isEmpty(), "A legal insertion should accept all three pancakes");
            assertCount(helper, position, 10, "Insertion did not use the public non-linear encoding");

            var sevenItems = serving.copyWithCount(7);
            var remainder = point.insert(null, sevenItems, false);
            helper.assertValueEqual(remainder.getCount(), 5, "Insertion did not stop at twelve pancakes");
            assertCount(helper, position, 12, "Insertion exceeded or missed the twelve-pancake cap");
            helper.assertTrue(ItemStack.matches(point.insert(null, serving, false), serving), "A full stack must reject excess pancakes");

            var wrongFlavor = flavors.get((index + 1) % flavors.size()).getServingItem();
            helper.setBlock(position, seven);
            helper.assertTrue(ItemStack.matches(point.insert(null, wrongFlavor, false), wrongFlavor), "A pancake stack accepted the wrong flavor");
            assertCount(helper, position, 7, "Wrong-flavor insertion changed the block");

            helper.setBlock(position, PancakeArmInteractionPoint.stateForCount(six, 1));
            helper.assertTrue(ItemStack.isSameItemSameComponents(point.extract(null, 0, 1, false), serving), "The final pancake was not returned");
            helper.assertTrue(helper.getBlockState(position).isAir(), "The final pancake must remove the block without loot");
        }
        helper.succeed();
    }

    private static void assertCount(GameTestHelper helper, BlockPos position, int expected, String message) {
        helper.assertValueEqual(PancakeBlock.getPancakesPresent(helper.getBlockState(position)), expected, message);
    }

    private static List<PancakeBlock> pancakeBlocks() {
        return List.of(
                (PancakeBlock) ModBlocks.PANCAKES.get(),
                (PancakeBlock) ModBlocks.HONEY_PANCAKES.get(),
                (PancakeBlock) ModBlocks.CHOCOLATE_PANCAKES.get(),
                (PancakeBlock) ModBlocks.CHERRY_BLOSSOM_PANCAKES.get(),
                (PancakeBlock) ModBlocks.VEGETABLE_PANCAKES.get(),
                (PancakeBlock) ModBlocks.PUMPKIN_PANCAKES.get(),
                (PancakeBlock) ModBlocks.COFFEE_PANCAKES.get());
    }
}
