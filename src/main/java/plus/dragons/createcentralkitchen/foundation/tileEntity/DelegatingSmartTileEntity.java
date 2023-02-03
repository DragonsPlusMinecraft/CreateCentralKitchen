package plus.dragons.createcentralkitchen.foundation.tileEntity;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class DelegatingSmartTileEntity<BE extends BlockEntity> extends SmartTileEntity {
    
    protected final BE blockEntity;
    
    public DelegatingSmartTileEntity(BE blockEntity) {
        super(blockEntity.getType(), blockEntity.getBlockPos(), blockEntity.getBlockState());
        this.blockEntity = blockEntity;
    }
    
    @Nullable
    @Override
    public Level getLevel() {
        return blockEntity.getLevel();
    }
    
    @Override
    public BlockPos getBlockPos() {
        return blockEntity.getBlockPos();
    }
    
    @Override
    public BlockState getBlockState() {
        return blockEntity.getBlockState();
    }
    
}
