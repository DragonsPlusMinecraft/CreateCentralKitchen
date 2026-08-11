package plus.dragons.createcentralkitchen.foundation.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import java.util.List;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;

@ModLoadSubscriber
@PrefixGameTestTemplate(false)
public class CentralKitchenConfigGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(CentralKitchenConfigGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void registryObjectListsHonorInitialConfigLoad(GameTestHelper helper) {
        CentralKitchenCommonConfig config = new CentralKitchenCommonConfig();
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        config.registerAll(builder);
        ForgeConfigSpec spec = builder.build();

        CommentedConfig loadedConfig = CommentedConfig.inMemory();
        spec.correct(loadedConfig);
        loadedConfig.set("automation.boostingCookerList", List.of("minecraft:furnace"));
        spec.acceptConfig(loadedConfig);
        config.onLoad();

        helper.assertTrue(config.automation.boostingCookerList.contains(BlockEntityType.FURNACE),
                "The initial config load did not apply the configured block entity type");
        helper.assertTrue(config.automation.boostingCookerList.getObjects(false).size() == 1,
                "The initial config load kept stale default block entity types");
        helper.succeed();
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void idListsHonorCustomValidators(GameTestHelper helper) {
        ValidatedIdConfig config = new ValidatedIdConfig();
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        config.registerAll(builder);
        ForgeConfigSpec spec = builder.build();

        CommentedConfig loadedConfig = CommentedConfig.inMemory();
        spec.correct(loadedConfig);
        loadedConfig.set("ids", List.of("minecraft:furnace"));
        spec.correct(loadedConfig);
        spec.acceptConfig(loadedConfig);
        config.onLoad();

        helper.assertTrue(config.ids.getIdList().equals(List.of(ResourceLocation.withDefaultNamespace("stone"))),
                "The custom ID validator did not restore the configured default");
        helper.succeed();
    }

    private static class ValidatedIdConfig extends CentralKitchenConfigBase {
        private final ConfigIdList ids = idList(
                List.of(ResourceLocation.withDefaultNamespace("stone")),
                "ids",
                value -> "minecraft:stone".equals(value),
                "Only stone is valid");

        @Override
        public String getName() {
            return "validatedIds";
        }
    }
}
