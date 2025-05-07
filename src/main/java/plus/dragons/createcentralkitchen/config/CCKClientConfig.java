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

import com.simibubi.create.content.kinetics.belt.BeltHelper;
import net.createmod.catnip.config.ConfigBase;

public class CCKClientConfig extends ConfigBase {
    public final ConfigBool renderDeployerUsingItemWithCustomTransform = b(true,
            "renderDeployerUsingItemWithCustomTransform",
            Comments.renderDeployerUsingItemWithCustomTransform);
    public final ConfigBool renderBlockItemWithNonGui3dModelUprightOnBelt = b(true,
            "renderBlockItemWithNonGui3dModelUprightOnBelt",
            Comments.renderBlockItemWithNonGui3dModelUprightOnBelt);

    @Override
    public String getName() {
        return "client";
    }

    @Override
    public void onReload() {
        BeltHelper.uprightCache.clear();
    }

    static class Comments {
        static final String renderDeployerUsingItemWithCustomTransform = "If Deployer held items in #create:handheld_in_deployer_use should have custom transform at Use mode";
        static final String renderBlockItemWithNonGui3dModelUprightOnBelt = "If Block Item without GUI 3D Model should be rendered upright on Belt";
    }
}
