package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

import cn.mcmod.corn_delight.block.BlockRegistry;
import cn.mcmod.corn_delight.block.CornCrop;
import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.CORN_DELIGHT)
public class CornDelightMovementBehaviorExtensions {
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            REGISTRY.put(BlockRegistry.CORN_CROP.get(),
                    CornDelightMovementBehaviorExtensions::harvestCorn);
        });
    }

    public static final BooleanProperty UPPER = BooleanProperty.create("upper");

    public static void harvestCorn(HarvesterMovementBehaviour behaviour,
            MovementContext context,
            BlockPos pos, BlockState state,
            boolean replant, boolean partial) {
        if (!(state.getBlock() instanceof CornCrop crop))
            return;
        int age = crop.getAge(state);
        if (age <= 0)
            return;
        if (!partial && age < crop.getMaxAge())
            return;

        Level level = context.world;
        BlockHelper.destroyBlock(level, pos, 1, stack -> behaviour.dropItem(context, stack));
        if (replant && !state.getValue(UPPER))
            level.setBlock(pos, crop.getStateForAge(0), 2);
    }
}
