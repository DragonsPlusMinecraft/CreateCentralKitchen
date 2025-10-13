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
    private static final ResourceLocation FIERY_COOKING_POT = ModIntegration.TWILIGHTDELIGHT.asResource("fiery_cooking_pot");
    private static final ResourceLocation POTTERY_COOKING_POT = ModIntegration.TRAILANDTALESDELIGHT.asResource("pottery_cooking_pot");
    private static final ResourceLocation COPPER_POT = ModIntegration.MINERSDELIGHT.asResource("copper_pot");
    private static final ResourceLocation SKILLET = ModIntegration.FARMERSDELIGHT.asResource("skillet");
    private static final ResourceLocation STOVE = ModIntegration.FARMERSDELIGHT.asResource("stove");
    private static final ResourceLocation NETHER_BRICKS_STOVE = ModIntegration.MYNETHERSDELIGHT.asResource("nether_bricks_stove");
    private static final ResourceLocation NETHER_BRICKS_SOUL_STOVE = ModIntegration.MYNETHERSDELIGHT.asResource("nether_bricks_soul_stove");
    private static final ResourceLocation END_STOVE = ModIntegration.ENDSDELIGHT.asResource("end_stove");
    private static final ResourceLocation MAZE_STOVE = ModIntegration.TWILIGHTDELIGHT.asResource("maze_stove");
    private static final ResourceLocation MUD_STOVE = ModIntegration.TRAILANDTALESDELIGHT.asResource("mud_stove");
    private static final ResourceLocation DUNGEON_STOVE = ModIntegration.DUNGEONSDELIGHT.asResource("dungeon_stove");
    private static final ResourceLocation MONSTER_POT = ModIntegration.DUNGEONSDELIGHT.asResource("monster_pot");
    private static final ResourceLocation DRYING_RACK = ModIntegration.EXTRADELIGHT.asResource("drying_rack");

    public static void register() {
        CCKPonderPlugin.SCENES.add(FDPonderPlugin::registerScenes);
        CCKPonderPlugin.TAGS.add(FDPonderPlugin::registerTags);
    }

    private static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(CUTTING_BOARD)
                .addStoryBoard("farmersdelight/cutting_board", FDPonderScenes::cuttingBoard,
                        AllCreatePonderTags.ARM_TARGETS);
        helper.forComponents(COOKING_POT, FIERY_COOKING_POT, POTTERY_COOKING_POT, COPPER_POT, MONSTER_POT)
                .addStoryBoard("farmersdelight/cooking_pot", FDPonderScenes::cookingPot,
                        AllCreatePonderTags.ARM_TARGETS,
                        AllCreatePonderTags.HIGH_LOGISTICS)
                .addStoryBoard("farmersdelight/heat_source", FDPonderScenes::heatSource);
        helper.forComponents(SKILLET)
                .addStoryBoard("farmersdelight/stove_and_skillet", FDPonderScenes::stoveAndSkillet,
                        AllCreatePonderTags.ARM_TARGETS)
                .addStoryBoard("farmersdelight/heat_source", FDPonderScenes::heatSource);
        helper.forComponents(STOVE, NETHER_BRICKS_STOVE, NETHER_BRICKS_SOUL_STOVE, END_STOVE, MAZE_STOVE, MUD_STOVE, DUNGEON_STOVE)
                .addStoryBoard("farmersdelight/stove_and_skillet", FDPonderScenes::stoveAndSkillet,
                        AllCreatePonderTags.ARM_TARGETS);
    }

    private static void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.addToTag(AllCreatePonderTags.ARM_TARGETS)
                .add(CUTTING_BOARD)
                .add(COOKING_POT)
                .add(FIERY_COOKING_POT)
                .add(POTTERY_COOKING_POT)
                .add(COPPER_POT)
                .add(SKILLET)
                .add(STOVE)
                .add(NETHER_BRICKS_STOVE)
                .add(NETHER_BRICKS_SOUL_STOVE)
                .add(END_STOVE)
                .add(MAZE_STOVE)
                .add(MUD_STOVE)
                .add(DRYING_RACK)
                .add(DUNGEON_STOVE)
                .add(MONSTER_POT);
    }
}
