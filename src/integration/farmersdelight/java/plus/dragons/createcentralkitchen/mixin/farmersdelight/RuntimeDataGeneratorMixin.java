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

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.JsonOps;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.data.RuntimeDataGenerator;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Optional;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.createmod.catnip.codecs.CatnipCodecUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import vectorwing.farmersdelight.common.registry.ModItems;

@Restriction(require = @Condition(ModIntegration.Mods.FARMERSDELIGHT))
@Mixin(RuntimeDataGenerator.class)
public class RuntimeDataGeneratorMixin {
    @Shadow
    @Final
    private static Object2ObjectOpenHashMap<ResourceLocation, JsonElement> JSON_FILES;

    @WrapOperation(method = "cuttingRecipes", at = @At(value = "INVOKE", ordinal = 0, target = "Lcom/simibubi/create/foundation/data/RuntimeDataGenerator;simpleWoodRecipe(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;)V"))
    private static void addTreeBarkToStripCuttingRecipes(ResourceLocation unstripped, ResourceLocation stripped, Operation<Void> original, @Local(name = "type") String type) {
        if (CCKConfig.recipes().addTreeBarkToStripSawingRecipes.get()) {
            if (BuiltInRegistries.ITEM.containsKey(stripped)) {
                var id = Create.asResource("cutting/runtime_generated/compat/" + unstripped.getNamespace() +
                        "/" + unstripped.getPath() + "_to_" + stripped.getPath());
                var recipe = new StandardProcessingRecipe.Builder<>(CuttingRecipe::new, id)
                        .require(BuiltInRegistries.ITEM.get(unstripped))
                        .output(BuiltInRegistries.ITEM.get(stripped))
                        .output(type.contains("block") ? ModItems.STRAW.get() : ModItems.TREE_BARK.get())
                        .duration(50)
                        .build();
                var serialized = CatnipCodecUtils.encode(Recipe.CONDITIONAL_CODEC, JsonOps.INSTANCE, Optional.of(new WithConditions<>(recipe)));
                serialized.ifPresent(r -> JSON_FILES.put(id.withPrefix("recipe/"), r));
            }
        } else original.call(unstripped, stripped);
    }
}
