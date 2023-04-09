package plus.dragons.createcentralkitchen.foundation.item;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

public class ItemHandlerModifiableView implements IItemHandlerModifiable {
    private final IItemHandlerModifiable inv;
    private final int start;
    private final int end;
    private final int size;
    
    public ItemHandlerModifiableView(IItemHandlerModifiable inv, int start, int end, int size) {
        this.inv = inv;
        this.start = start;
        this.end = end;
        this.size = size;
    }
    
    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        if (slot < size) {
            int index = start + slot;
            if (index <= end)
                inv.setStackInSlot(index, stack);
        } else {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + size + ")");
        }
    }
    
    @Override
    public int getSlots() {
        return size;
    }
    
    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < size) {
            int index = start + slot;
            if (index <= end)
                return inv.getStackInSlot(index);
            else
                return ItemStack.EMPTY;
        } else {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + size + ")");
        }
    }
    
    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (slot < size) {
            int index = start + slot;
            if (index <= end)
                return inv.insertItem(index, stack, simulate);
            else
                return stack;
        } else {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + size + ")");
        }
    }
    
    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot < size) {
            int index = start + slot;
            if (index <= end)
                return inv.extractItem(index, amount, simulate);
            else
                return ItemStack.EMPTY;
        } else {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + size + ")");
        }
    }
    
    @Override
    public int getSlotLimit(int slot) {
        if (slot < size) {
            int index = start + slot;
            if (index <= end)
                return inv.getSlotLimit(index);
            else
                return 0;
        } else {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + size + ")");
        }
    }
    
    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot < size) {
            int index = start + slot;
            return index <= end;
        } else {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + size + ")");
        }
    }
}
