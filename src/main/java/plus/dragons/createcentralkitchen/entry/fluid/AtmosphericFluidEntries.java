package plus.dragons.createcentralkitchen.entry.fluid;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import plus.dragons.createcentralkitchen.entry.item.AtmosphericItemEntries;
import plus.dragons.createcentralkitchen.foundation.fluid.SolidBlockFluid;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.ATMOSPHERIC)
public class AtmosphericFluidEntries {
    public static final FluidEntry<SolidBlockFluid> ALOE_GEL = REGISTRATE.virtualFluid("aloe_gel",
            Mods.atmospheric("block/aloe_gel_block_top"),
            Mods.atmospheric("block/aloe_gel_block_side"),
            CreateRegistrate::defaultFluidType,
            props -> new SolidBlockFluid(AtmosphericItemEntries.ALOE_GEL_BUCKET, props, true),
            propf -> new SolidBlockFluid(AtmosphericItemEntries.ALOE_GEL_BUCKET, propf, false))
            .defaultLang()
            .properties(builder -> builder.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL))
            .register();
}
