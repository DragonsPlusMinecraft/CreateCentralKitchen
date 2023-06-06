package plus.dragons.createcentralkitchen.foundation.mixin.client.create;

import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createcentralkitchen.api.event.FillCreateItemGroupEvent;

@Mixin(CreateCreativeModeTab.class)
public class CreateCreativeModeTabMixin {

    @Inject(method = "fillItemList", at = @At("TAIL"))
    private void cck$postFillCreateItemGroupEvent(NonNullList<ItemStack> items, CallbackInfo ci) {
        var event = new FillCreateItemGroupEvent((CreativeModeTab) (Object) this, items);
        MinecraftForge.EVENT_BUS.post(event);
        event.apply();
    }

}
