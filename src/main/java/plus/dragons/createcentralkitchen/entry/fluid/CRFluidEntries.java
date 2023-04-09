package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.brdle.collectorsreap.common.item.CRItems;
import plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem.virtual;

@ModLoadSubscriber(modid = Mods.CR)
public class CRFluidEntries {
    
    public static final FluidEntry<VirtualFluid>
        LIMEADE = VirtualFluidFromItem.watery(CRItems.LIMEADE, 0xADED39)
            .register(),
        BERRY_LIMEADE = VirtualFluidFromItem.watery(CRItems.BERRY_LIMEADE, 0xE05429)
            .register(),
        PINK_LIMEADE = VirtualFluidFromItem.watery(CRItems.PINK_LIMEADE, 0xFCA997)
            .register(),
        LIME_ICE_CREAM = virtual(CRItems.LIME_ICE_CREAM,
            Mods.cr("block/lime_ice_cream_block"), 500).register(),
        POMEGRANATE_ICE_CREAM = virtual(CRItems.POMEGRANATE_ICE_CREAM,
            Mods.cr("block/pomegranate_ice_cream_block"), 500).register(),
        LIME_MILKSHAKE = VirtualFluidFromItem.milky(CRItems.LIME_MILKSHAKE, 0xD7F7AC)
            .register(),
        POMEGRANATE_MILKSHAKE = VirtualFluidFromItem.milky(CRItems.POMEGRANATE_MILKSHAKE, 0xFB7C90)
            .register();
    
}
