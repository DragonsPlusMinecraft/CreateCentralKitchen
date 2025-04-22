package plus.dragons.createcentralkitchen.common.packager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class CookingPotUnpackingHandler extends ShapelessUnpackingHandler {
    public CookingPotUnpackingHandler() {
        super(0, 6);
    }

    @Override
    protected @Nullable IItemHandler getInventory(Level level, BlockPos pos, Direction side) {
        return level.getCapability(ItemHandler.BLOCK, pos, Direction.UP);
    }
}
