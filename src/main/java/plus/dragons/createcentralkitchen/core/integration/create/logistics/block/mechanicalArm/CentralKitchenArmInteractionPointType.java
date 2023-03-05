package plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.resources.ResourceLocation;

public abstract class CentralKitchenArmInteractionPointType extends ArmInteractionPointType {
    
    public CentralKitchenArmInteractionPointType(ResourceLocation id) {
        super(id);
    }
    
    public abstract void registerPonderTag();
    
}
