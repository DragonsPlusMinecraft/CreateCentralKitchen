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

package plus.dragons.createcentralkitchen.mixin.extradelight;

import com.lance5057.extradelight.ExtraDelightRecipes;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.extradelight.ExtraDelightIntegration;
import plus.dragons.createcentralkitchen.integration.extradelight.recipe.ExtraDelightRecipeConverters;

@Restriction(require = @Condition(ModIntegration.Mods.EXTRADELIGHT))
@Mixin(RecipeFinder.class)
public abstract class RecipeFinderMixin {
    @Unique
    @Nullable
    private final static Collection<RecipeHolder<? extends Recipe<?>>> create_central_kitchen$CACHED_MIXING_RECIPE = new HashSet<>();

    @Unique
    @Nullable
    private final static Collection<RecipeHolder<? extends Recipe<?>>> create_central_kitchen$CACHED_COMPACTING_RECIPE = new HashSet<>();

    static {
        ExtraDelightIntegration.Common.RELOADABLE_RECIPES.add(create_central_kitchen$CACHED_MIXING_RECIPE);
        ExtraDelightIntegration.Common.RELOADABLE_RECIPES.add(create_central_kitchen$CACHED_COMPACTING_RECIPE);
    }

    @ModifyReturnValue(method = "get", at = @At(value = "RETURN"))
    private static List<RecipeHolder<? extends Recipe<?>>> addRecipe(List<RecipeHolder<? extends Recipe<?>>> original, @Nullable Object cacheKey, Level level, Predicate<RecipeHolder<? extends Recipe<?>>> conditions) {
        if (cacheKey == MechanicalMixerBlockEntityAccessor.getShapelessOrMixingRecipesKey()) {
            if (create_central_kitchen$CACHED_MIXING_RECIPE.isEmpty()) {
                var recipeManager = level.getRecipeManager();
                if (CCKConfig.recipes().convertMeltingPotRecipesToMixingRecipes.get()) {
                    create_central_kitchen$CACHED_MIXING_RECIPE.addAll(
                            recipeManager.getAllRecipesFor(ExtraDelightRecipes.MELTING_POT.get())
                                    .stream().map(ExtraDelightRecipeConverters.AUTOMATIC_MELTING).filter(conditions).collect(Collectors.toSet()));
                }
            }
            original.addAll(create_central_kitchen$CACHED_MIXING_RECIPE);
        } else if (cacheKey == MechanicalPressBlockEntityAccessor.getCompressingRecipesKey()) {
            var recipeManager = level.getRecipeManager();
            if (create_central_kitchen$CACHED_COMPACTING_RECIPE.isEmpty()) {
                if (CCKConfig.recipes().convertMortarGrindingRecipesToCompactingRecipes.get()) {
                    create_central_kitchen$CACHED_COMPACTING_RECIPE.addAll(recipeManager.getAllRecipesFor(ExtraDelightRecipes.MORTAR.get())
                            .stream().map(ExtraDelightRecipeConverters.AUTOMATIC_GRINDING).filter(conditions).collect(Collectors.toSet()));

                }
                if (CCKConfig.recipes().convertJuicerRecipesToCompactingRecipes.get()) {
                    create_central_kitchen$CACHED_COMPACTING_RECIPE.addAll(recipeManager.getAllRecipesFor(ExtraDelightRecipes.JUICER.get())
                            .stream().map(ExtraDelightRecipeConverters.AUTOMATIC_JUICING).filter(conditions).collect(Collectors.toSet()));

                }
            }
            original.addAll(create_central_kitchen$CACHED_COMPACTING_RECIPE);
        }
        return original;
    }
}
