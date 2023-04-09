package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import com.tterrag.registrate.util.entry.FluidEntry;
import plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem.virtual;

@ModLoadSubscriber(modid = Mods.NEAPOLITAN)
public class NeapolitanFluidEntries {
    
    public static final FluidEntry<VirtualFluid>
        CHOCOLATE_ICE_CREAM = virtual(NeapolitanItems.CHOCOLATE_ICE_CREAM,
            Mods.neapolitan("block/chocolate_ice_cream_block"), 500).register(),
        STRAWBERRY_ICE_CREAM = virtual(NeapolitanItems.STRAWBERRY_ICE_CREAM,
            Mods.neapolitan("block/strawberry_ice_cream_block"), 500).register(),
        VANILLA_ICE_CREAM = virtual(NeapolitanItems.VANILLA_ICE_CREAM,
            Mods.neapolitan("block/vanilla_ice_cream_block"), 500).register(),
        BANANA_ICE_CREAM = virtual(NeapolitanItems.BANANA_ICE_CREAM,
            Mods.neapolitan("block/banana_ice_cream_block"), 500).register(),
        MINT_ICE_CREAM = virtual(NeapolitanItems.MINT_ICE_CREAM,
            Mods.neapolitan("block/mint_ice_cream_block"), 500).register(),
        ADZUKI_ICE_CREAM = virtual(NeapolitanItems.ADZUKI_ICE_CREAM,
            Mods.neapolitan("block/adzuki_ice_cream_block"), 500).register(),
        CHOCOLATE_MILKSHAKE = VirtualFluidFromItem.milky(NeapolitanItems.CHOCOLATE_MILKSHAKE, 0xD39576)
            .register(),
        STRAWBERRY_MILKSHAKE = VirtualFluidFromItem.milky(NeapolitanItems.STRAWBERRY_MILKSHAKE, 0xFCBFFa)
            .register(),
        VANILLA_MILKSHAKE = VirtualFluidFromItem.milky(NeapolitanItems.VANILLA_MILKSHAKE, 0xFCE8e5)
            .register(),
        BANANA_MILKSHAKE = VirtualFluidFromItem.milky(NeapolitanItems.BANANA_MILKSHAKE, 0xFCE797)
            .register(),
        MINT_MILKSHAKE = VirtualFluidFromItem.milky(NeapolitanItems.MINT_MILKSHAKE, 0xD1F7D8)
            .register(),
        ADZUKI_MILKSHAKE = VirtualFluidFromItem.milky(NeapolitanItems.ADZUKI_MILKSHAKE, 0xFCA8AE)
            .register();
    
}
