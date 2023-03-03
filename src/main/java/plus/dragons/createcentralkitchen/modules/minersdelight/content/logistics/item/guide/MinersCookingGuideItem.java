package plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.item.guide;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideItem;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleCapabilities;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleMenuTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MinersCookingGuideItem extends BlazeStoveGuideItem<MinersCookingGuide> {
    
    public MinersCookingGuideItem(Properties properties) {
        super(properties);
    }
    
    @Override
    protected Capability<MinersCookingGuide> getGuideCapability() {
        return MinersDelightModuleCapabilities.MINERS_COOKING_GUIDE;
    }
    
    @Override
    protected MinersCookingGuideMenu createGuideMenu(int syncId, Inventory inventory, ItemStack guide) {
        return new MinersCookingGuideMenu(MinersDelightModuleMenuTypes.MINERS_COOKING_GUIDE.get(), syncId, inventory, guide);
    }
    
    @Override
    protected MinersCookingGuideMenu createGuideMenu(int syncId, Inventory inventory, BlazeStoveBlockEntity stove) {
        return new MinersCookingGuideMenu(MinersDelightModuleMenuTypes.MINERS_COOKING_GUIDE.get(), syncId, inventory, stove);
    }
    
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new MinersCookingGuide(stack);
    }
    
}
