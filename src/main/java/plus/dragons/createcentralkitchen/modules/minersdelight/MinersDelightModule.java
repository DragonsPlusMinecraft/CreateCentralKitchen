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
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.block.mechanicalArm.FrArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MdCapabilities;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MdItems;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MdMenuTypes;
import plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder.MdPonderIndex;
import plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder.MdPonderTag;

@ModModule(id = "miners_delight", dependencies = {"farmersdelight", "miners_delight"}, priority = 1)
public class MinersDelightModule {
    private static final Logger LOGGER = LogUtils.getLogger();
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
        MdItems.register();
        MdMenuTypes.register();
        //MdFluids.register();
        FrArmInteractionPointTypes.register();
    }
    
    private void registerModEvents(IEventBus modBus) {
        modBus.addListener(MdCapabilities::register);
        modBus.addListener(this::setup);
    }
    
    private void registerForgeEvents(IEventBus forgeBus) {
        forgeBus.addListener(MdItems::fillCreateItemGroup);
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
            event.enqueueWork(MdPonderTag::register);
            event.enqueueWork(MdPonderIndex::register);
        }
        
    }
    
}
