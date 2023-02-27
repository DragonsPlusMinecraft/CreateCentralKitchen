package plus.dragons.createcentralkitchen.mixin.common.neapolitan;

import com.teamabnormals.neapolitan.common.item.DrinkItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DrinkItem.class)
public class DrinkItemMixin {
    
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;<init>(Lnet/minecraft/world/item/Item$Properties;)V"))
    private static Item.Properties setCraftRemainder(Item.Properties properties) {
        return properties.craftRemainder(Items.GLASS_BOTTLE);
    }
    
}
