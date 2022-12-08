package plus.dragons.createfarmersautomation.content.contraptions.components.cooking;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Triple;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.List;
import java.util.Optional;

public class BlazeStoveBlockEntity extends BlazeBurnerTileEntity {
    public static final Vec2[] ITEM_OFFSET_NS = new Vec2[9];
    public static final Vec2[] ITEM_OFFSET_WE = new Vec2[9];
    private static final VoxelShape GRILLING_AREA = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
    private static final int INVENTORY_SLOT_COUNT = 9;
    private final ItemStackHandler inventory = createItemHandler();
    private final int[] cookingTimes = new int[INVENTORY_SLOT_COUNT];
    private final int[] cookingTimesTotal = new int[INVENTORY_SLOT_COUNT];
    private final ResourceLocation[] lastRecipeIDs = new ResourceLocation[INVENTORY_SLOT_COUNT];
    
    public BlazeStoveBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    
    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(INVENTORY_SLOT_COUNT) {
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }
    
    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        //TODO: Advancements
    }
    
    @Override
    public void tick() {
        super.tick();
        BlazeBurnerBlock.HeatLevel heat = getHeatLevelFromBlock();
        if (level.isClientSide) {
            if (!heat.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) {
                for (int i = 0; i < INVENTORY_SLOT_COUNT; ++i) {
                    if (level.random.nextFloat() < 0.2F && !inventory.getStackInSlot(i).isEmpty()) {
                        addSmokeAtItem(i, 3);
                    }
                }
            }
        } else {
            boolean blockedAbove = isBlockedAbove();
            switch(heat) {
                case SEETHING -> {
                    if (blockedAbove)
                        boostCookingPot(4);
                    else
                        burnIngredients();
                }
                case KINDLED, FADING -> {
                    if (blockedAbove)
                        boostCookingPot(2);
                    else
                        processCooking(2);
                }
                case SMOULDERING -> {
                    if (!blockedAbove)
                        processCooking(1);
                }
                //Shouldn't reach this in normal cases
                default -> decrementCooking(blockedAbove);
            }
            if (blockedAbove) {
                dropAll();
            }
        }
    }
    
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        if (!clientPacket) {
            int[] array;
            array = compound.getIntArray("CookingTimes");
            System.arraycopy(array, 0, cookingTimes, 0, Math.min(cookingTimesTotal.length, array.length));
            array = compound.getIntArray("CookingTotalTimes");
            System.arraycopy(array, 0, cookingTimesTotal, 0, Math.min(cookingTimesTotal.length, array.length));
        }
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        super.read(compound, clientPacket);
    }
    
    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        if (!clientPacket) {
            compound.putIntArray("CookingTimes", cookingTimes);
            compound.putIntArray("CookingTotalTimes", cookingTimesTotal);
        }
        compound.put("Inventory", inventory.serializeNBT());
        super.write(compound, clientPacket);
    }
    
    @Override
    public boolean isValidBlockAbove() {
        BlockState blockState = level.getBlockState(worldPosition.above());
        return AllBlocks.BASIN.has(blockState)
            || blockState.getBlock() instanceof FluidTankBlock
            || blockState.getBlock() instanceof CookingPotBlock;
    }
    
    public boolean isBlockedAbove() {
        if (level != null) {
            BlockState above = level.getBlockState(worldPosition.above());
            return Shapes.joinIsNotEmpty(GRILLING_AREA, above.getShape(level, worldPosition.above()), BooleanOp.AND);
        } else {
            return false;
        }
    }
    
    public ItemStackHandler getInventory() {
        return inventory;
    }
    
    public int getNextEmptySlot() {
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (slotStack.isEmpty()) {
                return i;
            }
        }
        return -1;
    }
    
    public LerpedFloat getHeadAngle() {
        return headAngle;
    }
    
    public LerpedFloat getHeadAnimation() {
        return headAnimation;
    }
    
    @Override
    protected void setBlockHeat(BlazeBurnerBlock.HeatLevel newHeat) {
        BlazeBurnerBlock.HeatLevel originalHeat = getHeatLevelFromBlock();
        if (originalHeat == newHeat)
            return;
        level.setBlockAndUpdate(worldPosition, getBlockState()
            .setValue(BlazeBurnerBlock.HEAT_LEVEL, newHeat)
        );
        notifyUpdate();
    }
    
    @Override
    public void applyCreativeFuel() {
        super.applyCreativeFuel();
    }
    
    public boolean addFuelOrIngredient(ItemStack inStack, boolean forceOverflow, boolean simulate) {
        if (tryUpdateFuel(inStack, forceOverflow, simulate)) return true;
        
        int slot = getNextEmptySlot();
        if (slot < 0) return false;
        
        BlazeBurnerBlock.HeatLevel heat = getHeatLevelFromBlock();
        if (!heat.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) || heat.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) return false;
        
        if (isBlockedAbove()) return false;
        
        Optional<CampfireCookingRecipe> optional = findRecipe(new SimpleContainer(inStack), slot);
        if (optional.isPresent() && slot < inventory.getSlots()) {
            if (simulate) return true;
            
            CampfireCookingRecipe recipe = optional.get();
            ItemStack slotStack = inventory.getStackInSlot(slot);
            if (slotStack.isEmpty()) {
                ItemStack addStack = inStack.copy();
                addStack.setCount(1);
                cookingTimesTotal[slot] = recipe.getCookingTime();
                cookingTimes[slot] = 0;
                inventory.setStackInSlot(slot, addStack);
                lastRecipeIDs[slot] = recipe.getId();
                notifyUpdate();
                return true;
            }
        }
        
        return false;
    }
    
    public void dropAll() {
        if (!ItemUtils.isInventoryEmpty(inventory)) {
            ItemUtils.dropItems(level, getBlockPos(), inventory);
            notifyUpdate();
        }
    }

    public boolean addItem(ItemStack itemStackIn, CampfireCookingRecipe recipe, int slot) {
        if (0 <= slot && slot < this.inventory.getSlots()) {
            ItemStack slotStack = this.inventory.getStackInSlot(slot);
            if (slotStack.isEmpty()) {
                this.cookingTimesTotal[slot] = recipe.getCookingTime();
                this.cookingTimes[slot] = 0;
                this.inventory.setStackInSlot(slot, itemStackIn.split(1));
                this.lastRecipeIDs[slot] = recipe.getId();
                super.setChanged();
                if (this.level != null) {
                    this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
                }
                return true;
            }
        }
        return false;
    }
    
    protected void processCooking(int speed) {
        boolean didInventoryChange = false;
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            
            cookingTimes[i] += speed;
            if (cookingTimes[i] >= cookingTimesTotal[i]) {
                Container inventoryWrapper = new SimpleContainer(stack);
                Optional<CampfireCookingRecipe> recipe = findRecipe(inventoryWrapper, i);
                if (recipe.isPresent()) {
                    ItemStack resultStack = recipe.get().getResultItem();
                    if (!resultStack.isEmpty()) {
                        ItemUtils.spawnItemEntity(level, resultStack.copy(),
                            worldPosition.getX() + 0.5D,
                            worldPosition.getY() + 1.0D,
                            worldPosition.getZ() + 0.5D,
                            level.random.nextGaussian() * 0.001D,
                            0.1D,
                            level.random.nextGaussian() * 0.001D);
                    }
                }
                
                inventory.setStackInSlot(i, ItemStack.EMPTY);
                didInventoryChange = true;
            }
        }
        
        if (didInventoryChange) {
            notifyUpdate();
        }
    }
    
    public Optional<CampfireCookingRecipe> findRecipe(Container recipeWrapper, int slot) {
        if (level == null) return Optional.empty();
        
        if (lastRecipeIDs[slot] != null) {
            Recipe<Container> recipe = ((RecipeManagerAccessor)level.getRecipeManager()).getRecipeMap(RecipeType.CAMPFIRE_COOKING).get(lastRecipeIDs[slot]);
            if (recipe instanceof CampfireCookingRecipe && recipe.matches(recipeWrapper, level)) {
                return Optional.of((CampfireCookingRecipe)recipe);
            }
        }
        
        return level.getRecipeManager().getRecipeFor(RecipeType.CAMPFIRE_COOKING, recipeWrapper, level);
    }
    
    protected void burnIngredients() {
        boolean didInventoryChange = false;
        int totalUncooked = 0;
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            totalUncooked += Math.max(0, cookingTimesTotal[i] - cookingTimes[i]);
            cookingTimesTotal[i] = cookingTimes[0] = 0;
            inventory.setStackInSlot(i, ItemStack.EMPTY);
            addSmokeAtItem(i, 5);
            didInventoryChange = true;
        }
        
        if (didInventoryChange) {
            remainingBurnTime += totalUncooked / INVENTORY_SLOT_COUNT;
            level.levelEvent(1501, getBlockPos(), 0);
            notifyUpdate();
        }
    }
    
    protected void decrementCooking(boolean blockedAbove) {
        if (blockedAbove) return;
        for (int i = 0; i < INVENTORY_SLOT_COUNT; ++i) {
            if (cookingTimes[i] > 0) {
                cookingTimes[i] = Mth.clamp(cookingTimes[i] - 2, 0, cookingTimesTotal[i]);
            }
        }
    }
    
    protected void boostCookingPot(int times) {
        Triple<BlockPos, BlockState, CookingPotBlockEntity> result = null;
        BlockPos posAbove = getBlockPos().above();
        BlockState stateAbove = level.getBlockState(posAbove);
        if (level.getBlockEntity(posAbove) instanceof CookingPotBlockEntity cookingPot) {
            result = Triple.of(posAbove, stateAbove, cookingPot);
        } else {
            if (stateAbove.is(ModTags.HEAT_CONDUCTORS)) {
                BlockPos posFurtherAbove = posAbove.above();
                if (level.getBlockEntity(posFurtherAbove) instanceof CookingPotBlockEntity cookingPot
                && !cookingPot.requiresDirectHeat()) {
                    result = Triple.of(posFurtherAbove, level.getBlockState(posFurtherAbove), cookingPot);
                }
            }
        }
        if (result == null) return;
        for (int i = 0; i < times - 1; ++i) {
            CookingPotBlockEntity.cookingTick(level, result.getLeft(), result.getMiddle(), result.getRight());
        }
    }
    
    public void addSmokeAtItem(int slot, int amount) {
        Direction direction = getBlockState().getValue(StoveBlock.FACING);
        int directionIndex = direction.get2DDataValue();
        Vec2 offset = directionIndex % 2 == 0 ? ITEM_OFFSET_NS[slot] : ITEM_OFFSET_WE[slot];
        double x = worldPosition.getX() + 0.5D +
            (direction.getClockWise().getStepX() - direction.getStepX()) * offset.x;
        double y = worldPosition.getY() + 1.0D;
        double z = worldPosition.getZ() + 0.5D +
            (direction.getClockWise().getStepZ() - direction.getStepZ()) * offset.y;
        for (int k = 0; k < amount; ++k) {
            level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 5.0E-4D, 0.0D);
        }
    }
    
    static {
        float scale = 5 / 16F;
        for (int i = 0; i < 9; ++i) {
            float x = (i % 3 - 1) * scale;
            float y = (i / 3 - 1) * scale;
            ITEM_OFFSET_NS[i] = new Vec2(x, y);
            ITEM_OFFSET_WE[i] = new Vec2(y, x);
        }
    }
    
}
