package plus.dragons.createcentralkitchen.modules.farmersrespite.foundation.ponder;

import com.farmersrespite.core.registry.FRItems;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FarmersDelightModulePonders;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleItems;

public class FarmersRespiteModulePonders {
    
    public static void register() {
        FarmersDelightModulePonders.BLAZE_STOVE_INTRO
            .addComponent(FarmersRespiteModuleItems.BREWING_GUIDE);
        FarmersDelightModulePonders.BLAZE_STOVE_CONFIGURE
            .addComponent(FarmersRespiteModuleItems.BREWING_GUIDE)
            .addComponent(FRItems.KETTLE);
        FarmersDelightModulePonders.BLAZE_STOVE_HEAT_SOURCE
            .addComponent(FarmersRespiteModuleItems.BREWING_GUIDE)
            .addComponent(FRItems.KETTLE);
    }
    
}
