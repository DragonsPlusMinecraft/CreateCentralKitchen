package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import com.simibubi.create.content.contraptions.components.actors.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
public interface HarvesterMovementBehaviourExtension {
    Map<Block, HarvesterMovementBehaviourExtension> REGISTRY = new HashMap<>();
    HarvesterMovementBehaviourExtension NOOP = (behaviour, context, pos, state, replant, partial) -> {};
    
    void harvest(HarvesterMovementBehaviour behaviour,
                 MovementContext context,
                 BlockPos pos, BlockState state,
                 boolean replant, boolean partial);
    
}
