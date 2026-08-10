package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import java.util.function.IntPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

final class GuidedPotInsertion {
    private GuidedPotInsertion() {}

    static ItemStack insertIntoMatchingSlots(IItemHandler inventory, ItemStack stack, boolean simulate,
            int inputSlotCount, IntPredicate matchesSlot) {
        ItemStack remainder = stack.copy();
        for (int slot = 0; slot < inputSlotCount && !remainder.isEmpty(); slot++) {
            if (!inventory.getStackInSlot(slot).isEmpty() || !matchesSlot.test(slot))
                continue;

            ItemStack singleItem = remainder.copy();
            singleItem.setCount(1);
            if (inventory.insertItem(slot, singleItem, simulate).isEmpty())
                remainder.shrink(1);
        }
        return remainder;
    }
}
