package plus.dragons.createcentralkitchen.entry.fluid;

import com.farmersrespite.core.registry.FRItems;
import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.FR)
public class FRFluidEntries {
    
    public static final FluidEntry<VirtualFluid>
        LONG_APPLE_CIDER = VirtualFluidFromItem.watery(FRItems.LONG_APPLE_CIDER, 0xC68A47)
            .lang("Apple Cider").register(),
        STRONG_APPLE_CIDER = VirtualFluidFromItem.watery(FRItems.STRONG_APPLE_CIDER, 0xC68A47)
            .lang("Apple Cider").register(),
        GREEN_TEA = VirtualFluidFromItem.watery(FRItems.GREEN_TEA, 0xA1A83C)
            .register(),
        LONG_GREEN_TEA = VirtualFluidFromItem.watery(FRItems.LONG_GREEN_TEA, 0xA1A83C)
            .lang("Green Tea").register(),
        STRONG_GREEN_TEA = VirtualFluidFromItem.watery(FRItems.STRONG_GREEN_TEA, 0xA1A83C)
            .lang("Green Tea").register(),
        YELLOW_TEA = VirtualFluidFromItem.watery(FRItems.YELLOW_TEA, 0xAB8542)
            .register(),
        LONG_YELLOW_TEA = VirtualFluidFromItem.watery(FRItems.LONG_YELLOW_TEA, 0xAB8542)
            .lang("Yellow Tea").register(),
        STRONG_YELLOW_TEA = VirtualFluidFromItem.watery(FRItems.STRONG_YELLOW_TEA, 0xAB8542)
            .lang("Yellow Tea").register(),
        BLACK_TEA = VirtualFluidFromItem.watery(FRItems.BLACK_TEA, 0x783E27)
            .register(),
        LONG_BLACK_TEA = VirtualFluidFromItem.watery(FRItems.LONG_BLACK_TEA, 0x783E27)
            .lang("Black Tea").register(),
        STRONG_BLACK_TEA = VirtualFluidFromItem.watery(FRItems.STRONG_BLACK_TEA, 0x783E27)
            .lang("Black Tea").register(),
        DANDELION_TEA = VirtualFluidFromItem.watery(FRItems.DANDELION_TEA, 0xf7c750)
            .register(),
        LONG_DANDELION_TEA = VirtualFluidFromItem.watery(FRItems.LONG_DANDELION_TEA, 0xf7c750)
            .lang("Dandelion Tea").register(),
        PURULENT_TEA = VirtualFluidFromItem.watery(FRItems.PURULENT_TEA, 0x842236)
            .register(),
        STRONG_PURULENT_TEA = VirtualFluidFromItem.watery(FRItems.STRONG_PURULENT_TEA, 0x842236)
            .lang("Purulent Tea").register(),
        ROSE_HIP_TEA = VirtualFluidFromItem.watery(FRItems.ROSE_HIP_TEA, 0x741603)
            .register(),
        STRONG_ROSE_HIP_TEA = VirtualFluidFromItem.watery(FRItems.STRONG_ROSE_HIP_TEA, 0x741603)
            .lang("Rose Hip Tea").register(),
        COFFEE = VirtualFluidFromItem.milky(FRItems.COFFEE, 0x321F13)
            .register(),
        LONG_COFFEE = VirtualFluidFromItem.milky(FRItems.LONG_COFFEE, 0x321F13)
            .lang("Coffee").register(),
        STRONG_COFFEE = VirtualFluidFromItem.milky(FRItems.STRONG_COFFEE, 0x321F13)
            .lang("Coffee").register();
    
}
