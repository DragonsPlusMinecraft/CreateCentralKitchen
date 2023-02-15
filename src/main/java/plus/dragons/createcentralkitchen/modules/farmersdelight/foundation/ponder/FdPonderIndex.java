package plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.PonderTag;
import plus.dragons.createcentralkitchen.core.ponder.PonderRegistryObject;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdItems;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.content.BasketScenes;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.content.BlazeStoveScenes;
import vectorwing.farmersdelight.common.registry.ModItems;

import static plus.dragons.createcentralkitchen.CentralKitchen.PONDER_REGISTER;

public class FdPonderIndex {
    //Basket
    public static final PonderRegistryObject BASKET_INTRO =
        PONDER_REGISTER.create("basket/intro", BasketScenes::intro);
    public static final PonderRegistryObject BASKET_BELT_INTERACTION =
        PONDER_REGISTER.create("basket/intro", BasketScenes::intro);
    //Blaze Stove
    public static final PonderRegistryObject BLAZE_STOVE_INTRO =
        PONDER_REGISTER.create("blaze_stove/intro", BlazeStoveScenes::intro);
    public static final PonderRegistryObject BLAZE_STOVE_CONFIGURE =
        PONDER_REGISTER.create("blaze_stove/configure", BlazeStoveScenes::configure);
    public static final PonderRegistryObject BLAZE_STOVE_HEAT_SOURCE =
        PONDER_REGISTER.create("blaze_stove/heat_source", BlazeStoveScenes::heat_source);
    
    public static void register() {
        BASKET_INTRO
            .addComponent(ModItems.BASKET)
            .addTag(PonderTag.LOGISTICS);
        BASKET_BELT_INTERACTION
            .addComponent(ModItems.BASKET)
            .addTag(PonderTag.LOGISTICS);
        BLAZE_STOVE_INTRO
            .addComponent(AllBlocks.BLAZE_BURNER, FdItems.COOKING_GUIDE)
            .addTag(FdPonderTag.COOKING);
        BLAZE_STOVE_CONFIGURE
            .addComponent(AllBlocks.BLAZE_BURNER, FdItems.COOKING_GUIDE)
            .addComponent(ModItems.COOKING_POT)
            .addTag(FdPonderTag.COOKING);
        BLAZE_STOVE_HEAT_SOURCE
            .addComponent(AllBlocks.BLAZE_BURNER, FdItems.COOKING_GUIDE)
            .addComponent(ModItems.COOKING_POT, ModItems.SKILLET)
            .addTag(FdPonderTag.COOKING);
    }
    
}
