package plus.dragons.createcentralkitchen.modules.farmersrespite.foundation.ponder;

import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FdPonderIndex;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrItems;
import umpaz.farmersrespite.common.registry.FRItems;

public class FrPonderIndex {
    
    public static void register() {
        FdPonderIndex.BLAZE_STOVE_INTRO
            .addComponent(FrItems.BREWING_GUIDE);
        FdPonderIndex.BLAZE_STOVE_CONFIGURE
            .addComponent(FrItems.BREWING_GUIDE)
            .addComponent(FRItems.KETTLE);
        FdPonderIndex.BLAZE_STOVE_HEAT_SOURCE
            .addComponent(FrItems.BREWING_GUIDE)
            .addComponent(FRItems.KETTLE);
    }
    
}
