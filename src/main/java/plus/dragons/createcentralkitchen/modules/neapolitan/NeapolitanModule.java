package plus.dragons.createcentralkitchen.modules.neapolitan;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.core.modules.BaseModule;
import plus.dragons.createcentralkitchen.core.modules.Module;
import plus.dragons.createcentralkitchen.modules.neapolitan.entry.NeapolitanModuleFluids;
import plus.dragons.createcentralkitchen.modules.neapolitan.entry.NeapolitanModuleItems;

@Module(id = "neapolitan", dependencies = "neapolitan")
public class NeapolitanModule extends BaseModule {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "neapolitan";
    
    public NeapolitanModule() {
        super(() -> Client::new);
    }
    
    protected void registerEntries() {
        NeapolitanModuleItems.register();
        NeapolitanModuleFluids.register();
    }
    
    protected void registerModEvents(IEventBus modBus) {
    
    }
    
    protected void registerForgeEvents(IEventBus forgeBus) {
        
    }
    
    public void setup(final FMLCommonSetupEvent event) {
        
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }
    
    public static class Client extends BaseModule.Client {
        
    }
    
}
