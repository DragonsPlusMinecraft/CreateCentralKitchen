package plus.dragons.createcentralkitchen.foundation.ponder;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public abstract class PonderArmInteractionPointType extends ArmInteractionPointType {
    
    public PonderArmInteractionPointType(ResourceLocation id) {
        super(id);
    }
    
    public abstract void addToPonderTag(Consumer<ItemLike> consumer);
    
}
