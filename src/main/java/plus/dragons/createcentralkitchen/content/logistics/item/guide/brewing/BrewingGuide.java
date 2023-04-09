package plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuide;
import plus.dragons.createcentralkitchen.entry.capability.FRCapabilityEntries;
import umpaz.farmersrespite.common.registry.FRRecipeTypes;

public class BrewingGuide extends BlazeStoveGuide {
    private final LazyOptional<BrewingGuide> capability = LazyOptional.of(() -> this);
    private boolean needWater;
    
    public BrewingGuide(ItemStack owner) {
        super(owner, 2);
    }
    
    public static BrewingGuide of(ItemStack stack) {
        return stack.getCapability(FRCapabilityEntries.BREWING_GUIDE).orElseThrow(() ->
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
        if (cap == FRCapabilityEntries.BREWING_GUIDE) {
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
