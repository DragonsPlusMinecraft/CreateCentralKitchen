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

package plus.dragons.createcentralkitchen.integration.extradelight.recipe;

import com.google.common.cache.CacheBuilder;
import com.lance5057.extradelight.ExtraDelightRecipes;
import com.lance5057.extradelight.recipe.ToolOnBlockRecipe;
import com.lance5057.extradelight.workstations.juicer.JuicerRecipe;
import com.lance5057.extradelight.workstations.meltingpot.MeltingPotRecipe;
import com.lance5057.extradelight.workstations.mortar.recipes.MortarRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerRecipeSearchEvent;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import java.awt.*;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.mixin.extradelight.SingleItemRecipeAccessor;
import plus.dragons.createdragonsplus.common.recipe.RecipeConverter;

public class ExtraDelightRecipeConverters {
    public static final RecipeConverter<MortarRecipe, CompactingRecipe> AUTOMATIC_GRINDING = RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_using_press");
                var builder = new StandardProcessingRecipe.Builder<>(CompactingRecipe::new, id)
                        .require(recipe.getIngredients().getFirst());
                var result = ((SingleItemRecipeAccessor) recipe).getResult();
                if (!result.isEmpty())
                    builder.output(result);
                var fluid = recipe.getFluid();
                if (!fluid.isEmpty())
                    builder.output(fluid);
                return new RecipeHolder<>(id, builder.build());
            });

    public static final RecipeConverter<JuicerRecipe, CompactingRecipe> AUTOMATIC_JUICING = RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_using_press");
                var builder = new StandardProcessingRecipe.Builder<>(CompactingRecipe::new, id)
                        .require(recipe.getIngredients().getFirst());
                builder.output(recipe.getChance() * 0.01f, ((SingleItemRecipeAccessor) recipe).getResult());
                builder.output(recipe.getFluid());
                return new RecipeHolder<>(id, builder.build());
            });

    public static final RecipeConverter<MeltingPotRecipe, MixingRecipe> AUTOMATIC_MELTING = RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_using_mixer");
                var builder = new StandardProcessingRecipe.Builder<>(MixingRecipe::new, id)
                        .require(recipe.input);
                builder.output(recipe.result);
                builder.requiresHeat(HeatCondition.HEATED);
                return new RecipeHolder<>(id, builder.build());
            });

    public static final RecipeConverter<ToolOnBlockRecipe, DeployerApplicationRecipe> AUTOMATIC_GINGERBREAD_DECORATING = RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_using_deployer");
                var builder = new ItemApplicationRecipe.Builder<>(DeployerApplicationRecipe::new, id)
                        .require(recipe.getIn())
                        .require(recipe.getTool())
                        .output(recipe.getOut());
                return new RecipeHolder<>(id, builder.build());
            });

    // Extra Delight itself makes every mixing recipe to create-mixing recipe, so we don't need to do conversion for that.

    /*public static final Function<HolderLookup.Provider, RecipeConverter<MixingBowlRecipe, BasinRecipe>> AUTOMATIC_MIXING = registry -> RecipeConverter.cached(
            CacheBuilder.newBuilder(), holder -> {
                var recipe = holder.value();
                var id = holder.id().withSuffix("_using_mixer");
                var builder = new StandardProcessingRecipe.Builder<>(BasinRecipe::new, id);
                ArrayList<Ingredient> itemIngredients = new ArrayList<>(recipe.getIngredients());
                if(!recipe.getContainer().isEmpty()) itemIngredients.add(Ingredient.of(recipe.getContainer()));
                if(!itemIngredients.isEmpty()) builder.withItemIngredients(NonNullList.copyOf(itemIngredients));
                var fluidIngredients = recipe.getFluids();
                if(!fluidIngredients.isEmpty()) builder.withFluidIngredients(NonNullList.copyOf(fluidIngredients.stream()
                        .flatMap(sizedFluidIngredient -> convertBadDesignSizedFluidIngredient(sizedFluidIngredient.ingredient(),sizedFluidIngredient.amount())).toList()));
                builder.output(recipe.getResultItem(registry));
                return new RecipeHolder<>(id, builder.build());
            });
    
    private static Stream<? extends com.simibubi.create.foundation.fluid.FluidIngredient> convertBadDesignSizedFluidIngredient(FluidIngredient s, int amount) {
        ArrayList<com.simibubi.create.foundation.fluid.FluidIngredient> r = new ArrayList<>();
        if(s instanceof SingleFluidIngredient singleFluidIngredient){
            r.add(com.simibubi.create.foundation.fluid.FluidIngredient.fromFluid(singleFluidIngredient.fluid().value(), amount));
        } else if(s instanceof TagFluidIngredient tagFluidIngredient){
            r.add(com.simibubi.create.foundation.fluid.FluidIngredient.fromTag(tagFluidIngredient.tag(),amount));
        } else if(s instanceof CompoundFluidIngredient compoundFluidIngredient){
            r.addAll(compoundFluidIngredient.children().stream()
                    .flatMap(fluidIngredient -> convertBadDesignSizedFluidIngredient(fluidIngredient, amount)).toList());
        }
        return r.stream();
    }*/

    @SubscribeEvent
    public static void onDeployerRecipeSearch(final DeployerRecipeSearchEvent event) {
        if (CCKConfig.recipes().convertToolOnBlockRecipesToDeployingRecipes.get()) {
            var deployer = event.getBlockEntity();
            var inventory = event.getInventory();
            var level = deployer.getLevel();
            assert level != null;
            event.addRecipe(() -> level.getRecipeManager()
                    .getAllRecipesFor(ExtraDelightRecipes.TOOL_ON_BLOCK.get())
                    .stream()
                    .map(ExtraDelightRecipeConverters.AUTOMATIC_GINGERBREAD_DECORATING)
                    .filter(holder -> holder.value().matches(inventory, level))
                    .findFirst(), 50);
        }
    }
}
