package plus.dragons.createcentralkitchen.foundation.ponder.tag;

import com.simibubi.create.foundation.ponder.PonderRegistry;
import plus.dragons.createcentralkitchen.entry.item.FRItemEntries;
import umpaz.farmersrespite.common.registry.FRItems;

public class FRPonderTags {
    
    public static void register() {
        PonderRegistry.TAGS.forTag(FDPonderTags.COOKING)
            .add(FRItemEntries.BREWING_GUIDE)
            .add(FRItems.KETTLE.get());
    }
    
}
