package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import cn.mcmod.corn_delight.block.BlockRegistry;
import cn.mcmod.corn_delight.block.CornCrop;
import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

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
                                              boolean replant, boolean partial)
    {
        Level level = context.world;

        if (state.getValue(UPPER)) {
            BlockHelper.destroyBlock(level, pos, 1, stack -> behaviour.dropItem(context, stack));
        } else {
            if (!(state.getBlock() instanceof CornCrop crop))
                return;
            var ageProp = crop.getAgeProperty();
            int age = state.getValue(ageProp);
            if (age <= 0)
                return;
            if (!partial && age < crop.getMaxAge())
                return;
            if (replant) {
                level.playSound(null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, state.setValue(ageProp, 0), 2);
            } else {
                BlockHelper.destroyBlock(level, pos, 1, stack -> behaviour.dropItem(context, stack));
            }
        }
    }

}
