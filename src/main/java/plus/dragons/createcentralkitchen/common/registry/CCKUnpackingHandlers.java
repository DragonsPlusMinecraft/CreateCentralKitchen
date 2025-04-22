package plus.dragons.createcentralkitchen.common.registry;

import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import plus.dragons.createcentralkitchen.common.packager.CookingPotUnpackingHandler;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class CCKUnpackingHandlers {
    public static void register() {
        UnpackingHandler.REGISTRY.register(ModBlocks.COOKING_POT.get(), new CookingPotUnpackingHandler());
    }
}
