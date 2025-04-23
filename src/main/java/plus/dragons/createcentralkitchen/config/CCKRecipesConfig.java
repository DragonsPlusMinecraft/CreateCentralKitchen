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

package plus.dragons.createcentralkitchen.config;

import net.createmod.catnip.config.ConfigBase;

public class CCKRecipesConfig extends ConfigBase {
    public final ConfigBool convertCuttingBoardRecipesToSawingRecipes = b(true,
            "convertCuttingBoardRecipesToSawingRecipes",
            Comments.convertCuttingBoardRecipesToSawingRecipes);
    public final ConfigBool convertCuttingBoardRecipesToDeployingRecipes = b(true,
            "convertCuttingBoardRecipesToDeployingRecipes",
            Comments.convertCuttingBoardRecipesToDeployingRecipes);

    @Override
    public String getName() {
        return "recipes";
    }

    static class Comments {
        static final String convertCuttingBoardRecipesToSawingRecipes = "If Farmer's Delight's Cutting Board Recipes that uses knife can be converted to Sawing Recipes";
        static final String convertCuttingBoardRecipesToDeployingRecipes = "If Farmer's Delight's Cutting Board Recipes can be converted to Deploying Recipes";
    }
}
