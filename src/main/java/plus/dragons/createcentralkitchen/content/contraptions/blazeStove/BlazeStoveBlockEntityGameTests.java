package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.items.ItemStackHandler;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;

@ModLoadSubscriber(modid = Mods.FD)
@PrefixGameTestTemplate(false)
public class BlazeStoveBlockEntityGameTests {
    private static final BlockPos STOVE_POS = new BlockPos(1, 2, 1);

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(BlazeStoveBlockEntityGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void burningResetsEveryOccupiedCookingSlot(GameTestHelper helper) {
        var level = helper.getLevel();
        level.setBlock(helper.absolutePos(STOVE_POS), FDBlockEntries.BLAZE_STOVE.getDefaultState(), 3);
        BlockEntity blockEntity = level.getBlockEntity(helper.absolutePos(STOVE_POS));
        helper.assertTrue(blockEntity instanceof BlazeStoveBlockEntity,
                "Expected a blaze stove block entity");
        BlazeStoveBlockEntity stove = (BlazeStoveBlockEntity) blockEntity;

        int slots = stove.getInventory().getSlots();
        ItemStackHandler inventory = new ItemStackHandler(slots);
        inventory.setStackInSlot(0, new ItemStack(Items.BEEF));
        inventory.setStackInSlot(1, new ItemStack(Items.CHICKEN));
        int[] cookingTimes = new int[slots];
        int[] cookingTimesTotal = new int[slots];
        cookingTimes[0] = 11;
        cookingTimes[1] = 22;
        cookingTimesTotal[0] = 111;
        cookingTimesTotal[1] = 222;

        CompoundTag before = new CompoundTag();
        before.put("Inventory", inventory.serializeNBT());
        before.putIntArray("CookingTimes", cookingTimes);
        before.putIntArray("CookingTotalTimes", cookingTimesTotal);
        stove.read(before, false);
        stove.burnIngredients();

        CompoundTag after = new CompoundTag();
        stove.write(after, false);
        int[] cookingTimesAfter = after.getIntArray("CookingTimes");
        int[] cookingTimesTotalAfter = after.getIntArray("CookingTotalTimes");
        for (int slot = 0; slot < slots; slot++) {
            helper.assertTrue(cookingTimesAfter[slot] == 0,
                    "Burning left cooking progress in slot " + slot);
            helper.assertTrue(cookingTimesTotalAfter[slot] == 0,
                    "Burning left a total cooking time in slot " + slot);
            helper.assertTrue(stove.getInventory().getStackInSlot(slot).isEmpty(),
                    "Burning left an item in slot " + slot);
        }
        helper.succeed();
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void missingRecipesDoNotDestroyCookingInputs(GameTestHelper helper) {
        var level = helper.getLevel();
        level.setBlock(helper.absolutePos(STOVE_POS), FDBlockEntries.BLAZE_STOVE.getDefaultState(), 3);
        BlockEntity blockEntity = level.getBlockEntity(helper.absolutePos(STOVE_POS));
        helper.assertTrue(blockEntity instanceof BlazeStoveBlockEntity,
                "Expected a Blaze Stove block entity");
        BlazeStoveBlockEntity stove = (BlazeStoveBlockEntity) blockEntity;

        int slots = stove.getInventory().getSlots();
        ItemStackHandler inventory = new ItemStackHandler(slots);
        inventory.setStackInSlot(0, new ItemStack(Items.BEDROCK));
        int[] cookingTimes = new int[slots];
        int[] cookingTimesTotal = new int[slots];
        cookingTimes[0] = 1;
        cookingTimesTotal[0] = 1;

        CompoundTag before = new CompoundTag();
        before.put("Inventory", inventory.serializeNBT());
        before.putIntArray("CookingTimes", cookingTimes);
        before.putIntArray("CookingTotalTimes", cookingTimesTotal);
        stove.read(before, false);
        stove.processCooking(1);

        helper.assertTrue(stove.getInventory().getStackInSlot(0).is(Items.BEDROCK),
                "A missing campfire recipe destroyed its cooking input");
        CompoundTag after = new CompoundTag();
        stove.write(after, false);
        helper.assertTrue(after.getIntArray("CookingTimes")[0] == 0,
                "A missing campfire recipe did not reset cooking progress");
        helper.assertTrue(after.getIntArray("CookingTotalTimes")[0] == 1,
                "A missing campfire recipe discarded the retry duration");
        helper.succeed();
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void burnerConversionsPreserveFuelState(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos stovePos = helper.absolutePos(STOVE_POS);
        var burnerState = AllBlocks.BLAZE_BURNER.getDefaultState()
                .setValue(BlazeBurnerBlock.FACING, Direction.EAST)
                .setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.KINDLED);
        level.setBlock(stovePos, burnerState, 3);
        BlockEntity originalBlockEntity = level.getBlockEntity(stovePos);
        helper.assertTrue(originalBlockEntity instanceof BlazeBurnerBlockEntity,
                "Expected a Blaze Burner block entity");
        BlazeBurnerBlockEntity original = (BlazeBurnerBlockEntity) originalBlockEntity;
        CompoundTag burnerData = new CompoundTag();
        burnerData.putInt("fuelLevel", BlazeBurnerBlockEntity.FuelType.NORMAL.ordinal());
        burnerData.putInt("burnTimeRemaining", 2345);
        burnerData.putBoolean("Goggles", true);
        burnerData.putBoolean("TrainHat", true);
        original.load(burnerData);

        var player = helper.makeMockPlayer();
        player.setShiftKeyDown(true);
        ItemStack guide = FDItemEntries.COOKING_GUIDE.asStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, guide);
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(stovePos), Direction.UP, stovePos, false);
        UseOnContext context = new UseOnContext(player, InteractionHand.MAIN_HAND, hit);
        InteractionResult installResult = guide.getItem().useOn(context);
        helper.assertTrue(installResult.consumesAction(), "Installing the cooking guide failed");
        assertBurnerState(helper, stovePos, BlazeStoveBlockEntity.class);

        BlockEntity installedBlockEntity = level.getBlockEntity(stovePos);
        BlazeStoveBlock stoveBlock = (BlazeStoveBlock) level.getBlockState(stovePos).getBlock();
        InteractionResult wrenchResult = stoveBlock.onSneakWrenched(level.getBlockState(stovePos), context);
        helper.assertTrue(wrenchResult.consumesAction(), "Removing the cooking guide failed");
        helper.assertTrue(installedBlockEntity.isRemoved(), "The replaced Blaze Stove remained active");
        assertBurnerState(helper, stovePos, BlazeBurnerBlockEntity.class);
        helper.succeed();
    }

