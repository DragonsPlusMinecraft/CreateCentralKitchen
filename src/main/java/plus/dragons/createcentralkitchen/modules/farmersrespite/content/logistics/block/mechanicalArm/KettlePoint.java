package plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide.CookingGuide;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuide;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideItem;
import umpaz.farmersrespite.common.block.KettleBlock;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

public class KettlePoint extends ArmInteractionPoint {
    public static final int DISPLAY_SLOT = 2;
    public static final int CONTAINER_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;
    
    public KettlePoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atBottomCenterOf(pos).add(0, .625, 0);
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
        if (!(level.getBlockEntity(pos) instanceof KettleBlockEntity &&
              level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
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
        
        for (int slot = 0; slot < DISPLAY_SLOT; slot++) {
            if (inventory.getStackInSlot(slot).isEmpty() &&
                guide.needIngredient(slot) &&
                guide.isIngredient(slot, stack))
                return inventory.insertItem(slot, stack, simulate);
        }
    
        if (guide.needWater()) {
            BlockState state = level.getBlockState(pos);
            int water = state.getValue(KettleBlock.WATER_LEVEL);
            if (water == 3)
                return stack;
            ItemStack ret = stack.copy();
            ItemStack drop = ItemStack.EMPTY;
            if (stack.is(Items.WATER_BUCKET)) {
                ret.shrink(1);
                drop = Items.BUCKET.getDefaultInstance();
                water = 3;
            } else if (stack.is(Items.POTION) && PotionUtils.getPotion(stack) == Potions.WATER) {
                ret.shrink(1);
                drop = Items.GLASS_BOTTLE.getDefaultInstance();
                water += 1;
            }
            if (simulate) {
                return ret;
            } else if (!drop.isEmpty()) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), drop);
                level.setBlockAndUpdate(pos, state.setValue(KettleBlock.WATER_LEVEL, water));
            }
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
                CookingGuide cookingGuide = CookingGuide.of(blazeStove.getGuide());
                if (cookingGuide.getResult().isEmpty())
                    return ItemStack.EMPTY;
                
                IItemHandler inventory = getHandler();
                if (inventory == null)
                    return ItemStack.EMPTY;

                if (slot < DISPLAY_SLOT) {
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
            return level.getBlockEntity(pos) instanceof KettleBlockEntity &&
                    level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity;
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new KettlePoint(this, level, pos, state);
        }
        
    }
    
}