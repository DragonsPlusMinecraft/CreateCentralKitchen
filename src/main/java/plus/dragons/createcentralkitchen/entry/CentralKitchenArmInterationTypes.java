package plus.dragons.createcentralkitchen.entry;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm.*;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.FD)
public class CentralKitchenArmInterationTypes {
    public static final List<PonderArmInteractionPointType> TYPES = new ArrayList<>();
    public static final CuttingBoardPoint.Type CUTTING_BOARD = create("cutting_board", CuttingBoardPoint.Type::new);
    public static final BasketPoint.Type BASKET = create("basket", BasketPoint.Type::new);
    public static final StovePoint.Type STOVE = create("stove", StovePoint.Type::new);
    public static final BlazeStovePoint.Type BLAZE_STOVE = create("blaze_stove", BlazeStovePoint.Type::new);
    public static final CookingPotPoint.Type COOKING_POT = create("cooking_pot", CookingPotPoint.Type::new);
    public static final SkilletPoint.Type SKILLET = create("skillet", SkilletPoint.Type::new);
    // TODO public static final KettlePoint.Type KETTLE = create("kettel", KettlePoint.Type::new);

    private static <T extends PonderArmInteractionPointType> T create(String name, Function<ResourceLocation, T> factory) {
        ResourceLocation id = CentralKitchen.genRL(name);
        return factory.apply(id);
    }

    public static void register(PonderArmInteractionPointType... types) {
        for (var type : types) {
            pRegister(type);
            TYPES.add(type);
        }
    }

    private static <T extends PonderArmInteractionPointType> void pRegister(T type) {
        Registry.register(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE, type.getID(), type);
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        register(STOVE, BLAZE_STOVE, COOKING_POT, SKILLET, CUTTING_BOARD, BASKET/*, KETTLE TODO*/);
    }

    public static void registerPonderTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        //Consumer<ItemLike> consumer = PonderRegistry.TAGS.forTag(AllCreatePonderTags.ARM_TARGETS)::add;
        for (PonderArmInteractionPointType type : TYPES)
            type.addToPonderTag(helper);
    }
}
