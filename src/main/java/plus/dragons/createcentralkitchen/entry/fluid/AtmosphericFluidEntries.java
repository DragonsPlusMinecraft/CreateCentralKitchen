package plus.dragons.createcentralkitchen.entry.fluid;

import com.teamabnormals.atmospheric.core.registry.AtmosphericItems;
import com.tterrag.registrate.util.entry.FluidEntry;
import plus.dragons.createcentralkitchen.entry.item.AtmosphericItemEntries;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.fluid.SolidBlockFluid;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.ATMOSPHERIC)
public class AtmosphericFluidEntries {
    
    public static final FluidEntry<SolidBlockFluid> ALOE_GEL = REGISTRATE.virtualFluid("aloe_gel",
            Mods.atmospheric("block/aloe_gel_block_top"), Mods.atmospheric("block/aloe_gel_block_side"),
            null, prop -> new SolidBlockFluid(AtmosphericItemEntries.ALOE_GEL_BUCKET, prop))
        .defaultLang()
        .transform(Recipes.fluidHandling(AtmosphericItems.ALOE_GEL_BOTTLE, 250))
        .register();
    
}
