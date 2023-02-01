package plus.dragons.createcentralkitchen;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm.CckArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.entry.*;
import plus.dragons.createcentralkitchen.foundation.data.lang.LangMerger;
import plus.dragons.createcentralkitchen.foundation.utility.SafeRegistrate;

@Mod(CentralKitchen.ID)
public class CentralKitchen {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "create_central_kitchen";
    public static final String NAME = "Create: Central Kitchen";
    
    public static final SafeRegistrate REGISTRATE = new SafeRegistrate(ID);
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTER =
        DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ID);
    
    public CentralKitchen() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        REGISTRATE.registerEventListeners(modEventBus);
        registerEntries(modEventBus);
        modEventBus.addListener(EventPriority.LOW, CentralKitchen::datagen);
        modEventBus.addListener(CentralKitchen::setup);

        registerForgeEvents(forgeEventBus);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CentralKitchenClient::new);
    }

    private void registerEntries(IEventBus modEventBus) {
        CckBlocks.register();
        CckBlockEntities.register();
        CckItems.register();
        CckContainerTypes.register();
        CckFluids.register(modEventBus);
        CckTags.register();
        SERIALIZER_REGISTER.register(modEventBus);
        TYPE_REGISTER.register(modEventBus);
        CckArmInteractionPointTypes.register();
    }

    private void registerForgeEvents(IEventBus forgeEventBus) {
        forgeEventBus.addListener(CckItems::fillCreateItemGroup);
    }

    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CckPackets.registerPackets();
        });
    }

    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }
    
    public static void datagen(final GatherDataEvent event) {
        DataGenerator datagen = event.getGenerator();
        datagen.addProvider(new LangMerger(datagen));
    }

}