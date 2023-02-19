package plus.dragons.createcentralkitchen.mixin.common.farmersdelight;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

import java.util.Optional;

@Mixin(value = SkilletBlockEntity.class, remap = false)
public interface SkilletBlockEntityAccessor {
    
    @Invoker(value = "getMatchingRecipe")
    Optional<CampfireCookingRecipe> callGetMatchingRecipe(Container recipeWrapper);
    
}