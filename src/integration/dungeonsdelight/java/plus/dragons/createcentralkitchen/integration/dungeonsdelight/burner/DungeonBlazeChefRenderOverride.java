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

package plus.dragons.createcentralkitchen.integration.dungeonsdelight.burner;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.minecraft.core.particles.ParticleOptions;
import net.yirmiri.dungeonsdelight.core.registry.DDParticles;
import plus.dragons.createcentralkitchen.client.registry.CCKPartialModels;
import plus.dragons.createcentralkitchen.client.registry.CCKSpriteShifts;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.farmersdelight.burner.BlazeChefRenderOverride;

public class DungeonBlazeChefRenderOverride extends BlazeChefRenderOverride {
    public DungeonBlazeChefRenderOverride() {
        super(ModIntegration.DUNGEONSDELIGHT.asResource("monster_pot"), CCKPartialModels.DUNGEON_CHEF_HAT, CCKPartialModels.DUNGEON_CHEF_HAT_SMALL);
    }

    @Override
    public PartialModel getBlazeModel(HeatLevel heatLevel, boolean active) {
        if (heatLevel.isAtLeast(HeatLevel.SEETHING)) {
            return active ? CCKPartialModels.DUNGEON_BLAZE_SUPER_ACTIVE : CCKPartialModels.DUNGEON_BLAZE_SUPER;
        } else if (heatLevel.isAtLeast(HeatLevel.FADING)) {
            return active && heatLevel.isAtLeast(HeatLevel.KINDLED) ? CCKPartialModels.DUNGEON_BLAZE_ACTIVE
                    : CCKPartialModels.DUNGEON_BLAZE_IDLE;
        } else {
            return CCKPartialModels.DUNGEON_BLAZE_INERT;
        }
    }

    @Override
    public PartialModel getSmallRodsModel(boolean superHeated) {
        return superHeated ? CCKPartialModels.DUNGEON_BLAZE_BURNER_SUPER_RODS : CCKPartialModels.DUNGEON_BLAZE_BURNER_RODS;
    }

    @Override
    public PartialModel getLargeRodsModel(boolean superHeated) {
        return superHeated ? CCKPartialModels.DUNGEON_BLAZE_BURNER_SUPER_RODS_2 : CCKPartialModels.DUNGEON_BLAZE_BURNER_RODS_2;
    }

    @Override
    public SpriteShiftEntry getFlameSpriteShift(boolean superHeated) {
        return superHeated ? CCKSpriteShifts.SUPER_DUNGEON_BURNER_FLAME : CCKSpriteShifts.DUNGEON_BURNER_FLAME;
    }

    @Override
    public ParticleOptions getFlameParticle(boolean superHeated) {
        return DDParticles.LIVING_FLAME.get();
    }
}
