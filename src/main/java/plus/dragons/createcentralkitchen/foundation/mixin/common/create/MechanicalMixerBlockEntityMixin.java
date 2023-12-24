package plus.dragons.createcentralkitchen.foundation.mixin.common.create;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.foundation.data.recipe.IgnoreAutomaticShapelessRecipe;

@Mixin(value = MechanicalMixerBlockEntity.class, priority = 900, remap = false)
public abstract class MechanicalMixerBlockEntityMixin extends BasinOperatingBlockEntity {

    public MechanicalMixerBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    /**
     * For removing recipes that has tag create:ignored_in_automatic_shapeless in their ingredients/result.
     * Item that has a container will inherit this behavior from its container.
     *
     * @param recipe the recipe to check
     * @param original the original method call
     * @see IgnoreAutomaticShapelessRecipe
     * @author LimonBlaze
     */
    @WrapOperation(method = "matchStaticFilters", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/AllRecipeTypes;shouldIgnoreInAutomation(Lnet/minecraft/world/item/crafting/Recipe;)Z"))
    private boolean cck$shouldIgnoreShapelessRecipe(Recipe<?> recipe, Operation<Boolean> original) {
        return IgnoreAutomaticShapelessRecipe.get(recipe,getLevel().registryAccess()) || original.call(recipe);
    }
    
}
