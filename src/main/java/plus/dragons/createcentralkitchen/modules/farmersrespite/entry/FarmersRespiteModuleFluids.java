package plus.dragons.createcentralkitchen.modules.farmersrespite.entry;

import com.farmersrespite.core.registry.FRItems;
import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;

import static plus.dragons.createcentralkitchen.core.fluid.FluidFromContainerItem.milkyVirtual;
import static plus.dragons.createcentralkitchen.core.fluid.FluidFromContainerItem.wateryVirtual;

public class FarmersRespiteModuleFluids {
    
    public static final FluidEntry<VirtualFluid>
        LONG_APPLE_CIDER = wateryVirtual(FRItems.LONG_APPLE_CIDER, 0xc68a47)
            .lang("Apple Cider").register(),
        STRONG_APPLE_CIDER = wateryVirtual(FRItems.STRONG_APPLE_CIDER, 0xc68a47)
            .lang("Apple Cider").register(),
        GREEN_TEA = wateryVirtual(FRItems.GREEN_TEA, 0xa1a83c)
            .register(),
        LONG_GREEN_TEA = wateryVirtual(FRItems.LONG_GREEN_TEA, 0xa1a83c)
            .lang("Green Tea").register(),
        STRONG_GREEN_TEA = wateryVirtual(FRItems.STRONG_GREEN_TEA, 0xa1a83c)
            .lang("Green Tea").register(),
        YELLOW_TEA = wateryVirtual(FRItems.YELLOW_TEA, 0xab8542)
            .register(),
        LONG_YELLOW_TEA = wateryVirtual(FRItems.LONG_YELLOW_TEA, 0xab8542)
            .lang("Yellow Tea").register(),
        STRONG_YELLOW_TEA = wateryVirtual(FRItems.STRONG_YELLOW_TEA, 0xab8542)
            .lang("Yellow Tea").register(),
        BLACK_TEA = wateryVirtual(FRItems.BLACK_TEA, 0x783e27)
            .register(),
        LONG_BLACK_TEA = wateryVirtual(FRItems.LONG_BLACK_TEA, 0x783e27)
            .lang("Black Tea").register(),
        STRONG_BLACK_TEA = wateryVirtual(FRItems.STRONG_BLACK_TEA, 0x783e27)
            .lang("Black Tea").register(),
        DANDELION_TEA = wateryVirtual(FRItems.DANDELION_TEA, 0xf7c750)
            .register(),
        LONG_DANDELION_TEA = wateryVirtual(FRItems.LONG_DANDELION_TEA, 0xf7c750)
            .lang("Dandelion Tea").register(),
        PURULENT_TEA = wateryVirtual(FRItems.PURULENT_TEA, 0x842236)
            .register(),
        STRONG_PURULENT_TEA = wateryVirtual(FRItems.STRONG_PURULENT_TEA, 0x842236)
            .lang("Purulent Tea").register(),
        ROSE_HIP_TEA = wateryVirtual(FRItems.ROSE_HIP_TEA, 0x741603)
            .register(),
        STRONG_ROSE_HIP_TEA = wateryVirtual(FRItems.STRONG_ROSE_HIP_TEA, 0x741603)
            .lang("Rose Hip Tea").register(),
        COFFEE = milkyVirtual(FRItems.COFFEE, 0x321f13)
            .register(),
        LONG_COFFEE = milkyVirtual(FRItems.LONG_COFFEE, 0x321f13)
            .lang("Coffee").register(),
        STRONG_COFFEE = milkyVirtual(FRItems.STRONG_COFFEE, 0x321f13)
            .lang("Coffee").register();
    
    public static void register() {}
    
}
