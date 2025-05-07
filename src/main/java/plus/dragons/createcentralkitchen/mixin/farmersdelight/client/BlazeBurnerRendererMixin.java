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

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import plus.dragons.createcentralkitchen.client.model.CCKPartialModels;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.farmersdelight.burner.ChefBlazeBurnerBlockEntity;

@Restriction(require = @Condition(ModIntegration.Constants.FARMERSDELIGHT))
@Mixin(BlazeBurnerRenderer.class)
public class BlazeBurnerRendererMixin {
    @ModifyVariable(method = "renderSafe(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "STORE", ordinal = 0))
    private PartialModel updateChefHat(PartialModel drawHat, @Local(argsOnly = true) BlazeBurnerBlockEntity burner) {
        if (drawHat == null && ((ChefBlazeBurnerBlockEntity) burner).isChef())
            return CCKPartialModels.CHEF_HAT;
        return drawHat;
    }

    @SuppressWarnings("UnresolvedLocalCapture")
    @ModifyVariable(method = "renderShared", at = @At(value = "LOAD", ordinal = 0), argsOnly = true)
    private static PartialModel renderChefHat(PartialModel drawHat,
            @Local(argsOnly = true, ordinal = 0) PoseStack ms,
            @Local(argsOnly = true, ordinal = 1) @Nullable PoseStack modelTransform,
            @Local(argsOnly = true) BlockState blockState,
            @Local(argsOnly = true) MultiBufferSource bufferSource,
            @Local(argsOnly = true, ordinal = 1) float horizontalAngle,
            @Local(name = "headY") float headY,
            @Local(name = "blazeModel") PartialModel blazeModel) {
        if (drawHat == CCKPartialModels.CHEF_HAT) {
            if (blazeModel == AllPartialModels.BLAZE_INERT)
                drawHat = CCKPartialModels.CHEF_HAT_SMALL;
            SuperByteBuffer hatBuffer = CachedBuffers.partial(drawHat, blockState);
            if (modelTransform != null)
                hatBuffer.transform(modelTransform);
            hatBuffer.translate(0, headY + 0.75f, 0);
            VertexConsumer cutout = bufferSource.getBuffer(RenderType.cutoutMipped());
            hatBuffer
                    .rotateCentered(horizontalAngle + Mth.PI, Direction.UP)
                    .translate(0.5f, 0, 0.5f)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(ms, cutout);
            return null;
        }
        return drawHat;
    }
}
