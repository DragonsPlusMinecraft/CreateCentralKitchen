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

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createdragonsplus.data.runtime.RuntimePackResources;

public class CCKRuntimeTags {
    public static void register(RuntimePackResources pack, CompletableFuture<Provider> registries) {
        var output = pack.getPackOutput();
        var blockTags = new BlockTags(output, registries);
        var itemTags = new ItemTags(output, registries, blockTags.contentsGetter());
        pack.addDataProvider(blockTags);
        pack.addDataProvider(itemTags);
    }

    public static class BlockTags extends BlockTagsProvider {
        BlockTags(PackOutput output, CompletableFuture<Provider> lookupProvider) {
            super(output, lookupProvider, CCKCommon.ID, null);
        }

        @Override
        protected void addTags(Provider provider) {}
    }

    public static class ItemTags extends ItemTagsProvider {
        ItemTags(PackOutput output, CompletableFuture<Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
            super(output, lookupProvider, blockTags);
        }

        @Override
        protected void addTags(Provider provider) {
            var edibleWhenPlaced = tag(Items.FOODS_EDIBLE_WHEN_PLACED);
            BuiltInRegistries.ITEM.stream()
                    .filter(item -> item instanceof BlockItem blockItem && blockItem.getBlock() instanceof EdibleWhenPlaced)
                    .forEach(edibleWhenPlaced::add);
        }

        public interface EdibleWhenPlaced {}
    }
}
