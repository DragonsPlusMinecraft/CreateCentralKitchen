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
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.common.CCKCommon;
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
}
