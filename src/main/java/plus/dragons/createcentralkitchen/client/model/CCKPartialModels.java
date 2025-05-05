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

package plus.dragons.createcentralkitchen.client.model;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import plus.dragons.createcentralkitchen.common.CCKCommon;

public class CCKPartialModels {
    public static final PartialModel CHEF_HAT = block("blaze_burner/chef_hat");
    public static final PartialModel CHEF_HAT_SMALL = block("blaze_burner/chef_hat_small");

    public static void register() {}

    private static PartialModel block(String path) {
        return PartialModel.of(CCKCommon.asResource("block/" + path));
    }
}
