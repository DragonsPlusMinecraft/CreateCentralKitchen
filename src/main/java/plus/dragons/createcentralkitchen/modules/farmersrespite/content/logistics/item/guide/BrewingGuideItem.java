package plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideItem;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrCapabilities;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrMenuTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BrewingGuideItem extends BlazeStoveGuideItem<BrewingGuide> {
    
    public BrewingGuideItem(Properties properties) {
        super(properties);
    }
    
    @Override
    protected Capability<BrewingGuide> getGuideCapability() {
        return FrCapabilities.BREWING_GUIDE;
    }
    
    @Override
    protected BrewingGuideMenu createGuideMenu(int syncId, Inventory inventory, ItemStack guide) {
        return new BrewingGuideMenu(FrMenuTypes.BREWING_GUIDE.get(), syncId, inventory, guide);
    }
    
    @Override
    protected BrewingGuideMenu createGuideMenu(int syncId, Inventory inventory, BlazeStoveBlockEntity stove) {
        return new BrewingGuideMenu(FrMenuTypes.BREWING_GUIDE.get(), syncId, inventory, stove);
    }
    
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BrewingGuide(stack);
    }
    
}
