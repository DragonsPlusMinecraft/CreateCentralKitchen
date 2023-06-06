package plus.dragons.createcentralkitchen.foundation.fluid;

import com.simibubi.create.AllFluids;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class VirtualFluidFromItem {
    private static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    private static final ResourceLocation WATER_FLOW = new ResourceLocation("block/water_flow");
    private static final ResourceLocation MILK_STILL = Create.asResource("fluid/milk_still");
    private static final ResourceLocation MILK_FLOW = Create.asResource("fluid/milk_flow");
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> watery(String name, RegistryObject<? extends ItemLike> container, int colorIn, int amount) {
        final int color = 0xFF000000 | colorIn;
        return REGISTRATE.virtualFluid(name, WATER_STILL, WATER_FLOW, ((properties, stillTexture, flowingTexture) ->
            new AllFluids.TintedFluidType(properties, stillTexture, flowingTexture) {
                @Override
                protected int getTintColor(FluidStack stack) {
                    return color;
                }

                @Override
                protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                    return color;
                }
            }), VirtualFluid::new)
            .defaultLang()
            .transform(Recipes.fluidHandling(container, amount));
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> watery(RegistryObject<? extends ItemLike> container, int colorIn, int amount) {
        return watery(container.getId().getPath(), container, colorIn, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> watery(String name, RegistryObject<? extends ItemLike> container, int colorIn) {
        return watery(name, container, colorIn, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> watery(RegistryObject<? extends ItemLike> container, int colorIn) {
        return watery(container, colorIn, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> watery(String name, ItemProviderEntry<?> container, int colorIn, int amount) {
        return REGISTRATE.virtualFluid(name, WATER_STILL, WATER_FLOW)
            .defaultLang()
            .transform(Recipes.fluidHandling(container, amount));
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> watery(ItemProviderEntry<?> container, int colorIn, int amount) {
        return watery(container.getId().getPath(), container, colorIn, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> watery(String name, ItemProviderEntry<?> container, int colorIn) {
        return watery(name, container, colorIn, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> watery(ItemProviderEntry<?> container, int colorIn) {
        return watery(container, colorIn, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milky(String name, RegistryObject<? extends ItemLike> container, int colorIn, int amount) {
        final int color = 0xFF000000 | colorIn;
        return REGISTRATE.virtualFluid(name, MILK_STILL, MILK_FLOW, ((properties, stillTexture, flowingTexture) ->
                new AllFluids.TintedFluidType(properties, stillTexture, flowingTexture) {
                    @Override
                    protected int getTintColor(FluidStack stack) {
                        return color;
                    }
                
                    @Override
                    protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                        return color;
                    }
                }), VirtualFluid::new)
            .defaultLang()
            .transform(Recipes.fluidHandling(container, amount));
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milky(RegistryObject<? extends ItemLike> container, int colorIn, int amount) {
        return milky(container.getId().getPath(), container, colorIn, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milky(String name, RegistryObject<? extends ItemLike> container, int colorIn) {
        return milky(name, container, colorIn, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milky(RegistryObject<? extends ItemLike> container, int colorIn) {
        return milky(container, colorIn, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milky(String name, ItemProviderEntry<?> container, int colorIn, int amount) {
        final int color = 0xFF000000 | colorIn;
        return REGISTRATE.virtualFluid(name, MILK_STILL, MILK_FLOW, ((properties, stillTexture, flowingTexture) ->
                new AllFluids.TintedFluidType(properties, stillTexture, flowingTexture) {
                    @Override
                    protected int getTintColor(FluidStack stack) {
                        return color;
                    }
                
                    @Override
                    protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                        return color;
                    }
                }), VirtualFluid::new)
            .defaultLang()
            .transform(Recipes.fluidHandling(container, amount));
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milky(ItemProviderEntry<?> container, int colorIn, int amount) {
        return milky(container.getId().getPath(), container, colorIn, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milky(String name, ItemProviderEntry<?> container, int colorIn) {
        return milky(name, container, colorIn, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milky(ItemProviderEntry<?> container, int colorIn) {
        return milky(container, colorIn, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(String name,
                                                                       ResourceLocation stillTexture,
                                                                       ResourceLocation flowTexture,
                                                                       RegistryObject<? extends ItemLike> container,
                                                                       int amount) {
        return REGISTRATE.virtualFluid(name, stillTexture, flowTexture)
            .defaultLang()
            .transform(Recipes.fluidHandling(container, amount));
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(String name,
                                                                       ResourceLocation stillTexture,
                                                                       ResourceLocation flowTexture,
                                                                       RegistryObject<? extends ItemLike> container) {
        return virtual(name, stillTexture, flowTexture, container, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(ResourceLocation stillTexture,
                                                                       ResourceLocation flowTexture,
                                                                       RegistryObject<? extends ItemLike> container,
                                                                       int amount) {
        String name = container.getId().getPath();
        return virtual(name, stillTexture, flowTexture, container, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(ResourceLocation stillTexture,
                                                                       ResourceLocation flowTexture,
                                                                       RegistryObject<? extends ItemLike> container) {
        String name = container.getId().getPath();
        return virtual(name, stillTexture, flowTexture, container, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(RegistryObject<? extends ItemLike> container, ResourceLocation texture) {
        return virtual(texture, texture, container);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(RegistryObject<? extends ItemLike> container, ResourceLocation texture, int amount) {
        return virtual(texture, texture, container, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(RegistryObject<? extends ItemLike> container, int amount) {
        String name = container.getId().getPath();
        return virtual(name, CentralKitchen.genRL("fluid/" + name + "_still"), CentralKitchen.genRL("fluid/" + name + "_flow"), container, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(RegistryObject<? extends ItemLike> container) {
        String name = container.getId().getPath();
        return virtual(container, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(String name,
                                                                       ResourceLocation stillTexture,
                                                                       ResourceLocation flowTexture,
                                                                       ItemProviderEntry<?> container,
                                                                       int amount) {
        return REGISTRATE.virtualFluid(name, stillTexture, flowTexture)
            .defaultLang()
            .transform(Recipes.fluidHandling(container, amount));
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(String name,
                                                                       ResourceLocation stillTexture,
                                                                       ResourceLocation flowTexture,
                                                                       ItemProviderEntry<?> container) {
        return virtual(name, stillTexture, flowTexture, container, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(ResourceLocation stillTexture,
                                                                       ResourceLocation flowTexture,
                                                                       ItemProviderEntry<?> container,
                                                                       int amount) {
        String name = container.getId().getPath();
        return virtual(name, stillTexture, flowTexture, container, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(ResourceLocation stillTexture,
                                                                       ResourceLocation flowTexture,
                                                                       ItemProviderEntry<?> container) {
        String name = container.getId().getPath();
        return virtual(name, stillTexture, flowTexture, container, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(ItemProviderEntry<?> container, ResourceLocation texture) {
        return virtual(texture, texture, container);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(ItemProviderEntry<?> container, ResourceLocation texture, int amount) {
        return virtual(texture, texture, container, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(ItemProviderEntry<?> container, int amount) {
        String name = container.getId().getPath();
        return virtual(name, CentralKitchen.genRL("fluid/" + name + "_still"), CentralKitchen.genRL("fluid/" + name + "_flow"), container, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(ItemProviderEntry<?> container) {
        String name = container.getId().getPath();
        return virtual(container, 250);
    }
    
}
