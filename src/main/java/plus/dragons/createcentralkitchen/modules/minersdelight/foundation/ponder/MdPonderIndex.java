package plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder;

import com.sammy.minersdelight.setup.MDBlocks;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FdPonderIndex;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrItems;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MdItems;

public class MdPonderIndex {
    
    public static void register() {
        FdPonderIndex.BLAZE_STOVE_INTRO
            .addComponent(MdItems.MINERS_COOKING_GUIDE);
        FdPonderIndex.BLAZE_STOVE_CONFIGURE
            .addComponent(MdItems.MINERS_COOKING_GUIDE)
            .addComponent(MDBlocks.COPPER_POT.getId());
        FdPonderIndex.BLAZE_STOVE_HEAT_SOURCE
            .addComponent(FrItems.BREWING_GUIDE)
            .addComponent(MDBlocks.COPPER_POT.getId());
    }
    
}
