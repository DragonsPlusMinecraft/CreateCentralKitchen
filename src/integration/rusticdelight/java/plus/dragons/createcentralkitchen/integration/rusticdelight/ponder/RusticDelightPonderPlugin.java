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

package plus.dragons.createcentralkitchen.integration.rusticdelight.ponder;

import com.phantomwing.rusticdelight.block.ModBlocks;
import com.phantomwing.rusticdelight.block.custom.PancakeBlock;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import java.util.List;
import java.util.Optional;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.client.ponder.CCKPonderPlugin;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.farmersdelight.ponder.FDPonderScenes;
import plus.dragons.createcentralkitchen.integration.rusticdelight.mechanicalArm.PancakeArmInteractionPoint;

public class RusticDelightPonderPlugin {
    private static final List<ResourceLocation> PANCAKES = List.of(
            "pancakes",
            "honey_pancakes",
            "chocolate_pancakes",
            "cherry_blossom_pancakes",
            "vegetable_pancakes",
            "pumpkin_pancakes",
            "coffee_pancakes")
            .stream()
            .map(ModIntegration.RUSTICDELIGHT::asResource)
            .toList();

    public static void register() {
        CCKPonderPlugin.SCENES.add(RusticDelightPonderPlugin::registerScenes);
        CCKPonderPlugin.TAGS.add(RusticDelightPonderPlugin::registerTags);
    }

    private static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(PANCAKES)
                .addStoryBoard(
                        "farmersdelight/portionable_foods",
                        (builder, util) -> {
                            var pancakes = (PancakeBlock) ModBlocks.PANCAKES.get();
                            var six = PancakeArmInteractionPoint.stateForCount(pancakes.defaultBlockState(), 6);
                            var five = PancakeArmInteractionPoint.stateForCount(six, 5);
                            var eleven = PancakeArmInteractionPoint.stateForCount(six, 11);
                            var twelve = PancakeArmInteractionPoint.stateForCount(six, 12);
                            FDPonderScenes.statefulPortionableFood(
                                    builder,
                                    util,
                                    new FDPonderScenes.StatefulPortionScene(
                                            "rustic_delight_pancakes",
                                            "Automating Pancake Stacks",
                                            six,
                                            List.of(five),
                                            pancakes.getServingItem(),
                                            "Mechanical Arms can take pancakes from every flavored stack",
                                            Optional.of(new FDPonderScenes.InsertionDemo(
                                                    eleven,
                                                    pancakes.getServingItem().copyWithCount(2),
                                                    twelve,
                                                    pancakes.getServingItem(),
                                                    "Only matching pancakes are added, up to a stack of twelve"))));
                        },
                        AllCreatePonderTags.ARM_TARGETS);
    }

    private static void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        var armTargets = helper.addToTag(AllCreatePonderTags.ARM_TARGETS);
        PANCAKES.forEach(armTargets::add);
    }
}
