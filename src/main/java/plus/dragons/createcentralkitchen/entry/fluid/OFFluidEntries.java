package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.overweightfarming.init.OFItems;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;
import static plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem.WATER_FLOW;
import static plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem.WATER_STILL;

@ModLoadSubscriber(modid = Mods.OF)
public class OFFluidEntries {
    public static final FluidEntry<VirtualFluid>
            MELON_JUICE = watery(OFItems.MELON_JUICE, 0xc02f24).register();

    private static FluidBuilder<VirtualFluid, CreateRegistrate> watery(RegistryObject<? extends ItemLike> container, int colorIn) {
        final int color = 0xFF000000 | colorIn;
        return REGISTRATE.virtualFluid(container.getId().getPath(), WATER_STILL, WATER_FLOW)
                .attributes(b -> b.color(0xFF000000 | color).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY))
                .defaultLang();
    }
}
