package plus.dragons.createcentralkitchen.modules.farmersrespite;

import com.farmersrespite.core.registry.FRBlockEntityTypes;
import com.mojang.logging.LogUtils;
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
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.block.mechanicalArm.FarmersRespiteModuleArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleCapabilities;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleFluids;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleItems;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleMenuTypes;
import plus.dragons.createcentralkitchen.modules.farmersrespite.foundation.ponder.FarmersRespiteModulePonderTags;
import plus.dragons.createcentralkitchen.modules.farmersrespite.foundation.ponder.FarmersRespiteModulePonders;

@ModModule(id = "farmersrespite", dependencies = {"farmersdelight", "farmersrespite"}, priority = 1)
public class FarmersRespiteModule {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "farmersrespite";
    
    public FarmersRespiteModule() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        
        registerEntries();
        registerModEvents(modBus);
        registerForgeEvents(forgeBus);
        
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Client::new);
    }
    
    private void registerEntries() {
        FarmersRespiteModuleItems.register();
        FarmersRespiteModuleMenuTypes.register();
        FarmersRespiteModuleFluids.register();
        FarmersRespiteModuleArmInteractionPointTypes.register();
    }
    
    private void registerModEvents(IEventBus modBus) {
        modBus.addListener(FarmersRespiteModuleCapabilities::register);
        modBus.addListener(this::setup);
    }
    
    private void registerForgeEvents(IEventBus forgeBus) {
        forgeBus.addListener(FarmersRespiteModuleItems::fillCreateItemGroup);
    }
    
    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BlazeStoveBlockEntity.registerBoostingCooker(FRBlockEntityTypes.KETTLE.get());
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
            event.enqueueWork(FarmersRespiteModulePonderTags::register);
            event.enqueueWork(FarmersRespiteModulePonders::register);
        }
        
    }
    
}
