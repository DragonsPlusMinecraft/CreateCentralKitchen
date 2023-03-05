package plus.dragons.createcentralkitchen.modules.minersdelight;

import com.mojang.logging.LogUtils;
import com.sammy.minersdelight.setup.MDBlockEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.core.modules.CentralKitchenModule;
import plus.dragons.createcentralkitchen.core.modules.CentralKitchenModuleBase;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.block.mechanicalArm.MinersDelightModuleArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleCapabilities;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleItems;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleMenuTypes;
import plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder.MinersDelightModulePonderTags;
import plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder.MinersDelightModulePonders;

@CentralKitchenModule(id = "miners_delight", dependencies = {"farmersdelight", "miners_delight"}, priority = 1)
public class MinersDelightModule extends CentralKitchenModuleBase {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "miners_delight";
    
    public MinersDelightModule() {
        super(() -> Client::new);
    }
    
    @Override
    protected void registerEntries() {
        MinersDelightModuleItems.register();
        MinersDelightModuleMenuTypes.register();
        MinersDelightModuleArmInteractionPointTypes.register();
    }
    
    @Override
    protected void registerModEvents(IEventBus modBus) {
        modBus.addListener(MinersDelightModuleCapabilities::register);
    }
    
    @Override
    protected void registerForgeEvents(IEventBus forgeBus) {
        forgeBus.addListener(MinersDelightModuleItems::fillCreateItemGroup);
    }
    
    @Override
    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BlazeStoveBlockEntity.registerBoostingCooker(MDBlockEntities.COPPER_POT.get());
        });
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }
    
    public static class Client extends CentralKitchenModuleBase.Client {
        
        @Override
        public void setup(final FMLClientSetupEvent event) {
            event.enqueueWork(MinersDelightModulePonderTags::register);
            event.enqueueWork(MinersDelightModulePonders::register);
        }
        
    }
    
}
