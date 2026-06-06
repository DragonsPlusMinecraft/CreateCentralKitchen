package plus.dragons.createcentralkitchen.entry.fluid;

import static plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem.milky;
import static plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem.virtual;

import cn.mcmod.corn_delight.item.ItemRegistry;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.CORN_DELIGHT)
public class CornDelightFluidEntries {
    public static final FluidEntry<VirtualFluid>

    CORN_SOUP = virtual(CentralKitchen.genRL("fluid/corn_soup_still"), CentralKitchen.genRL("fluid/corn_soup_still"),
            ItemRegistry.CORN_SOUP, 250)
                    .register(),

            CREAMY_CORN_DRINK = milky(ItemRegistry.CREAMY_CORN_DRINK, 15321972)
                    .register();
}
