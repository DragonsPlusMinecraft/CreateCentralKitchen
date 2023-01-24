package plus.dragons.createfarmersautomation.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

import java.util.Optional;

public class CuttingBoardPoint extends ArmInteractionPoint {
    public CuttingBoardPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atBottomCenterOf(pos).add(0, .25, 0);
    }

    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof CuttingBoardBlockEntity cuttingBoard))
            return stack;
        var inv = cuttingBoard.getInventory();
        if (!inv.getStackInSlot(0).isEmpty()) {
            return stack;
        }
        return cuttingBoard.getInventory().insertItem(0, stack.copy(), simulate);
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
