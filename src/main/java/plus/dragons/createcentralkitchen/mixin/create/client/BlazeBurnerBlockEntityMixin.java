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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import plus.dragons.createcentralkitchen.client.burner.BlazeBurnerClientExtension;
import plus.dragons.createcentralkitchen.client.burner.BlazeBurnerRenderOverride;

@Mixin(BlazeBurnerBlockEntity.class)
public abstract class BlazeBurnerBlockEntityMixin extends SmartBlockEntity implements BlazeBurnerClientExtension {
    @Unique
    private @Nullable BlazeBurnerRenderOverride renderOverride;

    protected BlazeBurnerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Nullable
    @Override
    public BlazeBurnerRenderOverride getRenderOverride() {
        return renderOverride;
    }

    @Override
    public void setRenderOverride(@Nullable BlazeBurnerRenderOverride renderOverride) {
        this.renderOverride = renderOverride;
    }

    @ModifyExpressionValue(method = "tickAnimation", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlockEntity;isValidBlockAbove()Z"))
    private boolean tickAnimation$modifyIsValidBlockAbove(boolean original) {
        return renderOverride == null ? original : renderOverride.isValidBlockAbove(original);
    }

    @ModifyArg(method = "spawnParticles", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private ParticleOptions spawnParticles$modifySoulFlame(ParticleOptions original) {
        return renderOverride == null ? original : renderOverride.getFlameParticle(true);
    }

    @ModifyArg(method = "spawnParticles", at = @At(value = "INVOKE", ordinal = 2, target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private ParticleOptions spawnParticles$modifyFlame(ParticleOptions original) {
        return renderOverride == null ? original : renderOverride.getFlameParticle(false);
    }

    @ModifyArg(method = "spawnParticleBurst", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private ParticleOptions spawnParticleBurst$modify(ParticleOptions original, @Local(argsOnly = true) boolean superHeated) {
        return renderOverride == null ? original : renderOverride.getFlameParticle(superHeated);
    }
}
