package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import com.simibubi.create.content.contraptions.components.actors.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

@ModLoadSubscriber(modid = Mods.FD)
public class FDHarvesterMovementBehaviorExtensions {
    
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            REGISTRY.put(ModBlocks.RED_MUSHROOM_COLONY.get(),
                FDHarvesterMovementBehaviorExtensions::harvestMushroomColony);
            REGISTRY.put(ModBlocks.BROWN_MUSHROOM_COLONY.get(),
                FDHarvesterMovementBehaviorExtensions::harvestMushroomColony);
        });
    }
    
    public static void harvestMushroomColony(HarvesterMovementBehaviour behaviour,
                                      MovementContext context,
                                      BlockPos pos, BlockState state,
                                      boolean replant, boolean partial)
    {
        if (!(state.getBlock() instanceof MushroomColonyBlock colony))
            return;
        var ageProp = colony.getAgeProperty();
        int age = state.getValue(ageProp);
        if (age <= 0)
            return;
        if (!partial && age < colony.getMaxAge())
            return;
        Level level = context.world;
        if (replant) {
            level.playSound(null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlock(pos, state.setValue(ageProp, 0), 2);
        } else {
            BlockHelper.destroyBlock(level, pos, 1, $ -> {});
        }
        behaviour.dropItem(context, age < colony.getMaxAge()?
                new ItemStack(colony.mushroomType.get(), age):
                new ItemStack(colony.asItem()));
    }
    
}
