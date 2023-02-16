package plus.dragons.createcentralkitchen.modules.minersdelight.foundation.ponder;

import com.sammy.minersdelight.setup.MDBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FdPonderTag;
import plus.dragons.createcentralkitchen.modules.minersdelight.entry.MdItems;

public class MdPonderTag {
    
    public static void register() {
        PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS)
            .add(MDBlocks.COPPER_POT.get());
        PonderRegistry.TAGS.forTag(FdPonderTag.COOKING)
            .add(MdItems.MINERS_COOKING_GUIDE.get())
            .add(MDBlocks.COPPER_POT.get());
    }
    
}
