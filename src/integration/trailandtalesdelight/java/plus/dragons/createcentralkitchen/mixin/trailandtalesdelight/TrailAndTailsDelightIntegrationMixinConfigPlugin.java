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

package plus.dragons.createcentralkitchen.mixin.trailandtalesdelight;/*
                                                                      * Copyright (C) 2025 DragonsPlus
                                                                      * SPDX-License-Identifier: LGPL-3.0-or-later
                                                                      * This program is free software: you can redistribute it and/or modify
                                                                      * it under the terms of the GNU General Public License as published by
                                                                      * the Free Software Foundation, either version 3 of the License, or
                                                                      * (at your option) any later version.
                                                                      * This program is distributed in the hope that it will be useful,
                                                                      * but WITHOUT ANY WARRANTY; without even the implied warranty of
                                                                      * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
                                                                      * GNU General Public License for more details.
                                                                      * You should have received a copy of the GNU General Public License
                                                                      * along with this program. If not, see <https://www.gnu.org/licenses/>.
                                                                      */

import java.util.List;
import java.util.Set;
import net.neoforged.fml.loading.LoadingModList;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import plus.dragons.createcentralkitchen.integration.ModIntegration.Mods;

public class TrailAndTailsDelightIntegrationMixinConfigPlugin implements IMixinConfigPlugin {
    private boolean enabled;

    @Override
    public void onLoad(String mixinPackage) {
        this.enabled = LoadingModList.get().getModFileById(Mods.TWILIGHTDELIGHT) != null;
    }

    @Override
    @Nullable
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return enabled;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    @Nullable
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
