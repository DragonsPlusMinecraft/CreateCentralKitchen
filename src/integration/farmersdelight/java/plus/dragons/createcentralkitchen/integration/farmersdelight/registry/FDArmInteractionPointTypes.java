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

package plus.dragons.createcentralkitchen.integration.farmersdelight.registry;

import static plus.dragons.createcentralkitchen.common.registry.CCKArmInteractionPointTypes.*;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.neoforged.neoforge.registries.DeferredHolder;
import plus.dragons.createcentralkitchen.common.registry.CCKArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.integration.farmersdelight.mechanicalArm.CookingPotArmInteractionPoint;
import plus.dragons.createcentralkitchen.integration.farmersdelight.mechanicalArm.CuttingBoardArmInteractionPoint;
import plus.dragons.createcentralkitchen.integration.farmersdelight.mechanicalArm.PortionableFoodArmInteractionPoint;
import plus.dragons.createcentralkitchen.integration.farmersdelight.mechanicalArm.SkilletArmInteractionPoint;
import plus.dragons.createcentralkitchen.integration.farmersdelight.mechanicalArm.StoveArmInteractionPoint;

public class FDArmInteractionPointTypes {
    //spotless:off
    public static final DeferredHolder<ArmInteractionPointType, ArmInteractionPointType>
            COOKING_POT = holder("cooking_pot"),
            CUTTING_BOARD = holder("cutting_board"),
            PORTIONABLE_FOOD = holder("portionable_food"),
            SKILLET = holder("skillet"),
            STOVE = holder("stove");
    //spotless:on

    public static void register() {
        CCKArmInteractionPointTypes.register(COOKING_POT, CookingPotArmInteractionPoint.Type::new);
        CCKArmInteractionPointTypes.register(CUTTING_BOARD, CuttingBoardArmInteractionPoint.Type::new);
        CCKArmInteractionPointTypes.register(PORTIONABLE_FOOD, PortionableFoodArmInteractionPoint.Type::new);
        CCKArmInteractionPointTypes.register(SKILLET, SkilletArmInteractionPoint.Type::new);
        CCKArmInteractionPointTypes.register(STOVE, StoveArmInteractionPoint.Type::new);
    }
}
