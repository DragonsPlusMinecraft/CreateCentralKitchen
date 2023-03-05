package plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.core.integration.create.logistics.block.mechanicalArm.CentralKitchenArmInteractionPointType;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide.CookingGuide;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide.CookingGuideItem;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.registry.ModItems;

public class CookingPotPoint extends ArmInteractionPoint {
    public static final int INPUT_SLOT_COUNT = 6;
    public static final int CONTAINER_SLOT = 7;
    public static final int OUTPUT_SLOT = 8;
    
    public CookingPotPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, .625);
    }
    
    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    protected IItemHandler getHandler() {
        if (!cachedHandler.isPresent()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof CookingPotBlockEntity cookingPot))
                return null;
            cachedHandler = LazyOptional.of(cookingPot::getInventory);
        }
        return cachedHandler.orElse(null);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return stack;
        
        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof CookingGuideItem))
            return stack;
        
        CookingGuide guide = CookingGuide.of(guideStack);
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
        
        if (neededSlotCount == 0)
            return stack;
        
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
    
    @Override
    public ItemStack extract(int slot, int amount, boolean simulate) {
        if (slot == OUTPUT_SLOT) {
            return super.extract(slot, amount, simulate);
        }
    
        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return ItemStack.EMPTY;
    
        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof CookingGuideItem))
            return ItemStack.EMPTY;
    
        CookingGuide guide = CookingGuide.of(guideStack);
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
    
    public static class Type extends CentralKitchenArmInteractionPointType {
        
        public Type(ResourceLocation id) {
            super(id);
        }
        
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof CookingPotBlockEntity &&
                level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity;
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CookingPotPoint(this, level, pos, state);
        }
    
        @Override
        public void registerPonderTag() {
            PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS).add(ModItems.COOKING_POT.get());
        }
        
    }
    
}