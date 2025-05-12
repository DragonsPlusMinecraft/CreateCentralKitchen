package plus.dragons.createcentralkitchen.entry.fluid;

import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.item.FluidBucketItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import plus.dragons.createcentralkitchen.dragonLibLegacy.fluid.NoTintFluidType;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.AUTUMNITY)
public class AutumnityFluidEntries {
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SAP = REGISTRATE.fluid("sap",
            CentralKitchen.genRL("fluid/sap_still"),
            CentralKitchen.genRL("fluid/sap_flow"),
            NoTintFluidType::new)
        .lang("Sap")
        .properties(builder -> builder.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL))
        .fluidProperties(p -> p.levelDecreasePerBlock(2)
            .tickRate(15)
            .slopeFindDistance(3)
            .explosionResistance(100f))
        .source(ForgeFlowingFluid.Source::new) // TODO: remove when Registrate fixes FluidBuilder
        .bucket(FluidBucketItem::new)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SYRUP = REGISTRATE.fluid("syrup",
            CentralKitchen.genRL("fluid/syrup_still"),
            CentralKitchen.genRL("fluid/syrup_flow"),
            NoTintFluidType::new)
        .lang("Syrup")
        .properties(b -> b.viscosity(2000).density(1400).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL))
        .fluidProperties(p -> p.levelDecreasePerBlock(2)
            .tickRate(25)
            .slopeFindDistance(3)
            .explosionResistance(100f))
        .source(ForgeFlowingFluid.Source::new) // TODO: remove when Registrate fixes FluidBuilder
        .bucket(FluidBucketItem::new)
        .build()
        .register();
    
}
