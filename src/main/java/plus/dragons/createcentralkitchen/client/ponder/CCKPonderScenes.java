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

package plus.dragons.createcentralkitchen.client.ponder;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.client.ponder.scene.BrewinAndChewinScene;
import plus.dragons.createcentralkitchen.client.ponder.scene.FarmersDelightScene;
import plus.dragons.createcentralkitchen.integration.ModIntegration;

public class CCKPonderScenes {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> sceneHelper = helper.withKeyFunction(RegistryEntry::getId);

        if (ModIntegration.FARMERSDELIGHT.enabled()) {
            sceneHelper.forComponents(CCKPonderPlugin.COOKING_POT)
                    .addStoryBoard("farmersdelight/cooking_pot_automate", FarmersDelightScene::automate, AllCreatePonderTags.ARM_TARGETS, AllCreatePonderTags.HIGH_LOGISTICS)
                    .addStoryBoard("farmersdelight/heat_source", FarmersDelightScene::heatSource);

            sceneHelper.forComponents(CCKPonderPlugin.SKILLET)
                    .addStoryBoard("farmersdelight/stove_and_skillet", FarmersDelightScene::skilletAndStove, AllCreatePonderTags.ARM_TARGETS)
                    .addStoryBoard("farmersdelight/heat_source", FarmersDelightScene::heatSource);

            sceneHelper.forComponents(CCKPonderPlugin.STOVE)
                    .addStoryBoard("farmersdelight/stove_and_skillet", FarmersDelightScene::skilletAndStove, AllCreatePonderTags.ARM_TARGETS);

            sceneHelper.forComponents(CCKPonderPlugin.CUTTING_BOARD)
                    .addStoryBoard("farmersdelight/cutting_board", FarmersDelightScene::cuttingBoard, AllCreatePonderTags.ARM_TARGETS);
        }

        if (ModIntegration.BREWINANDCHEWIN.enabled()) {
            sceneHelper.forComponents(CCKPonderPlugin.KEG)
                    .addStoryBoard("brewinandchewin/keg_fluid", BrewinAndChewinScene::kegAutomate1, AllCreatePonderTags.FLUIDS)
                    .addStoryBoard("brewinandchewin/keg_ingredient", BrewinAndChewinScene::kegAutomate2, AllCreatePonderTags.HIGH_LOGISTICS)
                    .addStoryBoard("brewinandchewin/spout_and_item_drain", BrewinAndChewinScene::spoutAndItemDrain, AllCreatePonderTags.FLUIDS);
        }
    }
}
