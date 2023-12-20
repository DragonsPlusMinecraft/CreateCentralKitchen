package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;

import java.util.Optional;
import java.util.function.Consumer;

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
    
    public static class Type extends PonderArmInteractionPointType {
        
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
    
        @Override
        public void addToPonderTag(Consumer<ItemLike> consumer) {
            var builder = PonderRegistry.TAGS.forTag(AllPonderTags.ARM_TARGETS);
            ForgeRegistries.ITEMS
                .getValues()
                .stream()
                .filter(item -> item instanceof BlockItem blockItem && blockItem.getBlock() instanceof StoveBlock)
                .forEach(builder::add);
        }
        
    }
    
}