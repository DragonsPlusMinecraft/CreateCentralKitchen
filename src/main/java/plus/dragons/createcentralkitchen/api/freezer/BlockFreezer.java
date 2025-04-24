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

package plus.dragons.createcentralkitchen.api.freezer;

import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.api.registry.SimpleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

// TODO: Move this to Create: Dragons Plus
/**
 * The cold version of {@link BoilerHeater} with the same specification of "passive" and "active".
 * <p>
 * When used for Keg, {@link #PASSIVE_FREEZE} provides 1 cold level,
 * and active freeze provides (1 + {@link #getFreeze(Level, BlockPos, BlockState) active freeze}) cold levels.
 */
@FunctionalInterface
public interface BlockFreezer {
    int PASSIVE_FREEZE = 0;
    int NO_FREEZE = -1;

    SimpleRegistry<Block, BlockFreezer> REGISTRY = SimpleRegistry.create();

    /**
     * Gets the freeze at the given location. If a freezer is present, queries it for freeze.
     * If not, returns {@link #NO_FREEZE}.
     */
    static float findFreeze(Level level, BlockPos pos, BlockState state) {
        BlockFreezer freezer = REGISTRY.get(state);
        return freezer != null ? freezer.getFreeze(level, pos, state) : NO_FREEZE;
    }

    /**
     * @return the amount of freeze to provide.
     * @see #NO_FREEZE
     * @see #PASSIVE_FREEZE
     */
    float getFreeze(Level level, BlockPos pos, BlockState state);
}
