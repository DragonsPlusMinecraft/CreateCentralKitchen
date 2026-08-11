package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;

final class CookingSignalGameTestHelper {
    static final BlockPos STOVE_POS = new BlockPos(1, 2, 1);
    static final BlockPos APPLIANCE_POS = STOVE_POS.above();

    private CookingSignalGameTestHelper() {}

    static void placeBlazeStove(GameTestHelper helper) {
        var level = helper.getLevel();
        level.setBlock(helper.absolutePos(STOVE_POS), FDBlockEntries.BLAZE_STOVE.getDefaultState(), 3);
        BlockEntity blockEntity = level.getBlockEntity(helper.absolutePos(STOVE_POS));
        helper.assertTrue(blockEntity instanceof BlazeStoveBlockEntity,
                "Expected a Blaze Stove block entity");
    }

    static void fillInputs(GameTestHelper helper, IItemHandlerModifiable inventory,
            List<Ingredient> ingredients) {
        helper.assertTrue(ingredients.size() <= inventory.getSlots(),
                "Recipe has more ingredients than appliance input slots");
        for (int slot = 0; slot < ingredients.size(); slot++) {
            ItemStack[] candidates = ingredients.get(slot).getItems();
            helper.assertTrue(candidates.length > 0,
                    "Recipe ingredient " + slot + " has no matching item");
            inventory.setStackInSlot(slot, candidates[0].copy());
        }
    }

    static boolean containsItem(IItemHandler inventory, ItemStack expected) {
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            if (ItemStack.isSameItemSameTags(inventory.getStackInSlot(slot), expected))
                return true;
        }
        return false;
    }

    static boolean isSignalScheduled(GameTestHelper helper) {
        var level = helper.getLevel();
        return level.getBlockTicks().hasScheduledTick(
                helper.absolutePos(STOVE_POS), FDBlockEntries.BLAZE_STOVE.get());
    }

    static void assertSignalScheduled(GameTestHelper helper) {
        helper.assertTrue(isSignalScheduled(helper),
                "Completing the recipe did not schedule a Blaze Stove signal");
    }
}
