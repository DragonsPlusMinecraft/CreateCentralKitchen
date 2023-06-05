package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import com.simibubi.create.content.contraptions.components.actors.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import umpaz.nethersdelight.common.block.FungusColonyBlock;
import umpaz.nethersdelight.common.registry.NDBlocks;
import umpaz.nethersdelight.common.registry.NDItems;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

@ModLoadSubscriber(modid = Mods.ND)
public class NDHarvesterMovementBehaviorExtensions {
    
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            REGISTRY.put(NDBlocks.CRIMSON_FUNGUS_COLONY.get(),
                NDHarvesterMovementBehaviorExtensions::harvestFungusColony);
            REGISTRY.put(NDBlocks.WARPED_FUNGUS_COLONY.get(),
                NDHarvesterMovementBehaviorExtensions::harvestFungusColony);
            REGISTRY.put(NDBlocks.PROPELPLANT_BERRY_STEM.get(),
                NDHarvesterMovementBehaviorExtensions::harvestPropelplantStem);
            REGISTRY.put(NDBlocks.PROPELPLANT_CANE.get(),
                NDHarvesterMovementBehaviorExtensions::harvestPropelplantCane);
            REGISTRY.put(NDBlocks.PROPELPLANT_BERRY_CANE.get(),
                NDHarvesterMovementBehaviorExtensions::harvestPropelplantCane);
        });
    }
    
    public static void harvestFungusColony(HarvesterMovementBehaviour behaviour,
                                           MovementContext context,
                                           BlockPos pos, BlockState state,
                                           boolean replant, boolean partial)
    {
        if (!(state.getBlock() instanceof FungusColonyBlock colony))
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
                new ItemStack(colony.fungusType.get(), age):
                new ItemStack(colony.asItem()));
    }
    
    private static final BooleanProperty PEARL = BooleanProperty.create("pearl");
    
    public static void harvestPropelplantStem(HarvesterMovementBehaviour behaviour,
                                              MovementContext context,
                                              BlockPos pos, BlockState state,
                                              boolean replant, boolean partial)
    {
        Block block = state.getBlock();
        Level level = context.world;
        if (state.getValue(PEARL)) {
            behaviour.dropItem(context, new ItemStack(NDItems.PROPELPEARL.get(), 1 + level.random.nextInt(2)));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlock(pos, state.setValue(PEARL, Boolean.FALSE), 2);
        }
    }
    
    public static void harvestPropelplantCane(HarvesterMovementBehaviour behaviour,
                                              MovementContext context,
                                              BlockPos pos, BlockState state,
                                              boolean replant, boolean partial)
    {
        Level level = context.world;
        BlockPos posAbove = pos.above();
        BlockState stateAbove = level.getBlockState(posAbove);
        if (stateAbove.is(NDBlocks.PROPELPLANT_CANE.get()) || stateAbove.is(NDBlocks.PROPELPLANT_BERRY_CANE.get()))
            harvestPropelplantCane(behaviour, context, posAbove, stateAbove, false, partial);
        
        if (state.hasProperty(PEARL)) {
            if (state.getValue(PEARL)) {
                behaviour.dropItem(context, new ItemStack(NDItems.PROPELPEARL.get(), 1 + level.random.nextInt(2)));
                if (replant) {
                    level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
                    level.setBlock(pos, state.setValue(PEARL, Boolean.FALSE), 2);
                    return;
                }
            }
        }
        BlockHelper.destroyBlock(level, pos, 1, stack -> behaviour.dropItem(context, stack));
        if (replant) {
            BlockPos posBelow = pos.below();
            BlockState stateBelow = level.getBlockState(posBelow);
            if (stateBelow.is(NDBlocks.PROPELPLANT_STEM.get())) {
                level.setBlockAndUpdate(posBelow, NDBlocks.PROPELPLANT_BERRY_STEM.get().defaultBlockState());
            }
        }
    }
    
}
