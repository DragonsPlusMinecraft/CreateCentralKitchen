package plus.dragons.createcentralkitchen.foundation.mixin.common.minersdelight;

import com.sammy.minersdelight.content.block.sticky_basket.StickyBasketBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import plus.dragons.createcentralkitchen.api.block.entity.SmartBlockEntityLike;
import plus.dragons.createcentralkitchen.content.logistics.block.basket.SmartStickyBasketBlockEntity;
import vectorwing.farmersdelight.common.block.entity.Basket;

@Mixin(StickyBasketBlockEntity.class)
public abstract class StickyBasketBlockEntityMixin extends RandomizableContainerBlockEntity implements Basket, SmartBlockEntityLike {

    @Unique
    private final SmartBlockEntity smartBlockEntity = new SmartStickyBasketBlockEntity((StickyBasketBlockEntity) (Object) this);

    private StickyBasketBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public SmartBlockEntity asSmartBlockEntity() {
        return smartBlockEntity;
    }
    
}
