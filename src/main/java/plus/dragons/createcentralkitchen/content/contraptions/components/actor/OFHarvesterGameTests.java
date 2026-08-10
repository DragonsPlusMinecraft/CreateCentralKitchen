package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.overweightfarming.init.OFBlocks;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.OF)
@PrefixGameTestTemplate(false)
public class OFHarvesterGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(OFHarvesterGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void registersSnowySpiritGingerSupport(GameTestHelper helper) {
        helper.assertTrue(Mods.isLoaded(Mods.SNOWY_SPIRIT),
                "The full integration test profile did not load Snowy Spirit");
        helper.assertTrue(ForgeRegistries.BLOCKS.getValue(Mods.snowySpirit("ginger")) != null,
                "Snowy Spirit did not register its ginger crop");
        helper.assertTrue(REGISTRY.containsKey(OFBlocks.OVERWEIGHT_GINGER.get()),
                "Overweight ginger has no mechanical harvester behavior");
        helper.succeed();
    }
}
