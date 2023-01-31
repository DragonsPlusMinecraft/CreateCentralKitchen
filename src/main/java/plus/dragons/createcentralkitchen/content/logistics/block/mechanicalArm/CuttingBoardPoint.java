package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;


public class CuttingBoardPoint extends ArmInteractionPoint {
    public CuttingBoardPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atBottomCenterOf(pos).add(0, .25, 0);
    }

    @Nullable
    @Override
    protected IItemHandler getHandler() {
        if (!cachedHandler.isPresent()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof CuttingBoardBlockEntity cuttingBoard)) return null;
            cachedHandler = LazyOptional.of(cuttingBoard::getInventory);
        }
        return cachedHandler.resolve().orElse(null);
    }

    public static class Type extends ArmInteractionPointType {

        public Type(ResourceLocation id) {
            super(id);
        }

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity;
        }

        @Nullable
        @Override
        public CuttingBoardPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CuttingBoardPoint(this, level, pos, state);
        }

    }
}
