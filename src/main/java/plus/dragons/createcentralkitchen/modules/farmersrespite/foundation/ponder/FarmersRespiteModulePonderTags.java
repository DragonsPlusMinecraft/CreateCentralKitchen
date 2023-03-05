package plus.dragons.createcentralkitchen.modules.farmersrespite.foundation.ponder;

import com.farmersrespite.core.registry.FRItems;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FarmersDelightModulePonderTags;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleItems;

public class FarmersRespiteModulePonderTags {
    
    public static void register() {
        PonderRegistry.TAGS.forTag(FarmersDelightModulePonderTags.COOKING)
            .add(FarmersRespiteModuleItems.BREWING_GUIDE)
            .add(FRItems.KETTLE.get());
    }
    
}
