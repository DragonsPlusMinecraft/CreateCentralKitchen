package plus.dragons.createcentralkitchen.foundation.item;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;

@ModLoadSubscriber
@PrefixGameTestTemplate(false)
public class ItemHandlerModifiableViewGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(ItemHandlerModifiableViewGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void enforcesBackingAndViewSlotBoundaries(GameTestHelper helper) {
        var backing = new ItemStackHandler(4) {
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return slot != 1 || !stack.is(Items.STONE);
            }
        };
        backing.setStackInSlot(0, new ItemStack(Items.CARROT));
        var view = new ItemHandlerModifiableView(backing, 0, 3, 6);

        helper.assertTrue(view.getSlots() == 6, "The view did not expose its configured size");
        helper.assertTrue(view.getStackInSlot(0).is(Items.CARROT), "A real slot did not delegate to the backing handler");
        helper.assertTrue(!view.isItemValid(1, new ItemStack(Items.STONE)),
                "A real slot ignored the backing handler's item validation");
        helper.assertTrue(view.getStackInSlot(4).isEmpty(), "A virtual slot was not empty");
        ItemStack virtualInput = new ItemStack(Items.POTATO);
        helper.assertTrue(view.insertItem(4, virtualInput, false) == virtualInput,
                "A virtual slot did not reject its input unchanged");
        helper.assertTrue(view.getSlotLimit(4) == 0, "A virtual slot accepted items");
        helper.assertTrue(!view.isItemValid(4, virtualInput), "A virtual slot reported an item as valid");

        assertInvalidSlot(() -> view.getStackInSlot(-1), "A negative slot was accepted");
        assertInvalidSlot(() -> view.insertItem(-1, ItemStack.EMPTY, true), "A negative insert slot was accepted");
        assertInvalidSlot(() -> view.extractItem(-1, 1, true), "A negative extract slot was accepted");
        assertInvalidSlot(() -> view.setStackInSlot(-1, ItemStack.EMPTY), "A negative set slot was accepted");
        assertInvalidSlot(() -> view.getSlotLimit(-1), "A negative limit slot was accepted");
        assertInvalidSlot(() -> view.isItemValid(-1, ItemStack.EMPTY), "A negative validation slot was accepted");
        assertInvalidSlot(() -> view.getStackInSlot(6), "The exclusive upper slot bound was accepted");
        helper.succeed();
    }

    private static void assertInvalidSlot(Runnable action, String message) {
        try {
            action.run();
        } catch (IndexOutOfBoundsException expected) {
            return;
        }
        throw new AssertionError(message);
    }
}
