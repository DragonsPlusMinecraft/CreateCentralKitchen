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
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.extradelight.recipe.ExtraDelightRecipeConverters;

@Mixin(BasinOperatingBlockEntity.class)
public abstract class BasinOperatingBlockEntityMixin extends KineticBlockEntity {
    public BasinOperatingBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @ModifyReturnValue(method = "getMatchingRecipes", at = @At("RETURN"))
    private List<Recipe<?>> addCuttingBoardRecipe(List<Recipe<?>> original) {
        assert level != null;
        var recipeManager = level.getRecipeManager();
        if (CCKConfig.recipes().convertMortarGrindingRecipesToCompactingRecipes.get()) {
            ;
            var r = recipeManager.getAllRecipesFor(ExtraDelightRecipes.MORTAR.get())
                    .stream().map(ExtraDelightRecipeConverters.AUTOMATIC_GRINDING.apply(level.registryAccess())).map(RecipeHolder::value).collect(Collectors.toSet());
            original.addAll(r);
        }
        if (CCKConfig.recipes().convertJuicerRecipesToCompactingRecipes.get()) {
            ;
            var r = recipeManager.getAllRecipesFor(ExtraDelightRecipes.JUICER.get())
                    .stream().map(ExtraDelightRecipeConverters.AUTOMATIC_JUICING.apply(level.registryAccess())).map(RecipeHolder::value).collect(Collectors.toSet());
            original.addAll(r);
        }
        if (CCKConfig.recipes().convertMeltingPotRecipesToMixingRecipes.get()) {
            ;
            var r = recipeManager.getAllRecipesFor(ExtraDelightRecipes.MELTING_POT.get())
                    .stream().map(ExtraDelightRecipeConverters.AUTOMATIC_MELTING.apply(level.registryAccess())).map(RecipeHolder::value).collect(Collectors.toSet());
            original.addAll(r);
        }
        // Extra Delight itself makes every mixing recipe to create-mixing recipe, so we don't need to do conversion for that.
        /*if (CCKConfig.recipes().convertMixingBowlRecipesToMixingRecipes.get()) {;
            var r = recipeManager.getAllRecipesFor(ExtraDelightRecipes.MIXING_BOWL.get())
                    .stream().map(ExtraDelightRecipeConverters.AUTOMATIC_MIXING.apply(level.registryAccess())).map(RecipeHolder::value).collect(Collectors.toSet());
            original.addAll(r);
        }*/
        return original;
    }
}
