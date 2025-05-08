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
import plus.dragons.createcentralkitchen.client.ponder.scene.KegScene;

public class CCKPonderScenes {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> sceneHelper = helper.withKeyFunction(RegistryEntry::getId);

        CCKPonderPlugin.KEG.asOptional().ifPresent(block -> sceneHelper.forComponents(CCKPonderPlugin.KEG)
                .addStoryBoard("keg/fluid", KegScene::part1, AllCreatePonderTags.FLUIDS)
                .addStoryBoard("keg/ingredient", KegScene::part2, AllCreatePonderTags.HIGH_LOGISTICS));
    }
}
