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

package plus.dragons.createcentralkitchen.integration.farmersdelight;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.content.kinetics.deployer.BeltDeployerCallbacks;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.farmersdelight.recipe.CuttingBoardDeployerRecipe;
import plus.dragons.createcentralkitchen.integration.farmersdelight.recipe.CuttingBoardRecipeConverters;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.common.tag.ModTags;

@GameTestHolder(CCKCommon.ID)
@PrefixGameTestTemplate(false)
public class FarmersDelightRecipeGameTests {
    private static final int SAWING_DURATION = 50;

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void convertedCuttingBoardRecipesHaveSawingDuration(GameTestHelper helper) {
        var recipes = helper.getLevel()
                .getRecipeManager()
                .getAllRecipesFor(ModRecipeTypes.CUTTING.get())
                .stream()
                .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                .filter(CuttingBoardRecipeConverters::canSaw)
                .toList();
        helper.assertTrue(!recipes.isEmpty(), "Farmer's Delight must provide a cutting board recipe usable by a saw");

        for (var recipe : recipes) {
            var converted = CuttingBoardRecipeConverters.SAWING.apply(recipe);
            helper.assertValueEqual(
                    converted.value().getProcessingDuration(),
                    SAWING_DURATION,
                    "Converted sawing duration for " + recipe.id());
        }
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void convertedCuttingBoardRecipesDoNotConsumeHeldTools(GameTestHelper helper) {
        helper.assertTrue(
                BeltDeployerCallbacks.class.getDeclaredMethods().length > 0,
                "Create's belt deployer callback must load with the cutting tool mixin");
        var recipes = helper.getLevel()
                .getRecipeManager()
                .getAllRecipesFor(ModRecipeTypes.CUTTING.get())
                .stream()
                .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                .toList();
        helper.assertTrue(!recipes.isEmpty(), "Farmer's Delight must provide a cutting board recipe");

        for (var recipe : recipes) {
            var converted = CuttingBoardRecipeConverters.DEPLOYING.apply(recipe);
            helper.assertTrue(
                    converted.value().shouldKeepHeldItem(),
                    "Converted deploying recipe must not consume its held tool: " + recipe.id());
            helper.assertTrue(
                    converted.value() instanceof CuttingBoardDeployerRecipe,
                    "Converted deploying recipe must retain cutting board tool semantics: " + recipe.id());
        }

        var converted = (CuttingBoardDeployerRecipe) CuttingBoardRecipeConverters.DEPLOYING.apply(recipes.getFirst()).value();
        var damageableKnife = CuttingBoardRecipeConverters.sawAsKnife();
        helper.assertTrue(damageableKnife.isDamageableItem(), "The test knife must normally take durability damage");
        helper.assertTrue(!converted.shouldKeepHeldTool(damageableKnife), "A normal knife must take durability damage");

        var unbreakableKnife = damageableKnife.copy();
        unbreakableKnife.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
        helper.assertTrue(!unbreakableKnife.isDamageableItem(), "The test knife must be unbreakable");
        helper.assertTrue(converted.shouldKeepHeldTool(unbreakableKnife), "An unbreakable knife must be retained");

        var nonDamageableTool = new ItemStack(Items.PAPER);
        helper.assertTrue(!nonDamageableTool.isDamageableItem(), "The test sheet substitute must have no durability");
        helper.assertTrue(converted.shouldKeepHeldTool(nonDamageableTool), "A tool without durability must be retained");
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void packagersUnpackCookingIngredientsFromEveryUsableSide(GameTestHelper helper) {
        var potPosition = new BlockPos(1, 2, 1);
        helper.setBlock(potPosition, ModBlocks.COOKING_POT.get().defaultBlockState());
        var cookingPot = helper.getBlockEntity(potPosition);
        helper.assertTrue(cookingPot instanceof CookingPotBlockEntity, "The test cooking pot must have a block entity");

        var ingredients = List.of(
                new ItemStack(Items.CARROT),
                new ItemStack(Items.POTATO),
                new ItemStack(Items.BEETROOT),
                new ItemStack(Items.WHEAT),
                new ItemStack(Items.BROWN_MUSHROOM),
                new ItemStack(Items.RED_MUSHROOM));
        for (var side : List.of(Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            var packagerPosition = potPosition.relative(side);
            helper.setBlock(
                    packagerPosition,
                    AllBlocks.PACKAGER.getDefaultState().setValue(PackagerBlock.FACING, side));
            var packager = helper.getBlockEntity(packagerPosition);
            helper.assertTrue(packager instanceof PackagerBlockEntity, "The test packager must have a block entity");

            var remainder = ((PackagerBlockEntity) packager).inventory
                    .insertItem(0, PackageItem.containing(ingredients), false);
            helper.assertTrue(remainder.isEmpty(), "Packager failed to unpack a complete recipe from side " + side);
            for (int slot = 0; slot < ingredients.size(); slot++) {
                helper.assertTrue(
                        ItemStack.isSameItemSameComponents(
                                ((CookingPotBlockEntity) cookingPot).getInventory().getStackInSlot(slot),
                                ingredients.get(slot)),
                        "Cooking pot ingredient slot " + slot + " was not filled from side " + side);
            }

            ((CookingPotBlockEntity) cookingPot).clearContent();
            helper.setBlock(packagerPosition, Blocks.AIR);
        }
        helper.succeed();
    }

    @GameTest(templateNamespace = Create.ID, template = "gametest/processing/iron_compacting")
    public static void untaggedBoilerHeatersDoNotHeatCookingPots(GameTestHelper helper) {
        var potPosition = new BlockPos(1, 2, 1);
        var heaterPosition = potPosition.below();
        helper.setBlock(potPosition, ModBlocks.COOKING_POT.get().defaultBlockState());
        var cookingPot = helper.getBlockEntity(potPosition);
        helper.assertTrue(cookingPot instanceof CookingPotBlockEntity, "The test cooking pot must have a block entity");

        var activeBurner = AllBlocks.BLAZE_BURNER.getDefaultState()
                .setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.KINDLED);
        helper.setBlock(heaterPosition, activeBurner);
        helper.assertTrue(
                helper.getBlockState(heaterPosition).is(ModTags.Blocks.HEAT_SOURCES),
                "CCK must add Blaze Burners to Farmer's Delight heat sources");
        helper.assertTrue(
                ((CookingPotBlockEntity) cookingPot).isHeated(helper.getLevel(), helper.absolutePos(potPosition)),
                "An active tagged Blaze Burner must heat a cooking pot");

        helper.setBlock(heaterPosition, Blocks.DIAMOND_BLOCK.defaultBlockState());
        helper.assertTrue(
                !helper.getBlockState(heaterPosition).is(ModTags.Blocks.HEAT_SOURCES),
                "The synthetic boiler heater must not be a Farmer's Delight heat source");
        BoilerHeater.REGISTRY.register(Blocks.DIAMOND_BLOCK, (level, position, state) -> BoilerHeater.PASSIVE_HEAT);

        helper.assertTrue(
                !((CookingPotBlockEntity) cookingPot).isHeated(helper.getLevel(), helper.absolutePos(potPosition)),
                "A boiler heater removed from farmersdelight:heat_sources must not heat a cooking pot");
        helper.succeed();
    }
}
