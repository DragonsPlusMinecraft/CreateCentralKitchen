package plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.block.mechanicalArm;

import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm.CentralKitchenArmInteractionPointType;
import plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm.CentralKitchenArmInteractionPointTypes;

import java.util.function.Function;

public class FarmersRespiteModuleArmInteractionPointTypes {
    public static final KettlePoint.Type STOVE = register("kettle", KettlePoint.Type::new);

    private static <T extends CentralKitchenArmInteractionPointType> T register(String id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(CentralKitchen.genRL(id));
        CentralKitchenArmInteractionPointTypes.register(id, type);
        return type;
    }
    
    public static void register() {}
    
}