package plus.dragons.createcentralkitchen.foundation.ponder;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public abstract class PonderArmInteractionPointType extends ArmInteractionPointType {
    
    public PonderArmInteractionPointType(ResourceLocation id) {
        super();
    }
    
    public abstract void addToPonderTag(PonderTagRegistrationHelper<ResourceLocation> helper);
    
}
