package plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;

import java.util.function.Function;

public class FrArmInteractionPointTypes {
    public static final KettlePoint.Type STOVE = register("kettle", KettlePoint.Type::new);

    private static <T extends ArmInteractionPointType> T register(String id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(CentralKitchen.genRL(id));
        ArmInteractionPointType.register(type);
        return type;
    }
    
    public static void register() {}
    
}