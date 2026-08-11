package plus.dragons.createcentralkitchen.entry.network;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.FD)
@PrefixGameTestTemplate(false)
public class FDNetworkEntriesGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(FDNetworkEntriesGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void rejectsLegacyBlazeStovePacketFormat(GameTestHelper helper) {
        helper.assertTrue(FDNetworkEntries.acceptsNetworkVersion(FDNetworkEntries.NETWORK_VERSION),
                "The current network protocol version was rejected");
        helper.assertTrue(!FDNetworkEntries.acceptsNetworkVersion("1.3.0"),
                "The legacy NBT packet protocol was still accepted");
        helper.succeed();
    }
}
