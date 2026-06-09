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

package plus.dragons.createcentralkitchen.integration;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import org.slf4j.Logger;

public enum ModIntegration {
    FARMERSDELIGHT(Mods.FARMERSDELIGHT),
    BREWINANDCHEWIN(Mods.BREWINANDCHEWIN),
    MYNETHERSDELIGHT(Mods.MYNETHERSDELIGHT),
    ENDSDELIGHT(Mods.ENDSDELIGHT),
    TWILIGHTDELIGHT(Mods.TWILIGHTDELIGHT),
    TRAILANDTALESDELIGHT(Mods.TRAILANDTALESDELIGHT),
    MINERSDELIGHT(Mods.MINERSDELIGHT),
    EXTRADELIGHT(Mods.EXTRADELIGHT),
    DUNGEONSDELIGHT(Mods.DUNGEONSDELIGHT),;

    private static final Logger LOGGER = LogUtils.getLogger();
    private final String id;

    ModIntegration(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public boolean enabled() {
        return ModList.get().isLoaded(id);
    }

    public ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(id, path);
    }

    public ModLoadedCondition condition() {
        return new ModLoadedCondition(id);
    }

    public static class Mods {
        public static final String FARMERSDELIGHT = "farmersdelight";
        public static final String BREWINANDCHEWIN = "brewinandchewin";
        public static final String MYNETHERSDELIGHT = "mynethersdelight";
        public static final String ENDSDELIGHT = "ends_delight";
        public static final String TWILIGHTDELIGHT = "twilightdelight";
        public static final String TRAILANDTALESDELIGHT = "trailandtales_delight";
        public static final String MINERSDELIGHT = "minersdelight";
        public static final String EXTRADELIGHT = "extradelight";
        public static final String DUNGEONSDELIGHT = "dungeonsdelight";
    }
}
