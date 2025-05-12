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

package plus.dragons.createcentralkitchen.client.burner;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createdragonsplus.util.CodeReference;

public interface BlazeBurnerRenderOverride {
    boolean isValid(Level level, BlockPos pos, BlazeBurnerBlockEntity burner);

    default boolean isValidBlockAbove(boolean original) {
        return original;
    }

    default PartialModel getBlazeModel(HeatLevel heatLevel, boolean active) {
        return BlazeBurnerRenderer.getBlazeModel(heatLevel, active);
    }

    default PartialModel getSmallRodsModel(boolean superHeated) {
        return superHeated ? AllPartialModels.BLAZE_BURNER_SUPER_RODS : AllPartialModels.BLAZE_BURNER_RODS;
    }

    default PartialModel getLargeRodsModel(boolean superHeated) {
        return superHeated ? AllPartialModels.BLAZE_BURNER_SUPER_RODS_2 : AllPartialModels.BLAZE_BURNER_RODS_2;
    }

    default SpriteShiftEntry getFlameSpriteShift(boolean superHeated) {
        return superHeated ? AllSpriteShifts.SUPER_BURNER_FLAME : AllSpriteShifts.BURNER_FLAME;
    }

    default ParticleOptions getFlameParticle(boolean superHeated) {
        return superHeated ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME;
    }

    default @Nullable PartialModel getHatModel(boolean small) {
        return null;
    }

    @CodeReference(value = BlazeBurnerRenderer.class, targets = "renderShared", source = "create", license = "mit")
    default void render(PoseStack poseStack, @Nullable PoseStack modelTransform, MultiBufferSource bufferSource,
            Level level, BlockState blockState, HeatLevel heatLevel, float animation, float horizontalAngle,
            boolean canDrawFlame, boolean drawGoggles, @Nullable PartialModel drawHat, int hashCode) {
        boolean blockAbove = animation > 0.125f;
        float time = AnimationTickHolder.getRenderTime(level);
        float renderTick = time + (hashCode % 13) * 16f;
        float offsetMult = heatLevel.isAtLeast(HeatLevel.FADING) ? 64 : 16;
        float offset = Mth.sin((float) ((renderTick / 16f) % (2 * Math.PI))) / offsetMult;
        float offset1 = Mth.sin((float) ((renderTick / 16f + Math.PI) % (2 * Math.PI))) / offsetMult;
        float offset2 = Mth.sin((float) ((renderTick / 16f + Math.PI / 2) % (2 * Math.PI))) / offsetMult;
        float headY = offset - (animation * .75f);

        poseStack.pushPose();

        var blazeModel = this.getBlazeModel(heatLevel, blockAbove);

        SuperByteBuffer blazeBuffer = CachedBuffers.partial(blazeModel, blockState);
        if (modelTransform != null)
            blazeBuffer.transform(modelTransform);
        blazeBuffer.translate(0, headY, 0);
        blazeBuffer.rotateCentered(horizontalAngle, Direction.UP)
                .light(LightTexture.FULL_BRIGHT)
                .renderInto(poseStack, bufferSource.getBuffer(RenderType.solid()));

        boolean small = heatLevel.isAtLeast(HeatLevel.FADING);
        if (drawGoggles) {
            PartialModel gogglesModel = small ? AllPartialModels.BLAZE_GOGGLES : AllPartialModels.BLAZE_GOGGLES_SMALL;

            SuperByteBuffer gogglesBuffer = CachedBuffers.partial(gogglesModel, blockState);
            if (modelTransform != null)
                gogglesBuffer.transform(modelTransform);
            gogglesBuffer.translate(0, headY + 8 / 16f, 0);
            gogglesBuffer.rotateCentered(horizontalAngle, Direction.UP)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(poseStack, bufferSource.getBuffer(RenderType.solid()));
        }

        if (drawHat == null)
            drawHat = getHatModel(small);
        if (drawHat != null) {
            SuperByteBuffer hatBuffer = CachedBuffers.partial(drawHat, blockState);
            if (modelTransform != null)
                hatBuffer.transform(modelTransform);
            hatBuffer.translate(0, headY + 0.75f, 0);
            hatBuffer
                    .rotateCentered(horizontalAngle + Mth.PI, Direction.UP)
                    .translate(0.5f, 0, 0.5f)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
        }

        boolean superHeated = heatLevel == HeatLevel.SEETHING;
        if (heatLevel.isAtLeast(HeatLevel.FADING)) {
            var smallRodsModel = getSmallRodsModel(superHeated);
            var largeRodsModel = getLargeRodsModel(superHeated);

            SuperByteBuffer rodsBuffer = CachedBuffers.partial(smallRodsModel, blockState);
            if (modelTransform != null)
                rodsBuffer.transform(modelTransform);
            rodsBuffer.translate(0, offset1 + animation + .125f, 0)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(poseStack, bufferSource.getBuffer(RenderType.solid()));

            SuperByteBuffer rodsBuffer2 = CachedBuffers.partial(largeRodsModel, blockState);
            if (modelTransform != null)
                rodsBuffer2.transform(modelTransform);
            rodsBuffer2.translate(0, offset2 + animation - 3 / 16f, 0)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(poseStack, bufferSource.getBuffer(RenderType.solid()));
        }

        if (canDrawFlame && blockAbove) {
            SpriteShiftEntry spriteShift = getFlameSpriteShift(superHeated);

            float spriteWidth = spriteShift.getTarget()
                    .getU1()
                    - spriteShift.getTarget()
                            .getU0();

            float spriteHeight = spriteShift.getTarget()
                    .getV1()
                    - spriteShift.getTarget()
                            .getV0();

            float speed = 1 / 32f + 1 / 64f * heatLevel.ordinal();

            double vScroll = speed * time;
            vScroll = vScroll - Math.floor(vScroll);
            vScroll = vScroll * spriteHeight / 2;

            double uScroll = speed * time / 2;
            uScroll = uScroll - Math.floor(uScroll);
            uScroll = uScroll * spriteWidth / 2;

            SuperByteBuffer flameBuffer = CachedBuffers.partial(AllPartialModels.BLAZE_BURNER_FLAME, blockState);
            if (modelTransform != null)
                flameBuffer.transform(modelTransform);
            flameBuffer.shiftUVScrolling(spriteShift, (float) uScroll, (float) vScroll);

            VertexConsumer cutout = bufferSource.getBuffer(RenderType.cutoutMipped());
            flameBuffer.rotateCentered(horizontalAngle, Direction.UP)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(poseStack, cutout);
        }

        poseStack.popPose();
    }
}
