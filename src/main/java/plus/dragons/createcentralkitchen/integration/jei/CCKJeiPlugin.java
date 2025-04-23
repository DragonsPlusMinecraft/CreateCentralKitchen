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

package plus.dragons.createcentralkitchen.integration.jei;

import com.google.common.base.Preconditions;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import org.jetbrains.annotations.ApiStatus.Internal;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.farmersdelight.CuttingBoardRecipeConverter;
import plus.dragons.createdragonsplus.util.ErrorMessages;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

@JeiPlugin
public class CCKJeiPlugin implements IModPlugin {
    public static final ResourceLocation ID = CCKCommon.asResource("jei");
    public static final RecipeType<CuttingRecipe> SAWING = RecipeType
            .create(Create.ID, "sawing", CuttingRecipe.class);
    public static final RecipeType<DeployerApplicationRecipe> DEPLOYING = RecipeType
            .create(Create.ID, "deploying", DeployerApplicationRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var recipeManager = getRecipeManager();
        if (ModIntegration.FARMERSDELIGHT.enabled()) {
            var cuttingBoardRecipes = recipeManager.getAllRecipesFor(ModRecipeTypes.CUTTING.get());
            if (CCKConfig.recipes().convertCuttingBoardRecipesToSawingRecipes.get()) {
                ItemStack knife = new ItemStack(ModItems.IRON_KNIFE.get());
                registration.addRecipes(SAWING, cuttingBoardRecipes.stream()
                        .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                        .filter(holder -> holder.value().getTool().test(knife))
                        .map(CuttingBoardRecipeConverter::asSawing)
                        .map(RecipeHolder::value)
                        .toList());
            }
            if (CCKConfig.recipes().convertCuttingBoardRecipesToDeployingRecipes.get()) {
                registration.addRecipes(DEPLOYING, cuttingBoardRecipes.stream()
                        .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                        .map(CuttingBoardRecipeConverter::asDeploying)
                        .map(RecipeHolder::value)
                        .toList());
            }
        }
    }

    @Internal
    public static Level getLevel() {
        if (FMLLoader.getDist() != Dist.CLIENT)
            throw new IllegalStateException("Retreiving client level is only supported for client");
        var minecraft = Minecraft.getInstance();
        Preconditions.checkNotNull(minecraft, ErrorMessages.notNull("minecraft"));
        var level = minecraft.level;
        Preconditions.checkNotNull(level, ErrorMessages.notNull("level"));
        return level;
    }

    @Internal
    public static RecipeManager getRecipeManager() {
        if (FMLLoader.getDist() != Dist.CLIENT)
            throw new IllegalStateException("Retreiving recipe manager from client level is only supported for client");
        var minecraft = Minecraft.getInstance();
        Preconditions.checkNotNull(minecraft, ErrorMessages.notNull("minecraft"));
        var level = minecraft.level;
        Preconditions.checkNotNull(level, ErrorMessages.notNull("level"));
        return level.getRecipeManager();
    }
}
