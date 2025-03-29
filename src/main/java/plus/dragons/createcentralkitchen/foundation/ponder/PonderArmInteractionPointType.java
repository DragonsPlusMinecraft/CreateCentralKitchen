package plus.dragons.createcentralkitchen.foundation.ponder;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public abstract class PonderArmInteractionPointType extends ArmInteractionPointType {
    private final ResourceLocation id;
    public ResourceLocation getID(){
        return id;
    }
    public PonderArmInteractionPointType(ResourceLocation id) {
        super();
        this.id = id;
    }
    
    public abstract void addToPonderTag(PonderTagRegistrationHelper<ResourceLocation> helper);
    
}
