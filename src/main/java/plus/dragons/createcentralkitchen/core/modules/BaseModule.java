package plus.dragons.createcentralkitchen.core.modules;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BaseModule {
    
    public BaseModule(@Nullable Supplier<DistExecutor.SafeRunnable> clientInit) {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        
        registerEntries();
        modBus.addListener(this::setup);
        registerModEvents(modBus);
        registerForgeEvents(forgeBus);
        
        if (clientInit != null)
            DistExecutor.safeRunWhenOn(Dist.CLIENT, clientInit);
    }
    
    protected void registerEntries() {
    
    }
    
    protected void registerModEvents(IEventBus modBus) {
    
    }
    
    protected void registerForgeEvents(IEventBus forgeBus) {
    
    }
    
    protected void setup(final FMLCommonSetupEvent event) {
    
    }
    
    public static class Client {
        
        public Client() {
            IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
            IEventBus forgeBus = MinecraftForge.EVENT_BUS;
            
            registerEntries();
            modBus.addListener(this::setup);
            registerModEvents(modBus);
            registerForgeEvents(forgeBus);
        }
        
        protected void registerEntries() {
        
        }
    
        protected void registerModEvents(IEventBus modBus) {
        
        }
    
        protected void registerForgeEvents(IEventBus forgeBus) {
        
        }
    
        protected void setup(final FMLClientSetupEvent event) {
        
        }
        
    }
    
}
