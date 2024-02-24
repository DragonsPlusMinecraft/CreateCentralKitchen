package plus.dragons.createcentralkitchen.foundation.mixin.common.farmersdelight;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createcentralkitchen.api.block.entity.SmartBlockEntityLike;
import plus.dragons.createcentralkitchen.content.logistics.block.basket.SmartBasketBlockEntity;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigs;
import vectorwing.farmersdelight.common.block.entity.BasketBlockEntity;

@Mixin(BasketBlockEntity.class)
public abstract class BasketBlockEntityMixin extends RandomizableContainerBlockEntity implements SmartBlockEntityLike {
    
    @Unique
    private final SmartBlockEntity smartBlockEntity = new SmartBasketBlockEntity((BasketBlockEntity) (Object) this);
    
    private BasketBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
    
    public SmartBlockEntity asSmartBlockEntity() {
        return smartBlockEntity;
    }

    @Shadow(remap = false)
    private int transferCooldown;

    @Inject(method = "setTransferCooldown", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void cck$triggerConsumeItem(int ticks, CallbackInfo ci) {
        if(CentralKitchenConfigs.COMMON.integration.disableTransferCooldownForFarmersDelightBasket.get()){
            transferCooldown = -1;
            ci.cancel();
        }
    }
    
}
