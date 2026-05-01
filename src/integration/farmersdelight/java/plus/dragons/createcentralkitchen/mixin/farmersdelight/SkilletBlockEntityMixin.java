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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.api.boiler.BoilerHeater;
import java.util.Optional;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.access.farmersdelight.SkilletBlockEntityAccess;
import plus.dragons.createcentralkitchen.integration.ModIntegration;
import vectorwing.farmersdelight.common.block.SkilletBlock;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.tag.ModTags;

@Restriction(require = @Condition(ModIntegration.Mods.FARMERSDELIGHT))
@Mixin(SkilletBlockEntity.class)
public abstract class SkilletBlockEntityMixin extends SyncedBlockEntity implements HeatableBlockEntity,
        SkilletBlockEntityAccess {
    @Shadow
    @Final
    private ItemStackHandler inventory;
    @Shadow
    private int cookingTime;
    @Shadow
    private int cookingTimeTotal;
    @Shadow
    private int fireAspectLevel;

    private SkilletBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Shadow
    protected abstract Optional<RecipeHolder<CampfireCookingRecipe>> getMatchingRecipe(ItemStack stack);

    @Shadow
    public abstract ItemStack getStoredStack();

    @Override
    public ItemStack addItemToCook(ItemStack stack, boolean simulate) {
        Optional<RecipeHolder<CampfireCookingRecipe>> recipe = this.getMatchingRecipe(stack);
        if (recipe.isPresent() && this.getStoredStack().isEmpty()) {
            if (this.getBlockState().getValue(SkilletBlock.WATERLOGGED))
                return stack;
            boolean wasEmpty = this.getStoredStack().isEmpty();
            ItemStack remainder = this.inventory.insertItem(0, stack.copy(), simulate);
            if (!ItemStack.matches(remainder, stack)) {
                if (!simulate) {
                    this.cookingTimeTotal = SkilletBlock.getSkilletCookingTime(recipe.get().value().getCookingTime(), this.fireAspectLevel);
                    this.cookingTime = 0;
                    if (wasEmpty && this.level != null && this.isHeated(this.level, this.worldPosition)) {
                        this.level.playSound(null, this.worldPosition.getX() + 0.5F, this.worldPosition.getY() + 0.5F, this.worldPosition.getZ() + 0.5F, ModSounds.BLOCK_SKILLET_ADD_FOOD.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
                    }
                }
                return remainder;
            }
        }
        return stack;
    }

    @ModifyExpressionValue(method = "cookAndOutputItems", at = @At(value = "FIELD", target = "Lvectorwing/farmersdelight/common/block/entity/SkilletBlockEntity;cookingTime:I", ordinal = 0))
    private int speedUpCooking(int cookTime, ItemStack cookingStack, Level level) {
        var pos = this.getBlockPos();
        var heaterPos = pos.below();
        var heaterState = level.getBlockState(heaterPos);
        BoilerHeater heater = BoilerHeater.REGISTRY.get(heaterState);
        if (heater == null && !this.requiresDirectHeat() && heaterState.is(ModTags.Blocks.HEAT_CONDUCTORS)) {
            heaterPos = pos.below(2);
            heaterState = level.getBlockState(pos.below(2));
            heater = BoilerHeater.REGISTRY.get(heaterState);
        }
        if (heater != null) {
            float heat = heater.getHeat(level, heaterPos, heaterState);
            if (heat > 0)
                cookTime = cookTime + (int) heat;
        }
        return cookTime;
    }
}
