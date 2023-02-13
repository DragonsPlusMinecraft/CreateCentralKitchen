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
import plus.dragons.createcentralkitchen.common.modules.ModModule;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.block.mechanicalArm.FrArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrCapabilities;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrItems;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrMenuTypes;

@ModModule(id = "farmersrespite", dependencies = {"farmersdelight", "farmersrespite"}, priority = 1)
public class FarmersRespiteModule {
    private static final Logger LOGGER = LogUtils.getLogger();
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
        FrItems.register();
        FrMenuTypes.register();
        //FrFluids.register();
        FrArmInteractionPointTypes.register();
    }
    
    private void registerModEvents(IEventBus modBus) {
        modBus.addListener(FrCapabilities::register);
        modBus.addListener(this::setup);
    }
    
    private void registerForgeEvents(IEventBus forgeBus) {
        forgeBus.addListener(FrItems::fillCreateItemGroup);
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
        
        }
        
    }
    
}
