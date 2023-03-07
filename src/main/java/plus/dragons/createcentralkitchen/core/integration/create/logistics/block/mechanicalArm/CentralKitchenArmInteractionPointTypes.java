package plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.config.CentralKitchenConfigs;

import java.util.LinkedHashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = CentralKitchen.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CentralKitchenArmInteractionPointTypes {
    private static final Map<String, CentralKitchenArmInteractionPointType> REGISTRY =
        new LinkedHashMap<>();
    
    public static void register(String id, CentralKitchenArmInteractionPointType type) {
        REGISTRY.put(id, type);
    }
    
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        for (var entry : REGISTRY.entrySet()) {
            if (CentralKitchenConfigs.COMMON.automation.armInteractionPointBlackList.get().contains(entry.getKey()))
                continue;
            ArmInteractionPointType.register(entry.getValue());
        }
    }
    
    @SubscribeEvent
    public static void registerPonderTags(FMLClientSetupEvent event) {
        for (var entry : REGISTRY.entrySet()) {
            if (CentralKitchenConfigs.COMMON.automation.armInteractionPointBlackList.get().contains(entry.getKey()))
                continue;
            entry.getValue().registerPonderTag();
        }
    }
    
}
