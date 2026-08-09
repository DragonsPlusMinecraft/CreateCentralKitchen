package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import static net.brdle.collectorsreap.common.block.FruitBushBlock.AGE;
import static net.brdle.collectorsreap.common.block.FruitBushBlock.MAX_AGE;
import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.brdle.collectorsreap.common.block.CRBlocks;
import net.brdle.collectorsreap.common.block.LimeBushBlock;
import net.brdle.collectorsreap.common.block.PomegranateBushBlock;
import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.CR)
public class CRHarvesterMovementBehaviorExtensions {
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            REGISTRY.put(CRBlocks.PORTOBELLO_COLONY.get(),
                    FDHarvesterMovementBehaviorExtensions::harvestMushroomColony);
            REGISTRY.put(CRBlocks.LIME_BUSH.get(),
                    CRHarvesterMovementBehaviorExtensions::harvestLimeBush);
            REGISTRY.put(CRBlocks.POMEGRANATE_BUSH.get(),
                    CRHarvesterMovementBehaviorExtensions::harvestPomegranateBush);
        });
    }

    public static void harvestLimeBush(HarvesterMovementBehaviour behaviour,
            MovementContext context,
            BlockPos pos, BlockState state,
            boolean replant, boolean partial) {
        if (!(state.getBlock() instanceof LimeBushBlock))
            return;
        if (state.getValue(LimeBushBlock.STUNTED))
            return;
        Level level = context.world;
        boolean destroy = partial;
        boolean isLowerHalf = state.getValue(LimeBushBlock.HALF) == DoubleBlockHalf.LOWER;
        if (state.getValue(AGE) == MAX_AGE) {
            behaviour.dropItem(context, new ItemStack(CRItems.LIME.get(), 2 + level.random.nextInt(2)));
            if (replant) {
                level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS,
                        1.0F, 0.8F + level.random.nextFloat() * 0.4F);
                resetFruitBush(level, pos, state, isLowerHalf);
                return;
            }
            destroy = true;
        }
        if (destroy)
            destroyFruitBush(behaviour, context, pos, state, isLowerHalf);
    }

    public static void harvestPomegranateBush(HarvesterMovementBehaviour behaviour,
            MovementContext context,
            BlockPos pos, BlockState state,
            boolean replant, boolean partial) {
        if (!(state.getBlock() instanceof PomegranateBushBlock))
            return;
        if (state.getValue(PomegranateBushBlock.STUNTED))
            return;
        Level level = context.world;
        boolean destroy = partial;
        boolean isLowerHalf = state.getValue(PomegranateBushBlock.HALF) == DoubleBlockHalf.LOWER;
        if (state.getValue(AGE) == MAX_AGE) {
            behaviour.dropItem(context, new ItemStack(CRItems.POMEGRANATE.get(), 1 + level.random.nextInt(2)));
            if (replant) {
                level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS,
                        1.0F, 0.8F + level.random.nextFloat() * 0.4F);
                resetFruitBush(level, pos, state, isLowerHalf);
                return;
            }
            destroy = true;
        }
        if (destroy)
            destroyFruitBush(behaviour, context, pos, state, isLowerHalf);
    }

    static void resetFruitBush(Level level, BlockPos pos, BlockState state, boolean isLowerHalf) {
        var lowerPos = isLowerHalf ? pos : pos.below();
        var lowerState = level.getBlockState(lowerPos);
        if (!lowerState.is(state.getBlock()) || lowerState.getValue(LimeBushBlock.HALF) != DoubleBlockHalf.LOWER)
            return;
        var pickedState = lowerState.setValue(AGE, MAX_AGE - 2);
        level.setBlock(lowerPos, pickedState, 2);
        level.setBlock(lowerPos.above(), pickedState.setValue(LimeBushBlock.HALF, DoubleBlockHalf.UPPER), 2);
    }

    private static void destroyFruitBush(HarvesterMovementBehaviour behaviour, MovementContext context,
            BlockPos pos, BlockState state, boolean isLowerHalf) {
        var lowerPos = isLowerHalf ? pos : pos.below();
        var upperPos = lowerPos.above();
        var level = context.world;
        var lowerState = level.getBlockState(lowerPos);
        var upperState = level.getBlockState(upperPos);
        if (!lowerState.is(state.getBlock()) || lowerState.getValue(LimeBushBlock.HALF) != DoubleBlockHalf.LOWER ||
                !upperState.is(state.getBlock()) || upperState.getValue(LimeBushBlock.HALF) != DoubleBlockHalf.UPPER)
            return;
        BlockHelper.destroyBlock(level, upperPos, 1, stack -> behaviour.dropItem(context, stack));
        BlockHelper.destroyBlock(level, lowerPos, 1, stack -> behaviour.dropItem(context, stack));
    }
}
