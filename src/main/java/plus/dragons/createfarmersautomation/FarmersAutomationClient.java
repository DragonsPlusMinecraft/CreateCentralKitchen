package plus.dragons.createfarmersautomation;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import plus.dragons.createfarmersautomation.content.logistics.block.mechanicalArm.CfaArmInteractionPointTypes;
import plus.dragons.createfarmersautomation.entry.CfaBlockPartials;

public class FarmersAutomationClient {
    
    public FarmersAutomationClient() {
        CfaBlockPartials.register();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(FarmersAutomationClient::clientInit);
    }
    
    public static void clientInit(final FMLClientSetupEvent event) {
        event.enqueueWork(CfaArmInteractionPointTypes::registerPonderTags);
    }
    
}