package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.ATMOSPHERIC)
public class AtmosphericHarvesterMovementBehaviorExtensions {
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            REGISTRY.put(AtmosphericBlocks.ORANGE.get(),
                    AtmosphericHarvesterMovementBehaviorExtensions::harvestOrange);
            REGISTRY.put(AtmosphericBlocks.BLOOD_ORANGE.get(),
                    AtmosphericHarvesterMovementBehaviorExtensions::harvestOrange);
        });
    }

    public static void harvestOrange(HarvesterMovementBehaviour behaviour,
            MovementContext context,
            BlockPos pos, BlockState state,
            boolean replant, boolean partial) {
        harvestOrange(context.world, pos, stack -> behaviour.dropItem(context, stack));
    }

    static void harvestOrange(Level level, BlockPos pos, Consumer<ItemStack> drop) {
        BlockHelper.destroyBlock(level, pos, 1, drop);
    }
}
