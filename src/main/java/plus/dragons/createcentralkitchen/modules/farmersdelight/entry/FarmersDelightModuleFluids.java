package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import plus.dragons.createcentralkitchen.modules.farmersdelight.FarmersDelightModule;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.HashMap;
import java.util.Map;

import static plus.dragons.createcentralkitchen.core.fluid.FluidFromContainerItem.*;

public class FarmersDelightModuleFluids {
    
    public static final FluidEntry<VirtualFluid>
        APPLE_CIDER = wateryVirtual(ModItems.APPLE_CIDER, 0xc68a47)
            .register(),
        HOT_COCOA = milkyVirtual(ModItems.HOT_COCOA, 0xaf6c4c)
            .register(),
        MELON_JUICE = wateryVirtual(ModItems.MELON_JUICE, 0xe24334)
            .register(),
        TOMATO_SAUCE = virtual(ModItems.TOMATO_SAUCE)
            .register();
    
    private static final Map<ResourceLocation, FluidEntry<?>> REMAP = new HashMap<>();
    static  {
        REMAP.put(FarmersDelightModule.genRL("apple_cider"), APPLE_CIDER);
        REMAP.put(FarmersDelightModule.genRL("hot_cocoa"), HOT_COCOA);
        REMAP.put(FarmersDelightModule.genRL("melon_juice"), MELON_JUICE);
        REMAP.put(FarmersDelightModule.genRL("tomato_sauce"), TOMATO_SAUCE);
    }
    
    public static void remap(RegistryEvent.MissingMappings<Fluid> event) {
        for (var mapping : event.getMappings(FarmersDelightModule.ID)) {
            if (REMAP.containsKey(mapping.key)) {
                var entry = REMAP.get(mapping.key);
                mapping.remap(entry.get());
                FarmersDelightModule.LOGGER.warn("Remapping fluid '{}' to '{}'...", mapping.key, entry.getId());
            }
        }
    }
    
    public static void register() {}
    
}