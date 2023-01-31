package plus.dragons.createfarmersautomation.foundation.ponder.content;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import plus.dragons.createfarmersautomation.FarmersAutomation;
import plus.dragons.createfarmersautomation.entry.CfaItems;
import plus.dragons.createfarmersautomation.foundation.ponder.CfaPonderTag;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class CfaPonderIndex {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(FarmersAutomation.ID);

    public static void register() {
        HELPER.forComponents(CfaItems.COOKING_GUIDE)
                .addStoryBoard("stove_transform",CookScenes::transformBlazeBurner, CfaPonderTag.FARMERS_AUTONMATION)
                .addStoryBoard("stove_and_pot",CookScenes::stoveAndPot, CfaPonderTag.FARMERS_AUTONMATION);
    }

    public static void registerTags() {
        PonderRegistry.TAGS.forTag(CfaPonderTag.FARMERS_AUTONMATION)
                .add(CfaItems.COOKING_GUIDE)
                .add(ModBlocks.COOKING_POT.get())
                .add(ModBlocks.SKILLET.get())
                .add(ModBlocks.STOVE.get())
                .add(ModBlocks.BASKET.get())
                .add(ModBlocks.CUTTING_BOARD.get());
    }

}
