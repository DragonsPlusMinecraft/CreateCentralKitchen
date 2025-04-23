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

package plus.dragons.createcentralkitchen.common;

import com.simibubi.create.foundation.item.ItemDescription;
import java.util.concurrent.CompletableFuture;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.Util;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.data.RuntimePackResources;
import plus.dragons.createcentralkitchen.data.lang.CCKLang;
import plus.dragons.createcentralkitchen.data.tags.CCKRuntimeItemTags;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createdragonsplus.common.CDPRegistrate;

@Mod(CCKCommon.ID)
public class CCKCommon {
    public static final String ID = "create_central_kitchen";
    public static final String NAME = "Create: Central Kitchen";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final CDPRegistrate REGISTRATE = new CDPRegistrate(ID)
            .setTooltipModifier(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE));
    private final ModContainer modContainer;

    public CCKCommon(IEventBus modBus, ModContainer modContainer) {
        this.modContainer = modContainer;
        REGISTRATE.registerEventListeners(modBus);
        modBus.register(this);
        modBus.register(new CCKConfig(modContainer));
    }

    @SubscribeEvent
    public void onConstructMod(final FMLConstructModEvent event) {
        for (ModIntegration integration : ModIntegration.values()) {
            if (integration.enabled())
                event.enqueueWork(integration::onConstructMod);
        }
    }

    @SubscribeEvent
    public void onCommonSetup(final FMLCommonSetupEvent event) {
        for (ModIntegration integration : ModIntegration.values()) {
            if (integration.enabled())
                event.enqueueWork(integration::onCommonSetup);
        }
    }

    @SubscribeEvent
    public void onClientSetup(final FMLClientSetupEvent event) {
        for (ModIntegration integration : ModIntegration.values()) {
            if (integration.enabled())
                event.enqueueWork(integration::onClientSetup);
        }
    }

    @SubscribeEvent
    public void addPackFinders(final AddPackFindersEvent event) {
        var type = event.getPackType();
        var lookupProvider = CompletableFuture.supplyAsync(VanillaRegistries::createLookup, Util.backgroundExecutor());
        if (type == PackType.SERVER_DATA) {
            var data = new RuntimePackResources(
                    "runtime",
                    modContainer,
                    type,
                    Position.TOP,
                    CCKLang.RUNTIME_RESOUCE_PACK_TITLE,
                    CCKLang.RUNTIME_RESOUCE_PACK_DESCRIPTION);
            event.addRepositorySource(data);
            var output = data.getPackOutput();
            var blockTags = CompletableFuture.completedFuture(TagLookup.<Block>empty());
            data.addDataProvider(new CCKRuntimeItemTags(output, lookupProvider, blockTags));
        }
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
