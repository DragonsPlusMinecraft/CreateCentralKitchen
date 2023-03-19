package plus.dragons.createcentralkitchen.modules.neapolitan;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.core.modules.CentralKitchenModule;
import plus.dragons.createcentralkitchen.core.modules.CentralKitchenModuleBase;
import plus.dragons.createcentralkitchen.modules.neapolitan.entry.NeapolitanModuleFluids;
import plus.dragons.createcentralkitchen.modules.neapolitan.entry.NeapolitanModuleItems;

@CentralKitchenModule(id = "neapolitan", dependencies = "neapolitan")
public class NeapolitanModule extends CentralKitchenModuleBase {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "neapolitan";
    
    public NeapolitanModule() {
        super(null);
    }
    
    protected void registerEntries() {
        NeapolitanModuleItems.register();
        NeapolitanModuleFluids.register();
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }
    
}
