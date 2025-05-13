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

package plus.dragons.createcentralkitchen.integration.farmersdelight.ponder;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.client.ponder.CCKPonderPlugin;
import plus.dragons.createcentralkitchen.integration.ModIntegration;

public class FDPonderPlugin {
    private static final ResourceLocation CUTTING_BOARD = ModIntegration.FARMERSDELIGHT.asResource("cutting_board");
    private static final ResourceLocation COOKING_POT = ModIntegration.FARMERSDELIGHT.asResource("cooking_pot");
    private static final ResourceLocation SKILLET = ModIntegration.FARMERSDELIGHT.asResource("skillet");
    private static final ResourceLocation STOVE = ModIntegration.FARMERSDELIGHT.asResource("stove");

    public static void register() {
        CCKPonderPlugin.SCENES.add(FDPonderPlugin::registerScenes);
        CCKPonderPlugin.TAGS.add(FDPonderPlugin::registerTags);
    }

    private static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(CUTTING_BOARD)
                .addStoryBoard("farmersdelight/cutting_board", FDPonderScenes::cuttingBoard,
                        AllCreatePonderTags.ARM_TARGETS);
        helper.forComponents(COOKING_POT)
                .addStoryBoard("farmersdelight/cooking_pot_automate", FDPonderScenes::automate,
                        AllCreatePonderTags.ARM_TARGETS,
                        AllCreatePonderTags.HIGH_LOGISTICS)
                .addStoryBoard("farmersdelight/heat_source", FDPonderScenes::heatSource);
        helper.forComponents(SKILLET)
                .addStoryBoard("farmersdelight/stove_and_skillet", FDPonderScenes::skilletAndStove,
                        AllCreatePonderTags.ARM_TARGETS)
                .addStoryBoard("farmersdelight/heat_source", FDPonderScenes::heatSource);
        helper.forComponents(STOVE)
                .addStoryBoard("farmersdelight/stove_and_skillet", FDPonderScenes::skilletAndStove,
                        AllCreatePonderTags.ARM_TARGETS);
    }

    private static void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.addToTag(AllCreatePonderTags.ARM_TARGETS)
                .add(CUTTING_BOARD)
                .add(COOKING_POT)
                .add(SKILLET)
                .add(STOVE);
    }
}
