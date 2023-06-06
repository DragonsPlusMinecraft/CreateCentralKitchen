package plus.dragons.createcentralkitchen.api.block.entity;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class DelegatingSmartTileEntity<BE extends BlockEntity> extends SmartBlockEntity {
    
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
