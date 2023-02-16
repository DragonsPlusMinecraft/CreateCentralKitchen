package plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;

import java.util.function.Function;

public class MdArmInteractionPointTypes {
    public static final CopperPotPoint.Type STOVE = register("copper_pot", CopperPotPoint.Type::new);

    private static <T extends ArmInteractionPointType> T register(String id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(CentralKitchen.genRL(id));
        ArmInteractionPointType.register(type);
        return type;
    }
    
    public static void register() {}
    
}