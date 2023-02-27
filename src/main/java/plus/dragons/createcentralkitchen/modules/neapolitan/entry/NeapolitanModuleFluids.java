package plus.dragons.createcentralkitchen.modules.neapolitan.entry;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import com.tterrag.registrate.util.entry.FluidEntry;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.modules.neapolitan.NeapolitanModule;

import static plus.dragons.createcentralkitchen.core.fluid.FluidFromContainerItem.milkyVirtual;
import static plus.dragons.createcentralkitchen.core.fluid.FluidFromContainerItem.virtual;

public class NeapolitanModuleFluids {
    
    public static final FluidEntry<VirtualFluid>
        CHOCOLATE_ICE_CREAM = virtual(NeapolitanItems.CHOCOLATE_ICE_CREAM,
            NeapolitanModule.genRL("block/chocolate_ice_cream_block"), 500).register(),
        STRAWBERRY_ICE_CREAM = virtual(NeapolitanItems.STRAWBERRY_ICE_CREAM,
            NeapolitanModule.genRL("block/strawberry_ice_cream_block"), 500).register(),
        VANILLA_ICE_CREAM = virtual(NeapolitanItems.VANILLA_ICE_CREAM,
            NeapolitanModule.genRL("block/vanilla_ice_cream_block"), 500).register(),
        BANANA_ICE_CREAM = virtual(NeapolitanItems.BANANA_ICE_CREAM,
            NeapolitanModule.genRL("block/banana_ice_cream_block"), 500).register(),
        MINT_ICE_CREAM = virtual(NeapolitanItems.MINT_ICE_CREAM,
            NeapolitanModule.genRL("block/mint_ice_cream_block"), 500).register(),
        ADZUKI_ICE_CREAM = virtual(NeapolitanItems.ADZUKI_ICE_CREAM,
            NeapolitanModule.genRL("block/adzuki_ice_cream_block"), 500).register(),
        CHOCOLATE_MILKSHAKE = milkyVirtual(NeapolitanItems.CHOCOLATE_MILKSHAKE, 0xd39576)
            .register(),
        STRAWBERRY_MILKSHAKE = milkyVirtual(NeapolitanItems.STRAWBERRY_MILKSHAKE, 0xfcbffa)
            .register(),
        VANILLA_MILKSHAKE = milkyVirtual(NeapolitanItems.VANILLA_MILKSHAKE, 0xfce8e5)
            .register(),
        BANANA_MILKSHAKE = milkyVirtual(NeapolitanItems.BANANA_MILKSHAKE, 0xfce797)
            .register(),
        MINT_MILKSHAKE = milkyVirtual(NeapolitanItems.MINT_MILKSHAKE, 0xd1f7d8)
            .register(),
        ADZUKI_MILKSHAKE = milkyVirtual(NeapolitanItems.ADZUKI_MILKSHAKE, 0xfca8aE)
            .register(),
        STRAWBERRY_BANANA_SMOOTHIE = virtual(NeapolitanItems.STRAWBERRY_BANANA_SMOOTHIE,
            CentralKitchen.genRL("fluid/strawberry_banana_smoothie")).register();
    
    public static void register() {}

}
