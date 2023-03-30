package plus.dragons.createcentralkitchen.foundation.ponder.tag;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class FDPonderTags {
    
    public static final PonderTag COOKING = create("cooking_automation");
    
    private static PonderTag create(String id) {
        return new PonderTag(CentralKitchen.genRL(id));
    }
    
    public static void register() {
        COOKING.item(FDItemEntries.COOKING_GUIDE.get(), true, false).addToIndex();
        PonderRegistry.TAGS.forTag(COOKING)
            .add(AllBlocks.BLAZE_BURNER)
            .add(FDItemEntries.COOKING_GUIDE)
            .add(ModBlocks.COOKING_POT.get());
    }
    
}
