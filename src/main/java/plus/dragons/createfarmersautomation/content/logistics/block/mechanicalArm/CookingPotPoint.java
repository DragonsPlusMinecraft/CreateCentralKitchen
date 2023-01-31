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
import plus.dragons.createfarmersautomation.content.contraptions.components.stove.BlazeStoveBlockEntity;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
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
        if (!(level.getBlockEntity(pos) instanceof CookingPotBlockEntity) ||
                !(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return stack;
        CookingPotRecipe recipe = blazeStove.getRecipe();
        if (recipe==null) return stack;
        IItemHandler inventory = getHandler();
        if (inventory == null) return stack;
        // TODO Need Test

        if(!getHandler().getStackInSlot(MEAL_DISPLAY_SLOT).isEmpty()){
            if(stack.is(blazeStove.getRecipe().getOutputContainer().getItem())){
                return getHandler().insertItem(CONTAINER_SLOT,stack,simulate);
            }
        }

        var needIngredients = blazeStove.getCookingGuideIngredients();
        for (int slot = 0; slot < MEAL_DISPLAY_SLOT; slot++) {
            if (inventory.getStackInSlot(slot).isEmpty() &&
                    !needIngredients.get(slot).isEmpty() &&
                    needIngredients.get(slot).is(stack.getItem())) {
                return getHandler().insertItem(slot,stack,simulate);
            }
        }
        return stack;
    }
    
    @Override
    public ItemStack extract(int slot, boolean simulate) {
        if (slot == OUTPUT_SLOT) return super.extract(slot, simulate);
        else {
            if (!(level.getBlockEntity(pos) instanceof CookingPotBlockEntity) ||
                    !(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
                return ItemStack.EMPTY;
            else {
                // TODO Need Test
                CookingPotRecipe recipe = blazeStove.getRecipe();
                if (recipe==null) return ItemStack.EMPTY;
                IItemHandler inventory = getHandler();
                if (inventory == null) return ItemStack.EMPTY;

                if(slot < MEAL_DISPLAY_SLOT){
                    var needIngredients = blazeStove.getCookingGuideIngredients();
                    if (!inventory.getStackInSlot(slot).isEmpty() &&
                            !needIngredients.get(slot).is(inventory.getStackInSlot(slot).getItem())) {
                        return getHandler().extractItem(slot,64,simulate);
                    }
                } else if(slot == CONTAINER_SLOT){
                    if(!getHandler().getStackInSlot(MEAL_DISPLAY_SLOT).isEmpty() &&
                            !getHandler().getStackInSlot(CONTAINER_SLOT).is(blazeStove.getRecipe().getOutputContainer().getItem())){
                            return getHandler().extractItem(slot,64,simulate);
                    }
                }
            }
        }
        return ItemStack.EMPTY;
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