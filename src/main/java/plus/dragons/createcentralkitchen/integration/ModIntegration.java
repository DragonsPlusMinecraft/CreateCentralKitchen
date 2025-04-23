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

import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import plus.dragons.createcentralkitchen.integration.farmersdelight.CookingPotUnpackingHandler;
import plus.dragons.createcentralkitchen.integration.farmersdelight.CuttingBoardRecipeConverter;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public enum ModIntegration {
    FARMERSDELIGHT("farmersdelight") {
        @Override
        public void onCommonSetup() {
            UnpackingHandler.REGISTRY.register(ModBlocks.COOKING_POT.get(), new CookingPotUnpackingHandler());
            NeoForge.EVENT_BUS.register(CuttingBoardRecipeConverter.class);
        }
    };

    private final String id;

    ModIntegration(String id) {
        this.id = id;
    }

    public boolean enabled() {
        return ModList.get().isLoaded(id);
    }

    public void onConstructMod() {}

    public void onCommonSetup() {}

    @OnlyIn(Dist.CLIENT)
    public void onClientSetup() {}

    public ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(id, path);
    }
}
