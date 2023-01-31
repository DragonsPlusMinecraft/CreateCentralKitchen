package plus.dragons.createcentralkitchen;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm.CckArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.entry.CckBlockPartials;
import plus.dragons.createcentralkitchen.foundation.ponder.content.CfaPonderIndex;

public class FarmersAutomationClient {
    
    public FarmersAutomationClient() {
        CckBlockPartials.register();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(FarmersAutomationClient::clientInit);
        modEventBus.addListener(FarmersAutomationClient::setup);
    }
    
    public static void clientInit(final FMLClientSetupEvent event) {
        event.enqueueWork(CckArmInteractionPointTypes::registerPonderTags);
    }

    public static void setup(final FMLClientSetupEvent event) {
        CfaPonderIndex.register();
        CfaPonderIndex.registerTags();
    }
    
}