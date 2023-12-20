package plus.dragons.createcentralkitchen.foundation.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FluidBucketItem extends BucketItem {
    
    public FluidBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
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
