package plus.dragons.createcentralkitchen.foundation.ponder.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import plus.dragons.createcentralkitchen.foundation.ponder.CentralKitchenPonders;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderEntry;
import plus.dragons.createcentralkitchen.foundation.ponder.scene.BasketScenes;
import plus.dragons.createcentralkitchen.foundation.ponder.scene.BlazeStoveScenes;
import plus.dragons.createcentralkitchen.foundation.ponder.tag.FDPonderTags;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FDPonderEntries {

    public static final PonderEntry BASKET_INTRO =
        CentralKitchenPonders.create("basket/intro", BasketScenes::intro);

    public static final PonderEntry BASKET_BELT_INTERACTION =
        CentralKitchenPonders.create("basket/belt_interaction", BasketScenes::belt_interaction);

    public static final PonderEntry BLAZE_STOVE_INTRO =
        CentralKitchenPonders.create("blaze_stove/intro", BlazeStoveScenes::intro);

    public static final PonderEntry BLAZE_STOVE_CONFIGURE =
        CentralKitchenPonders.create("blaze_stove/automation", BlazeStoveScenes::automation);

    public static final PonderEntry BLAZE_STOVE_HEAT_SOURCE =
        CentralKitchenPonders.create("blaze_stove/heat_source", BlazeStoveScenes::heat_source);

    public static void register() {
        //ok
        BASKET_INTRO
            .addComponent(ModItems.BASKET)
            .addTag(AllCreatePonderTags.LOGISTICS);
        //ok
        BASKET_BELT_INTERACTION
            .addComponent(ModItems.BASKET)
            .addTag(AllCreatePonderTags.LOGISTICS);
        //ok
        BLAZE_STOVE_INTRO
            .addComponent(AllBlocks.BLAZE_BURNER, FDItemEntries.COOKING_GUIDE)
            .addTag(FDPonderTags.COOKING);
        //ok
        BLAZE_STOVE_CONFIGURE
            .addComponent(AllBlocks.BLAZE_BURNER, FDItemEntries.COOKING_GUIDE)
            .addComponent(ModItems.COOKING_POT)
            .addTag(FDPonderTags.COOKING);
        BLAZE_STOVE_HEAT_SOURCE
            .addComponent(AllBlocks.BLAZE_BURNER, FDItemEntries.COOKING_GUIDE)
            .addComponent(ModItems.COOKING_POT, ModItems.SKILLET)
            .addTag(FDPonderTags.COOKING);
    }

}
