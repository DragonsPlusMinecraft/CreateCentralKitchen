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

package plus.dragons.createcentralkitchen.client.ponder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.api.registration.SharedTextRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.common.CCKCommon;

public class CCKPonderPlugin implements PonderPlugin {
    public static final List<Consumer<PonderSceneRegistrationHelper<ResourceLocation>>> SCENES = new ArrayList<>();
    public static final List<Consumer<PonderTagRegistrationHelper<ResourceLocation>>> TAGS = new ArrayList<>();

    @Override
    public String getModId() {
        return CCKCommon.ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        for (var scene : SCENES)
            scene.accept(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        for (var tag : TAGS)
            tag.accept(helper);
    }

    @Override
    public void registerSharedText(SharedTextRegistrationHelper helper) {
        helper.registerSharedText("package_automate_ingredient_insertion", "Use Packager to automate ingredient insertion.");
        helper.registerSharedText("useful_factory_gauges", "Factory gauges are very useful in the packaging process.");
        helper.registerSharedText("arm_automate_container_insertion", "Use Mechanical Arm to insert food container.");
        helper.registerSharedText("arm_take_out_food", "Mechanical Arm can take out cooked food.");
        helper.registerSharedText("pipe_insert_liquid_ingredient", "Use Pipe to insert liquid ingredient.");
    }
}
