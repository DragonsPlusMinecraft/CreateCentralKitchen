package plus.dragons.createcentralkitchen.content.contraptions.components.stove;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CookingGuide implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<CookingGuide> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    
    private final LazyOptional<CookingGuide> capability = LazyOptional.of(() -> this);
    private final ItemStack owner;
    final ItemStackHandler inventory = new ItemStackHandler(7);
    private ItemStack container = ItemStack.EMPTY;
    private final RecipeWrapper recipeWrapper = new RecipeWrapper(inventory);
    
    public CookingGuide(ItemStack owner) {
        this.owner = owner;
    }
    
    public static CookingGuide of(ItemStack stack) {
        return stack.getCapability(CAPABILITY).orElseThrow(() ->
            new UnsupportedOperationException("Requested Item " + stack.getItem() + " is not a Cooking Guide"));
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CAPABILITY) {
            return capability.cast();
        }
        return LazyOptional.empty();
    }
    
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag ingredientsTag = new ListTag();
    
        for (byte i = 0; i < 6; ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                CompoundTag ingredientTag = new CompoundTag();
                ingredientTag.putByte("Slot", i);
                itemstack.save(ingredientTag);
                ingredientsTag.add(ingredientTag);
            }
        }
    
        if (!ingredientsTag.isEmpty())
            tag.put("Ingredients", ingredientsTag);
        if (!inventory.getStackInSlot(6).isEmpty())
            tag.put("Result", inventory.getStackInSlot(6).serializeNBT());
        if (!container.isEmpty())
            tag.put("Container", container.serializeNBT());
        
        return tag;
    }
    
    @Override
    public void deserializeNBT(CompoundTag tag) {
        ListTag ingredientsTag = tag.getList("Ingredients", 10);
        
        for (int i = 0; i < 7; ++i) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
        
        for (int i = 0; i < ingredientsTag.size(); ++i) {
            CompoundTag ingredientTag = ingredientsTag.getCompound(i);
            int slot = ingredientTag.getByte("Slot") & 255;
            if (slot < 6) {
                inventory.setStackInSlot(slot, ItemStack.of(ingredientTag));
            }
        }
    
        inventory.setStackInSlot(6, tag.contains("Result", Tag.TAG_COMPOUND)
            ? ItemStack.of(tag.getCompound("Result"))
            : ItemStack.EMPTY);
        
        container = tag.contains("Container", Tag.TAG_COMPOUND)
            ? ItemStack.of(tag.getCompound("Container"))
            : ItemStack.EMPTY;
    }
    
    public boolean needIngredient(int slot) {
        return !inventory.getStackInSlot(slot).isEmpty();
    }
    
    public boolean isIngredient(int slot, ItemStack stack) {
        return stack.sameItem(inventory.getStackInSlot(slot));
    }
    
    public boolean isContainer(ItemStack stack) {
        return stack.sameItem(container);
    }
    
    public ItemStack getResult() {
        return inventory.getStackInSlot(6).copy();
    }
    
    public ItemStack getOwner() {
        return owner;
    }
    
    public void updateRecipe(Level level) {
        level.getRecipeManager()
            .getRecipeFor(ModRecipeTypes.COOKING.get(), recipeWrapper, level)
            .ifPresentOrElse(
                recipe -> {
                    inventory.setStackInSlot(6, recipe.getResultItem());
                    container = recipe.getOutputContainer();
                },
                () -> {
                    inventory.setStackInSlot(6, ItemStack.EMPTY);
                    container = ItemStack.EMPTY;
                });
    }
    
}
