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

import com.lance5057.extradelight.ExtraDelightRecipes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import java.util.function.Predicate;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.common.CCKCommon;
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
}
