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

import com.simibubi.create.Create;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import plus.dragons.createcentralkitchen.common.CCKCommon;

public class CCKSpriteShifts {
    public static final SpriteShiftEntry DUNGEON_BURNER_FLAME = SpriteShifter.get(
            Create.asResource("block/blaze_burner_flame"),
            CCKCommon.asResource("block/dungeon_blaze_burner_flame_scroll"));
    public static final SpriteShiftEntry SUPER_DUNGEON_BURNER_FLAME = SpriteShifter.get(
            Create.asResource("block/blaze_burner_flame"),
            CCKCommon.asResource("block/dungeon_blaze_burner_flame_super_heated_scroll"));

    public static void register() {}
}
