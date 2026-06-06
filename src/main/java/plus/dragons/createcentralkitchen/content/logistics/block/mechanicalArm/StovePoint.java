package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import java.util.Optional;
import net.createmod.catnip.platform.ForgeRegisteredObjectsHelper;
import net.createmod.ponder.api.registration.MultiTagBuilder;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;

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
        if (!(blockEntity instanceof AbstractStoveBlockEntity stove))
            return stack;
        int slot = stove.getNextEmptySlot();
        if (slot < 0) {
            return stack;
        }
        Optional<?> recipe = stove.getCookingRecipe(stack);
        if (recipe.isEmpty())
            return stack;
        ItemStack remainder = stack.copy();
        if (simulate) {
            remainder.shrink(1);
            return remainder;
        }
        stove.placeFood(null, remainder, slot);
        return remainder;
    }

    public static class Type extends PonderArmInteractionPointType {
        public Type(ResourceLocation id) {
            super(id);
        }

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof AbstractStoveBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new StovePoint(this, level, pos, state);
        }

        @Override
        public void addToPonderTag(PonderTagRegistrationHelper<ResourceLocation> helper) {
            ForgeRegisteredObjectsHelper forgeRegisteredObjectsHelper = new ForgeRegisteredObjectsHelper();
            MultiTagBuilder.Tag<Item> builder = helper.withKeyFunction((Item s) -> forgeRegisteredObjectsHelper.getKeyOrThrow(s)).addToTag(AllCreatePonderTags.ARM_TARGETS);
//            var builder = PonderRegistry.TAGS.forTag(AllCreatePonderTags.ARM_TARGETS);
            ForgeRegistries.ITEMS
                    .getValues()
                    .stream()
                    .filter(item -> item instanceof BlockItem blockItem && blockItem.getBlock() instanceof StoveBlock)
                    .forEach(builder::add);
        }
    }
}
