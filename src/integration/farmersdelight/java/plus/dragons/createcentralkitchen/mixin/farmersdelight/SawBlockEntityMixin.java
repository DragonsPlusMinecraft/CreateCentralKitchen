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

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import java.util.List;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import plus.dragons.createcentralkitchen.integration.farmersdelight.recipe.CuttingBoardRecipeConverters;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

@Restriction(require = @Condition(ModIntegration.Mods.FARMERSDELIGHT))
@Mixin(SawBlockEntity.class)
public abstract class SawBlockEntityMixin extends BlockBreakingKineticBlockEntity {
    @Shadow
    public ProcessingInventory inventory;

    public SawBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @ModifyReturnValue(method = "getRecipes", at = @At("TAIL"))
    private List<RecipeHolder<? extends Recipe<?>>> addCuttingBoardRecipe(List<RecipeHolder<? extends Recipe<?>>> recipes) {
        if (CCKConfig.recipes().convertCuttingBoardRecipesToSawingRecipes.get()) {
            var input = CuttingBoardRecipeConverters.sawInput(inventory.getStackInSlot(0));
            assert level != null;
            level.getRecipeManager()
                    .getRecipeFor(ModRecipeTypes.CUTTING.get(), input, level)
                    .filter(AllRecipeTypes.CAN_BE_AUTOMATED)
                    .map(CuttingBoardRecipeConverters.SAWING)
                    .ifPresent(recipes::add);
        }
        return recipes;
    }
}
