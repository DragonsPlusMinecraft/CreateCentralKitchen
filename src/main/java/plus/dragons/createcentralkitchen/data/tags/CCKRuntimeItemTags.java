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

package plus.dragons.createcentralkitchen.data.tags;

import com.simibubi.create.AllTags.AllItemTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.PieBlock;

public class CCKRuntimeItemTags extends ItemTagsProvider {
    public CCKRuntimeItemTags(PackOutput output, CompletableFuture<Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, CCKCommon.ID, null);
    }

    @Override
    protected void addTags(Provider provider) {
        var uprightOnBelt = tag(AllItemTags.UPRIGHT_ON_BELT.tag);
        provider.lookupOrThrow(Registries.ITEM)
                .listElements()
                .map(Holder::value)
                .filter(this::isUprightOnBelt)
                .forEach(uprightOnBelt::add);
    }

    private boolean isUprightOnBelt(Item item) {
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof CakeBlock)
                return true;
            if (ModIntegration.FARMERSDELIGHT.enabled()) {
                if (block instanceof PieBlock)
                    return true;
                if (block instanceof FeastBlock)
                    return true;
            }
        }
        return false;
    }
}
