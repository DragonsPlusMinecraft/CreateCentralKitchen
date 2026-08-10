package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.sammy.minersdelight.content.block.copper_pot.CopperPotBlockEntity;
import com.sammy.minersdelight.setup.MDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.items.IItemHandler;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.minersCooking.MinersCookingGuide;
import plus.dragons.createcentralkitchen.entry.CentralKitchenArmInterationTypes;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;
import plus.dragons.createcentralkitchen.entry.item.MDItemEntries;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.MD)
@PrefixGameTestTemplate(false)
public class MDItemDuplicationGameTests {
    private static final BlockPos STOVE_POS = new BlockPos(1, 2, 1);
    private static final BlockPos POT_POS = STOVE_POS.above();

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        if (Mods.isLoaded(Mods.FD))
            GameTestRegistry.register(MDItemDuplicationGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void copperPotInsertionNeverCreatesExtraItems(GameTestHelper helper) {
        var level = helper.getLevel();
        level.setBlock(helper.absolutePos(STOVE_POS), FDBlockEntries.BLAZE_STOVE.getDefaultState(), 3);
        BlockEntity stoveBlockEntity = level.getBlockEntity(helper.absolutePos(STOVE_POS));
        helper.assertTrue(stoveBlockEntity instanceof BlazeStoveBlockEntity,
                "Expected a blaze stove block entity");
        BlazeStoveBlockEntity stove = (BlazeStoveBlockEntity) stoveBlockEntity;
        stove.setGuide(MDItemEntries.MINERS_COOKING_GUIDE.asStack());
        MinersCookingGuide.of(stove.getGuide()).deserializeNBT(FDItemDuplicationGameTests
                .repeatedIngredientGuide(Items.CARROT, CopperPotPoint.INPUT_SLOT_COUNT));

        var potState = MDBlocks.COPPER_POT.get().defaultBlockState();
        level.setBlock(helper.absolutePos(POT_POS), potState, 3);
        BlockEntity potBlockEntity = level.getBlockEntity(helper.absolutePos(POT_POS));
        helper.assertTrue(potBlockEntity instanceof CopperPotBlockEntity,
                "Expected a Miner's Delight copper pot block entity");
        CopperPotBlockEntity pot = (CopperPotBlockEntity) potBlockEntity;
        CopperPotPoint point = new CopperPotPoint(CentralKitchenArmInterationTypes.COPPER_POT,
                level, helper.absolutePos(POT_POS), potState);

        ItemStack simulatedInput = new ItemStack(Items.CARROT);
        ItemStack simulatedRemainder = point.insert(simulatedInput, true);
        helper.assertTrue(simulatedRemainder.isEmpty(), "Simulation should report one accepted item");
        helper.assertTrue(simulatedInput.getCount() == 1, "Simulation mutated its input stack");
        helper.assertTrue(countItems(pot.getInventory(), CopperPotPoint.INPUT_SLOT_COUNT) == 0,
                "Simulation changed the copper pot inventory");

        ItemStack input = new ItemStack(Items.CARROT);
        ItemStack remainder = point.insert(input, false);
        helper.assertTrue(remainder.isEmpty(), "The copper pot should accept the input item");
        helper.assertTrue(input.getCount() == 1, "Insertion mutated its input stack");
        helper.assertTrue(countItems(pot.getInventory(), CopperPotPoint.INPUT_SLOT_COUNT) == 1,
                "One input item must populate exactly one copper pot slot");
        helper.succeed();
    }

    private static int countItems(IItemHandler inventory, int slots) {
        int count = 0;
        for (int slot = 0; slot < slots; slot++)
            count += inventory.getStackInSlot(slot).getCount();
        return count;
    }
}
