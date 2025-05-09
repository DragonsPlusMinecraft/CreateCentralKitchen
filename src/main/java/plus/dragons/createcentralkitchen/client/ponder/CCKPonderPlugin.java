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

import static plus.dragons.createcentralkitchen.common.CCKCommon.REGISTRATE;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import plus.dragons.createcentralkitchen.common.CCKCommon;

public class CCKPonderPlugin implements PonderPlugin {
    static final BlockEntry<Block> COOKING_POT = new BlockEntry<>(REGISTRATE,
            DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("farmersdelight", "cooking_pot")));
    static final BlockEntry<Block> STOVE = new BlockEntry<>(REGISTRATE,
            DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("farmersdelight", "stove")));
    static final BlockEntry<Block> CUTTING_BOARD = new BlockEntry<>(REGISTRATE,
            DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("farmersdelight", "cutting_board")));
    static final BlockEntry<Block> SKILLET = new BlockEntry<>(REGISTRATE,
            DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("farmersdelight", "skillet")));
    static final BlockEntry<Block> KEG = new BlockEntry<>(REGISTRATE,
            DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("brewinandchewin", "keg")));


    @Override
    public String getModId() {
        return CCKCommon.ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        CCKPonderScenes.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        CCKPonderTags.register(helper);
    }
}
