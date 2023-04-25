package plus.dragons.createcentralkitchen.foundation.ponder.tag;

import com.sammy.minersdelight.setup.MDBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import plus.dragons.createcentralkitchen.entry.item.MDItemEntries;

public class MDPonderTags {

    public static void register() {
        PonderRegistry.TAGS.forTag(FDPonderTags.COOKING)
            .add(MDItemEntries.MINERS_COOKING_GUIDE.get())
            .add(MDBlocks.COPPER_POT.get());
    }

}
