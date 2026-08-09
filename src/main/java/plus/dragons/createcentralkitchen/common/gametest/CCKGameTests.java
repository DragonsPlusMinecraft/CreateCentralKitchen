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

package plus.dragons.createcentralkitchen.common.gametest;

import java.util.Map;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.ModIntegration;

@EventBusSubscriber(modid = CCKCommon.ID)
public class CCKGameTests {
    private static final Map<ModIntegration, String> INTEGRATION_TEST_CLASSES = Map.of(
            ModIntegration.EXTRADELIGHT,
            "plus.dragons.createcentralkitchen.integration.extradelight.ExtraDelightRecipeCacheGameTests");

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        INTEGRATION_TEST_CLASSES.forEach((integration, testClassName) -> {
            if (!integration.enabled())
                return;
            try {
                // The event applies its namespace filter to the structure template. Our integration tests reuse
                // templates from dependency mods, so register them directly without enabling every test from those
                // mods.
                GameTestRegistry.register(Class.forName(testClassName));
            } catch (ClassNotFoundException exception) {
                throw new IllegalStateException("Missing GameTest class for enabled integration " + integration, exception);
            }
        });
    }
}
