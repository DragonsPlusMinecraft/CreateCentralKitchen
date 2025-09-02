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

package plus.dragons.createcentralkitchen.client.registry;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import plus.dragons.createcentralkitchen.common.CCKCommon;

public class CCKPartialModels {
    public static final PartialModel CHEF_HAT = block("blaze_burner/chef_hat");
    public static final PartialModel CHEF_HAT_SMALL = block("blaze_burner/chef_hat_small");
    public static final PartialModel FIERY_CHEF_HAT = block("blaze_burner/fiery_chef_hat");
    public static final PartialModel FIERY_CHEF_HAT_SMALL = block("blaze_burner/fiery_chef_hat_small");
    public static final PartialModel POTTERY_CHEF_HAT = block("blaze_burner/pottery_chef_hat");
    public static final PartialModel POTTERY_CHEF_HAT_SMALL = block("blaze_burner/pottery_chef_hat_small");
    public static final PartialModel DUNGEON_BLAZE_INERT = block("blaze_burner/dungeon/blaze/inert");
    public static final PartialModel DUNGEON_BLAZE_IDLE = block("blaze_burner/dungeon/blaze/idle");
    public static final PartialModel DUNGEON_BLAZE_ACTIVE = block("blaze_burner/dungeon/blaze/active");
    public static final PartialModel DUNGEON_BLAZE_SUPER = block("blaze_burner/dungeon/blaze/super");
    public static final PartialModel DUNGEON_BLAZE_SUPER_ACTIVE = block("blaze_burner/dungeon/blaze/super_active");
    public static final PartialModel DUNGEON_BLAZE_BURNER_RODS = block("blaze_burner/dungeon/rods_small");
    public static final PartialModel DUNGEON_BLAZE_BURNER_RODS_2 = block("blaze_burner/dungeon/rods_large");
    public static final PartialModel DUNGEON_BLAZE_BURNER_SUPER_RODS = block("blaze_burner/dungeon/superheated_rods_small");
    public static final PartialModel DUNGEON_BLAZE_BURNER_SUPER_RODS_2 = block("blaze_burner/dungeon/superheated_rods_large");
    public static final PartialModel DUNGEON_CHEF_HAT = block("blaze_burner/dungeon/chef_hat");
    public static final PartialModel DUNGEON_CHEF_HAT_SMALL = block("blaze_burner/dungeon/chef_hat_small");

    public static void register() {}

    private static PartialModel block(String path) {
        return PartialModel.of(CCKCommon.asResource("block/" + path));
    }
}
