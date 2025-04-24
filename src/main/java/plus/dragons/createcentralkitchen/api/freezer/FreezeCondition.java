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

package plus.dragons.createcentralkitchen.api.freezer;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecBuilders;
import net.createmod.catnip.lang.Lang;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import plus.dragons.createcentralkitchen.data.lang.CCKLang;

// TODO: Move this to Create: Dragons Plus
public enum FreezeCondition implements StringRepresentable {
    COOL(0xFFFFFF),
    FROZEN(0x8ADCE8),
    SUPERFROZEN(0x5C93E8);

    public static final Codec<FreezeCondition> CODEC = StringRepresentable.fromEnum(FreezeCondition::values);
    public static final StreamCodec<ByteBuf, FreezeCondition> STREAM_CODEC = CatnipStreamCodecBuilders.ofEnum(FreezeCondition.class);
    private final int color;

    FreezeCondition(int color) {
        this.color = color;
    }

    public boolean testFreezer(float freeze) {
        return switch (this) {
            case COOL -> freeze >= 0;
            case FROZEN -> freeze >= 1;
            case SUPERFROZEN -> freeze >= 2;
        };
    }

    public Component getComponent() {
        return CCKLang.translate("recipe.freeze_condition").color(color).component();
    }

    public int getColor() {
        return color;
    }

    @Override
    public String getSerializedName() {
        return Lang.asId(name());
    }
}
