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

package plus.dragons.createcentralkitchen.common.recipe;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

// TODO: Move this to Create: Dragons Plus
@EventBusSubscriber
public interface RecipeConverter<K extends Recipe<?>, V extends Recipe<?>> extends Function<RecipeHolder<K>, RecipeHolder<V>> {
    Map<RecipeConverter<?, ?>, Runnable> CACHE_INVALIDATORS = new IdentityHashMap<>();

    @SubscribeEvent
    static void onAddReloadListener(final AddReloadListenerEvent event) {
        event.addListener((ResourceManagerReloadListener) RecipeConverter::invalidateAll);
    }

    static void invalidateAll(ResourceManager resourceManager) {
        CACHE_INVALIDATORS.values().forEach(Runnable::run);
    }

    static <K extends Recipe<?>, V extends Recipe<?>> RecipeConverter<K, V> cached(CacheBuilder<Object, Object> cacheBuilder, RecipeConverter<K, V> converter) {
        var cache = cacheBuilder.build(new CacheLoader<RecipeHolder<K>, RecipeHolder<V>>() {
            @Override
            public RecipeHolder<V> load(RecipeHolder<K> key) {
                return converter.apply(key);
            }
        });
        RecipeConverter<K, V> result = cache::getUnchecked;
        CACHE_INVALIDATORS.put(result, cache::invalidateAll);
        return result;
    }
}
