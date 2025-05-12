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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.client.burner.BlazeBurnerClientExtension;

@Mixin(BlazeBurnerRenderer.class)
public class BlazeBurnerRendererMixin {
    @WrapOperation(method = "renderSafe(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerRenderer;renderShared(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;FFZZLdev/engine_room/flywheel/lib/model/baked/PartialModel;I)V"))
    private void renderSafe$overrideRender(PoseStack ms, @Nullable PoseStack modelTransform, MultiBufferSource bufferSource,
            Level level, BlockState blockState, HeatLevel heatLevel, float animation, float horizontalAngle,
            boolean canDrawFlame, boolean drawGoggles, PartialModel drawHat, int hashCode,
            Operation<Void> original, @Local(argsOnly = true) BlazeBurnerBlockEntity burner) {
        var renderOverride = ((BlazeBurnerClientExtension) burner).getRenderOverride();
        if (renderOverride == null)
            original.call(ms, modelTransform, bufferSource,
                    level, blockState, heatLevel, animation, horizontalAngle,
                    canDrawFlame, drawGoggles, drawHat, hashCode);
        else
            renderOverride.render(ms, modelTransform, bufferSource,
                    level, blockState, heatLevel, animation, horizontalAngle,
                    canDrawFlame, drawGoggles, drawHat, hashCode);
    }
}
