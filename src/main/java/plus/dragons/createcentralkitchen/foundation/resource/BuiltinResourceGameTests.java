package plus.dragons.createcentralkitchen.foundation.resource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

@ModLoadSubscriber
@PrefixGameTestTemplate(false)
public class BuiltinResourceGameTests {
    private static final List<ResourceLocation> ABNORMALS_DELIGHT_RECIPES = List.of(
            Mods.ad("autumnity/maple_cookie"),
            Mods.ad("environmental/cherry_cookie"),
            Mods.ad("upgrade_aquatic/mulberry_cookie"),
            CentralKitchen.genRL("compacting/cherry_cookie"),
            CentralKitchen.genRL("compacting/mulberry_cookie"),
            CentralKitchen.genRL("filling/maple_glazed_bacon"));
    private static final List<ResourceLocation> ATMOSPHERIC_RECIPES = List.of(
            CentralKitchen.genRL("compacting/aloe_gel"),
            CentralKitchen.genRL("compacting/aloe_gel_block"),
            CentralKitchen.genRL("compacting/passionfruit_tart"),
            CentralKitchen.genRL("crafting/aloe_gel_block_from_bucket"),
            CentralKitchen.genRL("crafting/aloe_gel_bottles_from_bucket"),
            CentralKitchen.genRL("crafting/aloe_gel_bucket"),
            CentralKitchen.genRL("crafting/aloe_gel_bucket_from_block"),
            CentralKitchen.genRL("emptying/aloe_gel_bottle"),
            CentralKitchen.genRL("filling/aloe_gel_bottle"),
            CentralKitchen.genRL("filling/aloe_gel_bucket"),
            CentralKitchen.genRL("mixing/aloe_gel_from_block"),
            CentralKitchen.genRL("mixing/yucca_gateau"));
    private static final List<ResourceLocation> FARMERS_DELIGHT_RECIPES = List.of(
            CentralKitchen.genRL("crafting/passion_fruit_cake_from_slices"),
            CentralKitchen.genRL("sequenced_assembly/apple_pie"),
            CentralKitchen.genRL("sequenced_assembly/mulberry_pie"),
            CentralKitchen.genRL("sequenced_assembly/pumpkin_pie"),
            CentralKitchen.genRL("sequenced_assembly/sweet_berry_cheesecake"));
    private static final Map<String, List<ResourceLocation>> BUILTIN_RECIPES = Map.of(
            Mods.AD, ABNORMALS_DELIGHT_RECIPES,
            Mods.ATMOSPHERIC, ATMOSPHERIC_RECIPES);
    private static final Map<String, List<ResourceLocation>> RESTORED_RECIPES = new LinkedHashMap<>();

    static {
        RESTORED_RECIPES.put(Mods.FD, FARMERS_DELIGHT_RECIPES);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(BuiltinResourceGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void loadsEveryBuiltinRecipe(GameTestHelper helper) {
        var recipes = helper.getLevel().getRecipeManager();
        BUILTIN_RECIPES.forEach((modId, recipeIds) -> {
            if (!Mods.isLoaded(modId))
                return;
            for (ResourceLocation recipeId : recipeIds)
                helper.assertTrue(recipes.byKey(recipeId).isPresent(),
                        "The " + modId + " built-in pack did not load " + recipeId);
        });
        RESTORED_RECIPES.forEach((modId, recipeIds) -> {
            if (!Mods.isLoaded(modId))
                return;
            for (ResourceLocation recipeId : recipeIds)
                helper.assertTrue(recipes.byKey(recipeId).isPresent(),
                        "The " + modId + " built-in pack did not load " + recipeId);
        });
        helper.succeed();
    }
}
