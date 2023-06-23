package plus.dragons.createcentralkitchen.foundation.mixin.common.overweightfarming;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.orcinus.overweightfarming.blocks.CropFullBlock;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigs;

@Mixin(CropFullBlock.class)
public abstract class CropFullBlockMixin extends BushBlock implements BonemealableBlock {

    public CropFullBlockMixin(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Object getRenderPropertiesInternal() {
        return super.getRenderPropertiesInternal();
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if(CentralKitchenConfigs.COMMON.integration.enableHarvesterSupportForOverweightFarming.get()) return Shapes.empty();
        return super.getCollisionShape(pState,pLevel,pPos,pContext);
    }

    @Deprecated
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if(CentralKitchenConfigs.COMMON.integration.enableHarvesterSupportForOverweightFarming.get()) return Shapes.empty();
        return super.getOcclusionShape(pState,pLevel,pPos);
    }
}
