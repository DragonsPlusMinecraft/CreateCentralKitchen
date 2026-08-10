package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
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
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuide;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuideItem;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.registry.ModItems;

public class CookingPotPoint extends ArmInteractionPoint {
    public static final int INPUT_SLOT_COUNT = 6;
    public static final int CONTAINER_SLOT = 7;
    public static final int OUTPUT_SLOT = 8;

    public CookingPotPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
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
            if (!(be instanceof CookingPotBlockEntity cookingPot))
                return null;
            cachedHandler = LazyOptional.of(cookingPot::getInventory);
        }
        return cachedHandler.orElse(null);
    }

    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return stack;

        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof CookingGuideItem))
            return stack;

        CookingGuide guide = CookingGuide.of(guideStack);
        if (guide.getResult().isEmpty())
            return stack;

        IItemHandler inventory = getHandler();
        if (inventory == null)
            return stack;

        if (inventory.getStackInSlot(CONTAINER_SLOT).isEmpty() && guide.isContainer(stack))
            return inventory.insertItem(CONTAINER_SLOT, stack, simulate);

        return GuidedPotInsertion.insertIntoMatchingSlots(inventory, stack, simulate, INPUT_SLOT_COUNT,
                slot -> guide.needIngredient(slot) && guide.isIngredient(slot, stack));
    }

    @Override
    public ItemStack extract(int slot, int amount, boolean simulate) {
        if (slot == OUTPUT_SLOT) {
            return super.extract(slot, amount, simulate);
        }

        if (!(level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity blazeStove))
            return ItemStack.EMPTY;

        ItemStack guideStack = blazeStove.getGuide();
        if (!(guideStack.getItem() instanceof CookingGuideItem))
            return ItemStack.EMPTY;

        CookingGuide guide = CookingGuide.of(guideStack);
        if (guide.getResult().isEmpty())
            return ItemStack.EMPTY;

        IItemHandler inventory = getHandler();
        if (inventory == null)
            return ItemStack.EMPTY;

        if (slot < INPUT_SLOT_COUNT) {
            ItemStack ingredient = inventory.getStackInSlot(slot);
            if (!ingredient.isEmpty() && !guide.isIngredient(slot, ingredient))
                return inventory.extractItem(slot, amount, simulate);
        } else if (slot == CONTAINER_SLOT) {
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
            return level.getBlockEntity(pos) instanceof CookingPotBlockEntity &&
                    level.getBlockEntity(pos.below()) instanceof BlazeStoveBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CookingPotPoint(this, level, pos, state);
        }

        @Override
        public void addToPonderTag(PonderTagRegistrationHelper<ResourceLocation> helper) {
            helper.addToTag(AllCreatePonderTags.ARM_TARGETS).add(ModItems.COOKING_POT.getId());
//            PonderRegistry.TAGS.forTag(AllCreatePonderTags.ARM_TARGETS).add(ModItems.COOKING_POT.get());
        }
    }
}
