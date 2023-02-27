package plus.dragons.createcentralkitchen.core.fluid;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.data.recipe.RecipeGen;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class FluidFromContainerItem {
    private static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    private static final ResourceLocation WATER_FLOW = new ResourceLocation("block/water_flow");
    private static final ResourceLocation MILK_STILL = Create.asResource("fluid/milk_still");
    private static final ResourceLocation MILK_FLOW = Create.asResource("fluid/milk_flow");
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> wateryVirtual(String name, RegistryObject<? extends ItemLike> container, int color, int amount) {
        return REGISTRATE.virtualFluid(name, WATER_STILL, WATER_FLOW)
            .defaultLang()
            .attributes(b -> b.color(0xFF000000 | color))
            .transform(RecipeGen.fluidHandling(container, amount));
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> wateryVirtual(RegistryObject<? extends ItemLike> container, int color, int amount) {
        return wateryVirtual(container.getId().getPath(), container, color, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> wateryVirtual(String name, RegistryObject<? extends ItemLike> container, int color) {
        return wateryVirtual(name, container, color, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> wateryVirtual(RegistryObject<? extends ItemLike> container, int color) {
        return wateryVirtual(container, color, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milkyVirtual(String name, RegistryObject<? extends ItemLike> container, int color, int amount) {
        return REGISTRATE.virtualFluid(name, MILK_STILL, MILK_FLOW)
            .defaultLang()
            .attributes(b -> b.color(0xFF000000 | color))
            .transform(RecipeGen.fluidHandling(container, amount));
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milkyVirtual(RegistryObject<? extends ItemLike> container, int color, int amount) {
        return milkyVirtual(container.getId().getPath(), container, color, amount);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milkyVirtual(String name, RegistryObject<? extends ItemLike> container, int color) {
        return milkyVirtual(name, container, color, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> milkyVirtual(RegistryObject<? extends ItemLike> container, int color) {
        return milkyVirtual(container, color, 250);
    }
    
    public static FluidBuilder<VirtualFluid, CreateRegistrate> virtual(String name,
                                                                       ResourceLocation stillTexture,
                                                                       ResourceLocation flowTexture,
                                                                       RegistryObject<? extends ItemLike> container,
                                                                       int amount) {
        return REGISTRATE.virtualFluid(name, stillTexture, flowTexture)
            .defaultLang()
            .transform(RecipeGen.fluidHandling(container, amount));
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
    
}
