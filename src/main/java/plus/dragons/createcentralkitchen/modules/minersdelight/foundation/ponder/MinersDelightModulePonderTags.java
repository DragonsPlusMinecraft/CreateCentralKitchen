package plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder;

import com.sammy.minersdelight.setup.MDBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FarmersDelightModulePonderTags;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleItems;

public class MinersDelightModulePonderTags {
    
    public static void register() {
        PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS)
            .add(MDBlocks.COPPER_POT.get());
        PonderRegistry.TAGS.forTag(FarmersDelightModulePonderTags.COOKING)
            .add(MinersDelightModuleItems.MINERS_COOKING_GUIDE.get())
            .add(MDBlocks.COPPER_POT.get());
    }
    
}
