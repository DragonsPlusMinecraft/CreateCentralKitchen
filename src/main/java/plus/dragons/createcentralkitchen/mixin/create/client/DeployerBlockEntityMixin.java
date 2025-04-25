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
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DeployerBlockEntity.class)
public class DeployerBlockEntityMixin {
    @Shadow
    protected ItemStack heldItem;

    @ModifyExpressionValue(method = "getHandPose", at = @At(value = "FIELD", target = "Lcom/simibubi/create/AllPartialModels;DEPLOYER_HAND_HOLDING:Ldev/engine_room/flywheel/lib/model/baked/PartialModel;"))
    private PartialModel alwaysRenderToolAsPunching(PartialModel original) {
        return heldItem.is(Tags.Items.TOOLS) ? AllPartialModels.DEPLOYER_HAND_PUNCHING : original;
    }
}
