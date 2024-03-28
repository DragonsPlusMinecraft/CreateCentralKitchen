package plus.dragons.createcentralkitchen;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.content.contraptions.fluids.OpenEndedPipeEffects;
import plus.dragons.createcentralkitchen.entry.creativetab.CckCreativeModeTab;
import plus.dragons.createcentralkitchen.entry.fluid.CckFluidEntries;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigs;
import plus.dragons.createcentralkitchen.foundation.data.CentralKitchenData;
import plus.dragons.createcentralkitchen.foundation.ponder.CentralKitchenPonders;
import plus.dragons.createcentralkitchen.foundation.resource.condition.ConfigBoolCondition;
import plus.dragons.createcentralkitchen.foundation.resource.condition.ConfigListCondition;
import plus.dragons.createcentralkitchen.foundation.utility.AutomaticModLoadSubscriber;
import plus.dragons.createdragonlib.lang.Lang;

@Mod(CentralKitchen.ID)
public class CentralKitchen {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "create_central_kitchen";
    public static final String NAME = "Create: Central Kitchen";
    public static final Lang LANG = new Lang(ID);
    
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTER =
        DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTER =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ID);


    
    public CentralKitchen() {
        CentralKitchenConfigs.register(ModLoadingContext.get());

        CckFluidEntries.register();

        FMLModContainer container = (FMLModContainer) ModLoadingContext.get().getActiveContainer();
        AutomaticModLoadSubscriber.load(container, CentralKitchen.class);
        
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        CckCreativeModeTab.register(modBus);
        REGISTRATE.registerEventListeners(modBus);
        RECIPE_TYPE_REGISTER.register(modBus);
        RECIPE_SERIALIZER_REGISTER.register(modBus);
        CraftingHelper.register(new ConfigBoolCondition.Serializer());
        CraftingHelper.register(new ConfigListCondition.Serializer());
        
        if (DatagenModLoader.isRunningDataGen()) {
            CentralKitchenData.register(modBus);
        }
    }
    
    public void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(OpenEndedPipeEffects::register);
    }
    
    public void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(CentralKitchenPonders::register);
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }

}