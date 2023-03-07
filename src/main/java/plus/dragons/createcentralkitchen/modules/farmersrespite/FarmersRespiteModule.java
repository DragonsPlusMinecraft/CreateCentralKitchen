package plus.dragons.createcentralkitchen.modules.farmersrespite;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.core.modules.CentralKitchenModule;
import plus.dragons.createcentralkitchen.core.modules.CentralKitchenModuleBase;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.block.mechanicalArm.FarmersRespiteModuleArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleCapabilities;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleFluids;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleItems;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleMenuTypes;
import plus.dragons.createcentralkitchen.modules.farmersrespite.foundation.ponder.FarmersRespiteModulePonderTags;
import plus.dragons.createcentralkitchen.modules.farmersrespite.foundation.ponder.FarmersRespiteModulePonders;

@CentralKitchenModule(id = "farmersrespite", dependencies = {"farmersdelight", "farmersrespite"}, priority = 1)
public class FarmersRespiteModule extends CentralKitchenModuleBase {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "farmersrespite";
    
    public FarmersRespiteModule() {
        super(() -> Client::new);
    }
    
    @Override
    protected void registerEntries() {
        FarmersRespiteModuleItems.register();
        FarmersRespiteModuleMenuTypes.register();
        FarmersRespiteModuleFluids.register();
        FarmersRespiteModuleArmInteractionPointTypes.register();
    }
    
    @Override
    protected void registerModEvents(IEventBus modBus) {
        modBus.addListener(FarmersRespiteModuleCapabilities::register);
    }
    
    @Override
    protected void registerForgeEvents(IEventBus forgeBus) {
        forgeBus.addListener(FarmersRespiteModuleItems::fillCreateItemGroup);
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }
    
    public static class Client extends CentralKitchenModuleBase.Client {
        
        @Override
        public void setup(final FMLClientSetupEvent event) {
            event.enqueueWork(FarmersRespiteModulePonderTags::register);
            event.enqueueWork(FarmersRespiteModulePonders::register);
        }
        
    }
    
}
