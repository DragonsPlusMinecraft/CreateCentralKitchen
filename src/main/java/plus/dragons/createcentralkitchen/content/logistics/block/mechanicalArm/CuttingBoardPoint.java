package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.function.Consumer;


public class CuttingBoardPoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
    private final RecipeWrapper recipeWrapper = new RecipeWrapper(new ItemStackHandler(1));
    
    public CuttingBoardPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, .125);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof CuttingBoardBlockEntity cuttingBoard) || !cuttingBoard.isEmpty())
            return stack;
        ItemStack input = stack.copy();
        recipeWrapper.setItem(0, input);
        if (level.getRecipeManager().getRecipesFor(ModRecipeTypes.CUTTING.get(), recipeWrapper, level).isEmpty()) {
            return stack;
        }
        if (simulate)
            input.shrink(1);
        else
            cuttingBoard.addItem(input);
        return input;
    }
    
    public static class Type extends PonderArmInteractionPointType {

        public Type(ResourceLocation id) {
            super(id);
        }

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity;
        }

        @Nullable
        @Override
        public CuttingBoardPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CuttingBoardPoint(this, level, pos, state);
        }
    
        @Override
        public void addToPonderTag(Consumer<ItemLike> consumer) {
            var builder = PonderRegistry.TAGS.forTag(AllPonderTags.ARM_TARGETS);
            ForgeRegistries.ITEMS
                .getValues()
                .stream()
                .filter(item -> item instanceof BlockItem blockItem && blockItem.getBlock() instanceof CuttingBoardBlock)
                .forEach(builder::add);
        }

    }
}