    private static void assertBurnerState(GameTestHelper helper, BlockPos pos,
            Class<? extends BlazeBurnerBlockEntity> expectedType) {
        BlockEntity blockEntity = helper.getLevel().getBlockEntity(pos);
        helper.assertTrue(expectedType.isInstance(blockEntity),
                "Expected " + expectedType.getSimpleName());
        BlazeBurnerBlockEntity burner = (BlazeBurnerBlockEntity) blockEntity;
        helper.assertTrue(burner.getActiveFuel() == BlazeBurnerBlockEntity.FuelType.NORMAL,
                "Blaze Burner fuel type was reset during conversion");
        helper.assertTrue(burner.getRemainingBurnTime() == 2345,
                "Blaze Burner burn time was reset during conversion");
        helper.assertTrue(burner.goggles, "Blaze Burner goggles were lost during conversion");
        helper.assertTrue(burner.hat, "Blaze Burner train hat was lost during conversion");
        helper.assertTrue(burner.getBlockState().getValue(BlazeBurnerBlock.HEAT_LEVEL) == BlazeBurnerBlock.HeatLevel.KINDLED,
                "Blaze Burner heat level was reset during conversion");
        helper.assertTrue(burner.getBlockState().getValue(BlazeBurnerBlock.FACING) == Direction.EAST,
                "Blaze Burner facing was reset during conversion");
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void createHeatLevelsControlFarmersDelightHeating(GameTestHelper helper) {
        var level = helper.getLevel();
        var stovePos = helper.absolutePos(STOVE_POS);
        level.setBlock(stovePos, FDBlockEntries.BLAZE_STOVE.getDefaultState(), 3);
        BlockEntity blockEntity = level.getBlockEntity(stovePos);
        helper.assertTrue(blockEntity instanceof BlazeStoveBlockEntity,
                "Expected a Blaze Stove block entity");
        BlazeStoveBlockEntity stove = (BlazeStoveBlockEntity) blockEntity;
        HeatableBlockEntity indirectHeat = new HeatableBlockEntity() {};
        HeatableBlockEntity directHeat = new HeatableBlockEntity() {
            @Override
            public boolean requiresDirectHeat() {
                return true;
            }
        };

        stove.setBlockHeat(BlazeBurnerBlock.HeatLevel.NONE);
        helper.assertBlockProperty(STOVE_POS, BlazeStoveBlock.LIT, false);
        helper.assertTrue(!indirectHeat.isHeated(level, stovePos.above()),
                "An inactive Blaze Stove was accepted as a direct heat source");
        stove.setBlockHeat(BlazeBurnerBlock.HeatLevel.SMOULDERING);
        helper.assertBlockProperty(STOVE_POS, BlazeStoveBlock.LIT, true);
        helper.assertTrue(indirectHeat.isHeated(level, stovePos.above()),
                "An active Blaze Stove was rejected as a direct heat source");

        level.setBlock(stovePos.above(), Blocks.HOPPER.defaultBlockState(), 3);
        var cookingPos = stovePos.above(2);
        helper.assertTrue(indirectHeat.isHeated(level, cookingPos),
                "An active Blaze Stove did not heat through a conductor");
        helper.assertTrue(!directHeat.isHeated(level, cookingPos),
                "A direct-only appliance accepted heat through a conductor");
        stove.setBlockHeat(BlazeBurnerBlock.HeatLevel.NONE);
        helper.assertBlockProperty(STOVE_POS, BlazeStoveBlock.LIT, false);
        helper.assertTrue(!indirectHeat.isHeated(level, cookingPos),
                "An inactive Blaze Stove heated through a conductor");
        helper.succeed();
    }
}
