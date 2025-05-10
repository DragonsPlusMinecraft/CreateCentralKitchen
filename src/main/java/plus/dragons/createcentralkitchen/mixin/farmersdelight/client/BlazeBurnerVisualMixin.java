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

package plus.dragons.createcentralkitchen.mixin.farmersdelight.client;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import plus.dragons.createcentralkitchen.client.model.CCKPartialModels;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.farmersdelight.burner.ChefBlazeBurnerBlockEntity;

@Restriction(require = @Condition(ModIntegration.Constants.FARMERSDELIGHT))
@Mixin(BlazeBurnerVisual.class)
public abstract class BlazeBurnerVisualMixin extends AbstractBlockEntityVisual<BlazeBurnerBlockEntity> {
    @Shadow
    private HeatLevel heatLevel;
    @Shadow
    private @Nullable TransformedInstance hat;
    @Unique
    private @Nullable PartialModel chefHatModel;

    private BlazeBurnerVisualMixin(VisualizationContext ctx, BlazeBurnerBlockEntity blockEntity, float partialTick) {
        super(ctx, blockEntity, partialTick);
    }

    @ModifyVariable(method = "animate", at = @At(value = "STORE", ordinal = 0), index = 5)
    private boolean updateChefHat(boolean hatPresent) {
        boolean isChef = ((ChefBlazeBurnerBlockEntity) blockEntity).isChef();
        if (hatPresent || !isChef) {
            if (this.chefHatModel != null && hat != null) {
                hat.delete();
                hat = null;
            }
            this.chefHatModel = null;
        } else {
            var chefHatModel = heatLevel == HeatLevel.SMOULDERING ? CCKPartialModels.CHEF_HAT_SMALL : CCKPartialModels.CHEF_HAT;
            if (this.chefHatModel != chefHatModel) {
                var instancer = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(chefHatModel));
                if (hat != null)
                    instancer.stealInstance(hat);
                else
                    hat = instancer.createInstance();
                this.chefHatModel = chefHatModel;
            }
        }
        return hatPresent || isChef;
    }
}
