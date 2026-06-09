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

package plus.dragons.createcentralkitchen.integration.dungeonsdelight;

import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.yirmiri.dungeonsdelight.core.registry.DDBlockEntities;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.dungeonsdelight.burner.DungeonBlazeChefRenderOverride;
import plus.dragons.createcentralkitchen.integration.dungeonsdelight.data.DungeonsDelightTagsProvider;
import plus.dragons.createcentralkitchen.integration.dungeonsdelight.handler.MonsterPotUnpackingHandler;
import plus.dragons.createcentralkitchen.integration.dungeonsdelight.registery.DungeonDelightArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.integration.farmersdelight.burner.BlazeChefRenderOverrides;

@Mod(CCKCommon.ID)
public class DungeonDelightIntegration {
    public DungeonDelightIntegration(IEventBus modBus) {
        if (ModIntegration.DUNGEONSDELIGHT.enabled()) {
            modBus.register(new DungeonDelightIntegration.Common());
            if (FMLLoader.getDist() == Dist.CLIENT)
                modBus.register(new Client());
        }
    }

    public static class Common {
        @SubscribeEvent
        public void construct(final FMLConstructModEvent event) {
            DungeonDelightArmInteractionPointTypes.register();
            DungeonsDelightTagsProvider.register();
        }

        @SubscribeEvent
        public void setup(final FMLCommonSetupEvent event) {
            event.enqueueWork(this::registerUnpackingHandlers);
        }

        private void registerUnpackingHandlers() {
            DDBlockEntities.MONSTER_COOKING_POT.get()
                    .getValidBlocks()
                    .forEach(block -> UnpackingHandler.REGISTRY.register(block, new MonsterPotUnpackingHandler()));
        }
    }

    public static class Client {
        @SubscribeEvent
        public void construct(final FMLConstructModEvent event) {
            BlazeChefRenderOverrides.register(BlazeChefRenderOverrides.SPECIAL_PRIORITY, new DungeonBlazeChefRenderOverride());
        }
    }
}
