package plus.dragons.createfarmersautomation.entry;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.Locale;

public enum CfaFluids implements NonNullSupplier<Fluid> {
    TOMATO_SAUCE(FarmersDelight.MODID),
    HOT_COCOA(FarmersDelight.MODID),
    APPLE_CIDER(FarmersDelight.MODID),
    MELON_JUICE(FarmersDelight.MODID);
    
    public final RegistryObject<Fluid> source;
    public final RegistryObject<Fluid> flow;
    
    CfaFluids(String namespace, boolean virtual) {
        String name = name().toLowerCase(Locale.ROOT);
        ResourceLocation id = new ResourceLocation(namespace, name);
        this.source = RegistryObject.create(id, ForgeRegistries.FLUIDS);
        this.flow = virtual
            ? this.source
            : RegistryObject.create(new ResourceLocation(namespace, name), ForgeRegistries.FLUIDS);
    }
    
    CfaFluids(String namespace) {
        this(namespace, true);
    }
    
    public static void register(IEventBus modEventBus) {
        modEventBus.addGenericListener(Fluid.class, CfaFluids::onRegister);
    }
    
    public static void onRegister(RegistryEvent.Register<Fluid> event) {
        for (var entry : values()) {
            ResourceLocation id = entry.source.getId();
            VirtualFluid fluid = new VirtualFluid(new ForgeFlowingFluid.Properties(
                entry.source, entry.source,
                FluidAttributes.builder(
                    new ResourceLocation(id.getNamespace(), "fluid/" + id.getPath() + "_still"),
                    new ResourceLocation(id.getNamespace(), "fluid/" + id.getPath() + "_flow")
                )
            ));
            fluid.setRegistryName(id);
            event.getRegistry().register(fluid);
        }
    }
    
    @Override
    @NotNull
    public Fluid get() {
        return source.get();
    }
    
}