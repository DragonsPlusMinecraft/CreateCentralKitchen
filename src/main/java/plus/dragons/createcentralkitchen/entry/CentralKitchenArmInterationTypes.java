package plus.dragons.createcentralkitchen.entry;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm.*;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = CentralKitchen.ID, bus = Bus.MOD)
public class CentralKitchenArmInterationTypes {
    public static final List<PonderArmInteractionPointType> TYPES = new ArrayList<>();
    public static final CuttingBoardPoint.Type CUTTING_BOARD = create("cutting_board", CuttingBoardPoint.Type::new);
    public static final BasketPoint.Type BASKET = create("basket", BasketPoint.Type::new);
    public static final StovePoint.Type STOVE = create("stove", StovePoint.Type::new);
    public static final BlazeStovePoint.Type BLAZE_STOVE = create("blaze_stove", BlazeStovePoint.Type::new);
    public static final CookingPotPoint.Type COOKING_POT = create("cooking_pot", CookingPotPoint.Type::new);
    public static final SkilletPoint.Type SKILLET = create("skillet", SkilletPoint.Type::new);
    public static final KettlePoint.Type KETTLE = create("kettle", KettlePoint.Type::new);
    public static final CopperPotPoint.Type COPPER_POT = create("copper_pot", CopperPotPoint.Type::new);
    
    private static <T extends PonderArmInteractionPointType> T create(String name, Function<ResourceLocation, T> factory) {
        ResourceLocation id = CentralKitchen.genRL(name);
        return factory.apply(id);
    }
    
    private static void register(PonderArmInteractionPointType... types) {
        for (var type : types) {
            ArmInteractionPointType.register(type);
            TYPES.add(type);
        }
    }
    
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        ModList mods = ModList.get();
        if (Mods.isLoaded(Mods.FD)) {
            register(STOVE, BLAZE_STOVE, COOKING_POT, SKILLET, CUTTING_BOARD, BASKET);
        }
        if (Mods.isLoaded(Mods.FR)) {
            register(KETTLE);
        }
        if (Mods.isLoaded(Mods.MD)) {
            register(COPPER_POT);
        }
    }
    
    @SubscribeEvent
    public static void registerPonderTags(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Consumer<ItemLike> consumer = PonderRegistry.TAGS.forTag(AllPonderTags.ARM_TARGETS)::add;
            for (var type : TYPES)
                type.addToPonderTag(consumer);
        });
    }
    
}
