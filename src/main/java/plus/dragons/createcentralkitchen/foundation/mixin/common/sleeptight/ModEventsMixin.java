package plus.dragons.createcentralkitchen.foundation.mixin.common.sleeptight;

import net.mehvahdjukaar.sleep_tight.core.ModEvents;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createcentralkitchen.api.block.entity.DelegatingSmartTileEntity;

@Mixin(value = ModEvents.class, remap = false)
public class ModEventsMixin {
    @Inject(method = "shouldHaveBedData", at = @At("HEAD"), cancellable = true)
    private static void injected(BlockEntity blockEntity, CallbackInfoReturnable<Boolean> cir) {
        if (blockEntity instanceof DelegatingSmartTileEntity) {
            cir.setReturnValue(false);
        }
    }
}
