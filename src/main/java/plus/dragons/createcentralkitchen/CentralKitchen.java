package plus.dragons.createcentralkitchen;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.core.modules.ModModuleLoader;
import plus.dragons.createcentralkitchen.core.ponder.PonderDeferredRegister;
import plus.dragons.createcentralkitchen.data.CentralKitchenData;

@Mod(CentralKitchen.ID)
public class CentralKitchen {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "create_central_kitchen";
    public static final String NAME = "Create: Central Kitchen";
    
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTER =
        DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ID);
    public static final PonderDeferredRegister PONDER_REGISTER =
        PonderDeferredRegister.create(ID);
    
    public CentralKitchen() {
        ModModuleLoader.loadModules();
        
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        CentralKitchenData.register(modBus);
        REGISTRATE.registerEventListeners(modBus);
        TYPE_REGISTER.register(modBus);
        SERIALIZER_REGISTER.register(modBus);
        PONDER_REGISTER.register(modBus);
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }

}