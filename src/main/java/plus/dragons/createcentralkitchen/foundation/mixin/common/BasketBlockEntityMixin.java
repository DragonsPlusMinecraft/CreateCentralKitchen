package plus.dragons.createcentralkitchen.foundation.mixin.common;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import plus.dragons.createcentralkitchen.content.logistics.block.basket.BasketBlockEntityAccess;
import plus.dragons.createcentralkitchen.content.logistics.block.basket.SmartBasketBlockEntity;
import plus.dragons.createcentralkitchen.foundation.tileEntity.SmartTileEntityLike;
import vectorwing.farmersdelight.common.block.entity.Basket;
import vectorwing.farmersdelight.common.block.entity.BasketBlockEntity;

@Mixin(BasketBlockEntity.class)
public abstract class BasketBlockEntityMixin extends RandomizableContainerBlockEntity implements Basket, SmartTileEntityLike, BasketBlockEntityAccess {
    
    @Shadow(remap = false)
    private int transferCooldown;
    
    @Unique
    private final SmartTileEntity cck$smartTE = new SmartBasketBlockEntity((BasketBlockEntity) (Object) this);
    
    private BasketBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
    
    public SmartTileEntity cck$asSmartTE() {
        return cck$smartTE;
    }
    
    @Override
    public boolean cck$isOnTransferCooldown() {
        return this.transferCooldown > 0;
    }
    
}
