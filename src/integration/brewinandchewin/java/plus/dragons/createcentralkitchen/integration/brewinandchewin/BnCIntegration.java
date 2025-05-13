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

package plus.dragons.createcentralkitchen.integration.brewinandchewin;

import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import com.simibubi.create.api.registry.SimpleRegistry;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.fml.loading.FMLLoader;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.brewinandchewin.packager.KegUnpackingHandler;
import plus.dragons.createcentralkitchen.integration.brewinandchewin.ponder.BnCPonderPlugin;
import plus.dragons.createdragonsplus.common.processing.freeze.BlockFreezer;
import umpaz.brewinandchewin.common.registry.BnCBlocks;
import umpaz.brewinandchewin.common.tag.BnCTags;

@Mod(CCKCommon.ID)
public class BnCIntegration {
    public BnCIntegration(IEventBus modBus) {
        if (ModIntegration.BREWINANDCHEWIN.enabled()) {
            modBus.register(new Common());
            if (FMLLoader.getDist() == Dist.CLIENT)
                modBus.register(new Client());
        }
    }

    public static class Common {
        @SubscribeEvent
        public void setup(final FMLCommonSetupEvent event) {
            event.enqueueWork(this::registerUnpackingHandlers);
            event.enqueueWork(this::registerBlockFreezers);
        }

        private void registerUnpackingHandlers() {
            UnpackingHandler.REGISTRY.register(BnCBlocks.KEG, new KegUnpackingHandler());
        }

        private void registerBlockFreezers() {
            BlockFreezer.REGISTRY.registerProvider(SimpleRegistry.Provider.forBlockTag(
                    BnCTags.Blocks.FREEZE_SOURCES,
                    (level, pos, state) -> {
                        if (state.hasProperty(BlockStateProperties.LIT) && !state.getValue(BlockStateProperties.LIT))
                            return BlockFreezer.NO_FREEZE;
                        return BlockFreezer.PASSIVE_FREEZE;
                    }));
        }
    }

    public static class Client {
        @SubscribeEvent
        public void construct(final FMLConstructModEvent event) {
            BnCPonderPlugin.register();
        }
    }
}
