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

package plus.dragons.createcentralkitchen.integration.dungeonsdelight.data;

import com.simibubi.create.AllBlocks;
import net.yirmiri.dungeonsdelight.core.init.DDTags;
import plus.dragons.createcentralkitchen.data.CCKBlockTags;

public class DungeonsDelightTagsProvider {
    public static void register() {
        CCKBlockTags.register(DDTags.BlockT.MONSTER_TRAY_HEAT_SOURCES, builder -> builder
                .add(AllBlocks.BLAZE_BURNER.get())
                .add(AllBlocks.LIT_BLAZE_BURNER.get()));
    }
}
