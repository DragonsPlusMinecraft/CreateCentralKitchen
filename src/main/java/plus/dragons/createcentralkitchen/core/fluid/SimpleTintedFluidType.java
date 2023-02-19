package plus.dragons.createcentralkitchen.core.fluid;

import com.simibubi.create.AllFluids;
import com.tterrag.registrate.builders.FluidBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;

public class SimpleTintedFluidType extends AllFluids.TintedFluidType {
    private final int tint;
    
    public SimpleTintedFluidType(Properties properties,
                                 ResourceLocation stillTexture,
                                 ResourceLocation flowingTexture,
                                 int tint) {
        super(properties, stillTexture, flowingTexture);
        this.tint = tint;
    }
    
    public static FluidBuilder.FluidTypeFactory factory(int tint) {
        return (prop, still, flow) -> new SimpleTintedFluidType(prop, still, flow, tint);
    }
    
    @Override
    protected int getTintColor(FluidStack stack) {
        return tint;
    }
    
    @Override
    protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
        return tint;
    }
    
}
