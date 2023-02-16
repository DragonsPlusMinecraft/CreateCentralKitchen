package plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;

import java.util.Optional;

public class StovePoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
    
    public StovePoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, 1);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof StoveBlockEntity stove))
            return stack;
        int slot = stove.getNextEmptySlot();
        if (slot < 0 || slot >= stove.getInventory().getSlots() || stove.isStoveBlockedAbove()) {
            return stack;
        }
        Optional<CampfireCookingRecipe> recipe = stove.getMatchingRecipe(new SimpleContainer(stack), slot);
        if (recipe.isEmpty())
            return stack;
        ItemStack remainder = stack.copy();
        if (simulate) {
            remainder.shrink(1);
            return remainder;
        }
        stove.addItem(remainder, recipe.get(), slot);
        return remainder;
    }
    
    public static class Type extends ArmInteractionPointType {
        
        public Type(ResourceLocation id) {
            super(id);
        }
        
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof StoveBlockEntity;
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new StovePoint(this, level, pos, state);
        }
        
    }
    
}