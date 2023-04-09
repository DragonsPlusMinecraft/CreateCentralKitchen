package plus.dragons.createcentralkitchen.entry.fluid;

import com.cosmicgelatin.peculiars.core.registry.PeculiarsItems;
import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem.virtual;

@ModLoadSubscriber(modid = Mods.PECULIARS)
public class PeculiarsFluidEntries {
    
    public static final FluidEntry<VirtualFluid>
        YUCCA_ICE_CREAM = virtual(PeculiarsItems.YUCCA_ICE_CREAM,
            Mods.peculiars("block/yucca/yucca_ice_cream_block"), 500).register(),
        ALOE_ICE_CREAM = virtual(PeculiarsItems.ALOE_ICE_CREAM,
            Mods.peculiars("block/aloe/aloe_ice_cream_block"), 500).register(),
        PASSIONFRUIT_ICE_CREAM = virtual(PeculiarsItems.PASSIONFRUIT_ICE_CREAM,
            Mods.peculiars("block/passionfruit/passionfruit_ice_cream_block"), 500).register(),
        YUCCA_MILKSHAKE = VirtualFluidFromItem.milky(PeculiarsItems.YUCCA_MILKSHAKE, 0xC4DD44)
            .register(),
        ALOE_MILKSHAKE = VirtualFluidFromItem.milky(PeculiarsItems.ALOE_MILKSHAKE, 0xBFE29C)
            .register(),
        PASSIONFRUIT_MILKSHAKE = VirtualFluidFromItem.milky(PeculiarsItems.PASSIONFRUIT_MILKSHAKE, 0xFFE987)
            .register();
    
}
