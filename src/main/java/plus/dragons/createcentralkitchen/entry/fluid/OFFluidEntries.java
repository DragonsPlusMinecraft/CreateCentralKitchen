package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.orcinus.overweightfarming.init.OFItems;
import plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.OF)
public class OFFluidEntries {
    public static final FluidEntry<VirtualFluid>
            MELON_JUICE = VirtualFluidFromItem.watery(OFItems.MELON_JUICE, 0xc02f24).register();
}
