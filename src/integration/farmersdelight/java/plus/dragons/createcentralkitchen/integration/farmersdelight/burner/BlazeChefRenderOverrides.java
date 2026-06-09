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

package plus.dragons.createcentralkitchen.integration.farmersdelight.burner;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.client.burner.BlazeBurnerRenderOverride;
import plus.dragons.createcentralkitchen.client.registry.CCKPartialModels;

public class BlazeChefRenderOverrides {
    public static final int SPECIAL_PRIORITY = 0;
    public static final int DEFAULT_PRIORITY = -1000;
    private static final List<Entry> OVERRIDES = new ArrayList<>();
    private static List<BlazeBurnerRenderOverride> sortedOverrides = List.of();
    private static boolean defaultsRegistered = false;
    private static boolean dirty = false;

    public static void registerDefaults() {
        if (defaultsRegistered)
            return;
        register(DEFAULT_PRIORITY, null, CCKPartialModels.CHEF_HAT, CCKPartialModels.CHEF_HAT_SMALL);
        defaultsRegistered = true;
    }

    public static void register(ResourceLocation targetBlock, PartialModel hat, PartialModel smallHat) {
        register(SPECIAL_PRIORITY, targetBlock, hat, smallHat);
    }

    public static void register(int priority, @Nullable ResourceLocation targetBlock, PartialModel hat, PartialModel smallHat) {
        register(priority, new BlazeChefRenderOverride(targetBlock, hat, smallHat));
    }

    public static void register(int priority, BlazeBurnerRenderOverride override) {
        OVERRIDES.add(new Entry(priority, override));
        dirty = true;
    }

    public static List<BlazeBurnerRenderOverride> values() {
        if (dirty) {
            sortedOverrides = OVERRIDES.stream()
                    .sorted(Comparator.comparingInt(Entry::priority).reversed())
                    .map(Entry::override)
                    .toList();
            dirty = false;
        }
        return sortedOverrides;
    }

    private record Entry(int priority, BlazeBurnerRenderOverride override) {}
}
