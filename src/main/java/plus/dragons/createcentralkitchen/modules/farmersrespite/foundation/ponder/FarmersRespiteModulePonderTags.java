package plus.dragons.createcentralkitchen.modules.farmersrespite.foundation.ponder;

import com.farmersrespite.core.registry.FRItems;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FarmersDelightModulePonderTags;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleItems;

public class FarmersRespiteModulePonderTags {
    
    public static void register() {
        PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS)
            .add(FRItems.KETTLE.get());
        PonderRegistry.TAGS.forTag(FarmersDelightModulePonderTags.COOKING)
            .add(FarmersRespiteModuleItems.BREWING_GUIDE)
            .add(FRItems.KETTLE.get());
    }
    
}
