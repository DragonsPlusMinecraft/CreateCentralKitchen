package plus.dragons.createcentralkitchen.foundation.ponder.tag;

import com.farmersrespite.core.registry.FRItems;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import plus.dragons.createcentralkitchen.entry.item.FRItemEntries;

public class FRPonderTags {
    
    public static void register() {
        PonderRegistry.TAGS.forTag(FDPonderTags.COOKING)
            .add(FRItemEntries.BREWING_GUIDE)
            .add(FRItems.KETTLE.get());
    }
    
}
