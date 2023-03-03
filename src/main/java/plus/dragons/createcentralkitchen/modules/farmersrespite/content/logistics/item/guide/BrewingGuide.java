package plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide;

import com.farmersrespite.core.registry.FRRecipeTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuide;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleCapabilities;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BrewingGuide extends BlazeStoveGuide {
    private final LazyOptional<BrewingGuide> capability = LazyOptional.of(() -> this);
    private boolean needWater;
    
    public BrewingGuide(ItemStack owner) {
        super(owner, 2);
    }
    
    public static BrewingGuide of(ItemStack stack) {
        return stack.getCapability(FarmersRespiteModuleCapabilities.BREWING_GUIDE).orElseThrow(() ->
            new UnsupportedOperationException("Requested Item " + stack.getItem() + " is not a Brewing Guide"));
    }
    
    public boolean needWater() {
        return needWater;
    }
    
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putBoolean("NeedWater", needWater);
        return tag;
    }
    
    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        needWater = tag.getBoolean("NeedWater");
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == FarmersRespiteModuleCapabilities.BREWING_GUIDE) {
            return capability.cast();
        }
        return LazyOptional.empty();
    }
    
    public void updateRecipe(Level level) {
        level.getRecipeManager()
            .getRecipeFor(FRRecipeTypes.BREWING.get(), recipeWrapper, level)
            .ifPresentOrElse(
                recipe -> {
                    inventory.setStackInSlot(ingredientSize, recipe.getResultItem());
                    container = recipe.getOutputContainer();
                    needWater = recipe.getNeedWater();
                },
                () -> {
                    inventory.setStackInSlot(ingredientSize, ItemStack.EMPTY);
                    container = ItemStack.EMPTY;
                    needWater = false;
                });
    }
    
}
