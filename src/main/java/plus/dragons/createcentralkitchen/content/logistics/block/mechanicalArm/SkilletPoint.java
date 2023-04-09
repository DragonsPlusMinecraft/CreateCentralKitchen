package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.foundation.mixin.common.farmersdelight.SkilletBlockEntityAccessor;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.item.SkilletItem;

import java.util.Optional;
import java.util.function.Consumer;

public class SkilletPoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
    
    public SkilletPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, .125);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof SkilletBlockEntity skillet))
            return stack;
        ItemStack cookingStack = skillet.getStoredStack();
        if (cookingStack.isEmpty()) {
            Optional<CampfireCookingRecipe> recipe = ((SkilletBlockEntityAccessor)skillet).callGetMatchingRecipe(new SimpleContainer(stack));
            if (recipe.isEmpty()) return stack;
        }
        ItemStack remainder = stack.copy();
        if (simulate) return skillet.getInventory().insertItem(0, remainder, true);
        return skillet.addItemToCook(remainder, null);
    }
    
    public static class Type extends PonderArmInteractionPointType {
        
        public Type(ResourceLocation id) {
            super(id);
        }
        
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof SkilletBlockEntity;
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new SkilletPoint(this, level, pos, state);
        }
    
        @Override
        public void addToPonderTag(Consumer<ItemLike> consumer) {
            var builder = PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS);
            ForgeRegistries.ITEMS
                .getValues()
                .stream()
                .filter(item -> item instanceof SkilletItem)
                .forEach(builder::add);
        }
        
    }
    
}