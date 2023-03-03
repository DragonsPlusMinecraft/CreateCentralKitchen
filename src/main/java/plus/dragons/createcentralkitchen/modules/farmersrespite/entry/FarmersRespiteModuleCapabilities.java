package plus.dragons.createcentralkitchen.modules.farmersrespite.entry;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuide;

import java.util.ArrayList;
import java.util.List;

public class FarmersRespiteModuleCapabilities {
    private static final List<Class<?>> CAPABILITIES = new ArrayList<>();
    
    public static final Capability<BrewingGuide> BREWING_GUIDE =
        register(BrewingGuide.class, new CapabilityToken<>() {});
    
    private static <T> Capability<T> register(Class<T> clazz, CapabilityToken<T> token) {
        CAPABILITIES.add(clazz);
        return CapabilityManager.get(token);
    }
    
    public static void register(RegisterCapabilitiesEvent event) {
        CAPABILITIES.forEach(event::register);
    }
    
}
