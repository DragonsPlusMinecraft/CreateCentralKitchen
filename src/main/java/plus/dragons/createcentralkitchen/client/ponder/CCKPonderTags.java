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

import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.ARM_TARGETS;
import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.FLUIDS;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import plus.dragons.createcentralkitchen.integration.ModIntegration;

public class CCKPonderTags {
    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?, ?>> entryHelper = helper.withKeyFunction(RegistryEntry::getId);

        PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(
                RegisteredObjectsHelper::getKeyOrThrow);

        if(ModIntegration.FARMERSDELIGHT.enabled()){
            entryHelper.addToTag(ARM_TARGETS).add(CCKPonderPlugin.COOKING_POT);
            entryHelper.addToTag(ARM_TARGETS).add(CCKPonderPlugin.STOVE);
            entryHelper.addToTag(ARM_TARGETS).add(CCKPonderPlugin.SKILLET);
            entryHelper.addToTag(ARM_TARGETS).add(CCKPonderPlugin.CUTTING_BOARD);
        }

        if(ModIntegration.BREWINANDCHEWIN.enabled()){
            entryHelper.addToTag(FLUIDS).add(CCKPonderPlugin.KEG);
        }
    }
}
