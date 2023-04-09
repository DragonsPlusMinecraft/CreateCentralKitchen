package plus.dragons.createcentralkitchen.foundation.mixin.common.create;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.relays.belt.BeltHelper;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BeltHelper.class, remap = false, priority = 900)
public class BeltHelperMixin {
    
    /**
     * For improving the usage of tag create:upright_on_belt so items with containers inherits upright behavior from their containers.
     *
     * @param original If the item is in tag create:upright_on_belt
     * @param stack the item
     * @return if the item should be upright on belt
     * @author LimonBlaze
     */
    @ModifyExpressionValue(method = "isItemUpright", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/AllTags$AllItemTags;matches(Lnet/minecraft/world/item/ItemStack;)Z"))
    private static boolean cck$isItemUpright(boolean original, ItemStack stack) {
        return original || (stack.hasCraftingRemainingItem() && stack.getCraftingRemainingItem().is(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag));
    }
    
}
