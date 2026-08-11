package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.items.IItemHandler;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlock;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuide;
import plus.dragons.createcentralkitchen.entry.CentralKitchenArmInterationTypes;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@ModLoadSubscriber(modid = Mods.FD)
@PrefixGameTestTemplate(false)
public class FDItemDuplicationGameTests {
    private static final BlockPos STOVE_POS = new BlockPos(1, 2, 1);
    private static final BlockPos POT_POS = STOVE_POS.above();

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(FDItemDuplicationGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void cookingPotInsertionNeverCreatesExtraItems(GameTestHelper helper) {
        BlazeStoveBlockEntity stove = placeBlazeStove(helper);
        ItemStack guideStack = FDItemEntries.COOKING_GUIDE.asStack();
        stove.setGuide(guideStack);
        CookingGuide.of(stove.getGuide()).deserializeNBT(repeatedIngredientGuide(Items.WHEAT,
                CookingPotPoint.INPUT_SLOT_COUNT));

        var level = helper.getLevel();
        var potState = ModBlocks.COOKING_POT.get().defaultBlockState();
        level.setBlock(helper.absolutePos(POT_POS), potState, 3);
        BlockEntity blockEntity = level.getBlockEntity(helper.absolutePos(POT_POS));
        helper.assertTrue(blockEntity instanceof CookingPotBlockEntity,
                "Expected a Farmer's Delight cooking pot block entity");
        CookingPotBlockEntity pot = (CookingPotBlockEntity) blockEntity;
        CookingPotPoint point = new CookingPotPoint(CentralKitchenArmInterationTypes.COOKING_POT,
                level, helper.absolutePos(POT_POS), potState);

        ItemStack simulatedInput = new ItemStack(Items.WHEAT);
        ItemStack simulatedRemainder = point.insert(simulatedInput, true);
        helper.assertTrue(simulatedRemainder.isEmpty(), "Simulation should report one accepted item");
        helper.assertTrue(simulatedInput.getCount() == 1, "Simulation mutated its input stack");
        helper.assertTrue(countItems(pot.getInventory(), CookingPotPoint.INPUT_SLOT_COUNT) == 0,
                "Simulation changed the cooking pot inventory");

        ItemStack input = new ItemStack(Items.WHEAT);
        ItemStack remainder = point.insert(input, false);
        helper.assertTrue(remainder.isEmpty(), "The cooking pot should accept the input item");
        helper.assertTrue(input.getCount() == 1, "Insertion mutated its input stack");
        helper.assertTrue(countItems(pot.getInventory(), CookingPotPoint.INPUT_SLOT_COUNT) == 1,
                "One input item must populate exactly one cooking pot slot");
        helper.succeed();
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void blazeStoveGuideInsertionConsumesSurvivalItems(GameTestHelper helper) {
        BlazeStoveBlockEntity stove = placeBlazeStove(helper);
        var level = helper.getLevel();
        BlockPos absolutePos = helper.absolutePos(STOVE_POS);

        ItemStack first = namedGuide("first");
        ItemStack expectedFirst = first.copy();
        InteractionResultHolder<ItemStack> firstResult = BlazeStoveBlock.tryInsert(
                level, absolutePos, first, false, false, false);
        helper.assertTrue(first.isEmpty(), "Installing a guide in survival must consume it");
        helper.assertTrue(firstResult.getObject().isEmpty(), "An empty stove returned an old guide");
        helper.assertTrue(ItemStack.isSameItemSameTags(stove.getGuide(), expectedFirst),
                "The first guide was not installed");

        ItemStack second = namedGuide("second");
        ItemStack expectedSecond = second.copy();
        InteractionResultHolder<ItemStack> simulatedResult = BlazeStoveBlock.tryInsert(
                level, absolutePos, second, false, false, true);
        helper.assertTrue(second.getCount() == 1, "Simulation consumed the replacement guide");
        helper.assertTrue(ItemStack.isSameItemSameTags(stove.getGuide(), expectedFirst),
                "Simulation replaced the installed guide");
        helper.assertTrue(ItemStack.isSameItemSameTags(simulatedResult.getObject(), expectedFirst),
                "Simulation did not return the currently installed guide");

        InteractionResultHolder<ItemStack> replacementResult = BlazeStoveBlock.tryInsert(
                level, absolutePos, second, false, false, false);
        helper.assertTrue(second.isEmpty(), "Replacing a guide in survival must consume it");
        helper.assertTrue(ItemStack.isSameItemSameTags(stove.getGuide(), expectedSecond),
                "The replacement guide was not installed");
        helper.assertTrue(ItemStack.isSameItemSameTags(replacementResult.getObject(), expectedFirst),
                "Replacing a guide did not return the old guide");

        ItemStack creativeGuide = namedGuide("creative");
        ItemStack expectedCreativeGuide = creativeGuide.copy();
        InteractionResultHolder<ItemStack> creativeResult = BlazeStoveBlock.tryInsert(
                level, absolutePos, creativeGuide, true, false, false);
        helper.assertTrue(creativeGuide.getCount() == 1, "Creative insertion consumed its guide");
        helper.assertTrue(ItemStack.isSameItemSameTags(stove.getGuide(), expectedCreativeGuide),
                "Creative insertion did not replace the installed guide");
        helper.assertTrue(ItemStack.isSameItemSameTags(creativeResult.getObject(), expectedSecond),
                "Creative insertion did not expose the replaced guide");
        helper.succeed();
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void blazeStovePointSimulatesGuideInsertion(GameTestHelper helper) {
        BlazeStoveBlockEntity stove = placeBlazeStove(helper);
        var level = helper.getLevel();
        BlockPos absolutePos = helper.absolutePos(STOVE_POS);
        BlazeStovePoint point = new BlazeStovePoint(CentralKitchenArmInterationTypes.BLAZE_STOVE,
                level, absolutePos, level.getBlockState(absolutePos));

        ItemStack first = namedGuide("arm_first");
        ItemStack expectedFirst = first.copy();
        ItemStack simulatedFirstRemainder = point.insert(first, true);
        helper.assertTrue(simulatedFirstRemainder.isEmpty(),
                "A mechanical arm must see that an empty stove accepts a guide");
        helper.assertTrue(first.getCount() == 1, "Mechanical arm simulation mutated its input guide");
        helper.assertTrue(stove.getGuide().isEmpty(), "Mechanical arm simulation installed the guide");

        ItemStack firstRemainder = point.insert(first, false);
        helper.assertTrue(firstRemainder.isEmpty(), "An empty stove returned a guide to the mechanical arm");
        helper.assertTrue(first.getCount() == 1, "Mechanical arm insertion mutated its caller-owned guide");
        helper.assertTrue(ItemStack.isSameItemSameTags(stove.getGuide(), expectedFirst),
                "The mechanical arm did not install the first guide");

        ItemStack second = namedGuide("arm_second");
        ItemStack expectedSecond = second.copy();
        ItemStack simulatedSecondRemainder = point.insert(second, true);
        helper.assertTrue(ItemStack.isSameItemSameTags(simulatedSecondRemainder, expectedFirst),
                "Mechanical arm simulation did not expose the installed guide");
        helper.assertTrue(ItemStack.isSameItemSameTags(stove.getGuide(), expectedFirst),
                "Mechanical arm simulation replaced the installed guide");

        ItemStack secondRemainder = point.insert(second, false);
        helper.assertTrue(ItemStack.isSameItemSameTags(secondRemainder, expectedFirst),
                "Mechanical arm replacement did not return the installed guide");
        helper.assertTrue(ItemStack.isSameItemSameTags(stove.getGuide(), expectedSecond),
                "The mechanical arm did not install the replacement guide");
        helper.succeed();
    }

    static CompoundTag repeatedIngredientGuide(Item ingredient, int ingredientSlots) {
        CompoundTag tag = new CompoundTag();
        ListTag ingredients = new ListTag();
        for (byte slot = 0; slot < ingredientSlots; slot++) {
            CompoundTag ingredientTag = new CompoundTag();
            ingredientTag.putByte("Slot", slot);
            new ItemStack(ingredient).save(ingredientTag);
            ingredients.add(ingredientTag);
        }
        tag.put("Ingredients", ingredients);
        tag.put("Result", new ItemStack(Items.BREAD).serializeNBT());
        return tag;
    }

    private static BlazeStoveBlockEntity placeBlazeStove(GameTestHelper helper) {
        var level = helper.getLevel();
        level.setBlock(helper.absolutePos(STOVE_POS), FDBlockEntries.BLAZE_STOVE.getDefaultState(), 3);
        BlockEntity blockEntity = level.getBlockEntity(helper.absolutePos(STOVE_POS));
        helper.assertTrue(blockEntity instanceof BlazeStoveBlockEntity, "Expected a blaze stove block entity");
        return (BlazeStoveBlockEntity) blockEntity;
    }

    private static ItemStack namedGuide(String name) {
        ItemStack guide = FDItemEntries.COOKING_GUIDE.asStack();
        guide.setHoverName(Component.literal(name));
        return guide;
    }

    private static int countItems(IItemHandler inventory, int slots) {
        int count = 0;
        for (int slot = 0; slot < slots; slot++)
            count += inventory.getStackInSlot(slot).getCount();
        return count;
    }
}
