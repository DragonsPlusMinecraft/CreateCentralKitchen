package plus.dragons.createcentralkitchen.modules.farmersrespite.foundation.ponder;

import com.farmersrespite.core.registry.FRItems;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FdPonderTag;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrItems;

public class FrPonderTag {
    
    public static void register() {
        PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS)
            .add(FRItems.KETTLE.get());
        PonderRegistry.TAGS.forTag(FdPonderTag.COOKING)
            .add(FrItems.BREWING_GUIDE)
            .add(FRItems.KETTLE.get());
    }
    
}
