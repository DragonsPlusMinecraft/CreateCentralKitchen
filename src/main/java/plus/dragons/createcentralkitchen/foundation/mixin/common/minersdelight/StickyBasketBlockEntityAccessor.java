package plus.dragons.createcentralkitchen.foundation.mixin.common.minersdelight;

import com.sammy.minersdelight.content.block.sticky_basket.StickyBasketBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = StickyBasketBlockEntity.class, remap = false)
public interface StickyBasketBlockEntityAccessor {
    
    @Invoker("isOnTransferCooldown")
    boolean invokeIsOnTransferCooldown();
    
}
