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

import com.lance5057.extradelight.ExtraDelightBlocks;
import com.lance5057.extradelight.ExtraDelightRecipes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.DeployingCategory;
import com.simibubi.create.compat.jei.category.MixingCategory;
import com.simibubi.create.compat.jei.category.PackingCategory;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.extradelight.recipe.ExtraDelightRecipeConverters;
import plus.dragons.createcentralkitchen.integration.jei.CCKJeiPlugin;

@Restriction(require = @Condition(ModIntegration.Mods.EXTRADELIGHT))
@Mixin(CCKJeiPlugin.class)
public abstract class CCKJeiPluginMixin {
    @Unique
    private static final RecipeType<RecipeHolder<CompactingRecipe>> AUTOMATIC_GRINDING = RecipeType
            .createRecipeHolderType(CCKCommon.asResource("extradelight.automatic_grinding"));
    private static final RecipeType<RecipeHolder<CompactingRecipe>> AUTOMATIC_JUICING = RecipeType
            .createRecipeHolderType(CCKCommon.asResource("extradelight.automatic_juicing"));
    private static final RecipeType<RecipeHolder<MixingRecipe>> AUTOMATIC_MELTING = RecipeType
            .createRecipeHolderType(CCKCommon.asResource("extradelight.automatic_melting"));
    // Extra Delight itself makes every mixing recipe to create-mixing recipe, so we don't need to do conversion for that.
    /*private static final RecipeType<RecipeHolder<BasinRecipe>> AUTOMATIC_MIXING = RecipeType
            .createRecipeHolderType(CCKCommon.asResource("extradelight.automatic_mixing"));*/
    private static final RecipeType<RecipeHolder<DeployerApplicationRecipe>> AUTOMATIC_GINGERBREAD_DECORATING = RecipeType
            .createRecipeHolderType(CCKCommon.asResource("extradelight.automatic_gingerbread_decorating"));

    @Shadow
    public static Level getLevel() {
        throw new AssertionError();
    }

    @Shadow
    public static RecipeManager getRecipeManager() {
        throw new AssertionError();
    }

    @Inject(method = "loadCategories", at = @At("RETURN"))
    private void loadCategories$extradelight(CallbackInfo ci) {
        CreateRecipeCategory<?> grinding = ((CCKJeiPlugin) (Object) this).builder(BasinRecipe.class)
                .addTypedRecipes(AllRecipeTypes.PRESSING)
                .catalyst(AllBlocks.MECHANICAL_PRESS::get)
                .catalyst(AllBlocks.BASIN::get)
                .doubleItemIcon(AllBlocks.MECHANICAL_PRESS.get(), ExtraDelightBlocks.MORTAR_STONE.get())
                .emptyBackground(177, 103)
                .build(CCKCommon.asResource("extradelight.automatic_grinding"), PackingCategory::standard);

        CreateRecipeCategory<?> juicing = ((CCKJeiPlugin) (Object) this).builder(BasinRecipe.class)
                .addTypedRecipes(AllRecipeTypes.PRESSING)
                .catalyst(AllBlocks.MECHANICAL_PRESS::get)
                .catalyst(AllBlocks.BASIN::get)
                .doubleItemIcon(AllBlocks.MECHANICAL_PRESS.get(), ExtraDelightBlocks.JUICER.get())
                .emptyBackground(177, 103)
                .build(CCKCommon.asResource("extradelight.automatic_juicing"), PackingCategory::standard);

        CreateRecipeCategory<?> melting = ((CCKJeiPlugin) (Object) this).builder(BasinRecipe.class)
                .catalyst(AllBlocks.MECHANICAL_MIXER::get)
                .catalyst(AllBlocks.BASIN::get)
                .doubleItemIcon(AllBlocks.MECHANICAL_MIXER.get(), ExtraDelightBlocks.MELTING_POT.get())
                .emptyBackground(177, 103)
                .build(CCKCommon.asResource("extradelight.automatic_melting"), MixingCategory::standard);

        // Extra Delight itself makes every mixing recipe to create-mixing recipe, so we don't need to do conversion for that.
        /*CreateRecipeCategory<?> mixing = ((CCKJeiPlugin) (Object) this).builder(BasinRecipe.class)
                .catalyst(AllBlocks.MECHANICAL_MIXER::get)
                .catalyst(AllBlocks.BASIN::get)
                .doubleItemIcon(AllBlocks.MECHANICAL_MIXER.get(), ExtraDelightBlocks.MIXING_BOWL.get())
                .emptyBackground(177, 103)
                .build(CCKCommon.asResource("extradelight.automatic_mixing"), MixingCategory::standard);*/

        CreateRecipeCategory<?> automaticGingerbreadDecorating = ((CCKJeiPlugin) (Object) this).builder(DeployerApplicationRecipe.class)
                .addTypedRecipes(AllRecipeTypes.DEPLOYING)
                .catalyst(AllBlocks.DEPLOYER::get)
                .catalyst(AllBlocks.DEPOT::get)
                .catalyst(AllItems.BELT_CONNECTOR::get)
                .doubleItemIcon(AllBlocks.DEPLOYER.get(), ExtraDelightBlocks.GINGERBREAD_COOKIE_BLOCK)
                .emptyBackground(177, 70)
                .build(CCKCommon.asResource("extradelight.automatic_gingerbread_decorating"), DeployingCategory::new);
    }

