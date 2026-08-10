package plus.dragons.createcentralkitchen.foundation.resource;

import java.util.List;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.AD)
@PrefixGameTestTemplate(false)
public class BuiltinResourceGameTests {
    private static final List<ResourceLocation> ABNORMALS_DELIGHT_RECIPES = List.of(
            Mods.ad("autumnity/maple_cookie"),
            Mods.ad("environmental/cherry_cookie"),
            Mods.ad("upgrade_aquatic/mulberry_cookie"),
            CentralKitchen.genRL("compacting/cherry_cookie"),
            CentralKitchen.genRL("compacting/mulberry_cookie"),
            CentralKitchen.genRL("filling/maple_glazed_bacon"));

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(BuiltinResourceGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void loadsEveryAbnormalsDelightRecipe(GameTestHelper helper) {
        var recipes = helper.getLevel().getRecipeManager();
        for (ResourceLocation recipeId : ABNORMALS_DELIGHT_RECIPES)
            helper.assertTrue(recipes.byKey(recipeId).isPresent(),
                    "The Abnormals Delight built-in pack did not load " + recipeId);
        helper.succeed();
    }
}
