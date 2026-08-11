package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.items.ItemStackHandler;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;
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
