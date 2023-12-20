package plus.dragons.createcentralkitchen.foundation.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

import static net.minecraftforge.fluids.FluidType.BUCKET_VOLUME;
public class SolidBlockFluidBucketItem extends SolidBucketItem {
    protected final Supplier<? extends Fluid> fluidSupplier;
    
    public SolidBlockFluidBucketItem(Supplier<? extends Fluid> fluidSupplier, Block block, SoundEvent placeSound, Properties properties) {
        super(block, placeSound, properties);
        this.fluidSupplier = fluidSupplier;
    }
    
    public void registerBlocks(Map<Block, Item> blockToItemMap, Item item) {
        Block block = this.getBlock();
        if (!blockToItemMap.containsKey(block))
            blockToItemMap.put(block, item);
    }
    
    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item item) {
        blockToItemMap.remove(this.getBlock(), item);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new Wrapper(stack);
    }
    
    protected class Wrapper extends FluidBucketWrapper {
        
        public Wrapper(@NotNull ItemStack container) {
            super(container);
        }
    
        @NotNull
        @Override
        public FluidStack getFluid() {
            if (container.is(SolidBlockFluidBucketItem.this))
                return new FluidStack(fluidSupplier.get(), BUCKET_VOLUME);
            return FluidStack.EMPTY;
        }
    
        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }
    
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return false;
        }
    
        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return stack.getFluid().isSame(fluidSupplier.get());
        }
        
    }
    
}
