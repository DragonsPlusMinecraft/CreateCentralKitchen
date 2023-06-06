package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.sammy.minersdelight.content.block.copper_pot.CopperPotBlockEntity;
import com.sammy.minersdelight.setup.MDBlocks;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.minersCooking.MinersCookingGuide;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.minersCooking.MinersCookingGuideItem;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;

import java.util.function.Consumer;

public class CopperPotPoint extends ArmInteractionPoint {
    public static final int INPUT_SLOT_COUNT = 4;
    public static final int CONTAINER_SLOT = 5;
    public static final int OUTPUT_SLOT = 6;

    public CopperPotPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.upFromBottomCenterOf(pos, .625);
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    protected IItemHandler getHandler() {
        if (!cachedHandler.isPresent()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof CopperPotBlockEntity copperPot))
                return null;
            cachedHandler = LazyOptional.of(copperPot::getInventory);
        }
        return cachedHandler.orElse(null);
    }

    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return stack;

        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof MinersCookingGuideItem))
            return stack;

        MinersCookingGuide guide = MinersCookingGuide.of(guideStack);
        if (guide.getResult().isEmpty())
            return stack;

        IItemHandler inventory = getHandler();
        if (inventory == null)
            return stack;

        if (inventory.getStackInSlot(CONTAINER_SLOT).isEmpty() && guide.isContainer(stack))
            return inventory.insertItem(CONTAINER_SLOT, stack, simulate);

        boolean[] neededSlots = new boolean[INPUT_SLOT_COUNT];
        int neededSlotCount = 0;
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            if (inventory.getStackInSlot(slot).isEmpty() &&
                guide.needIngredient(slot) &&
                guide.isIngredient(slot, stack))
            {
                neededSlots[slot] = true;
                neededSlotCount++;
            }
        }

        if (neededSlotCount == 0)
            return stack;

        int countPerSlot = stack.getCount() / neededSlotCount;
        ItemStack ret = stack.copy();
        for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
            if (neededSlots[slot]) {
                ItemStack inserted = ret.split(countPerSlot);
                inventory.insertItem(slot, inserted, simulate);
            }
        }

        return ret;
    }

    @Override
    public ItemStack extract(int slot, int amount, boolean simulate) {
        if (slot == OUTPUT_SLOT) {
            return super.extract(slot, amount, simulate);
        }

        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return ItemStack.EMPTY;

        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof MinersCookingGuideItem))
            return ItemStack.EMPTY;

        MinersCookingGuide guide = MinersCookingGuide.of(guideStack);
        if (guide.getResult().isEmpty())
            return ItemStack.EMPTY;

        IItemHandler inventory = getHandler();
        if (inventory == null)
            return ItemStack.EMPTY;

        if (slot < INPUT_SLOT_COUNT) {
            ItemStack ingredient = inventory.getStackInSlot(slot);
            if (!ingredient.isEmpty() && !guide.isIngredient(slot, ingredient))
                return inventory.extractItem(slot, amount, simulate);
        } else if(slot == CONTAINER_SLOT) {
            ItemStack container = inventory.getStackInSlot(slot);
            if (!container.isEmpty() && !guide.isContainer(container))
                return inventory.extractItem(slot, amount, simulate);
        }

        return ItemStack.EMPTY;
    }

    public static class Type extends PonderArmInteractionPointType {

        public Type(ResourceLocation id) {
            super(id);
        }

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof CopperPotBlockEntity &&
                level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CopperPotPoint(this, level, pos, state);
        }

        @Override
        public void addToPonderTag(Consumer<ItemLike> consumer) {
            PonderRegistry.TAGS.forTag(AllPonderTags.ARM_TARGETS).add(MDBlocks.COPPER_POT.get());
        }

    }

}