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

package plus.dragons.createcentralkitchen.mixin.create.client;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerRenderer;
import com.simibubi.create.content.processing.burner.BlazeBurnerVisual;
import com.simibubi.create.content.processing.burner.ScrollInstance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import net.createmod.catnip.render.SpriteShiftEntry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createcentralkitchen.client.burner.BlazeBurnerClientExtension;
import plus.dragons.createcentralkitchen.client.burner.BlazeBurnerRenderOverride;

@Mixin(BlazeBurnerVisual.class)
public abstract class BlazeBurnerVisualMixin extends AbstractBlockEntityVisual<BlazeBurnerBlockEntity> {
    @Shadow
    private HeatLevel heatLevel;
    @Shadow
    private boolean validBlockAbove;
    @Shadow
    @Final
    private TransformedInstance head;
    @Shadow
    private @Nullable TransformedInstance smallRods;
    @Shadow
    private @Nullable TransformedInstance largeRods;
    @Shadow
    private @Nullable ScrollInstance flame;
    @Shadow
    private @Nullable TransformedInstance hat;
    @Unique
    private @Nullable PartialModel hatModelOverride;
    @Unique
    private @Nullable BlazeBurnerRenderOverride renderOverride;

    private BlazeBurnerVisualMixin(VisualizationContext ctx, BlazeBurnerBlockEntity blockEntity, float partialTick) {
        super(ctx, blockEntity, partialTick);
    }

    @ModifyVariable(method = "animate", at = @At(value = "STORE", ordinal = 0), index = 5)
    private boolean updateChefHat(boolean hatPresent) {
        var renderOverride = ((BlazeBurnerClientExtension) blockEntity).getRenderOverride();
        PartialModel hatModelOverride = renderOverride == null
                ? null
                : renderOverride.getHatModel(heatLevel == HeatLevel.SMOULDERING);
        if (hatPresent || hatModelOverride == null) {
            if (this.hatModelOverride != null && hat != null) {
                hat.delete();
                hat = null;
            }
            this.hatModelOverride = null;
        } else {
            if (this.hatModelOverride != hatModelOverride) {
                var instancer = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(hatModelOverride));
                if (hat != null)
                    instancer.stealInstance(hat);
                else
                    hat = instancer.createInstance();
                this.hatModelOverride = hatModelOverride;
            }
        }
        return hatPresent || hatModelOverride != null;
    }

    @Inject(method = "animate", at = {
            @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerRenderer;getBlazeModel(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;Z)Ldev/engine_room/flywheel/lib/model/baked/PartialModel;"),
            @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerVisual;setupFlameInstance()V")
    })
    private void animate$resetOverride(float partialTicks, CallbackInfo ci) {
        this.renderOverride = null;
    }

    @Inject(method = "animate", at = @At(value = "INVOKE", target = "Ljava/lang/Object;hashCode()I"))
    private void animate$setupOverride(float partialTicks, CallbackInfo ci) {
        var renderOverride = ((BlazeBurnerClientExtension) blockEntity).getRenderOverride();
        if (this.renderOverride != renderOverride) {
            PartialModel headModel = renderOverride != null
                    ? renderOverride.getBlazeModel(heatLevel, validBlockAbove)
                    : BlazeBurnerRenderer.getBlazeModel(heatLevel, validBlockAbove);
            instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(headModel)).stealInstance(head);
            boolean superHeated = heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING;
            if (smallRods != null) {
                PartialModel smallRodsModel = renderOverride != null
                        ? renderOverride.getSmallRodsModel(superHeated)
                        : (superHeated ? AllPartialModels.BLAZE_BURNER_SUPER_RODS : AllPartialModels.BLAZE_BURNER_RODS);
                instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(smallRodsModel)).stealInstance(smallRods);
            }
            if (largeRods != null) {
                PartialModel largeRodsModel = renderOverride != null
                        ? renderOverride.getLargeRodsModel(superHeated)
                        : (superHeated ? AllPartialModels.BLAZE_BURNER_SUPER_RODS_2 : AllPartialModels.BLAZE_BURNER_RODS_2);
                instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(largeRodsModel)).stealInstance(largeRods);
            }
            if (flame != null) {
                SpriteShiftEntry spriteShift = renderOverride != null
                        ? renderOverride.getFlameSpriteShift(superHeated)
                        : (superHeated ? AllSpriteShifts.SUPER_BURNER_FLAME : AllSpriteShifts.BURNER_FLAME);

                float spriteWidth = spriteShift.getTarget().getU1() - spriteShift.getTarget().getU0();
                float spriteHeight = spriteShift.getTarget().getV1() - spriteShift.getTarget().getV0();
                float speed = 1 / 32f + 1 / 64f * heatLevel.ordinal();
                flame.speedU = speed / 2;
                flame.speedV = speed;
                flame.scaleU = spriteWidth / 2;
                flame.scaleV = spriteHeight / 2;
                flame.diffU = spriteShift.getTarget().getU0() - spriteShift.getOriginal().getU0();
                flame.diffV = spriteShift.getTarget().getV0() - spriteShift.getOriginal().getV0();
            }
            this.renderOverride = renderOverride;
        }
    }
}
