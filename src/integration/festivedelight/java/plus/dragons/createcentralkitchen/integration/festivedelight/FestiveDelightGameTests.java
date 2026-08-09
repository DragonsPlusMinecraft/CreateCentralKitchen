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

package plus.dragons.createcentralkitchen.integration.festivedelight;

import com.simibubi.create.Create;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.toopa.festivedelight.init.FestiveDelightModBlocks;
import net.toopa.festivedelight.init.FestiveDelightModItems;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.festivedelight.mechanicalArm.FestiveChickenArmInteractionPoint;

@GameTestHolder(CCKCommon.ID)
@PrefixGameTestTemplate(false)
public class FestiveDelightGameTests {
    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void festiveChickenTransitionsAreAtomic(GameTestHelper helper) {
        var position = new BlockPos(1, 2, 1);
        var initial = FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_0
                .get()
                .defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST);
        helper.setBlock(position, initial);
        var type = new FestiveChickenArmInteractionPoint.Type();
        var point = type.createPoint(helper.getLevel(), helper.absolutePos(position), initial);
        helper.assertTrue(point != null, "Festive chicken must create an arm interaction point");
        helper.assertTrue(point.extract(null, 0, 0, false).isEmpty(), "A zero-sized extraction must be rejected");

        var simulated = point.extract(null, 0, 1, true);
        helper.assertTrue(simulated.is(FestiveDelightModItems.FESTIVE_CHIKEN.get()), "Simulation returned the wrong serving");
        helper.assertTrue(helper.getBlockState(position).is(FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_0.get()), "Simulation changed the stage");

        var expectedStages = List.of(
                FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_1.get(),
                FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_2.get(),
                FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_3.get(),
                FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_LEFTOVER.get());
        for (var expected : expectedStages) {
            var serving = point.extract(null, 0, 1, false);
            helper.assertTrue(serving.is(FestiveDelightModItems.FESTIVE_CHIKEN.get()), "A stage returned the wrong plated serving");
            helper.assertTrue(helper.getBlockState(position).is(expected), "Festive chicken advanced to the wrong registered stage");
            helper.assertValueEqual(
                    helper.getBlockState(position).getValue(BlockStateProperties.HORIZONTAL_FACING),
                    Direction.WEST,
                    "Stage transition changed facing");
        }

        helper.assertTrue(type.canCreatePoint(helper.getLevel(), helper.absolutePos(position), helper.getBlockState(position)), "Leftovers must remain recognized as terminal");
        helper.assertTrue(point.extract(null, 0, 1, false).isEmpty(), "Leftovers must not yield a fifth serving");
        var wholeChicken = new ItemStack(FestiveDelightModItems.FESTIVE_CHICKEN_BLOCK.get());
        helper.assertTrue(ItemStack.matches(point.insert(null, wholeChicken, false), wholeChicken), "This release must not refresh leftovers");
        helper.assertTrue(helper.getBlockState(position).is(FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_LEFTOVER.get()), "Terminal leftovers changed");
        helper.succeed();
    }
}
