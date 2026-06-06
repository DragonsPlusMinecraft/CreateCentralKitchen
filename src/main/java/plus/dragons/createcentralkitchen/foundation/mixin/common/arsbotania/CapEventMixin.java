package plus.dragons.createcentralkitchen.foundation.mixin.common.arsbotania;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createcentralkitchen.api.block.entity.DelegatingSmartTileEntity;
import studio.fantasyit.ars_botania.event.CapEvent;

@Mixin(value = CapEvent.class, remap = false)
public class CapEventMixin {
    @Inject(method = "onCapabilityAttach", at = @At("HEAD"), cancellable = true)
    private static void injected(AttachCapabilitiesEvent<BlockEntity> event, CallbackInfo ci) {
        Object var2 = event.getObject();
        if (var2 instanceof DelegatingSmartTileEntity) {
            ci.cancel();
        }
    }
}
