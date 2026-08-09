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

package plus.dragons.createcentralkitchen.integration.hearthandharvest;

import alabaster.hearthandharvest.common.block.entity.CaskBlockEntity;
import alabaster.hearthandharvest.common.crafting.CaskRecipe;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.items.ItemStackHandler;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.hearthandharvest.cask.CaskRecipeSelection;
import plus.dragons.createcentralkitchen.integration.hearthandharvest.mechanicalArm.CaskArmInteractionPoint;

@GameTestHolder(CCKCommon.ID)
@PrefixGameTestTemplate(false)
public class HearthAndHarvestGameTests {
    private static final BlockPos CASK_POSITION = new BlockPos(1, 2, 1);

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void caskArmUsesRecipeBoundedSlots(GameTestHelper helper) {
        var cask = placeCask(helper);
        var point = new CaskArmInteractionPoint.Type()
                .createPoint(
                        helper.getLevel(),
                        helper.absolutePos(CASK_POSITION),
                        helper.getBlockState(CASK_POSITION));
        helper.assertTrue(point != null, "A Cask must create an arm interaction point");
        var inventory = cask.getInventory();
        var salt = HHModItems.SALT.get().getDefaultInstance();
        var meat = new ItemStack(Items.BEEF, 3);

        helper.assertTrue(point.insert(null, salt, true).isEmpty(), "A valid simulated ingredient must be accepted");
        assertInputsEmpty(helper, inventory, "Insertion simulation changed the Cask");
        helper.assertTrue(point.insert(null, salt, false).isEmpty(), "Salt must start a valid jerky recipe");
        helper.assertTrue(inventory.getStackInSlot(0).is(HHModItems.SALT.get()), "Salt was not inserted into the first empty slot");

        helper.assertTrue(point.insert(null, meat, true).isEmpty(), "All three repeated meat units must fit the recipe");
        helper.assertValueEqual(occupiedInputSlots(inventory), 1, "Repeated-ingredient simulation changed the Cask");
        helper.assertTrue(point.insert(null, meat, false).isEmpty(), "All three repeated meat units must be accepted");
        for (int slot = 1; slot < 4; slot++) {
            helper.assertTrue(inventory.getStackInSlot(slot).is(Items.BEEF), "Repeated meat was not split into slot " + slot);
            helper.assertValueEqual(inventory.getStackInSlot(slot).getCount(), 1, "Repeated meat stacked within slot " + slot);
        }
        helper.assertTrue(inventory.getStackInSlot(4).isEmpty(), "A complete recipe was processed during insertion");
        helper.assertTrue(
                ItemStack.matches(point.insert(null, Items.BEEF.getDefaultInstance(), false), Items.BEEF.getDefaultInstance()),
                "A full recipe accepted an extra input");

        cask.clearContent();
        var cheese = new ItemStack(HHModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get(), 4);
        var cheeseRemainder = point.insert(null, cheese, false);
        helper.assertValueEqual(cheeseRemainder.getCount(), 3, "A single-ingredient recipe accepted more than one unit");
        helper.assertValueEqual(occupiedInputSlots(inventory), 1, "A single-ingredient recipe filled multiple slots");

        cask.clearContent();
        inventory.setStackInSlot(4, Items.DIAMOND.getDefaultInstance());
        helper.assertTrue(ItemStack.matches(point.insert(null, salt, false), salt), "A conflicting output slot accepted inputs");
        assertInputsEmpty(helper, inventory, "Output conflict caused a partial insertion");

        inventory.setStackInSlot(4, new ItemStack(HHModItems.JERKY.get(), 3));
        var simulatedOutput = point.extract(null, 0, 2, true);
        helper.assertValueEqual(simulatedOutput.getCount(), 2, "Output simulation returned the wrong quantity");
        helper.assertValueEqual(inventory.getStackInSlot(4).getCount(), 3, "Output simulation changed the Cask");
        var extracted = point.extract(null, 0, 2, false);
        helper.assertTrue(extracted.is(HHModItems.JERKY.get()), "The arm extracted the wrong Cask output");
        helper.assertValueEqual(extracted.getCount(), 2, "The arm extracted the wrong output quantity");
        helper.assertValueEqual(inventory.getStackInSlot(4).getCount(), 1, "Output extraction changed the wrong slot");
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void caskPackagerRejectsPartialAndConflictingPackages(GameTestHelper helper) {
        var cask = placeCask(helper);
        var inventory = cask.getInventory();
        var packagerPosition = CASK_POSITION.relative(Direction.NORTH);
        var salt = HHModItems.SALT.get().getDefaultInstance();
        var complete = List.of(salt, new ItemStack(Items.BEEF, 3));

        var simulatedPackage = PackageItem.containing(complete);
        helper.assertTrue(
                insertPackage(helper, packagerPosition, simulatedPackage, true).isEmpty(),
                "A complete Cask recipe must pass package simulation");
        assertInputsEmpty(helper, inventory, "Package simulation changed the Cask");
        helper.assertTrue(
                insertPackage(helper, packagerPosition, PackageItem.containing(complete), false).isEmpty(),
                "A complete Cask recipe package was rejected");
        helper.assertTrue(inventory.getStackInSlot(0).is(HHModItems.SALT.get()), "Package recipe order was not preserved");
        for (int slot = 1; slot < 4; slot++) {
            helper.assertTrue(inventory.getStackInSlot(slot).is(Items.BEEF), "Packaged repeated item missed slot " + slot);
            helper.assertValueEqual(inventory.getStackInSlot(slot).getCount(), 1, "Packaged repeated item stacked in slot " + slot);
        }
        helper.assertTrue(inventory.getStackInSlot(4).isEmpty(), "Package insertion processed the recipe immediately");

        cask.clearContent();
        var partial = PackageItem.containing(List.of(salt, new ItemStack(Items.BEEF, 2)));
        helper.assertTrue(
                ItemStack.matches(insertPackage(helper, packagerPosition, partial, false), partial),
                "A missing ingredient package was accepted");
        assertInputsEmpty(helper, inventory, "A missing ingredient package caused a partial write");

        var extra = PackageItem.containing(List.of(salt, new ItemStack(Items.BEEF, 4)));
        helper.assertTrue(
                ItemStack.matches(insertPackage(helper, packagerPosition, extra, false), extra),
                "An extra ingredient package was accepted");
        assertInputsEmpty(helper, inventory, "An extra ingredient package caused a partial write");

        var wrong = PackageItem.containing(List.of(new ItemStack(Items.DIAMOND, 4)));
        helper.assertTrue(
                ItemStack.matches(insertPackage(helper, packagerPosition, wrong, false), wrong),
                "An unrelated package was accepted");
        assertInputsEmpty(helper, inventory, "An unrelated package caused a partial write");

        inventory.setStackInSlot(4, Items.DIAMOND.getDefaultInstance());
        var conflict = PackageItem.containing(complete);
        helper.assertTrue(
                ItemStack.matches(insertPackage(helper, packagerPosition, conflict, false), conflict),
                "A package ignored an output conflict");
        assertInputsEmpty(helper, inventory, "An output conflict caused a partial write");

        inventory.setStackInSlot(4, ItemStack.EMPTY);
        inventory.setStackInSlot(0, salt);
        var occupied = PackageItem.containing(complete);
        helper.assertTrue(
                ItemStack.matches(insertPackage(helper, packagerPosition, occupied, false), occupied),
                "A package wrote into a non-empty Cask");
        helper.assertValueEqual(occupiedInputSlots(inventory), 1, "A non-empty Cask was partially overwritten");
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void caskSelectionIsDeterministicAndOneToOne(GameTestHelper helper) {
        var inventory = new ItemStackHandler(5);
        var later = recipe("create_central_kitchen:test_z", Items.PAPER.getDefaultInstance(), Items.APPLE, Items.CARROT);
        var earlier = recipe("create_central_kitchen:test_a", Items.BOOK.getDefaultInstance(), Items.APPLE, Items.POTATO);
        var deterministic = CaskRecipeSelection.findInsertion(
                List.of(later, earlier), inventory, Items.APPLE.getDefaultInstance());
        helper.assertTrue(deterministic.isPresent(), "Synthetic overlapping Cask recipes did not match");
        helper.assertValueEqual(
                deterministic.get().recipe().id(), earlier.id(), "Overlapping recipes were not selected by ID");

        var fourUnique = recipe(
                "create_central_kitchen:four_unique",
                Items.BOOK.getDefaultInstance(),
                Items.APPLE,
                Items.CARROT,
                Items.POTATO,
                Items.BEETROOT);
        var uniquePlan = CaskRecipeSelection.findUnpacking(
                List.of(fourUnique),
                inventory,
                List.of(
                        Items.BEETROOT.getDefaultInstance(),
                        Items.POTATO.getDefaultInstance(),
                        Items.APPLE.getDefaultInstance(),
                        Items.CARROT.getDefaultInstance()));
        helper.assertTrue(uniquePlan.isPresent(), "Four unique ingredients did not match one-to-one");
        helper.assertTrue(uniquePlan.get().ingredients().get(0).is(Items.APPLE), "Recipe-order assignment lost the apple");
        helper.assertTrue(uniquePlan.get().ingredients().get(3).is(Items.BEETROOT), "Recipe-order assignment lost the beetroot");

        inventory.setStackInSlot(0, HHModItems.SALT.get().getDefaultInstance());
        var repeated = recipe(
                "create_central_kitchen:three_repeated",
                new ItemStack(HHModItems.JERKY.get(), 3),
                HHModItems.SALT.get(),
                Items.BEEF,
                Items.BEEF,
                Items.BEEF);
        var repeatedPlan = CaskRecipeSelection.findInsertion(
                List.of(repeated), inventory, new ItemStack(Items.BEEF, 4));
        helper.assertTrue(repeatedPlan.isPresent(), "Three repeated ingredients did not match one-to-one");
        helper.assertValueEqual(repeatedPlan.get().targetSlots().size(), 3, "Repeated matching exceeded or missed the recipe");
        helper.succeed();
    }

    private static CaskBlockEntity placeCask(GameTestHelper helper) {
        helper.setBlock(CASK_POSITION, HHModBlocks.CASK.get().defaultBlockState());
        var blockEntity = helper.getBlockEntity(CASK_POSITION);
        helper.assertTrue(blockEntity instanceof CaskBlockEntity, "The test Cask must have a block entity");
        return (CaskBlockEntity) blockEntity;
    }

    private static ItemStack insertPackage(
            GameTestHelper helper, BlockPos packagerPosition, ItemStack pack, boolean simulate) {
        helper.setBlock(packagerPosition, Blocks.AIR);
        helper.setBlock(
                packagerPosition,
                AllBlocks.PACKAGER.getDefaultState().setValue(PackagerBlock.FACING, Direction.NORTH));
        var blockEntity = helper.getBlockEntity(packagerPosition);
        helper.assertTrue(blockEntity instanceof PackagerBlockEntity, "The test Packager must have a block entity");
        return ((PackagerBlockEntity) blockEntity).inventory.insertItem(0, pack, simulate);
    }

    private static RecipeHolder<CaskRecipe> recipe(
            String id, ItemStack output, net.minecraft.world.level.ItemLike... ingredients) {
        var ingredientList = NonNullList.<Ingredient>create();
        for (var ingredient : ingredients)
            ingredientList.add(Ingredient.of(ingredient));
        return new RecipeHolder<>(
                ResourceLocation.parse(id),
                new CaskRecipe(null, ingredientList, output, 0, 200));
    }

    private static void assertInputsEmpty(GameTestHelper helper, ItemStackHandler inventory, String message) {
        helper.assertValueEqual(occupiedInputSlots(inventory), 0, message);
    }

    private static int occupiedInputSlots(ItemStackHandler inventory) {
        int occupied = 0;
        for (int slot = 0; slot < CaskRecipeSelection.INPUT_SLOTS; slot++) {
            if (!inventory.getStackInSlot(slot).isEmpty())
                occupied++;
        }
        return occupied;
    }
}
