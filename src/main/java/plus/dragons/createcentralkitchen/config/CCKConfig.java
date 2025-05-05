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

package plus.dragons.createcentralkitchen.config;

import net.minecraft.Util;
import net.minecraft.util.Unit;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import plus.dragons.createdragonsplus.util.FieldsNullabilityUnknownByDefault;

@FieldsNullabilityUnknownByDefault
public class CCKConfig {
    private static final CCKCommonConfig COMMON_CONFIG = new CCKCommonConfig();
    private static final CCKClientConfig CLIENT_CONFIG = new CCKClientConfig();
    private static ModConfigSpec COMMON_SPEC;
    private static ModConfigSpec CLIENT_SPEC;

    public CCKConfig(ModContainer modContainer) {
        COMMON_SPEC = Util.make(new ModConfigSpec.Builder().configure(builder -> {
            COMMON_CONFIG.registerAll(builder);
            return Unit.INSTANCE;
        }).getValue(), spec -> modContainer.registerConfig(Type.COMMON, spec));
        CLIENT_SPEC = Util.make(new ModConfigSpec.Builder().configure(builder -> {
            CLIENT_CONFIG.registerAll(builder);
            return Unit.INSTANCE;
        }).getValue(), spec -> modContainer.registerConfig(Type.CLIENT, spec));
    }

    public static CCKCommonConfig common() {
        return COMMON_CONFIG;
    }

    public static CCKClientConfig client() {
        return CLIENT_CONFIG;
    }

    public static CCKRecipesConfig recipes() {
        return COMMON_CONFIG.recipes;
    }

    @SubscribeEvent
    public void onLoad(ModConfigEvent.Loading event) {
        var spec = event.getConfig().getSpec();
        if (spec == COMMON_SPEC)
            COMMON_CONFIG.onLoad();
        else if (spec == CLIENT_SPEC)
            CLIENT_CONFIG.onLoad();
    }

    @SubscribeEvent
    public void onReload(ModConfigEvent.Reloading event) {
        var spec = event.getConfig().getSpec();
        if (spec == COMMON_SPEC)
            COMMON_CONFIG.onReload();
        else if (spec == CLIENT_SPEC)
            CLIENT_CONFIG.onReload();
    }
}
