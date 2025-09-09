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

package plus.dragons.createcentralkitchen.integration.extradelight;

import com.lance5057.extradelight.ExtraDelightBlockEntities;
import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import java.util.*;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.extradelight.packager.ChillerUnpackingHandler;
import plus.dragons.createcentralkitchen.integration.extradelight.packager.OvenUnpackingHandler;
import plus.dragons.createcentralkitchen.integration.extradelight.ponder.ExtraDelightPonderPlugin;
import plus.dragons.createcentralkitchen.integration.extradelight.recipe.ExtraDelightRecipeConverters;
import plus.dragons.createcentralkitchen.integration.extradelight.registry.ExtraDelightArmInteractionPointTypes;

@Mod(CCKCommon.ID)
public class ExtraDelightIntegration {
    public ExtraDelightIntegration(IEventBus modBus) {
        if (ModIntegration.EXTRADELIGHT.enabled()) {
            modBus.register(new Common());
            if (FMLLoader.getDist() == Dist.CLIENT)
                modBus.register(new Client());
        }
    }

    public static class Common {
        public static final Set<Collection<RecipeHolder<? extends Recipe<?>>>> RELOADABLE_RECIPES = new HashSet<>();
        private static final ResourceManagerReloadListener RELOAD_LISTENER = resourceManager -> RELOADABLE_RECIPES.forEach(Collection::clear);

        @SubscribeEvent
        public void construct(final FMLConstructModEvent event) {
            ExtraDelightArmInteractionPointTypes.register();
            // Extra Delight itself makes every mixing recipe to create-mixing recipe, so we don't need to do conversion for that.
            NeoForge.EVENT_BUS.register(ExtraDelightRecipeConverters.class);
            NeoForge.EVENT_BUS.addListener(Common::addReloadListeners);
        }

        @SubscribeEvent
        public void setup(final FMLCommonSetupEvent event) {
            event.enqueueWork(this::registerUnpackingHandlers);
        }

        private void registerUnpackingHandlers() {
            ExtraDelightBlockEntities.OVEN.get()
                    .getValidBlocks()
                    .forEach(block -> UnpackingHandler.REGISTRY.register(block, new OvenUnpackingHandler()));
            ExtraDelightBlockEntities.CHILLER.get()
                    .getValidBlocks()
                    .forEach(block -> UnpackingHandler.REGISTRY.register(block, new ChillerUnpackingHandler()));
        }

        public static void addReloadListeners(AddReloadListenerEvent event) {
            event.addListener(RELOAD_LISTENER);
        }
    }

    public static class Client {
        @SubscribeEvent
        public void construct(final FMLConstructModEvent event) {
            ExtraDelightPonderPlugin.register();
        }
    }
}
