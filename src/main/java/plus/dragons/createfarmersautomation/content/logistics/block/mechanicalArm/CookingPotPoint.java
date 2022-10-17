package plus.dragons.createfarmersautomation.content.logistics.block.mechanicalArm;

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
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

public class CookingPotPoint extends ArmInteractionPoint {
    public static final int MEAL_DISPLAY_SLOT = 6;
    public static final int CONTAINER_SLOT = 7;
    public static final int OUTPUT_SLOT = 8;
    
    public CookingPotPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atBottomCenterOf(pos).add(0, .625, 0);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof CookingPotBlockEntity cookingPot)) return stack;
        IItemHandler inventory = getHandler();
        if (inventory == null) return stack;
        ItemStack currentContainer = inventory.getStackInSlot(CONTAINER_SLOT);
        ItemStack neededContainer = cookingPot.getContainer();
        if (ItemHandlerHelper.canItemStacksStack(neededContainer, currentContainer) &&
            ItemHandlerHelper.canItemStacksStack(neededContainer, stack))
            return inventory.insertItem(CONTAINER_SLOT, stack, simulate);
        int targetSlot = -1;
        int targetCount = Integer.MAX_VALUE;
        for (int slot = 0; slot < MEAL_DISPLAY_SLOT; ++slot) {
            ItemStack ingredient = inventory.getStackInSlot(slot);
            if (ItemHandlerHelper.canItemStacksStack(ingredient, stack)) {
                int count = ingredient.getCount();
                if (count < targetCount) {
                    targetSlot = slot;
                    targetCount = count;
                }
            }
        }
        return targetSlot < 0 ? stack : inventory.insertItem(targetSlot, stack, simulate);
    }
    
    @Override
    public ItemStack extract(int slot, boolean simulate) {
        if (slot != OUTPUT_SLOT) return ItemStack.EMPTY;
        return super.extract(slot, simulate);
    }
    
    @Nullable
    @Override
    protected IItemHandler getHandler() {
        if (!cachedHandler.isPresent()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof CookingPotBlockEntity cookingPot)) return null;
            cachedHandler = LazyOptional.of(cookingPot::getInventory);
        }
        return cachedHandler.resolve().orElse(null);
    }
    
    public static class Type extends ArmInteractionPointType {
        
        public Type(ResourceLocation id) {
            super(id);
        }
        
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            var be = level.getBlockEntity(pos);
            return be != null && be.getType() == ModBlockEntityTypes.COOKING_POT.get();
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CookingPotPoint(this, level, pos, state);
        }
        
    }
    
}
