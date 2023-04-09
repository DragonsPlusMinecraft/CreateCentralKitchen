package plus.dragons.createcentralkitchen.foundation.mixin.common.farmersdelight;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(value = CuttingBoardBlockEntity.class, remap = false)
public interface CuttingBoardBlockEntityAccessor {

    @Invoker(value = "getMatchingRecipe")
    Optional<CuttingBoardRecipe> callGetMatchingRecipe(RecipeWrapper recipeWrapper, ItemStack toolStack, @Nullable Player player);
}
