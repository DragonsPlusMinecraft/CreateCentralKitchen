package plus.dragons.createcentralkitchen.entry;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.Locale;

public enum CckFluids implements NonNullSupplier<Fluid> {
    TOMATO_SAUCE(FarmersDelight.MODID),
    HOT_COCOA(FarmersDelight.MODID),
    APPLE_CIDER(FarmersDelight.MODID),
    MELON_JUICE(FarmersDelight.MODID);
    
    public final RegistryObject<Fluid> source;
    public final RegistryObject<Fluid> flow;
    
    CckFluids(String namespace, boolean virtual) {
        String name = name().toLowerCase(Locale.ROOT);
        ResourceLocation id = new ResourceLocation(namespace, name);
        this.source = RegistryObject.create(id, ForgeRegistries.FLUIDS);
        this.flow = virtual
            ? this.source
            : RegistryObject.create(new ResourceLocation(namespace, name), ForgeRegistries.FLUIDS);
    }
    
    CckFluids(String namespace) {
        this(namespace, true);
    }
    
    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(CckFluids::onRegister);
    }
    
    public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.FLUIDS,fluidRegisterHelper -> {
            for (var entry : values()) {
                ResourceLocation id = entry.source.getId();
                VirtualFluid fluid = new VirtualFluid(new ForgeFlowingFluid.Properties(
                        ()-> CreateRegistrate.defaultFluidType(FluidType.Properties.create(),
                                new ResourceLocation(id.getNamespace(), "fluid/" + id.getPath() + "_still"),
                                new ResourceLocation(id.getNamespace(), "fluid/" + id.getPath() + "_flow")),
                        entry.source, entry.source
                ));
                fluidRegisterHelper.register(id,fluid);
            }
        });

    }
    
    @Override
    @NotNull
    public Fluid get() {
        return source.get();
    }
    
}