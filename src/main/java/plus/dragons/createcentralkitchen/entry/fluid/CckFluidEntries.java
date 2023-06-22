package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class CckFluidEntries {
    private static final ResourceLocation DRAGON_BREATH_STILL = CentralKitchen.genRL("fluid/dragon_breath_still");
    private static final ResourceLocation DRAGON_BREATH_FLOW = CentralKitchen.genRL("fluid/dragon_breath_flow");
    public static final FluidEntry<VirtualFluid>
            DRAGONS_BREATH = REGISTRATE.virtualFluid("dragon_breath", DRAGON_BREATH_STILL, DRAGON_BREATH_FLOW)
            .defaultLang()
            .properties(builder -> builder.lightLevel(15))
            .register();

    public static void register(){}
}
