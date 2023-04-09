package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import vectorwing.farmersdelight.common.block.BasketBlock;
import vectorwing.farmersdelight.common.block.entity.BasketBlockEntity;

import java.util.function.Consumer;

public class BasketPoint extends ArmInteractionPoint {
    public BasketPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atCenterOf(pos);
    }

    public static class Type extends PonderArmInteractionPointType {

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
        public void addToPonderTag(Consumer<ItemLike> consumer) {
            ForgeRegistries.ITEMS
                .getValues()
                .stream()
                .filter(item -> item instanceof BlockItem blockItem && blockItem.getBlock() instanceof BasketBlock)
                .forEach(consumer);
        }
        
    }
}
