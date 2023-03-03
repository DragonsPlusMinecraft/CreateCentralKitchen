package plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuide;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FarmersDelightModuleCapabilities;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CookingGuide extends BlazeStoveGuide {
    private final LazyOptional<CookingGuide> capability = LazyOptional.of(() -> this);
    
    public CookingGuide(ItemStack owner) {
        super(owner, 6);
    }
    
    public static CookingGuide of(ItemStack stack) {
        return stack.getCapability(FarmersDelightModuleCapabilities.COOKING_GUIDE).orElseThrow(() ->
            new UnsupportedOperationException("Requested Item " + stack.getItem() + " is not a Cooking Guide"));
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == FarmersDelightModuleCapabilities.COOKING_GUIDE) {
            return capability.cast();
        }
        return LazyOptional.empty();
    }
    
    public void updateRecipe(Level level) {
        level.getRecipeManager()
            .getRecipeFor(ModRecipeTypes.COOKING.get(), recipeWrapper, level)
            .ifPresentOrElse(
                recipe -> {
                    inventory.setStackInSlot(ingredientSize, recipe.getResultItem());
                    container = recipe.getOutputContainer();
                },
                () -> {
                    inventory.setStackInSlot(ingredientSize, ItemStack.EMPTY);
                    container = ItemStack.EMPTY;
                });
    }
    
}
