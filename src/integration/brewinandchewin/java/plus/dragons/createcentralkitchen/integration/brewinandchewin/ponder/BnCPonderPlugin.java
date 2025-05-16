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

package plus.dragons.createcentralkitchen.integration.brewinandchewin.ponder;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.client.ponder.CCKPonderPlugin;
import plus.dragons.createcentralkitchen.integration.ModIntegration;

public class BnCPonderPlugin {
    private static final ResourceLocation KEG = ModIntegration.BREWINANDCHEWIN.asResource("keg");

    public static void register() {
        CCKPonderPlugin.SCENES.add(BnCPonderPlugin::registerScenes);
        CCKPonderPlugin.TAGS.add(BnCPonderPlugin::registerTags);
    }

    private static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(KEG)
                .addStoryBoard("brewinandchewin/keg_fluid", BnCPonderScenes::kegAutomate1,
                        AllCreatePonderTags.FLUIDS)
                .addStoryBoard("brewinandchewin/keg_ingredient", BnCPonderScenes::kegAutomate2,
                        AllCreatePonderTags.HIGH_LOGISTICS)
                .addStoryBoard("brewinandchewin/keg_pouring", BnCPonderScenes::kegPouring,
                        AllCreatePonderTags.FLUIDS);
    }

    private static void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.addToTag(AllCreatePonderTags.FLUIDS).add(KEG);
    }
}
