package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import plus.dragons.respiteful.entries.RespitefulItems;

import static plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem.virtual;

@ModLoadSubscriber(modid = Mods.RESPITEFUL)
public class RespitefulFluidEntries {
    
    public static final FluidEntry<VirtualFluid>
        GREEN_TEA_ICE_CREAM = virtual(RespitefulItems.GREEN_TEA_ICE_CREAM,
            Mods.respiteful("block/green_tea_ice_cream_block"), 500).register(),
        YELLOW_TEA_ICE_CREAM = virtual(RespitefulItems.YELLOW_TEA_ICE_CREAM,
            Mods.respiteful("block/yellow_tea_ice_cream_block"), 500).register(),
        BLACK_TEA_ICE_CREAM = virtual(RespitefulItems.BLACK_TEA_ICE_CREAM,
            Mods.respiteful("block/black_tea_ice_cream_block"), 500).register(),
        COFFEE_ICE_CREAM = virtual(RespitefulItems.COFFEE_ICE_CREAM,
            Mods.respiteful("block/coffee_ice_cream_block"), 500).register();
    
}
