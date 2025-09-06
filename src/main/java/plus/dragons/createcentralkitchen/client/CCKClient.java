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

package plus.dragons.createcentralkitchen.client;

import static plus.dragons.createcentralkitchen.common.CCKCommon.REGISTRATE;

import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import plus.dragons.createcentralkitchen.client.ponder.CCKPonderPlugin;
import plus.dragons.createcentralkitchen.client.registry.CCKPartialModels;
import plus.dragons.createcentralkitchen.client.registry.CCKSpriteShifts;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.data.CCKLang;

@Mod(value = CCKCommon.ID, dist = Dist.CLIENT)
public class CCKClient {
    public CCKClient(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(CCKClient::clientInit);
        CCKLang.register();
        if (DatagenModLoader.isRunningDataGen()) {
            REGISTRATE.registerPonderLocalization(CCKPonderPlugin::new);
        }
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        CCKPartialModels.register();
        CCKSpriteShifts.register();
        PonderIndex.addPlugin(new CCKPonderPlugin());
    }
}
