package plus.dragons.createcentralkitchen.foundation.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;

public class NoColorFluidAttributes extends FluidAttributes {
    
    public NoColorFluidAttributes(Builder builder, Fluid fluid) {
        super(builder, fluid);
    }
    
    @Override
    public int getColor(BlockAndTintGetter world, BlockPos pos) {
        return 0x00ffffff;
    }
    
}