    @Inject(method = "registerRecipes", at = @At("HEAD"))
    private void registerRecipes$extradelight(IRecipeRegistration registration, CallbackInfo ci) {
        RecipeManager recipeManager = getRecipeManager();
        var mortarRecipes = recipeManager.getAllRecipesFor(ExtraDelightRecipes.MORTAR.get());
        if (CCKConfig.recipes().convertMortarGrindingRecipesToCompactingRecipes.get()) {
            registration.addRecipes(AUTOMATIC_GRINDING, mortarRecipes.stream()
                    .map(ExtraDelightRecipeConverters.AUTOMATIC_GRINDING)
                    .toList());
        }
        var juicerRecipes = recipeManager.getAllRecipesFor(ExtraDelightRecipes.JUICER.get());
        if (CCKConfig.recipes().convertJuicerRecipesToCompactingRecipes.get()) {
            registration.addRecipes(AUTOMATIC_JUICING, juicerRecipes.stream()
                    .map(ExtraDelightRecipeConverters.AUTOMATIC_JUICING)
                    .toList());
        }
        var meltingPotRecipes = recipeManager.getAllRecipesFor(ExtraDelightRecipes.MELTING_POT.get());
        if (CCKConfig.recipes().convertMeltingPotRecipesToMixingRecipes.get()) {
            registration.addRecipes(AUTOMATIC_MELTING, meltingPotRecipes.stream()
                    .map(ExtraDelightRecipeConverters.AUTOMATIC_MELTING)
                    .toList());
        }
        // Extra Delight itself makes every mixing recipe to create-mixing recipe, so we don't need to do conversion for that.
        /*var mixingBowlRecipes = recipeManager.getAllRecipesFor(ExtraDelightRecipes.MIXING_BOWL.get());
        if (CCKConfig.recipes().convertMixingBowlRecipesToMixingRecipes.get()) {
            registration.addRecipes(AUTOMATIC_MIXING, mixingBowlRecipes.stream()
                    .map(ExtraDelightRecipeConverters.AUTOMATIC_MIXING.apply(level.registryAccess()))
                    .toList());
        }*/
        var toolOnBlockRecipes = recipeManager.getAllRecipesFor(ExtraDelightRecipes.TOOL_ON_BLOCK.get());
        if (CCKConfig.recipes().convertToolOnBlockRecipesToDeployingRecipes.get()) {
            registration.addRecipes(AUTOMATIC_GINGERBREAD_DECORATING, toolOnBlockRecipes.stream()
                    .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                    .map(ExtraDelightRecipeConverters.AUTOMATIC_GINGERBREAD_DECORATING)
                    .toList());
        }
    }
}
