package plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdBlocks;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdItems;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.content.BasketScenes;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.content.BlazeStoveScenes;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FdPonderIndex {
    
    private static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CentralKitchen.ID);
    
    public static void register() {
        HELPER.forComponents(FdItems.COOKING_GUIDE)
                .addStoryBoard("blaze_stove/intro", BlazeStoveScenes::intro, FdPonderTag.FARMERS_DELIGHT)
                .addStoryBoard("blaze_stove/processing", BlazeStoveScenes::processing, FdPonderTag.FARMERS_DELIGHT);
        HELPER.forComponents(new ItemProviderEntry<>(CentralKitchen.REGISTRATE, ModItems.BASKET))
                .addStoryBoard("basket", BasketScenes::intro, FdPonderTag.FARMERS_DELIGHT);
    }

    public static void registerTags() {
        PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS)
            .add(ModItems.STOVE.get())
            .add(ModItems.COOKING_POT.get())
            .add(ModItems.SKILLET.get())
            .add(ModItems.BASKET.get())
            .add(ModItems.CUTTING_BOARD.get())
            .add(FdBlocks.BLAZE_STOVE.get());
        PonderRegistry.TAGS.forTag(FdPonderTag.FARMERS_DELIGHT)
                .add(FdItems.COOKING_GUIDE)
                .add(AllBlocks.MECHANICAL_ARM.get())
                .add(ModBlocks.COOKING_POT.get())
                .add(ModBlocks.SKILLET.get())
                .add(ModBlocks.STOVE.get())
                .add(ModBlocks.BASKET.get())
                .add(ModBlocks.CUTTING_BOARD.get());
    }

}
