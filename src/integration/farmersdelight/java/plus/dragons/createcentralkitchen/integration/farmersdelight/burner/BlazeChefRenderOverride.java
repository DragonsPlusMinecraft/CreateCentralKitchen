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

package plus.dragons.createcentralkitchen.integration.farmersdelight.burner;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import plus.dragons.createcentralkitchen.client.burner.BlazeBurnerRenderOverride;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.tag.ModTags;

public class BlazeChefRenderOverride implements BlazeBurnerRenderOverride {
    @Nullable
    private final ResourceLocation targetBlock;
    private final PartialModel hat;
    private final PartialModel smallHat;

    public BlazeChefRenderOverride(@Nullable ResourceLocation targetBlock, PartialModel hat, PartialModel smallHat) {
        this.targetBlock = targetBlock;
        this.hat = hat;
        this.smallHat = smallHat;
    }

    @Override
    public boolean isValid(Level level, BlockPos pos, BlazeBurnerBlockEntity burner) {
        if (targetBlock != null)
            return BuiltInRegistries.BLOCK.getKey(level.getBlockState(pos.above()).getBlock()).equals(targetBlock);
        if (level.getBlockEntity(pos.above()) instanceof HeatableBlockEntity)
            return true;
        return level.getBlockState(pos.above()).is(ModTags.Blocks.HEAT_CONDUCTORS) &&
                level.getBlockEntity(pos.above(2)) instanceof HeatableBlockEntity heatable &&
                !heatable.requiresDirectHeat();
    }

    @Override
    public boolean isValidBlockAbove(boolean original) {
        return true;
    }

    @Override
    public PartialModel getHatModel(boolean small) {
        return small ? smallHat : hat;
    }
}
