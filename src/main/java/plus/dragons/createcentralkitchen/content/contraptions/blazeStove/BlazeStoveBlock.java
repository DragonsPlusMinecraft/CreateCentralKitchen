package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.entry.block.entity.FDBlockEntityEntries;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.registry.ModDamageTypes;

@SuppressWarnings("deprecation")
public class BlazeStoveBlock extends HorizontalDirectionalBlock implements IBE<BlazeStoveBlockEntity>, IWrenchable {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<BlazeBurnerBlock.HeatLevel> HEAT_LEVEL = BlazeBurnerBlock.HEAT_LEVEL;
    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(2, 0, 2, 14, 5, 14),
        Block.box(1, 5, 1, 15, 15, 15),
        Block.box(0, 15, 0, 16, 16, 16)
    );
    
    public BlazeStoveBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false)
            .setValue(HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING));
    }
    
    @Override
    public Item asItem() {
        return AllBlocks.BLAZE_BURNER.get().asItem();
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, POWERED, HEAT_LEVEL);
    }
    
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult blockRayTraceResult) {
        if(hand==InteractionHand.OFF_HAND)
            return InteractionResult.PASS;

        ItemStack stack = player.getItemInHand(hand);
        if (stack.isEmpty()) {
            if(!level.isClientSide()) {
                withBlockEntityDo(level, pos, stove -> NetworkHooks.openScreen(
                    (ServerPlayer) player, stove, buf -> {
                        buf.writeItem(stove.getGuide());
                        buf.writeBoolean(false);
                        buf.writeBlockPos(pos);
                    }));
            }
            return InteractionResult.SUCCESS;
        }
        
        boolean noConsume = player.isCreative();
        boolean forceOverflow = !(player instanceof FakePlayer);
        
        InteractionResultHolder<ItemStack> holder = tryInsert(level, pos, stack, noConsume, forceOverflow, false);
        ItemStack leftover = holder.getObject();
        if (!level.isClientSide && !noConsume && !leftover.isEmpty()) {
            if (stack.isEmpty()) {
                player.setItemInHand(hand, leftover);
            } else if (!player.getInventory().add(leftover)) {
                player.drop(leftover, false);
            }
        }
        
        return holder.getResult().shouldAwardStats()
            ? InteractionResult.sidedSuccess(level.isClientSide)
            : InteractionResult.PASS;
    }
    
    public static InteractionResultHolder<ItemStack> tryInsert(Level level, BlockPos pos, ItemStack stack,
                                                               boolean noConsume, boolean forceOverflow, boolean simulate) {
        if (!(level.getBlockEntity(pos) instanceof BlazeStoveBlockEntity stove))
            return InteractionResultHolder.fail(ItemStack.EMPTY);
        
        if (stack.getItem() instanceof BlazeStoveGuideItem<?>) {
            var original = stove.getGuide();
            if(!level.isClientSide()) {
                ItemStack guide = stack.copy().split(1);
                if (!simulate)
                    stove.setGuide(guide);
            }
            return InteractionResultHolder.success(original);
        }
        
        if (stove.isCreativeFuel(stack)) {
            if (!simulate)
                stove.applyCreativeFuel();
            return InteractionResultHolder.success(ItemStack.EMPTY);
        }
        
        if (!(stove.tryUpdateFuel(stack, forceOverflow, simulate) ||
            stove.tryAddIngredient(stack, forceOverflow, simulate)))
            return InteractionResultHolder.fail(ItemStack.EMPTY);
        
        if (!noConsume) {
            ItemStack container = stack.hasCraftingRemainingItem() ? stack.getCraftingRemainingItem() : ItemStack.EMPTY;
            if (!level.isClientSide) {
                stack.shrink(1);
            }
            return InteractionResultHolder.success(container);
        }
        
        return InteractionResultHolder.success(ItemStack.EMPTY);
    }
    
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, false), 2);
        } else {
            level.setBlock(pos, state.setValue(POWERED, true), 2);
            level.scheduleTick(pos, this, 2);
        }
        this.updateNeighborsInFront(level, pos, state);
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) != 0)
            return;
        if (!state.getValue(HEAT_LEVEL).isAtLeast(BlazeBurnerBlock.HeatLevel.SMOULDERING))
            return;
        world.playLocalSound((float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F,
            SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
            0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
    }
    
    public void startSignal(LevelAccessor level, BlockPos pos) {
        if (!level.isClientSide() && !level.getBlockTicks().hasScheduledTick(pos, this)) {
            level.scheduleTick(pos, this, 2);
        }
    }
    
    protected void updateNeighborsInFront(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        BlockPos posFront = pos.relative(facing.getOpposite());
        level.neighborChanged(posFront, this, pos);
        level.updateNeighborsAtExceptFromFacing(posFront, this, facing);
    }
    
    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return direction == state.getValue(FACING);
    }
    
    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }
    
    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
        return state.getSignal(level, pos, side);
    }
    
    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
        return state.getValue(POWERED) && state.getValue(FACING) == side ? 15 : 0;
    }
    
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
    
    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return Math.max(0, state.getValue(HEAT_LEVEL).ordinal() - 1);
    }
    
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        AdvancementBehaviour.setPlacedBy(level, pos, placer);
    }
    
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level.isClientSide) return;
        BlockEntity blockEntity = level.getBlockEntity(pos.above());
        if (blockEntity instanceof BasinBlockEntity basin) {
            basin.notifyChangeOfContents();
        }
        if (!state.is(oldState.getBlock())) {
            if (!level.isClientSide() && state.getValue(POWERED) && !level.getBlockTicks().hasScheduledTick(pos, this)) {
                state = state.setValue(POWERED, false);
                level.setBlock(pos, state, 18);
                this.updateNeighborsInFront(level, pos, state);
            }
        }
    }
    
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        IBE.onRemove(state,level,pos,newState);
        if (!state.is(newState.getBlock())) {
            if (!level.isClientSide && state.getValue(POWERED) && level.getBlockTicks().hasScheduledTick(pos, this)) {
                this.updateNeighborsInFront(level, pos, state.setValue(POWERED, false));
            }
        }
    }

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.setBlockAndUpdate(pos, AllBlocks.BLAZE_BURNER.getDefaultState()
                .setValue(BlazeBurnerBlock.FACING, state.getValue(FACING))
                .setValue(HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING));
        }
        return InteractionResult.SUCCESS;
    }
    
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        float damage = switch(state.getValue(HEAT_LEVEL)) {
            case SEETHING -> 2f;
            case KINDLED, FADING -> 1f;
            case SMOULDERING -> .5f;
            default -> -1f;
        };
        if (damage > 0 && !entity.fireImmune() &&
            entity instanceof LivingEntity living && !EnchantmentHelper.hasFrostWalker(living))
            entity.hurt(ModDamageTypes.getSimpleDamageSource(level, ModDamageTypes.STOVE_BURN), damage);
        super.stepOn(level, pos, state, entity);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }
    
    @Override
    public Class<BlazeStoveBlockEntity> getBlockEntityClass() {
        return BlazeStoveBlockEntity.class;
    }
    
    @Override
    public BlockEntityType<? extends BlazeStoveBlockEntity> getBlockEntityType() {
        return FDBlockEntityEntries.BLAZE_STOVE.get();
    }
    
}
