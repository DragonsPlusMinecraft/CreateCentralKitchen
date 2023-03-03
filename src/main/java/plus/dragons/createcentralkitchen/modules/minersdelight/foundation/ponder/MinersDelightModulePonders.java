package plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder;

import com.sammy.minersdelight.setup.MDBlocks;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FarmersDelightModulePonders;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MinersDelightModuleItems;

public class MinersDelightModulePonders {
    
    public static void register() {
        FarmersDelightModulePonders.BLAZE_STOVE_INTRO
            .addComponent(MinersDelightModuleItems.MINERS_COOKING_GUIDE);
        FarmersDelightModulePonders.BLAZE_STOVE_CONFIGURE
            .addComponent(MinersDelightModuleItems.MINERS_COOKING_GUIDE)
            .addComponent(MDBlocks.COPPER_POT.getId());
        FarmersDelightModulePonders.BLAZE_STOVE_HEAT_SOURCE
            .addComponent(MinersDelightModuleItems.MINERS_COOKING_GUIDE)
            .addComponent(MDBlocks.COPPER_POT.getId());
    }
    
}
