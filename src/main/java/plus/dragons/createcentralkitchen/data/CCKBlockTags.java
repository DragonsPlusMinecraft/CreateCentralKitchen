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

package plus.dragons.createcentralkitchen.data;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import plus.dragons.createcentralkitchen.common.CCKCommon;

public class CCKBlockTags extends BlockTagsProvider {
    private static final ListMultimap<TagKey<Block>, Consumer<IntrinsicTagAppender<Block>>> TAGS = ArrayListMultimap.create();

    public CCKBlockTags(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CCKCommon.ID, existingFileHelper);
    }

    public static void register(TagKey<Block> tag, Consumer<IntrinsicTagAppender<Block>> callback) {
        TAGS.put(tag, callback);
    }

    @Override
    protected void addTags(Provider provider) {
        TAGS.forEach((key, consumer) -> consumer.accept(tag(key)));
    }
}
