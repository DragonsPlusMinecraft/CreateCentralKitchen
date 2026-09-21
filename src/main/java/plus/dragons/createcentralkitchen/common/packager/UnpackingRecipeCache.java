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

package plus.dragons.createcentralkitchen.common.packager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import plus.dragons.createcentralkitchen.common.CCKCommon;

@EventBusSubscriber(modid = CCKCommon.ID)
public final class UnpackingRecipeCache {
    private static final Cache<RecipeManager, Map<RecipeType<?>, List<RecipeUnpacking.Candidate>>> RECIPES = CacheBuilder.newBuilder().weakKeys().build();

    private UnpackingRecipeCache() {}

    static List<RecipeUnpacking.Candidate> get(RecipeManager manager, RecipeType<?> type) {
        return RECIPES.asMap().computeIfAbsent(manager, ignored -> new ConcurrentHashMap<>())
                .computeIfAbsent(type, key -> load(manager, key));
    }

    @SuppressWarnings("unchecked")
    private static List<RecipeUnpacking.Candidate> load(RecipeManager manager, RecipeType<?> type) {
        // The manager associates each type with its matching input and recipe classes.
        return manager.getAllRecipesFor((RecipeType<Recipe<RecipeInput>>) type).stream().map(RecipeHolder::value)
                .map(RecipeUnpacking.Candidate::new)
                .filter(candidate -> !candidate.ingredients().isEmpty() && candidate.ingredients().size() <= 9).toList();
    }

    @SubscribeEvent
    public static void reload(AddReloadListenerEvent event) {
        event.addListener((ResourceManagerReloadListener) resources -> RECIPES.invalidateAll());
    }

    @SubscribeEvent
    public static void tagsUpdated(TagsUpdatedEvent event) {
        if (event.shouldUpdateStaticData())
            RECIPES.invalidateAll();
    }

    @SubscribeEvent
    public static void serverStopped(ServerStoppedEvent event) {
        RECIPES.invalidateAll();
    }
}
