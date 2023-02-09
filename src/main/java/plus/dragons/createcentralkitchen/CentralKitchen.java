package plus.dragons.createcentralkitchen;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.common.modules.ModModuleLoader;
import plus.dragons.createdragonlib.init.SafeRegistrate;
import plus.dragons.createdragonlib.lang.Lang;
import plus.dragons.createdragonlib.lang.LangFactory;

@Mod(CentralKitchen.ID)
public class CentralKitchen {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "create_central_kitchen";
    public static final String NAME = "Create: Central Kitchen";

    public static final CreateRegistrate REGISTRATE = new SafeRegistrate(ID);
    public static final Lang LANG = new Lang(ID);
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTER =
        DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ID);
    
    public CentralKitchen() {
        ModModuleLoader.loadModules();
        
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modBus);
        TYPE_REGISTER.register(modBus);
        SERIALIZER_REGISTER.register(modBus);

        LangFactory langFactory = LangFactory.create(NAME, ID)
                .ponders(() -> {})
                .tooltips()
                .merge("Game Elements: Liquid", "game_elements");
        modBus.addListener(EventPriority.LOWEST, langFactory::datagen);
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }


}