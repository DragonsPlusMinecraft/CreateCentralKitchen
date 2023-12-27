package plus.dragons.createcentralkitchen.foundation.mixin.common.farmersdelight;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import plus.dragons.createcentralkitchen.api.block.entity.SmartBlockEntityLike;
import plus.dragons.createcentralkitchen.content.logistics.block.basket.SmartBasketBlockEntity;
import vectorwing.farmersdelight.common.block.entity.Basket;
import vectorwing.farmersdelight.common.block.entity.BasketBlockEntity;

@Mixin(BasketBlockEntity.class)
public abstract class BasketBlockEntityMixin extends RandomizableContainerBlockEntity implements Basket, SmartBlockEntityLike {
    
    @Unique
    private final SmartBlockEntity smartBlockEntity = new SmartBasketBlockEntity((BasketBlockEntity) (Object) this);
    
    private BasketBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public SmartBlockEntity asSmartBlockEntity() {
        return smartBlockEntity;
    }
    
}
