package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.ForgeRegistries;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuideMenu;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import plus.dragons.createcentralkitchen.entry.menu.FDMenuEntries;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.FD)
@PrefixGameTestTemplate(false)
public class BlazeStoveGuideSecurityGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(BlazeStoveGuideSecurityGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void guideSyncOnlyAcceptsBoundedIngredientIds(GameTestHelper helper) {
        var player = helper.makeMockPlayer();
        CookingGuideMenu clientMenu = new CookingGuideMenu(FDMenuEntries.COOKING_GUIDE.get(), 23,
                new Inventory(player), FDItemEntries.COOKING_GUIDE.asStack());
        clientMenu.getSlot(36).set(new ItemStack(Items.BEDROCK));

        BlazeStoveGuideSyncPacket packet = new BlazeStoveGuideSyncPacket(clientMenu);
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        BlazeStoveGuideSyncPacket decoded;
        try {
            packet.write(buffer);
            decoded = new BlazeStoveGuideSyncPacket(buffer);
        } finally {
            buffer.release();
        }

        ResourceLocation bedrockId = ForgeRegistries.ITEMS.getKey(Items.BEDROCK);
        ResourceLocation airId = ForgeRegistries.ITEMS.getKey(Items.AIR);
        helper.assertTrue(decoded.containerId() == 23, "The menu id changed during packet encoding");
        helper.assertTrue(decoded.inputIds().size() == 6, "The packet did not preserve the input slot count");
        helper.assertTrue(decoded.inputIds().get(0).equals(bedrockId), "The first ingredient id was not preserved");
        helper.assertTrue(decoded.inputIds().subList(1, 6).stream().allMatch(airId::equals),
                "Empty guide slots were not encoded as air");

        CookingGuideMenu serverMenu = new CookingGuideMenu(FDMenuEntries.COOKING_GUIDE.get(), 23,
                new Inventory(player), FDItemEntries.COOKING_GUIDE.asStack());
        serverMenu.guide.deserializeNBT(forgedGuideTag());
        helper.assertTrue(serverMenu.guide.getResult().is(Items.DIAMOND), "The forged test result was not installed");
        helper.assertTrue(serverMenu.guide.isContainer(new ItemStack(Items.DIAMOND)),
                "The forged test container was not installed");

        helper.assertTrue(serverMenu.updateGuideInputs(decoded.inputIds()), "The server rejected valid guide inputs");
        helper.assertTrue(serverMenu.guide.getResult().isEmpty(), "The server retained a client-forged result");
        helper.assertTrue(!serverMenu.guide.isContainer(new ItemStack(Items.DIAMOND)),
                "The server retained a client-forged container");
        helper.assertTrue(serverMenu.getSlot(36).getItem().is(Items.BEDROCK),
                "The server did not apply the validated ingredient id");

        FriendlyByteBuf oversized = new FriendlyByteBuf(Unpooled.buffer());
        boolean rejected = false;
        try {
            oversized.writeVarInt(23);
            oversized.writeVarInt(BlazeStoveGuideSyncPacket.MAX_INPUT_SLOTS + 1);
            try {
                new BlazeStoveGuideSyncPacket(oversized);
            } catch (DecoderException expected) {
                rejected = true;
            }
        } finally {
            oversized.release();
        }
        helper.assertTrue(rejected, "The packet accepted an oversized ingredient list");
        helper.succeed();
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void shiftClickClearsTheSelectedGuideSlot(GameTestHelper helper) {
        var player = helper.makeMockPlayer();
        CookingGuideMenu menu = new CookingGuideMenu(FDMenuEntries.COOKING_GUIDE.get(), 24,
                new Inventory(player), FDItemEntries.COOKING_GUIDE.asStack());

        for (int selectedSlot = 0; selectedSlot < menu.getInputSize(); selectedSlot++) {
            menu.getSlot(36).set(new ItemStack(Items.BEDROCK));
            menu.getSlot(36 + selectedSlot).set(new ItemStack(Items.STONE));
            menu.quickMoveStack(player, 36 + selectedSlot);

            helper.assertTrue(menu.getSlot(36 + selectedSlot).getItem().isEmpty(),
                    "Shift-click did not clear guide input " + selectedSlot);
            if (selectedSlot > 0)
                helper.assertTrue(menu.getSlot(36).getItem().is(Items.BEDROCK),
                        "Shift-click on guide input " + selectedSlot + " cleared input 0 instead");
        }
        helper.succeed();
    }

    private static CompoundTag forgedGuideTag() {
        CompoundTag tag = new CompoundTag();
        ListTag ingredients = new ListTag();
        CompoundTag ingredient = new CompoundTag();
        ingredient.putByte("Slot", (byte) 0);
        new ItemStack(Items.BEDROCK).save(ingredient);
        ingredients.add(ingredient);
        tag.put("Ingredients", ingredients);
        tag.put("Result", new ItemStack(Items.DIAMOND).serializeNBT());
        tag.put("Container", new ItemStack(Items.DIAMOND).serializeNBT());
        return tag;
    }
}
