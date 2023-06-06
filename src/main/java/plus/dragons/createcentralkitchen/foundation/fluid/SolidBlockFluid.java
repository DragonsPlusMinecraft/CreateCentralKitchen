package plus.dragons.createcentralkitchen.foundation.fluid;

import com.simibubi.create.content.fluids.VirtualFluid;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.function.Supplier;

public class SolidBlockFluid extends VirtualFluid {
    private final Supplier<? extends SolidBucketItem> bucket;
    
    public SolidBlockFluid(Supplier<? extends SolidBucketItem> bucket, Properties properties) {
        super(properties);
        this.bucket = bucket;
    }
    
    @Override
    public Item getBucket() {
        return this.bucket.get();
    }
    
    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return this.bucket.get().getBlock().defaultBlockState();
    }
    
}
