package plus.dragons.createcentralkitchen.foundation.mixin.common.farmersrespite;

import com.farmersrespite.common.block.CoffeeStemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigs;

@Mixin(CoffeeStemBlock.class)
public abstract class CoffeeStemBlockMixin extends BushBlock implements BonemealableBlock {

    public CoffeeStemBlockMixin(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if(CentralKitchenConfigs.COMMON.integration.enableHarvesterSupportForFarmersRespite.get()) return Shapes.empty();
        return super.getCollisionShape(pState,pLevel,pPos,pContext);
    }

    @Deprecated
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if(CentralKitchenConfigs.COMMON.integration.enableHarvesterSupportForFarmersRespite.get()) return Shapes.empty();
        return super.getOcclusionShape(pState,pLevel,pPos);
    }
}
