package plus.dragons.createfarmersautomation.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createfarmersautomation.FarmersAutomation;
import plus.dragons.createfarmersautomation.entry.CfaBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Function;

public class CfaArmInteractionPointTypes {
    public static final StovePoint.Type STOVE = register("stove", StovePoint.Type::new);
    public static final CookingPotPoint.Type COOKING_POT = register("cooking_pot", CookingPotPoint.Type::new);
    public static final SkilletPoint.Type SKILLET = register("skillet", SkilletPoint.Type::new);
    public static final BlazeStovePoint.Type BLAZE_STOVE = register("blaze_stove", BlazeStovePoint.Type::new);
    
    private static <T extends ArmInteractionPointType> T register(String id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(FarmersAutomation.genRL(id));
        ArmInteractionPointType.register(type);
        return type;
    }
    
    public static void register() {}
    
    public static void registerPonderTags() {
        PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS)
            .add(ModItems.STOVE.get())
            .add(ModItems.COOKING_POT.get())
            .add(ModItems.SKILLET.get());
            //.add(CfaBlocks.BLAZE_STOVE.get()); TODO need a transform item
    }
    
}