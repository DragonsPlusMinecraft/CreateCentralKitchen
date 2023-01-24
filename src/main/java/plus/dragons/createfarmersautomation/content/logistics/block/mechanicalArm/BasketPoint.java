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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.BasketBlockEntity;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

public class BasketPoint extends ArmInteractionPoint {
    public BasketPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atBottomCenterOf(pos).add(0, 1, 0);
    }

    @Nullable
    @Override
    protected IItemHandler getHandler() {
        if (!cachedHandler.isPresent()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof BasketBlockEntity basket)) return null;
            cachedHandler = basket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
        }
        return cachedHandler.resolve().orElse(null);
    }

    public static class Type extends ArmInteractionPointType {

        public Type(ResourceLocation id) {
            super(id);
        }

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof BasketBlockEntity;
        }

        @Nullable
        @Override
        public BasketPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new BasketPoint(this, level, pos, state);
        }

    }
}
