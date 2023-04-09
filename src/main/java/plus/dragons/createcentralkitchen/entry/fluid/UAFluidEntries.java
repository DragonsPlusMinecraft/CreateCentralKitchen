package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.teamabnormals.upgrade_aquatic.core.registry.UAItems;
import com.tterrag.registrate.util.entry.FluidEntry;
import plus.dragons.createcentralkitchen.entry.item.UAItemEntries;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.fluid.SolidBlockFluid;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.UA)
public class UAFluidEntries {
    
    public static final FluidEntry<SolidBlockFluid>
        MULBERRY_JAM = REGISTRATE.virtualFluid("mulberry_jam",
            Mods.ua("block/mulberry_jam_block_top"),
            Mods.ua("block/mulberry_jam_block_side"),
            CreateRegistrate::defaultFluidType,
            prop -> new SolidBlockFluid(UAItemEntries.MULBERRY_JAM_BUCKET, prop))
        .defaultLang()
        .transform(Recipes.fluidHandling(UAItems.MULBERRY_JAM_BOTTLE, 250))
        .register();
    
}
