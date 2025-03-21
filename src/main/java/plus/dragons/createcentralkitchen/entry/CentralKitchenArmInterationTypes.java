package plus.dragons.createcentralkitchen.entry;

import com.sammy.minersdelight.content.block.copper_pot.CopperPotBlockEntity;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlock;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuide;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuideItem;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.minersCooking.MinersCookingGuide;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.minersCooking.MinersCookingGuideItem;
import plus.dragons.createcentralkitchen.foundation.mixin.common.farmersdelight.SkilletBlockEntityAccessor;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.block.entity.*;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = CentralKitchen.ID, bus = Bus.MOD)
public class CentralKitchenArmInterationTypes {
    
//    private static <T extends PonderArmInteractionPointType> T create(String name, Function<ResourceLocation, T> factory) {
//        ResourceLocation id = CentralKitchen.genRL(name);
//        return factory.apply(id);
//    }
    
    private static <T extends ArmInteractionPointType> void register(String name, T type) {
            Registry.register(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE, CentralKitchen.genRL(name), type);
    }
    
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        if (Mods.isLoaded(Mods.FD)) {
            register("cutting_board", new CuttingBoardType());
            register("basket", new BasketType());
            register("stove", new StoveType());
            register("blaze_stove", new BlazeStoveType());
            register("skillet", new SkilletType());
            register("cooking_pot", new CookingPotType());
            /*, KETTLE TODO*/
        }
        if (Mods.isLoaded(Mods.MD)) {
            register("copper_pot", new CopperPotType());
        }
    }
    
//    @SubscribeEvent
//    public static void registerPonderTags(FMLClientSetupEvent event) {
//        event.enqueueWork(() -> {
//            Consumer<ItemLike> consumer = PonderTagRegistry PonderRegist.TAGS.forTag(AllCreatePonderTags.ARM_TARGETS)::add;
//            for (var type : TYPES)
//                type.addToPonderTag(consumer);
//        });
//    }

    public static class CuttingBoardType extends ArmInteractionPointType {

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity;
        }

        @Override
        public @Nullable ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CuttingBoardPoint(this, level, pos, state);
        }
    }
    
    public static class CuttingBoardPoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
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
    }

    // Farmer's Delight arm interaction points

    public static class BasketType extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof BasketBlockEntity;
        }

        @Override
        public @Nullable ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new BasketPoint(this, level, pos, state);
        }
    }

    public static class BasketPoint extends ArmInteractionPoint {
        public BasketPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
            super(type, level, pos, state);
        }

        @Override
        protected Vec3 getInteractionPositionVector() {
            return Vec3.atCenterOf(pos);
        }
    }

    public static class StoveType extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof StoveBlockEntity;
        }

        @Override
        public @Nullable ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new StovePoint(this, level, pos, state);
        }
    }

    public static class StovePoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {

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
    }

    public static class BlazeStoveType extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof BlazeStoveBlockEntity;
        }

        @Override
        public @Nullable ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new BlazeStovePoint(this, level, pos, state);
        }
    }

    public static class BlazeStovePoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
        public BlazeStovePoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
            super(type, level, pos, state);
        }

        @Override
        protected Vec3 getInteractionPositionVector() {
            return Vec3.upFromBottomCenterOf(pos, 1);
        }

        @Override
        public ItemStack insert(ItemStack stack, boolean simulate) {
            ItemStack input = stack.copy();
            InteractionResultHolder<ItemStack> res = BlazeStoveBlock.tryInsert(level, pos, input, false, false, simulate);
            ItemStack remainder = res.getObject();
            if (input.isEmpty()) {
                return remainder;
            } else {
                if (!simulate)
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), remainder);
                return input;
            }
        }
    }

    public static class CookingPotType extends ArmInteractionPointType {
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

    public static class CookingPotPoint extends ArmInteractionPoint {
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

            boolean[] neededSlots = new boolean[INPUT_SLOT_COUNT];
            int neededSlotCount = 0;
            for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
                if (inventory.getStackInSlot(slot).isEmpty() &&
                        guide.needIngredient(slot) &&
                        guide.isIngredient(slot, stack)) {
                    neededSlots[slot] = true;
                    neededSlotCount++;
                }
            }

            if (neededSlotCount == 0)
                return stack;

            ItemStack ret = stack.copy();
            ret.shrink(neededSlotCount);
            for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
                if (neededSlots[slot]) {
                    ItemStack inserted = stack.copy();
                    inserted.setCount(1);
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
    }

    public static class SkilletType extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof SkilletBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new SkilletPoint(this, level, pos, state);
        }
    }

    public static class SkilletPoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
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
    }

    // Miner's Delight arm interaction points

    public static class CopperPotType extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof CopperPotBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CopperPotPoint(this, level, pos, state);
        }
    }

    public static class CopperPotPoint extends ArmInteractionPoint {
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

            ItemStack ret = stack.copy();
            ret.shrink(neededSlotCount);
            for (int slot = 0; slot < INPUT_SLOT_COUNT; slot++) {
                if (neededSlots[slot]) {
                    ItemStack inserted = stack.copy();
                    inserted.setCount(1);
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
    }
}
