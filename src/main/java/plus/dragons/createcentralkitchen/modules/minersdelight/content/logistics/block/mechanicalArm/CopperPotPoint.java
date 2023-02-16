package plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.block.mechanicalArm;

import com.sammy.minersdelight.content.block.copper_pot.CopperPotBlockEntity;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
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
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.item.guide.MinersCookingGuide;
import plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.item.guide.MinersCookingGuideItem;

public class CopperPotPoint extends ArmInteractionPoint {
    public static final int MEAL_DISPLAY_SLOT = 4;
    public static final int CONTAINER_SLOT = 5;
    public static final int OUTPUT_SLOT = 6;
    
    public CopperPotPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
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
            if (!(be instanceof CopperPotBlockEntity copperPot))
                return null;
            cachedHandler = LazyOptional.of(copperPot::getInventory);
        }
        return cachedHandler.orElse(null);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return stack;
        
        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof MinersCookingGuideItem))
            return stack;
        
        MinersCookingGuide guide = MinersCookingGuide.of(guideStack);
        if (guide.getResult().isEmpty())
            return stack;
        
        IItemHandler inventory = getHandler();
        if (inventory == null)
            return stack;
        
        if (inventory.getStackInSlot(CONTAINER_SLOT).isEmpty() && guide.isContainer(stack))
            return inventory.insertItem(CONTAINER_SLOT, stack, simulate);
        
        for (int slot = 0; slot < MEAL_DISPLAY_SLOT; slot++) {
            if (inventory.getStackInSlot(slot).isEmpty() &&
                guide.needIngredient(slot) &&
                guide.isIngredient(slot, stack))
                return inventory.insertItem(slot, stack, simulate);
        }
        
        return stack;
    }
    
    @Override
    public ItemStack extract(int slot, int amount, boolean simulate) {
        if (slot == OUTPUT_SLOT) {
            return super.extract(slot, amount, simulate);
        }
    
        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return ItemStack.EMPTY;
    
        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof MinersCookingGuideItem))
            return ItemStack.EMPTY;
    
        MinersCookingGuide guide = MinersCookingGuide.of(guideStack);
        if (guide.getResult().isEmpty())
            return ItemStack.EMPTY;
        
        IItemHandler inventory = getHandler();
        if (inventory == null)
            return ItemStack.EMPTY;
    
        if (slot < MEAL_DISPLAY_SLOT) {
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
    
    public static class Type extends ArmInteractionPointType {
        
        public Type(ResourceLocation id) {
            super(id);
        }
        
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof CopperPotBlockEntity &&
                level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity;
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CopperPotPoint(this, level, pos, state);
        }
        
    }
    
}