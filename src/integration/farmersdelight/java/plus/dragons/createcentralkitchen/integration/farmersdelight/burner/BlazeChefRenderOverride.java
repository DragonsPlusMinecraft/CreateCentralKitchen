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
import plus.dragons.createcentralkitchen.client.registry.CCKPartialModels;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.tag.ModTags;

public enum BlazeChefRenderOverride implements BlazeBurnerRenderOverride {
    FIERY_COOKING_POT(ModIntegration.TWILIGHTDELIGHT.asResource("fiery_cooking_pot"), CCKPartialModels.FIERY_CHEF_HAT, CCKPartialModels.FIERY_CHEF_HAT_SMALL),
    POTTERY_COOKING_OIT(ModIntegration.TRAILANDTALESDELIGHT.asResource("pottery_cooking_pot"), CCKPartialModels.POTTERY_CHEF_HAT, CCKPartialModels.POTTERY_CHEF_HAT_SMALL),
    DEFAULT(null, CCKPartialModels.CHEF_HAT, CCKPartialModels.CHEF_HAT_SMALL);

    @Nullable
    final ResourceLocation specialHatRenderHeatable;
    final PartialModel hat;
    final PartialModel smallHat;

    BlazeChefRenderOverride(@Nullable ResourceLocation specialHatRenderHeatable, PartialModel hat, PartialModel smallHat) {
        this.specialHatRenderHeatable = specialHatRenderHeatable;
        this.hat = hat;
        this.smallHat = smallHat;
    }

    @Override
    public boolean isValid(Level level, BlockPos pos, BlazeBurnerBlockEntity burner) {
        if (specialHatRenderHeatable == null) {
            if (level.getBlockEntity(pos.above()) instanceof HeatableBlockEntity)
                return true;
            return level.getBlockState(pos.above()).is(ModTags.HEAT_CONDUCTORS) &&
                    level.getBlockEntity(pos.above(2)) instanceof HeatableBlockEntity heatable &&
                    !heatable.requiresDirectHeat();
        } else {
            return BuiltInRegistries.BLOCK.getKey(level.getBlockState(pos.above()).getBlock()).equals(specialHatRenderHeatable);
        }
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
