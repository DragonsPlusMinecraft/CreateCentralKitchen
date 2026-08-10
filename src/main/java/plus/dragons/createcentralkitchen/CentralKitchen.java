package plus.dragons.createcentralkitchen;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
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
import plus.dragons.createcentralkitchen.foundation.ponder.CCKPonderPlugin;
import plus.dragons.createcentralkitchen.foundation.resource.condition.ConfigBoolCondition;
import plus.dragons.createcentralkitchen.foundation.resource.condition.ConfigListCondition;
import plus.dragons.createcentralkitchen.foundation.utility.AutomaticModLoadSubscriber;

@Mod(CentralKitchen.ID)
public class CentralKitchen {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "create_central_kitchen";
    public static final String NAME = "Create: Central Kitchen";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ID);

    public CentralKitchen(FMLJavaModLoadingContext context) {
        FMLModContainer container = context.getContainer();
        CentralKitchenConfigs.register(container);

        CckFluidEntries.register();

        AutomaticModLoadSubscriber.load(container, CentralKitchen.class);

        IEventBus modBus = context.getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        CckCreativeModeTab.register(modBus);
        REGISTRATE.registerEventListeners(modBus);
        RECIPE_TYPE_REGISTER.register(modBus);
        RECIPE_SERIALIZER_REGISTER.register(modBus);
        CraftingHelper.register(new ConfigBoolCondition.Serializer());
        CraftingHelper.register(new ConfigListCondition.Serializer());
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(OpenEndedPipeEffects::register);
    }

    public void clientSetup(FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new CCKPonderPlugin());
    }

    public static ResourceLocation genRL(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
