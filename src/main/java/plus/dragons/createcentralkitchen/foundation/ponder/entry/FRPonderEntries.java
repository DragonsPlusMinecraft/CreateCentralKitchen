package plus.dragons.createcentralkitchen.foundation.ponder.entry;

import com.farmersrespite.core.registry.FRItems;
import plus.dragons.createcentralkitchen.entry.item.FRItemEntries;

public class FRPonderEntries {
    
    public static void register() {
        FDPonderEntries.BLAZE_STOVE_INTRO
            .addComponent(FRItemEntries.BREWING_GUIDE);
        FDPonderEntries.BLAZE_STOVE_CONFIGURE
            .addComponent(FRItemEntries.BREWING_GUIDE)
            .addComponent(FRItems.KETTLE);
        FDPonderEntries.BLAZE_STOVE_HEAT_SOURCE
            .addComponent(FRItemEntries.BREWING_GUIDE)
            .addComponent(FRItems.KETTLE);
    }
    
}
