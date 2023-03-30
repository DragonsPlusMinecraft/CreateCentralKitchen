package plus.dragons.createcentralkitchen.foundation.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ListIterator;
import java.util.function.Supplier;

public class FluidBucketItem extends BucketItem {
    
    public FluidBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
    }
    
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (tab == this.category) {
            ListIterator<ItemStack> item = items.listIterator();
            while (item.hasNext()) {
                if (item.next().is(Items.POWDER_SNOW_BUCKET)) {
                    item.add(new ItemStack(this));
                    return;
                }
            }
        }
        super.fillItemCategory(tab, items);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new Wrapper(stack);
    }
    
    protected static class Wrapper extends FluidBucketWrapper {
        
        public Wrapper(@NotNull ItemStack container) {
            super(container);
        }
        
    }

}
