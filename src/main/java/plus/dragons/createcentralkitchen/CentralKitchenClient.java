package plus.dragons.createcentralkitchen;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm.CckArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.entry.CckBlockPartials;
import plus.dragons.createcentralkitchen.foundation.ponder.content.CckPonderIndex;

public class CentralKitchenClient {
    
    public CentralKitchenClient() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        //Have to do this here because flywheel lied about the init timing ;(
        //Things won't work if you try init PartialModels in FMLClientSetupEvent
        CckBlockPartials.register();
        modEventBus.addListener(CentralKitchenClient::setup);
    }


    public static void setup(final FMLClientSetupEvent event) {
        event.enqueueWork(()->{
            CckPonderIndex.register();
            CckPonderIndex.registerTags();
            CckArmInteractionPointTypes.registerPonderTags();
        });

    }
    
}