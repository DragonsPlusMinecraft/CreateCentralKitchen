package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

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
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.CookingGuide;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

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
    
    @Nullable
    @Override
    protected IItemHandler getHandler() {
        if (!cachedHandler.isPresent()) {
            BlockEntity te = level.getBlockEntity(pos);
            if (!(te instanceof CookingPotBlockEntity cookingPot))
                return null;
            cachedHandler = LazyOptional.of(cookingPot::getInventory);
        }
        return cachedHandler.orElse(null);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        if (!(level.getBlockEntity(pos) instanceof CookingPotBlockEntity &&
              level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return stack;
        
        CookingGuide cookingGuide = CookingGuide.of(blazeStove.getCookingGuide());
        if (cookingGuide.getResult().isEmpty())
            return stack;
        
        IItemHandler inventory = getHandler();
        if (inventory == null)
            return stack;
        
        if (inventory.getStackInSlot(CONTAINER_SLOT).isEmpty() && cookingGuide.isContainer(stack))
            return inventory.insertItem(CONTAINER_SLOT, stack, simulate);
        
        for (int slot = 0; slot < MEAL_DISPLAY_SLOT; slot++) {
            if (inventory.getStackInSlot(slot).isEmpty() &&
                cookingGuide.needIngredient(slot) &&
                cookingGuide.isIngredient(slot, stack))
                return inventory.insertItem(slot, stack, simulate);
        }
        
        return stack;
    }
    
    @Override
    public ItemStack extract(int slot, int amount, boolean simulate) {
        if (slot == OUTPUT_SLOT) {
            return super.extract(slot, amount, simulate);
        } else {
            if (!(level.getBlockEntity(pos) instanceof CookingPotBlockEntity) ||
                !(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
                return ItemStack.EMPTY;
            else {
                CookingGuide cookingGuide = CookingGuide.of(blazeStove.getCookingGuide());
                if (cookingGuide.getResult().isEmpty())
                    return ItemStack.EMPTY;
                
                IItemHandler inventory = getHandler();
                if (inventory == null)
                    return ItemStack.EMPTY;

                if (slot < MEAL_DISPLAY_SLOT) {
                    ItemStack ingredient = inventory.getStackInSlot(slot);
                    if (!ingredient.isEmpty() && !cookingGuide.isIngredient(slot, ingredient))
                        return inventory.extractItem(slot, amount, simulate);
                } else if(slot == CONTAINER_SLOT) {
                    ItemStack container = inventory.getStackInSlot(slot);
                    if (!container.isEmpty() && !cookingGuide.isContainer(container)) {
                        return inventory.extractItem(slot, amount, simulate);
                    }
                }
            }
        }
        
        return ItemStack.EMPTY;
    }
    
    public static class Type extends ArmInteractionPointType {
        
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
        
    }
    
}