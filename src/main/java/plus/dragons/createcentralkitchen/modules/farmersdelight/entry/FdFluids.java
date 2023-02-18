package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.VirtualFluidBuilder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.data.recipe.RecipeGen;
import plus.dragons.createcentralkitchen.modules.farmersdelight.FarmersDelightModule;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.HashMap;
import java.util.Map;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class FdFluids {
    
    public static final FluidEntry<VirtualFluid> APPLE_CIDER = fromItem(ModItems.APPLE_CIDER, 250).register();
    
    public static final FluidEntry<VirtualFluid> HOT_COCOA = fromItem(ModItems.HOT_COCOA, 250).register();
    
    public static final FluidEntry<VirtualFluid> MELON_JUICE = fromItem(ModItems.MELON_JUICE, 250).register();
    
    public static final FluidEntry<VirtualFluid> TOMATO_SAUCE = fromItem(ModItems.TOMATO_SAUCE, 250).register();
    
    @SuppressWarnings("deprecation")
    public static FluidBuilder<VirtualFluid, CreateRegistrate> fromItem(RegistryObject<? extends Item> item, int amount) {
        String mod = item.getId().getNamespace();
        String name = item.getId().getPath();
        ResourceLocation tagId = new ResourceLocation("forge", name);
        return REGISTRATE.entry(name, callback -> new VirtualFluidBuilder<>(
                REGISTRATE, REGISTRATE, name, callback,
                CentralKitchen.genRL("fluid/" + name + "_still"),
                CentralKitchen.genRL("fluid/" + name + "_flow"),
                CreateRegistrate::defaultFluidType,
                VirtualFluid::new)
            )
            .defaultLang()
            .setData(ProviderType.RECIPE, (ctx, prov) -> {
                Fluid source = ctx.get().getSource();
                RecipeGen.emptying(name)
                    .require(item.get())
                    .output(source, amount)
                    .whenModLoaded(mod)
                    .build(prov);
                RecipeGen.filling(name)
                    .require(item.get().getCraftingRemainingItem())
                    .require(source, amount)
                    .output(item.get())
                    .whenModLoaded(mod)
                    .build(prov);
            });
    }
    
    private static final Map<ResourceLocation, FluidEntry<?>> REMAP = new HashMap<>();
    static  {
        REMAP.put(FarmersDelightModule.genRL("apple_cider"), APPLE_CIDER);
        REMAP.put(FarmersDelightModule.genRL("hot_cocoa"), HOT_COCOA);
        REMAP.put(FarmersDelightModule.genRL("melon_juice"), MELON_JUICE);
        REMAP.put(FarmersDelightModule.genRL("tomato_sauce"), TOMATO_SAUCE);
    }
    
    public static void remap(MissingMappingsEvent event) {
        for (var mapping : event.getMappings(Registry.FLUID_REGISTRY,FarmersDelightModule.ID)) {
            if (REMAP.containsKey(mapping.getKey())) {
                var entry = REMAP.get(mapping.getKey());
                mapping.remap(entry.get());
                FarmersDelightModule.LOGGER.warn("Remapping fluid '{}' to '{}'...", mapping.getKey(), entry.getId());
            }
        }
    }
    
    public static void register() {}
    
}