package plus.dragons.createcentralkitchen.common.packager;

import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import com.simibubi.create.content.logistics.stockTicker.PackageOrderWithCrafts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ShapelessUnpackingHandler implements UnpackingHandler {
    protected final int slotOffset;
    protected final int slotCount;

    protected ShapelessUnpackingHandler(int slotOffset, int slotCount) {
        this.slotOffset = slotOffset;
        this.slotCount = slotCount;
    }

    @Override
    public boolean unpack(Level level, BlockPos pos, BlockState state, Direction side, List<ItemStack> items, @Nullable PackageOrderWithCrafts orderContext, boolean simulate) {
        var inventory = getInventory(level, pos, side);
        if (inventory == null)
            return false;
        for (int slot = slotOffset; slot < slotOffset + slotCount; slot++) {
            var item = items.getFirst();
            if (inventory.getStackInSlot(slot).isEmpty() && inventory.insertItem(slot, item.split(1), simulate).isEmpty()) {
                if (item.isEmpty()) {
                    items.removeFirst();
                    if (items.isEmpty())
                        return true;
                }
            } else return false;
        }
        return false;
    }

    protected abstract @Nullable IItemHandler getInventory(Level level, BlockPos pos, Direction side);
}
