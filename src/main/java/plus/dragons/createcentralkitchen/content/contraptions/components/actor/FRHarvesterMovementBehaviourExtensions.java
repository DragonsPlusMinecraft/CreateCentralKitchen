package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import com.farmersrespite.common.block.CoffeeDoubleStemBlock;
import com.farmersrespite.common.block.CoffeeMiddleStemBlock;
import com.farmersrespite.common.block.TeaBushBlock;
import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRItems;
import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

@ModLoadSubscriber(modid = Mods.FR)
public class FRHarvesterMovementBehaviourExtensions {
    
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            REGISTRY.put(FRBlocks.TEA_BUSH.get(),
                FRHarvesterMovementBehaviourExtensions::harvestTeaBush);
            REGISTRY.put(FRBlocks.COFFEE_STEM.get(),
                FRHarvesterMovementBehaviourExtensions::harvestCoffeeStem);
            REGISTRY.put(FRBlocks.COFFEE_STEM_MIDDLE.get(),
                FRHarvesterMovementBehaviourExtensions::harvestCoffeeMiddleStem);
            REGISTRY.put(FRBlocks.COFFEE_STEM_DOUBLE.get(),
                FRHarvesterMovementBehaviourExtensions::harvestCoffeeDoubleStem);
        });
    }
    
    public static void harvestTeaBush(HarvesterMovementBehaviour behaviour,
                                      MovementContext context,
                                      BlockPos pos, BlockState state,
                                      boolean replant, boolean partial) {
        Level level = context.world;
        var half = state.getValue(TeaBushBlock.HALF);
        if (replant && half == DoubleBlockHalf.UPPER) {
            switch (state.getValue(TeaBushBlock.AGE)) {
                case 0 -> behaviour.dropItem(context,
                    new ItemStack(FRItems.GREEN_TEA_LEAVES.get(), 2 + level.random.nextInt(2)));
                case 1 -> behaviour.dropItem(context,
                    new ItemStack(FRItems.YELLOW_TEA_LEAVES.get(), 2 + level.random.nextInt(2)));
                case 2 -> {
                    behaviour.dropItem(context,
                        new ItemStack(FRItems.YELLOW_TEA_LEAVES.get(), 1 + level.random.nextInt(2)));
                    behaviour.dropItem(context,
                        new ItemStack(FRItems.BLACK_TEA_LEAVES.get(), 1 + level.random.nextInt(2)));
                }
                case 3 -> behaviour.dropItem(context,
                    new ItemStack(FRItems.BLACK_TEA_LEAVES.get(), 2 + level.random.nextInt(2)));
            }
            behaviour.dropItem(context, new ItemStack(Items.STICK, 2 + level.random.nextInt(2)));
            level.setBlockAndUpdate(pos.below(), FRBlocks.SMALL_TEA_BUSH.get().defaultBlockState());
        } else {
            var destroyPos = half == DoubleBlockHalf.UPPER ? pos.below() : pos;
            BlockHelper.destroyBlock(level, destroyPos, 1, stack -> behaviour.dropItem(context, stack));
        }
    }
    
    private static void destroyCoffeeStems(HarvesterMovementBehaviour behaviour,
                                           MovementContext context,
                                           BlockPos rootPos, BlockState rootState,
                                           BlockPos middlePos, BlockState middleState,
                                           boolean partial) {
        Level level = context.world;
        int rootAge0 = rootState.getValue(CoffeeDoubleStemBlock.AGE);
        int rootAge1 = rootState.getValue(CoffeeDoubleStemBlock.AGE1);
        int middleAge = middleState.getValue(CoffeeMiddleStemBlock.AGE);
        if (partial) {
            if (rootAge0 < 2 && rootAge1 < 2 && middleAge < 2)
                return;
        } else if (rootAge0 < 2 || rootAge1 < 2 || middleAge < 2) {
            return;
        }
        BlockHelper.destroyBlock(level, middlePos, 1, stack -> behaviour.dropItem(context, stack));
        BlockHelper.destroyBlock(level, rootPos, 1, stack -> behaviour.dropItem(context, stack));
    }
    
    public static void harvestCoffeeStem(HarvesterMovementBehaviour behaviour,
                                         MovementContext context,
                                         BlockPos pos, BlockState state,
                                         boolean replant, boolean partial) {
        if (state.getValue(BlockStateProperties.AGE_2) < 2)
            return;
        Level level = context.world;
        if (replant) {
            behaviour.dropItem(context, new ItemStack(FRItems.COFFEE_BERRIES.get(), 1));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS,
                            1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_2, 0));
        } else {
            BlockHelper.destroyBlock(level, pos, 1, stack -> behaviour.dropItem(context, stack));
        }
    }
    
    public static void harvestCoffeeMiddleStem(HarvesterMovementBehaviour behaviour,
                                               MovementContext context,
                                               BlockPos pos, BlockState state,
                                               boolean replant, boolean partial) {
        if (replant) {
            harvestCoffeeStem(behaviour, context, pos, state, true, partial);
        } else {
            BlockPos posBelow = pos.below();
            BlockState stateBelow = context.world.getBlockState(posBelow);
            if (stateBelow.getBlock() instanceof CoffeeDoubleStemBlock) {
                destroyCoffeeStems(behaviour, context, posBelow, stateBelow, pos, state, partial);
            }
        }
    }
    
    public static void harvestCoffeeDoubleStem(HarvesterMovementBehaviour behaviour,
                                               MovementContext context,
                                               BlockPos pos, BlockState state,
                                               boolean replant, boolean partial)
    {
        if (replant) {
            int count0 = state.getValue(CoffeeDoubleStemBlock.AGE) == 2 ? 1 : 0;
            int count1 = state.getValue(CoffeeDoubleStemBlock.AGE1) == 2 ? 1 : 0;
            int count = count0 + count1;
            if (count == 0)
                return;
            Level level = context.world;
            behaviour.dropItem(context, new ItemStack(FRItems.COFFEE_BERRIES.get(), count));
            var newState = state;
            if (count0 == 1)
                newState = state.setValue(CoffeeDoubleStemBlock.AGE, 0);
            if (count1 == 1)
                newState = state.setValue(CoffeeDoubleStemBlock.AGE1, 0);
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS,
                1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlockAndUpdate(pos, newState);
        } else {
            BlockPos posAbove = pos.above();
            BlockState stateAbove = context.world.getBlockState(posAbove);
            if (stateAbove.getBlock() instanceof CoffeeMiddleStemBlock) {
                destroyCoffeeStems(behaviour, context, pos, state, posAbove, stateAbove, partial);
            }
        }
    }
    
}
