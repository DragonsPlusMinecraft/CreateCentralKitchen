/*
 * Copyright (C) 2025 DragonsPlus
 * SPDX-License-Identifier: LGPL-3.0-or-later
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

import com.simibubi.create.AllTags.AllBlockTags;
import com.teamabnormals.atmospheric.common.block.OrangeBlock;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.ATMOSPHERIC)
@PrefixGameTestTemplate(false)
public class AtmosphericHarvesterGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(AtmosphericHarvesterGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void harvestsAtmosphericOranges(GameTestHelper helper) {
        assertOrangeHarvest(helper, new BlockPos(1, 2, 1), AtmosphericBlocks.ORANGE.get(), 1);
        assertOrangeHarvest(helper, new BlockPos(1, 2, 1), AtmosphericBlocks.ORANGE.get(), 2);
        assertOrangeHarvest(helper, new BlockPos(2, 2, 1), AtmosphericBlocks.BLOOD_ORANGE.get(), 1);
        assertOrangeHarvest(helper, new BlockPos(2, 2, 1), AtmosphericBlocks.BLOOD_ORANGE.get(), 2);
        helper.succeed();
    }

    private static void assertOrangeHarvest(GameTestHelper helper, BlockPos pos, Block orange, int expectedCount) {
        var state = orange.defaultBlockState().setValue(OrangeBlock.ORANGES, expectedCount);
        helper.assertTrue(AllBlockTags.TREE_ATTACHMENTS.matches(state),
                orange + " is not tagged as a Create tree attachment");
        helper.assertTrue(REGISTRY.containsKey(orange),
                orange + " has no mechanical harvester behavior");

        var drops = new ArrayList<ItemStack>();
        var level = helper.getLevel();
        level.setBlock(helper.absolutePos(pos), state, 2);
        AtmosphericHarvesterMovementBehaviorExtensions.harvestOrange(
                level, helper.absolutePos(pos), drops::add);

        helper.assertBlockNotPresent(orange, pos);
        int orangeCount = drops.stream()
                .filter(stack -> stack.is(orange.asItem()))
                .mapToInt(ItemStack::getCount)
                .sum();
        helper.assertTrue(orangeCount == expectedCount,
                "Expected " + expectedCount + " drops from " + orange + ", got " + orangeCount);
    }
}
