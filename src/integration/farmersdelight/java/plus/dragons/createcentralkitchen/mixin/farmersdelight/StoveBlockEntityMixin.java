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

package plus.dragons.createcentralkitchen.mixin.farmersdelight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import plus.dragons.createcentralkitchen.access.farmersdelight.StoveBlockEntityAccess;

@Pseudo
@Mixin(targets = {
        "vectorwing.farmersdelight.common.block.entity.StoveBlockEntity",
        "com.soytutta.mynethersdelight.common.block.entity.NetherStoveBlockEntity",
        "cn.foggyhillside.ends_delight.block.entity.EndStoveBlockEntity"
})
public abstract class StoveBlockEntityMixin implements StoveBlockEntityAccess {}
