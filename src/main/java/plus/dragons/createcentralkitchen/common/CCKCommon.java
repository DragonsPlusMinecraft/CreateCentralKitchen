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
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack.Position;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plus.dragons.createcentralkitchen.common.registry.CCKArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.data.CCKBlockTags;
import plus.dragons.createcentralkitchen.data.CCKItemTags;
import plus.dragons.createcentralkitchen.data.CCKLang;
import plus.dragons.createdragonsplus.common.CDPRegistrate;
import plus.dragons.createdragonsplus.data.runtime.RuntimePackResources;

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
        CCKArmInteractionPointTypes.register(modBus);

        modBus.register(this);
        modBus.register(new CCKConfig(modContainer));

        if (DatagenModLoader.isRunningDataGen()) {
            REGISTRATE.registerBuiltinLocalization("interface");
            REGISTRATE.registerForeignLocalization();
        }
    }

    @SubscribeEvent
    public void addPackFinders(final AddPackFindersEvent event) {
        var type = event.getPackType();
        if (type == PackType.SERVER_DATA) {
            var pack = new RuntimePackResources(
                    "runtime",
                    modContainer,
                    type,
                    Position.TOP,
                    CCKLang.RUNTIME_PACK_TITLE,
                    CCKLang.RUNTIME_PACK_DESCRIPTION);
            var registries = CompletableFuture.<HolderLookup.Provider>completedFuture(RegistryLayer.createRegistryAccess().compositeAccess());
            var output = pack.getPackOutput();
            var existingFileHelper = new ExistingFileHelper(Set.of(), Set.of(), false, null, null);
            var blockTags = new CCKBlockTags(output, registries, existingFileHelper);
            var itemTags = new CCKItemTags(output, registries, blockTags.contentsGetter(), existingFileHelper);
            pack.addDataProvider(blockTags);
            pack.addDataProvider(itemTags);
            event.addRepositorySource(pack);
        }
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
