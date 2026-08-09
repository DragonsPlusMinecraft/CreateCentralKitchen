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

package plus.dragons.createcentralkitchen.integration.culturaldelights.ponder;

import com.baisylia.culturaldelights.block.ModBlocks;
import com.baisylia.culturaldelights.block.custom.EggplantFeastBlock;
import com.baisylia.culturaldelights.item.ModItems;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import java.util.List;
import java.util.Optional;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createcentralkitchen.client.ponder.CCKPonderPlugin;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.culturaldelights.registry.CulturalDelightsArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.integration.farmersdelight.ponder.FDPonderScenes;

public class CulturalDelightsPonderPlugin {
    private static final ResourceLocation EGGPLANT_PARMESAN = ModIntegration.CULTURALDELIGHTS.asResource("eggplant_parmesan_block");

    public static void register() {
        CCKPonderPlugin.SCENES.add(CulturalDelightsPonderPlugin::registerScenes);
        CCKPonderPlugin.TAGS.add(CulturalDelightsPonderPlugin::registerTags);
    }

    private static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(EGGPLANT_PARMESAN)
                .addStoryBoard(
                        "farmersdelight/stateful_portionable_food",
                        (builder, util) -> {
                            var feast = (EggplantFeastBlock) ModBlocks.EGGPLANT_PARMESAN_BLOCK.get();
                            var full = feast.defaultBlockState();
                            var servings = feast.getServingsProperty();
                            FDPonderScenes.statefulPortionableFood(
                                    builder,
                                    util,
                                    new FDPonderScenes.StatefulPortionScene(
                                            "cultural_delights_portioning",
                                            "Automating Eggplant Parmesan",
                                            CulturalDelightsArmInteractionPointTypes.EGGPLANT_PARMESAN.getId(),
                                            full,
                                            List.of(full.setValue(servings, feast.getMaxServings() - 1)),
                                            new ItemStack(ModItems.EGGPLANT_PARMESAN.get()),
                                            "Mechanical Arms can take plated servings without supplying bowls",
                                            Optional.of(new FDPonderScenes.InsertionDemo(
                                                    full.setValue(servings, 1),
                                                    new ItemStack(feast),
                                                    full,
                                                    new ItemStack(ModItems.EGGPLANT_PARMESAN.get()),
                                                    "The last serving stays until a fresh whole feast replaces it"))));
                        },
                        AllCreatePonderTags.ARM_TARGETS);
    }

    private static void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.addToTag(AllCreatePonderTags.ARM_TARGETS).add(EGGPLANT_PARMESAN);
    }
}
