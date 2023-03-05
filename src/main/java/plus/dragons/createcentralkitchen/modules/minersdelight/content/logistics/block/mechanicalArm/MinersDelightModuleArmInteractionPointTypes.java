package plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.block.mechanicalArm;

import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm.CentralKitchenArmInteractionPointType;
import plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm.CentralKitchenArmInteractionPointTypes;

import java.util.function.Function;

public class MinersDelightModuleArmInteractionPointTypes {
    public static final CopperPotPoint.Type STOVE = register("copper_pot", CopperPotPoint.Type::new);

    private static <T extends CentralKitchenArmInteractionPointType> T register(String id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(CentralKitchen.genRL(id));
        CentralKitchenArmInteractionPointTypes.register(id, type);
        return type;
    }
    
    public static void register() {}
    
}