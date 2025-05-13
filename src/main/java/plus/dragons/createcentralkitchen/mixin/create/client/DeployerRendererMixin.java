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

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.data.CCKItemTags;
import plus.dragons.createcentralkitchen.mixin.create.DeployerBlockEntityAccessor;

@Mixin(DeployerRenderer.class)
public class DeployerRendererMixin {
    @ModifyVariable(method = "renderItem", ordinal = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;"))
    private boolean modifyToolOffset(boolean punching, @Local(name = "displayMode") boolean displayMode, @Local(argsOnly = true) DeployerBlockEntity deployer, @Local(argsOnly = true) PoseStack ms) {
        if (punching || displayMode || !CCKConfig.client().renderDeployerUsingItemWithCustomTransform.get())
            return punching;
        if (((DeployerBlockEntityAccessor) deployer).getHeldItem().is(CCKItemTags.HANDHELD_IN_DEPLOYER_USE)) {
            ms.translate(0, -1 / 8f, 0);
            ms.mulPose(Axis.XP.rotationDegrees(-22.5f));
            return true;
        }
        return false;
    }
}
