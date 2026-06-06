package plus.dragons.createcentralkitchen.entry.fluid;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import plus.dragons.createcentralkitchen.entry.item.UAItemEntries;
import plus.dragons.createcentralkitchen.foundation.fluid.SolidBlockFluid;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.UA)
public class UAFluidEntries {
    public static final FluidEntry<SolidBlockFluid> MULBERRY_JAM = REGISTRATE.virtualFluid("mulberry_jam",
            Mods.ua("block/mulberry_jam_block_top"),
            Mods.ua("block/mulberry_jam_block_side"),
            CreateRegistrate::defaultFluidType,
            props -> new SolidBlockFluid(UAItemEntries.MULBERRY_JAM_BUCKET, props, true),
            propf -> new SolidBlockFluid(UAItemEntries.MULBERRY_JAM_BUCKET, propf, false))
            .defaultLang()
            .properties(builder -> builder.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL))
            .register();
}
