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
import com.simibubi.create.api.registry.SimpleRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import plus.dragons.createcentralkitchen.common.registry.CCKArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.integration.brewinandchewin.packager.KegUnpackingHandler;
import plus.dragons.createcentralkitchen.integration.farmersdelight.mechanicalArm.CookingPotArmInteractionPoint;
import plus.dragons.createcentralkitchen.integration.farmersdelight.mechanicalArm.CuttingBoardArmInteractionPoint;
import plus.dragons.createcentralkitchen.integration.farmersdelight.mechanicalArm.StoveArmInteractionPoint;
import plus.dragons.createcentralkitchen.integration.farmersdelight.packager.CookingPotUnpackingHandler;
import plus.dragons.createcentralkitchen.integration.farmersdelight.recipe.CuttingBoardRecipeConverters;
import plus.dragons.createcentralkitchen.integration.mynethersdelight.mechanicalArm.NetherStoveArmInteractionPoint;
import plus.dragons.createdragonsplus.common.recipe.freeze.BlockFreezer;
import umpaz.brewinandchewin.common.registry.BnCBlocks;
import umpaz.brewinandchewin.common.tag.BnCTags;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

public enum ModIntegration {
    FARMERSDELIGHT(Constants.FARMERSDELIGHT) {
        @Override
        public void onConstructMod() {
            CCKArmInteractionPointTypes.TYPES.register("cooking_pot", CookingPotArmInteractionPoint.Type::new);
            CCKArmInteractionPointTypes.TYPES.register("cutting_board", CuttingBoardArmInteractionPoint.Type::new);
            CCKArmInteractionPointTypes.TYPES.register("stove", StoveArmInteractionPoint.Type::new);
        }

        @Override
        public void onCommonSetup() {
            ModBlockEntityTypes.COOKING_POT.get()
                    .getValidBlocks()
                    .forEach(block -> UnpackingHandler.REGISTRY.register(block, new CookingPotUnpackingHandler()));
            NeoForge.EVENT_BUS.register(CuttingBoardRecipeConverters.class);
        }
    },
    MYNETHERSDELIGHT(Constants.MYNETHERSDELIGHT) {
        @Override
        public void onConstructMod() {
            CCKArmInteractionPointTypes.TYPES.register("nether_stove", NetherStoveArmInteractionPoint.Type::new);
        }
    },
    BREWINANDCHEWIN(Constants.BREWINANDCHEWIN) {
        @Override
        public void onCommonSetup() {
            UnpackingHandler.REGISTRY.register(BnCBlocks.KEG, new KegUnpackingHandler());
            BlockFreezer.REGISTRY.registerProvider(SimpleRegistry.Provider.forBlockTag(
                    BnCTags.Blocks.FREEZE_SOURCES,
                    (level, pos, state) -> {
                        if (state.hasProperty(BlockStateProperties.LIT) && !state.getValue(BlockStateProperties.LIT))
                            return BlockFreezer.NO_FREEZE;
                        return BlockFreezer.PASSIVE_FREEZE;
                    }));
        }
    };

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

    public void onConstructMod() {}

    public void onCommonSetup() {}

    @OnlyIn(Dist.CLIENT)
    public void onClientSetup() {}

    public static class Constants {
        public static final String FARMERSDELIGHT = "farmersdelight";
        public static final String MYNETHERSDELIGHT = "mynethersdelight";
        public static final String BREWINANDCHEWIN = "brewinandchewin";
    }
}
