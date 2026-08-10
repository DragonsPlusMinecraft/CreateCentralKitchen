package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.items.ItemStackHandler;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

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
}
