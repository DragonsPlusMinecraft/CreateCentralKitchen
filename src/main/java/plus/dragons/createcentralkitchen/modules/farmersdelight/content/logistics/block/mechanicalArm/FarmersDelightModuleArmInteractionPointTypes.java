package plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.block.mechanicalArm;

import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm.CentralKitchenArmInteractionPointType;
import plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm.CentralKitchenArmInteractionPointTypes;

import java.util.function.Function;

public class FarmersDelightModuleArmInteractionPointTypes {
    public static final StovePoint.Type STOVE = register("stove", StovePoint.Type::new);
    public static final BlazeStovePoint.Type BLAZE_STOVE = register("blaze_stove", BlazeStovePoint.Type::new);
    public static final CookingPotPoint.Type COOKING_POT = register("cooking_pot", CookingPotPoint.Type::new);
    public static final SkilletPoint.Type SKILLET = register("skillet", SkilletPoint.Type::new);
    public static final CuttingBoardPoint.Type CUTTING_BOARD = register("cutting_board", CuttingBoardPoint.Type::new);
    public static final BasketPoint.Type BASKET = register("basket", BasketPoint.Type::new);

    private static <T extends CentralKitchenArmInteractionPointType> T register(String id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(CentralKitchen.genRL(id));
        CentralKitchenArmInteractionPointTypes.register(id, type);
        return type;
    }
    
    public static void register() {}
    
}