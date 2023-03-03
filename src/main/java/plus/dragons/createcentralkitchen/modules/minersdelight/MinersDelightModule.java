package plus.dragons.createcentralkitchen.modules.minersdelight;

import com.mojang.logging.LogUtils;
import com.sammy.minersdelight.setup.MDBlockEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.core.modules.ModModule;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.block.mechanicalArm.MinersDelightModuleArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleCapabilities;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleItems;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleMenuTypes;
import plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder.MinersDelightModulePonderTags;
import plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder.MinersDelightModulePonders;

@ModModule(id = "miners_delight", dependencies = {"farmersdelight", "miners_delight"}, priority = 1)
public class MinersDelightModule {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "miners_delight";
    
    public MinersDelightModule() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        
        registerEntries();
        registerModEvents(modBus);
        registerForgeEvents(forgeBus);
        
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Client::new);
    }
    
    private void registerEntries() {
        MinersDelightModuleItems.register();
        MinersDelightModuleMenuTypes.register();
        //MdFluids.register();
        MinersDelightModuleArmInteractionPointTypes.register();
    }
    
    private void registerModEvents(IEventBus modBus) {
        modBus.addListener(MinersDelightModuleCapabilities::register);
        modBus.addListener(this::setup);
    }
    
    private void registerForgeEvents(IEventBus forgeBus) {
        forgeBus.addListener(MinersDelightModuleItems::fillCreateItemGroup);
    }
    
    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BlazeStoveBlockEntity.registerBoostingCooker(MDBlockEntities.COPPER_POT.get());
        });
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }
    
    public static class Client {
        
        public Client() {
            IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
            IEventBus forgeBus = MinecraftForge.EVENT_BUS;
            
            registerEntries();
            registerModEvents(modBus);
            registerForgeEvents(forgeBus);
        }
        
        private void registerEntries() {
        
        }
    
        private void registerModEvents(IEventBus modBus) {
            modBus.addListener(this::setup);
        }
    
        private void registerForgeEvents(IEventBus forgeBus) {
        
        }
    
        public void setup(final FMLClientSetupEvent event) {
            event.enqueueWork(MinersDelightModulePonderTags::register);
            event.enqueueWork(MinersDelightModulePonders::register);
        }
        
    }
    
}
