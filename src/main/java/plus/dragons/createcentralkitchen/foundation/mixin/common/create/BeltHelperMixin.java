package plus.dragons.createcentralkitchen.foundation.mixin.common.create;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BeltHelper.class, remap = false, priority = 900)
public class BeltHelperMixin {

    @Inject(method = "isItemUpright", at = @At("RETURN"), cancellable = true)
    private static void cck$isItemUpright(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if(stack.hasContainerItem() && stack.getContainerItem().is(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)){
            cir.setReturnValue(true);
        }
    }
    
}
