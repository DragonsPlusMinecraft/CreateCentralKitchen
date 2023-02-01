package plus.dragons.createcentralkitchen.foundation.ponder.content;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.CckItems;
import plus.dragons.createcentralkitchen.foundation.ponder.CfaPonderTag;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class CfaPonderIndex {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CentralKitchen.ID);

    public static void register() {
        HELPER.forComponents(CckItems.COOKING_GUIDE)
                .addStoryBoard("transform",CookScenes::transformBlazeBurner, CfaPonderTag.FARMERS_DELIGHT)
                .addStoryBoard("stove",CookScenes::stoveAndPot, CfaPonderTag.FARMERS_DELIGHT);
    }

    public static void registerTags() {
        PonderRegistry.TAGS.forTag(CfaPonderTag.FARMERS_DELIGHT)
                .add(CckItems.COOKING_GUIDE)
                .add(AllBlocks.MECHANICAL_ARM.get())
                .add(ModBlocks.COOKING_POT.get())
                .add(ModBlocks.SKILLET.get())
                .add(ModBlocks.STOVE.get())
                .add(ModBlocks.BASKET.get())
                .add(ModBlocks.CUTTING_BOARD.get());
    }

}
