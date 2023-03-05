package plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FarmersDelightModuleItems;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class FarmersDelightModulePonderTags {
    public static final PonderTag COOKING = create("cooking_automation")
        .item(FarmersDelightModuleItems.COOKING_GUIDE.get(), true, false).addToIndex();

    private static PonderTag create(String id) {
        return new PonderTag(CentralKitchen.genRL(id));
    }
    
    public static void register() {
        PonderRegistry.TAGS.forTag(COOKING)
            .add(AllBlocks.BLAZE_BURNER)
            .add(FarmersDelightModuleItems.COOKING_GUIDE)
            .add(ModBlocks.COOKING_POT.get());
    }
    
}
