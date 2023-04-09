package plus.dragons.createcentralkitchen.foundation.mixin.common.farmersdelight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.BasketBlockEntity;

@Mixin(value = BasketBlockEntity.class, remap = false)
public interface BasketBlockEntityAccessor {
    
    @Invoker("isOnTransferCooldown")
    boolean invokeIsOnTransferCooldown();
    
}
