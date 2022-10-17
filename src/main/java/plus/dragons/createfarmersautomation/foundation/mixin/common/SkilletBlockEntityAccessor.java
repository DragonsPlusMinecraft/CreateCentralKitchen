package plus.dragons.createfarmersautomation.foundation.mixin.common;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

import java.util.Optional;

@Mixin(SkilletBlockEntity.class)
public interface SkilletBlockEntityAccessor {
    
    @Invoker(value = "getMatchingRecipe", remap = false)
    Optional<CampfireCookingRecipe> callGetMatchingRecipe(Container recipeWrapper);
    
}