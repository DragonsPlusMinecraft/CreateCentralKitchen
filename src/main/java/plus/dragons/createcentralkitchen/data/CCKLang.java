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

package plus.dragons.createcentralkitchen.data;

import static plus.dragons.createcentralkitchen.common.CCKCommon.REGISTRATE;

import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.lang.LangNumberFormat;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.ApiStatus.Internal;
import plus.dragons.createcentralkitchen.common.CCKCommon;

@Internal
public class CCKLang {
    public static final Component RUNTIME_PACK_TITLE = REGISTRATE
            .addLang("pack", CCKCommon.asResource("runtime"), CCKCommon.NAME);
    public static final Component RUNTIME_PACK_DESCRIPTION = REGISTRATE
            .addLang("pack", CCKCommon.asResource("runtime"), "description", CCKCommon.NAME + " Runtime Generated Resources");

    public static void register() {}

    public static LangBuilder builder() {
        return new LangBuilder(CCKCommon.ID);
    }

    public static LangBuilder number(double d) {
        return builder().text(LangNumberFormat.format(d));
    }

    public static LangBuilder text(String text) {
        return builder().text(text);
    }

    public static LangBuilder translate(String key, Object... args) {
        return builder().translate(key, args);
    }

    public static LangBuilder description(String category, ResourceLocation location, Object... args) {
        return builder().add(Component.translatable(Util.makeDescriptionId(category, location), args));
    }

    public static LangBuilder description(String category, ResourceLocation location, String suffix, Object... args) {
        return builder().add(Component.translatable(Util.makeDescriptionId(category, location) + "." + suffix, args));
    }

    public static LangBuilder description(Holder<?> holder, Object... args) {
        var key = holder.getKey();
        if (key == null)
            throw new IllegalArgumentException("Can not build description for unregistered object: " + holder);
        return description(key.registry().getPath(), key.location(), args);
    }

    public static LangBuilder description(Holder<?> holder, String suffix, Object... args) {
        var key = holder.getKey();
        if (key == null)
            throw new IllegalArgumentException("Can not build description for unregistered object: " + holder);
        return description(key.registry().getPath(), key.location(), suffix, args);
    }

    public static LangBuilder block(BlockState state) {
        return builder().add(state.getBlock().getName());
    }

    public static LangBuilder item(ItemStack stack) {
        return builder().add(stack.getHoverName().copy());
    }

    public static LangBuilder fluid(FluidStack stack) {
        return builder().add(stack.getHoverName().copy());
    }
}
