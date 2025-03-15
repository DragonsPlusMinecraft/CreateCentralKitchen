package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.teamabnormals.atmospheric.core.registry.AtmosphericItems;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import plus.dragons.createcentralkitchen.entry.item.AtmosphericItemEntries;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.fluid.SolidBlockFluid;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.ATMOSPHERIC)
public class AtmosphericFluidEntries {
    
    public static final FluidEntry<SolidBlockFluid> ALOE_GEL = REGISTRATE.virtualFluid("aloe_gel",
            Mods.atmospheric("block/aloe_gel_block_top"),
            Mods.atmospheric("block/aloe_gel_block_side"),
            CreateRegistrate::defaultFluidType,
            prop -> new SolidBlockFluid(AtmosphericItemEntries.ALOE_GEL_BUCKET, prop))
        .defaultLang()
        .properties(builder -> builder.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL))
        .transform(Recipes.fluidHandling(AtmosphericItems.ALOE_GEL_BOTTLE, 250))
        .register();
    
}
