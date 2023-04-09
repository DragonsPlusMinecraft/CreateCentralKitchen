package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class BlazeStoveGuide implements ICapabilitySerializable<CompoundTag> {
    protected final ItemStack owner;
    protected final int ingredientSize;
    protected final ItemStackHandler inventory;
    protected ItemStack container = ItemStack.EMPTY;
    protected RecipeWrapper recipeWrapper;
    
    public BlazeStoveGuide(ItemStack owner, int ingredientSize) {
        this.owner = owner;
        this.ingredientSize = ingredientSize;
        this.inventory = new ItemStackHandler(ingredientSize + 1);
        this.recipeWrapper = new RecipeWrapper(inventory);
    }
    
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag ingredientsTag = new ListTag();
        
        for (byte i = 0; i < ingredientSize; ++i) {
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
        if (!inventory.getStackInSlot(ingredientSize).isEmpty())
            tag.put("Result", inventory.getStackInSlot(ingredientSize).serializeNBT());
        if (!container.isEmpty())
            tag.put("Container", container.serializeNBT());
        
        return tag;
    }
    
    @Override
    public void deserializeNBT(CompoundTag tag) {
        ListTag ingredientsTag = tag.getList("Ingredients", 10);
        
        for (int i = 0; i < inventory.getSlots(); ++i) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
        
        for (int i = 0; i < ingredientsTag.size(); ++i) {
            CompoundTag ingredientTag = ingredientsTag.getCompound(i);
            int slot = ingredientTag.getByte("Slot") & 255;
            if (slot < ingredientSize) {
                inventory.setStackInSlot(slot, ItemStack.of(ingredientTag));
            }
        }
        
        inventory.setStackInSlot(ingredientSize, tag.contains("Result", Tag.TAG_COMPOUND)
            ? ItemStack.of(tag.getCompound("Result"))
            : ItemStack.EMPTY);
        
        container = tag.contains("Container", Tag.TAG_COMPOUND)
            ? ItemStack.of(tag.getCompound("Container"))
            : ItemStack.EMPTY;
    }
    
    public final int getIngredientSize() {
        return ingredientSize;
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
        return inventory.getStackInSlot(ingredientSize).copy();
    }
    
    public ItemStack getOwner() {
        return owner;
    }
    
    public abstract void updateRecipe(Level level);
    
}
