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

package plus.dragons.createcentralkitchen.integration.minersdelight;

import com.sammy.minersdelight.setup.MDBlockEntities;
import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.minersdelight.handler.CopperPotUnpackingHandler;
import plus.dragons.createcentralkitchen.integration.minersdelight.registry.MDArmInteractionPointTypes;

@Mod(CCKCommon.ID)
public class MDIntegration {
    public MDIntegration(IEventBus modBus) {
        if (ModIntegration.MINERSDELIGHT.enabled()) {
            modBus.register(new MDIntegration.Common());
        }
    }

    public static class Common {
        @SubscribeEvent
        public void construct(final FMLConstructModEvent event) {
            MDArmInteractionPointTypes.register();
        }

        @SubscribeEvent
        public void setup(final FMLCommonSetupEvent event) {
            event.enqueueWork(this::registerUnpackingHandlers);
        }

        private void registerUnpackingHandlers() {
            MDBlockEntities.COPPER_POT.get()
                    .getValidBlocks()
                    .forEach(block -> UnpackingHandler.REGISTRY.register(block, new CopperPotUnpackingHandler()));
        }
    }
}
