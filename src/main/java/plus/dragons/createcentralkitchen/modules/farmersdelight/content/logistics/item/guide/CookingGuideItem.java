package plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideItem;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdCapabilities;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdMenuTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CookingGuideItem extends BlazeStoveGuideItem<CookingGuide> {
    
    public CookingGuideItem(Properties properties) {
        super(properties);
    }
    
    @Override
    protected Capability<CookingGuide> getGuideCapability() {
        return FdCapabilities.COOKING_GUIDE;
    }
    
    @Override
    protected CookingGuideMenu createGuideMenu(int syncId, Inventory inventory, ItemStack guide) {
        return new CookingGuideMenu(FdMenuTypes.COOKING_GUIDE.get(), syncId, inventory, guide);
    }
    
    @Override
    protected CookingGuideMenu createGuideMenu(int syncId, Inventory inventory, BlazeStoveBlockEntity stove) {
        return new CookingGuideMenu(FdMenuTypes.COOKING_GUIDE.get(), syncId, inventory, stove);
    }
    
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CookingGuide(stack);
    }
    
}
