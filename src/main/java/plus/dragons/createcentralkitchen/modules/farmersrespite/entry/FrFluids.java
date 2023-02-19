package plus.dragons.createcentralkitchen.modules.farmersrespite.entry;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.data.recipe.RecipeGen;
import umpaz.farmersrespite.common.registry.FRItems;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class FrFluids {
    
    public static final FluidEntry<VirtualFluid>
        LONG_APPLE_CIDER = wateryDrink("long_apple_cider", 0xc68a47, FRItems.LONG_APPLE_CIDER)
            .lang("Apple Cider").register(),
        STRONG_APPLE_CIDER = wateryDrink("strong_apple_cider", 0xc68a47, FRItems.STRONG_APPLE_CIDER)
            .lang("Apple Cider").register(),
        GREEN_TEA = wateryDrink("green_tea", 0xa1a83c, FRItems.GREEN_TEA)
            .register(),
        LONG_GREEN_TEA = wateryDrink("long_green_tea", 0xa1a83c, FRItems.LONG_GREEN_TEA)
            .lang("Green Tea").register(),
        STRONG_GREEN_TEA = wateryDrink("strong_green_tea", 0xa1a83c, FRItems.STRONG_GREEN_TEA)
            .lang("Green Tea").register(),
        YELLOW_TEA = wateryDrink("yellow_tea", 0xab8542, FRItems.YELLOW_TEA)
            .register(),
        LONG_YELLOW_TEA = wateryDrink("long_yellow_tea", 0xab8542, FRItems.LONG_YELLOW_TEA)
            .lang("Yellow Tea").register(),
        STRONG_YELLOW_TEA = wateryDrink("strong_yellow_tea", 0xab8542, FRItems.STRONG_YELLOW_TEA)
            .lang("Yellow Tea").register(),
        BLACK_TEA = wateryDrink("black_tea", 0x783e27, FRItems.BLACK_TEA)
            .register(),
        LONG_BLACK_TEA = wateryDrink("long_black_tea", 0x783e27, FRItems.LONG_BLACK_TEA)
            .lang("Black Tea").register(),
        STRONG_BLACK_TEA = wateryDrink("strong_black_tea", 0x783e27, FRItems.STRONG_BLACK_TEA)
            .lang("Black Tea").register(),
        DANDELION_TEA = wateryDrink("dandelion_tea", 0xf7c750, FRItems.DANDELION_TEA)
            .register(),
        LONG_DANDELION_TEA = wateryDrink("long_dandelion_tea", 0xf7c750, FRItems.LONG_DANDELION_TEA)
            .lang("Dandelion Tea").register(),
        PURULENT_TEA = wateryDrink("purulent_tea", 0x842236, FRItems.PURULENT_TEA)
            .register(),
        STRONG_PURULENT_TEA = wateryDrink("strong_purulent_tea", 0x842236, FRItems.STRONG_PURULENT_TEA)
            .lang("Purulent Tea").register(),
        ROSE_HIP_TEA = wateryDrink("rose_hip_tea", 0x741603, FRItems.ROSE_HIP_TEA)
            .register(),
        STRONG_ROSE_HIP_TEA = wateryDrink("strong_rose_hip_tea", 0x741603, FRItems.STRONG_ROSE_HIP_TEA)
            .lang("Rose Hip Tea").register(),
        COFFEE = thickDrink("coffee", 0x321f13, FRItems.COFFEE)
            .register(),
        LONG_COFFEE = thickDrink("long_coffee", 0x321f13, FRItems.LONG_COFFEE)
            .lang("Coffee").register(),
        STRONG_COFFEE = thickDrink("strong_coffee", 0x321f13, FRItems.STRONG_COFFEE)
            .lang("Coffee").register();
    
    private static FluidBuilder<VirtualFluid, CreateRegistrate> wateryDrink(String name,
                                                                            int color,
                                                                            RegistryObject<? extends ItemLike> drink)
    {
        return REGISTRATE.virtualFluid(name,
                new ResourceLocation("forge", "block/milk_still"),
                new ResourceLocation("forge", "block/milk_flowing"))
            .defaultLang()
                // TODO
            .attributes(b -> b.color(0xFF000000 | color))
            .transform(RecipeGen.fluidHandling(drink, 250));
    }
    
    private static FluidBuilder<VirtualFluid, CreateRegistrate> thickDrink(String name,
                                                                           int color,
                                                                           RegistryObject<? extends ItemLike> drink)
    {
        return REGISTRATE.virtualFluid(name,
                Create.asResource("fluid/milk_still"),
                Create.asResource("fluid/milk_flow"))
            .defaultLang()
                // TODO
            .attributes(b -> b.color(0xFF000000 | color))
            .transform(RecipeGen.fluidHandling(drink, 250));
    }
    
    public static void register() {}
    
}
