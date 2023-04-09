package plus.dragons.createcentralkitchen.foundation.mixin.common.minecraft;

import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BowlFoodItem.class)
public class BowlFoodItemMixin {
    
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;<init>(Lnet/minecraft/world/item/Item$Properties;)V"))
    private static Item.Properties cck$setCraftRemainder(Item.Properties properties) {
        return properties.craftRemainder(Items.BOWL);
    }
    
}
