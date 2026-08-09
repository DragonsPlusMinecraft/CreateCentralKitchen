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

package plus.dragons.createcentralkitchen.integration.extradelight;

import com.lance5057.extradelight.ExtraDelightBlocks;
import com.lance5057.extradelight.ExtraDelightRecipes;
import com.lance5057.extradelight.workstations.chiller.ChillerBlockEntity;
import com.lance5057.extradelight.workstations.oven.OvenBlockEntity;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.extradelight.packager.ChillerUnpackingHandler;
import plus.dragons.createcentralkitchen.integration.extradelight.packager.OvenUnpackingHandler;
import plus.dragons.createcentralkitchen.mixin.extradelight.MechanicalMixerBlockEntityAccessor;

@GameTestHolder(CCKCommon.ID)
@PrefixGameTestTemplate(false)
public class ExtraDelightRecipeCacheGameTests {
    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void repeatedRecipeFinderLookupsDoNotGrow(GameTestHelper helper) {
        var level = helper.getLevel();
        Predicate<RecipeHolder<? extends Recipe<?>>> conditions = holder -> holder.value().getType() == AllRecipeTypes.MIXING.getType();
        helper.assertTrue(
                !level.getRecipeManager().getAllRecipesFor(ExtraDelightRecipes.MELTING_POT.get()).isEmpty(),
                "Extra Delight must provide a melting pot recipe for this regression test");

        var cacheKey = MechanicalMixerBlockEntityAccessor.getShapelessOrMixingRecipesKey();
        int expectedSize = RecipeFinder.get(cacheKey, level, conditions).size();
        for (int lookup = 0; lookup < 1_000; lookup++) {
            int actualSize = RecipeFinder.get(cacheKey, level, conditions).size();
            helper.assertValueEqual(
                    actualSize,
                    expectedSize,
                    "Repeated RecipeFinder lookup changed the cached recipe count at lookup " + lookup);
        }
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void packagersUnpackOvenIngredientsFromEveryUsableSide(GameTestHelper helper) {
        var ovenPosition = new BlockPos(1, 2, 1);
        helper.setBlock(ovenPosition, ExtraDelightBlocks.OVEN.get().defaultBlockState());
        var oven = helper.getBlockEntity(ovenPosition);
        helper.assertTrue(oven instanceof OvenBlockEntity, "The test oven must have a block entity");
        helper.assertTrue(
                UnpackingHandler.REGISTRY.get(helper.getBlockState(ovenPosition)) instanceof OvenUnpackingHandler,
                "Extra Delight's oven must have CCK's unpacking handler registered");

        var ingredients = List.of(
                new ItemStack(Items.CARROT),
                new ItemStack(Items.POTATO),
                new ItemStack(Items.BEETROOT),
                new ItemStack(Items.WHEAT),
                new ItemStack(Items.BROWN_MUSHROOM),
                new ItemStack(Items.RED_MUSHROOM),
                new ItemStack(Items.APPLE),
                new ItemStack(Items.SUGAR),
                new ItemStack(Items.EGG));
        for (var side : List.of(Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            var packagerPosition = ovenPosition.relative(side);
            helper.setBlock(
                    packagerPosition,
                    AllBlocks.PACKAGER.getDefaultState().setValue(PackagerBlock.FACING, side));
            var packager = helper.getBlockEntity(packagerPosition);
            helper.assertTrue(packager instanceof PackagerBlockEntity, "The test packager must have a block entity");

            var remainder = ((PackagerBlockEntity) packager).inventory
                    .insertItem(0, PackageItem.containing(ingredients), false);
            helper.assertTrue(remainder.isEmpty(), "Packager failed to unpack a complete oven recipe from side " + side);
            for (int slot = 0; slot < ingredients.size(); slot++) {
                helper.assertTrue(
                        ItemStack.isSameItemSameComponents(
                                ((OvenBlockEntity) oven).getInventory().getStackInSlot(slot),
                                ingredients.get(slot)),
                        "Oven ingredient slot " + slot + " was not filled from side " + side);
                ((OvenBlockEntity) oven).getInventory().setStackInSlot(slot, ItemStack.EMPTY);
            }

            helper.setBlock(packagerPosition, Blocks.AIR);
        }
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void packagersUnpackChillerIngredientsFromEveryUsableSide(GameTestHelper helper) {
        var chillerPosition = new BlockPos(1, 2, 1);
        helper.setBlock(chillerPosition, ExtraDelightBlocks.CHILLER.get().defaultBlockState());
        var chiller = helper.getBlockEntity(chillerPosition);
        helper.assertTrue(chiller instanceof ChillerBlockEntity, "The test chiller must have a block entity");
        helper.assertTrue(
                UnpackingHandler.REGISTRY.get(helper.getBlockState(chillerPosition)) instanceof ChillerUnpackingHandler,
                "Extra Delight's chiller must have CCK's unpacking handler registered");

        var ingredients = List.of(
                new ItemStack(Items.CARROT),
                new ItemStack(Items.POTATO),
                new ItemStack(Items.BEETROOT),
                new ItemStack(Items.WHEAT));
        for (var side : List.of(Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            var packagerPosition = chillerPosition.relative(side);
            helper.setBlock(
                    packagerPosition,
                    AllBlocks.PACKAGER.getDefaultState().setValue(PackagerBlock.FACING, side));
            var packager = helper.getBlockEntity(packagerPosition);
            helper.assertTrue(packager instanceof PackagerBlockEntity, "The test packager must have a block entity");

            var remainder = ((PackagerBlockEntity) packager).inventory
                    .insertItem(0, PackageItem.containing(ingredients), false);
            helper.assertTrue(remainder.isEmpty(), "Packager failed to unpack a complete chiller recipe from side " + side);
            for (int slot = 0; slot < ingredients.size(); slot++) {
                helper.assertTrue(
                        ItemStack.isSameItemSameComponents(
                                ((ChillerBlockEntity) chiller).getInventory().getStackInSlot(slot),
                                ingredients.get(slot)),
                        "Chiller ingredient slot " + slot + " was not filled from side " + side);
                ((ChillerBlockEntity) chiller).getInventory().setStackInSlot(slot, ItemStack.EMPTY);
            }

            helper.setBlock(packagerPosition, Blocks.AIR);
        }
        helper.succeed();
    }
}
