package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing.BrewingGuide;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing.BrewingGuideItem;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import umpaz.farmersrespite.common.block.KettleBlock;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;
import umpaz.farmersrespite.common.registry.FRItems;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.function.Consumer;

public class KettlePoint extends ArmInteractionPoint {
    public static final int INPUT_SLOT_COUNT = 2;
    public static final int CONTAINER_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;
    
    public KettlePoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, .625);
    }
    
    @SuppressWarnings({"NullableProblems", "ConstantConditions"})
    @Nullable
    @Override
    protected IItemHandler getHandler() {
        if (!cachedHandler.isPresent()) {
            BlockEntity te = level.getBlockEntity(pos);
            if (!(te instanceof KettleBlockEntity kettle))
                return null;
            cachedHandler = LazyOptional.of(kettle::getInventory);
        }
        return cachedHandler.orElse(null);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return stack;
    
        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof BrewingGuideItem))
            return stack;
    
        BrewingGuide guide = BrewingGuide.of(guideStack);
        if (guide.getResult().isEmpty())
            return stack;
        
        IItemHandler inventory = getHandler();
        if (inventory == null)
            return stack;
        
        if (inventory.getStackInSlot(CONTAINER_SLOT).isEmpty() && guide.isContainer(stack))
            return inventory.insertItem(CONTAINER_SLOT, stack, simulate);
    
        boolean[] neededSlots = new boolean[INPUT_SLOT_COUNT];
        int neededSlotCount = 0;
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            if (inventory.getStackInSlot(slot).isEmpty() &&
                guide.needIngredient(slot) &&
                guide.isIngredient(slot, stack))
            {
                neededSlots[slot] = true;
                neededSlotCount++;
            }
        }
        
        if (neededSlotCount == 0) {
            if (guide.needWater())
                return insertWater(guide, inventory, stack, simulate);
            else
                return stack;
        }
        
        int countPerSlot = stack.getCount() / neededSlotCount;
        ItemStack ret = stack.copy();
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            if (neededSlots[slot]) {
                ItemStack inserted = ret.split(countPerSlot);
                inventory.insertItem(slot, inserted, simulate);
            }
        }
        return ret;
    }
    
    private ItemStack insertWater(BrewingGuide guide, IItemHandler inventory, ItemStack stack, boolean simulate) {
        BlockState state = level.getBlockState(pos);
        int waterLevel = state.getValue(KettleBlock.WATER_LEVEL);
        if (waterLevel == 3)
            return stack;
        
        int waterIncrement = 0;
        ItemStack remainder = stack.copy();
        ItemStack container = ItemStack.EMPTY;
    
        if (stack.is(Items.WATER_BUCKET)) {
            waterIncrement = 3 - waterLevel;
            remainder.shrink(1);
            container = Items.BUCKET.getDefaultInstance();
        } else if (stack.is(Items.POTION) && PotionUtils.getPotion(stack) == Potions.WATER) {
            waterIncrement = Math.min(stack.getCount(), 3 - waterLevel);
            remainder.shrink(waterIncrement);
            container = new ItemStack(Items.GLASS_BOTTLE, waterIncrement);
        }
    
        if (waterIncrement > 0 && !simulate) {
            if (guide.isContainer(container))
                container = inventory.insertItem(CONTAINER_SLOT, container, true);
            
            if (!container.isEmpty()) {
                Direction direction = state.getValue(KettleBlock.FACING).getCounterClockWise();
                double x = pos.getX() + 0.5D + (double) direction.getStepX() * 0.25D;
                double y = pos.getY() + 0.7D;
                double z = pos.getZ() + 0.5D + (double) direction.getStepZ() * 0.25D;
                ItemUtils.spawnItemEntity(this.level, container, x, y, z, direction.getStepX() * 0.08D, 0.25D, direction.getStepZ() * 0.08D);
            }
            
            level.setBlockAndUpdate(pos, state.setValue(KettleBlock.WATER_LEVEL, waterLevel + waterIncrement));
        }
    
        return remainder;
    }
    
    @Override
    public ItemStack extract(int slot, int amount, boolean simulate) {
        if (slot == OUTPUT_SLOT)
            return super.extract(slot, amount, simulate);
    
        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return ItemStack.EMPTY;
    
        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof BrewingGuideItem))
            return ItemStack.EMPTY;
    
        BrewingGuide guide = BrewingGuide.of(guideStack);
        if (guide.getResult().isEmpty())
            return ItemStack.EMPTY;
    
        IItemHandler inventory = getHandler();
        if (inventory == null)
            return ItemStack.EMPTY;
    
        if (slot < INPUT_SLOT_COUNT) {
            ItemStack ingredient = inventory.getStackInSlot(slot);
            if (!ingredient.isEmpty() && !guide.isIngredient(slot, ingredient))
                return inventory.extractItem(slot, amount, simulate);
        } else if(slot == CONTAINER_SLOT) {
            ItemStack container = inventory.getStackInSlot(slot);
            if (!container.isEmpty() && !guide.isContainer(container))
                return inventory.extractItem(slot, amount, simulate);
        }
        
        return ItemStack.EMPTY;
    }
    
    public static class Type extends PonderArmInteractionPointType {
        
        public Type(ResourceLocation id) {
            super(id);
        }
        
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof KettleBlockEntity &&
                level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity;
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new KettlePoint(this, level, pos, state);
        }
    
        @Override
        public void addToPonderTag(Consumer<ItemLike> consumer) {
            PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS).add(FRItems.KETTLE.get());
        }
        
    }
    
}