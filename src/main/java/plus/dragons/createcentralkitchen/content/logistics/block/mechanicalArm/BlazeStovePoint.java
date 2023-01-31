package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

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
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.BlazeStoveBlockEntity;

import java.util.Optional;

public class BlazeStovePoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {

    public BlazeStovePoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atBottomCenterOf(pos).add(0, 1, 0);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof BlazeStoveBlockEntity stove))
            return stack;
        // TODO support fuel insertion
        int slot = stove.getNextEmptySlot();
        if (slot < 0 || slot >= stove.getInventory().getSlots() || stove.isBlockedAbove()) {
            return stack;
        }
        Optional<CampfireCookingRecipe> recipe = stove.findRecipe(new SimpleContainer(stack), slot);
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
            return level.getBlockEntity(pos) instanceof BlazeStoveBlockEntity;
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new BlazeStovePoint(this, level, pos, state);
        }
        
    }
    
}