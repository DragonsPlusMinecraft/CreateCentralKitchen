package plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm.CentralKitchenArmInteractionPointType;
import vectorwing.farmersdelight.common.block.BasketBlock;
import vectorwing.farmersdelight.common.block.entity.BasketBlockEntity;

public class BasketPoint extends ArmInteractionPoint {
    public BasketPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atCenterOf(pos);
    }

    public static class Type extends CentralKitchenArmInteractionPointType {

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
    
        @Override
        public void registerPonderTag() {
            var builder = PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS);
            ForgeRegistries.ITEMS
                .getValues()
                .stream()
                .filter(item -> item instanceof BlockItem blockItem &&
                    blockItem.getBlock() instanceof BasketBlock)
                .forEach(builder::add);
        }
        
    }
}
