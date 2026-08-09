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

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.deployer.BeltDeployerCallbacks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.farmersdelight.recipe.CuttingBoardDeployerRecipe;
import plus.dragons.createcentralkitchen.integration.farmersdelight.recipe.CuttingBoardRecipeConverters;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

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
}
