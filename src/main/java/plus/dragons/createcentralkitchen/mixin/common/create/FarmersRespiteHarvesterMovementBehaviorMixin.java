package plus.dragons.createcentralkitchen.mixin.common.create;

import com.simibubi.create.content.contraptions.components.actors.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import com.simibubi.create.foundation.config.AllConfigs;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import umpaz.farmersrespite.common.block.CoffeeDoubleStemBlock;
import umpaz.farmersrespite.common.block.CoffeeMiddleStemBlock;
import umpaz.farmersrespite.common.block.CoffeeStemBlock;
import umpaz.farmersrespite.common.block.TeaBushBlock;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRItems;

@Mixin(value = HarvesterMovementBehaviour.class, remap = false)
public abstract class FarmersRespiteHarvesterMovementBehaviorMixin implements MovementBehaviour {
    
    @Inject(method = "visitNewPosition", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/components/actors/HarvesterMovementBehaviour;isValidCrop(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"), cancellable = true)
    private void farmersrespite$visitNewPosition(MovementContext context, BlockPos pos, CallbackInfo ci) {
        Level level = context.world;
        BlockState state = level.getBlockState(pos);
        boolean harvestPartial = AllConfigs.SERVER.kinetics.harvestPartiallyGrown.get();
        boolean replant = AllConfigs.SERVER.kinetics.harvesterReplants.get();
        boolean handled = true;
        if (state.getBlock() instanceof TeaBushBlock) {
            farmersrespite$handleTeaBush(context, pos, state, replant);
        } else if (state.getBlock() instanceof CoffeeStemBlock) {
            farmersrespite$handleCoffeeStem(context, pos, state, replant);
        } else if (state.getBlock() instanceof CoffeeMiddleStemBlock) {
            if (replant) {
                farmersrespite$handleCoffeeStem(context, pos, state, true);
            } else {
                BlockPos posBelow = pos.below();
                BlockState stateBelow = level.getBlockState(posBelow);
                if (stateBelow.getBlock() instanceof CoffeeDoubleStemBlock) {
                    farmersrespite$destroyCoffeeStems(context, posBelow, stateBelow, pos, state, harvestPartial);
                }
            }
        } else if (state.getBlock() instanceof CoffeeDoubleStemBlock) {
            if (replant) {
                farmersrespite$replantCoffeeDoubleStem(context, pos, state);
            } else {
                BlockPos posAbove = pos.above();
                BlockState stateAbove = level.getBlockState(posAbove);
                if (stateAbove.getBlock() instanceof CoffeeMiddleStemBlock) {
                    farmersrespite$destroyCoffeeStems(context, pos, state, posAbove, stateAbove, harvestPartial);
                }
            }
        } else {
            handled = false;
        }
        if (handled)
            ci.cancel();
    }
    
    private void farmersrespite$handleTeaBush(MovementContext context, BlockPos pos, BlockState state, boolean replant) {
        Level level = context.world;
        var half = state.getValue(TeaBushBlock.HALF);
        if (replant && half == DoubleBlockHalf.UPPER) {
            switch (state.getValue(TeaBushBlock.AGE)) {
                case 0 -> dropItem(context, new ItemStack(FRItems.GREEN_TEA_LEAVES.get(), 2 + level.random.nextInt(2)));
                case 1 -> dropItem(context, new ItemStack(FRItems.YELLOW_TEA_LEAVES.get(), 2 + level.random.nextInt(2)));
                case 2 -> {
                    dropItem(context, new ItemStack(FRItems.YELLOW_TEA_LEAVES.get(), 1 + level.random.nextInt(2)));
                    dropItem(context, new ItemStack(FRItems.BLACK_TEA_LEAVES.get(), 1 + level.random.nextInt(2)));
                }
                case 3 -> dropItem(context, new ItemStack(FRItems.BLACK_TEA_LEAVES.get(), 2 + level.random.nextInt(2)));
            }
            dropItem(context, new ItemStack(Items.STICK, 2 + level.random.nextInt(2)));
            level.setBlockAndUpdate(pos.below(), FRBlocks.SMALL_TEA_BUSH.get().defaultBlockState());
        } else {
            var destroyPos = half == DoubleBlockHalf.UPPER ? pos.below() : pos;
            BlockHelper.destroyBlockAs(level, destroyPos, null, ItemStack.EMPTY, 1, stack -> dropItem(context, stack));
        }
    }
    
    private void farmersrespite$handleCoffeeStem(MovementContext context, BlockPos pos, BlockState state, boolean replant) {
        if (state.getValue(BlockStateProperties.AGE_2) < 2)
            return;
        Level level = context.world;
        if (replant) {
            dropItem(context, new ItemStack(FRItems.COFFEE_BERRIES.get(), 1));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlock(pos, state.setValue(BlockStateProperties.AGE_2, 0), 2);
        } else {
            BlockHelper.destroyBlockAs(level, pos, null, ItemStack.EMPTY, 1, stack -> dropItem(context, stack));
        }
    }
    
    private void farmersrespite$replantCoffeeDoubleStem(MovementContext context, BlockPos pos, BlockState state) {
        int flag0 = 0;
        int flag1 = 0;
        if (state.getValue(CoffeeDoubleStemBlock.AGE) == 2)
            flag0 = 1;
        if (state.getValue(CoffeeDoubleStemBlock.AGE1) == 2)
            flag1 = 1;
        if (flag0 + flag1 == 0)
            return;
        Level level = context.world;
        dropItem(context, new ItemStack(FRItems.COFFEE_BERRIES.get(), flag0 + flag1));
        if (flag0 == 1)
            state = state.setValue(CoffeeDoubleStemBlock.AGE, 0);
        if (flag1 == 1)
            state = state.setValue(CoffeeDoubleStemBlock.AGE1, 0);
        level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        level.setBlock(pos, state, 2);
    }

    private void farmersrespite$destroyCoffeeStems(MovementContext context,
                                                   BlockPos rootPos, BlockState rootState,
                                                   BlockPos middlePos, BlockState middleState,
                                                   boolean harvestPartial) {
        Level level = context.world;
        int rootAge0 = rootState.getValue(CoffeeDoubleStemBlock.AGE);
        int rootAge1 = rootState.getValue(CoffeeDoubleStemBlock.AGE1);
        int middleAge = middleState.getValue(CoffeeMiddleStemBlock.AGE);
        if (harvestPartial) {
            if (rootAge0 < 2 && rootAge1 < 2 && middleAge < 2)
                return;
        } else if (rootAge0 < 2 || rootAge1 < 2 || middleAge < 2) {
            return;
        }
        BlockHelper.destroyBlockAs(level, middlePos, null, ItemStack.EMPTY, 1, stack -> dropItem(context, stack));
        BlockHelper.destroyBlockAs(level, rootPos, null, ItemStack.EMPTY, 1, stack -> dropItem(context, stack));
    }
    
}