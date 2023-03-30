package plus.dragons.createcentralkitchen.entry.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing.BrewingGuide;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.FR)
public class FRCapabilityEntries {
    
    public static final Capability<BrewingGuide> BREWING_GUIDE = CapabilityManager.get(new CapabilityToken<>() {});
    
    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(BrewingGuide.class);
    }
    
}
