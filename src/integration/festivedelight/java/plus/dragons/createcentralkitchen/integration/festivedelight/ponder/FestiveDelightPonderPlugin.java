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

package plus.dragons.createcentralkitchen.integration.festivedelight.ponder;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import java.util.List;
import java.util.Optional;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.toopa.festivedelight.init.FestiveDelightModBlocks;
import net.toopa.festivedelight.init.FestiveDelightModItems;
import plus.dragons.createcentralkitchen.client.ponder.CCKPonderPlugin;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.farmersdelight.ponder.FDPonderScenes;

public class FestiveDelightPonderPlugin {
    private static final List<ResourceLocation> FESTIVE_CHICKEN_STAGES = List.of(
            "festive_chicken_stage_0",
            "festive_chicken_stage_1",
            "festive_chicken_stage_2",
            "festive_chicken_stage_3",
            "festive_chicken_stage_leftover")
            .stream()
            .map(ModIntegration.FESTIVEDELIGHT::asResource)
            .toList();

    public static void register() {
        CCKPonderPlugin.SCENES.add(FestiveDelightPonderPlugin::registerScenes);
        CCKPonderPlugin.TAGS.add(FestiveDelightPonderPlugin::registerTags);
    }

    private static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(FESTIVE_CHICKEN_STAGES)
                .addStoryBoard(
                        "farmersdelight/portionable_foods",
                        (builder, util) -> FDPonderScenes.statefulPortionableFood(
                                builder,
                                util,
                                new FDPonderScenes.StatefulPortionScene(
                                        "festive_delight_chicken",
                                        "Automating Festive Chicken",
                                        FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_0.get().defaultBlockState(),
                                        List.of(
                                                FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_1.get().defaultBlockState(),
                                                FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_2.get().defaultBlockState(),
                                                FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_3.get().defaultBlockState(),
                                                FestiveDelightModBlocks.FESTIVE_CHICKEN_STAGE_LEFTOVER.get().defaultBlockState()),
                                        new ItemStack(FestiveDelightModItems.FESTIVE_CHIKEN.get()),
                                        "Mechanical Arms take four plated servings and leave the final leftovers",
                                        Optional.empty())),
                        AllCreatePonderTags.ARM_TARGETS);
    }

    private static void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        var armTargets = helper.addToTag(AllCreatePonderTags.ARM_TARGETS);
        FESTIVE_CHICKEN_STAGES.forEach(armTargets::add);
    }
}
