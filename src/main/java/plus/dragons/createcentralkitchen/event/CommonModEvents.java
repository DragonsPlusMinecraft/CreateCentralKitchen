package plus.dragons.createcentralkitchen.event;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.CookingGuide;

@Mod.EventBusSubscriber(modid = CentralKitchen.ID)
public class CommonModEvents {
    
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(CookingGuide.class);
    }
    
}
