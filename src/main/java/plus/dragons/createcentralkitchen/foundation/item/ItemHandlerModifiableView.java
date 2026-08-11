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
        validateSlot(slot);
        int index = start + slot;
        if (index <= end)
            inv.setStackInSlot(index, stack);
    }

    @Override
    public int getSlots() {
        return size;
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        validateSlot(slot);
        int index = start + slot;
        return index <= end ? inv.getStackInSlot(index) : ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        validateSlot(slot);
        int index = start + slot;
        return index <= end ? inv.insertItem(index, stack, simulate) : stack;
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        validateSlot(slot);
        int index = start + slot;
        return index <= end ? inv.extractItem(index, amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        validateSlot(slot);
        int index = start + slot;
        return index <= end ? inv.getSlotLimit(index) : 0;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        validateSlot(slot);
        int index = start + slot;
        return index <= end && inv.isItemValid(index, stack);
    }

    private void validateSlot(int slot) {
        if (slot < 0 || slot >= size)
            throw new IndexOutOfBoundsException("Slot " + slot + " not in valid range - [0," + size + ")");
    }
}
