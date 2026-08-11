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
    private static final List<ResourceLocation> ENDS_DELIGHT_RECIPES = List.of(
            CentralKitchen.genRL("compacting/chorus_cookie"),
            CentralKitchen.genRL("emptying/bubble_tea"),
            CentralKitchen.genRL("emptying/chorus_flower_tea"),
            CentralKitchen.genRL("emptying/chorus_fruit_milk_tea"),
            CentralKitchen.genRL("emptying/chorus_fruit_wine"),
            CentralKitchen.genRL("filling/bubble_tea"),
            CentralKitchen.genRL("filling/chorus_flower_tea"),
            CentralKitchen.genRL("filling/chorus_fruit_milk_tea"),
            CentralKitchen.genRL("filling/chorus_fruit_wine"),
            CentralKitchen.genRL("filling/dragon_breath_soda"),
            CentralKitchen.genRL("mixing/chorus_flower_tea"),
            CentralKitchen.genRL("mixing/chorus_fruit_bubble_tea"),
            CentralKitchen.genRL("mixing/chorus_fruit_milk_tea"),
            CentralKitchen.genRL("mixing/chorus_fruit_wine"),
            CentralKitchen.genRL("mixing/dragon_breath_soda"),
            CentralKitchen.genRL("mixing/upgrade_to_chorus_fruit_bubble_tea"),
            CentralKitchen.genRL("sequenced_assembly/chorus_flower_pie"),
            CentralKitchen.genRL("sequenced_assembly/chorus_fruit_pie"));
    private static final List<ResourceLocation> UPGRADE_AQUATIC_RECIPES = List.of(
            CentralKitchen.genRL("crafting/mulberry_jam_block_from_bucket"),
            CentralKitchen.genRL("crafting/mulberry_jam_bottles_from_bucket"),
            CentralKitchen.genRL("crafting/mulberry_jam_bucket"),
            CentralKitchen.genRL("crafting/mulberry_jam_bucket_from_block"),
            CentralKitchen.genRL("emptying/mulberry_jam_bottle"),
            CentralKitchen.genRL("filling/mulberry_jam_bottle"),
            CentralKitchen.genRL("filling/mulberry_jam_bucket"));
    private static final List<ResourceLocation> AUTUMNITY_RECIPES = List.of(
            CentralKitchen.genRL("syrup_bucket_from_sap_bucket_campfire"),
            CentralKitchen.genRL("syrup_bucket_from_sap_bucket_smelting"),
            CentralKitchen.genRL("syrup_bucket_from_sap_bucket_smoking"),
            CentralKitchen.genRL("compacting/maple_cookie"),
            CentralKitchen.genRL("compacting/pancake"),
            CentralKitchen.genRL("compacting/pancake_from_dough"),
            CentralKitchen.genRL("crafting/pancake_from_dough"),
            CentralKitchen.genRL("crafting/pumpkin_bread_from_dough_and_pumpkin_slice"),
            CentralKitchen.genRL("crafting/sappy_maple_log_from_sap_bucket"),
            CentralKitchen.genRL("crafting/sappy_maple_wood_from_sap_bucket"),
            CentralKitchen.genRL("crafting/sap_bottles_from_bucket"),
            CentralKitchen.genRL("crafting/sap_bucket_from_bottles"),
            CentralKitchen.genRL("crafting/syrup_bottles_from_bucket"),
            CentralKitchen.genRL("crafting/syrup_bucket_from_bottles"),
            CentralKitchen.genRL("emptying/sap_bottle"),
            CentralKitchen.genRL("emptying/syrup_bottle"),
            CentralKitchen.genRL("filling/pancake"),
            CentralKitchen.genRL("filling/sappy_maple_log"),
            CentralKitchen.genRL("filling/sappy_maple_wood"),
            CentralKitchen.genRL("filling/sap_bottle"),
            CentralKitchen.genRL("filling/syrup_bottle"),
            CentralKitchen.genRL("mixing/pumpkin_bread_from_pumpkin_slice"),
            CentralKitchen.genRL("mixing/syrup"));
    private static final List<ResourceLocation> CORN_DELIGHT_RECIPES = List.of(
            CentralKitchen.genRL("emptying/corn_soup"),
            CentralKitchen.genRL("emptying/creamy_corn_drink"),
            CentralKitchen.genRL("filling/corn_soup"),
            CentralKitchen.genRL("filling/creamy_corn_drink"),
            CentralKitchen.genRL("mixing/corn_soup"),
            CentralKitchen.genRL("mixing/creamy_corn_drink"));
    private static final List<ResourceLocation> COLLECTORS_REAP_RECIPES = List.of(
            CentralKitchen.genRL("compacting/lime_cake"),
            CentralKitchen.genRL("compacting/lime_cookie"),
            CentralKitchen.genRL("compacting/pomegranate_cake"),
            CentralKitchen.genRL("compacting/strawberry_jam_bun"),
            CentralKitchen.genRL("crafting/lime_cake_from_dough"),
            CentralKitchen.genRL("crafting/pomegranate_cake_from_dough"),
            CentralKitchen.genRL("emptying/berry_limeade"),
            CentralKitchen.genRL("emptying/lime_ice_cream"),
            CentralKitchen.genRL("emptying/lime_milkshake"),
            CentralKitchen.genRL("emptying/limeade"),
            CentralKitchen.genRL("emptying/mint_limeade"),
            CentralKitchen.genRL("emptying/pink_limeade"),
            CentralKitchen.genRL("emptying/pomegranate_ice_cream"),
            CentralKitchen.genRL("emptying/pomegranate_milkshake"),
            CentralKitchen.genRL("emptying/pomegranate_smoothie"),
            CentralKitchen.genRL("filling/berry_limeade"),
            CentralKitchen.genRL("filling/candied_lime"),
            CentralKitchen.genRL("filling/lime_ice_cream"),
            CentralKitchen.genRL("filling/lime_milkshake"),
            CentralKitchen.genRL("filling/limeade"),
            CentralKitchen.genRL("filling/mint_limeade"),
            CentralKitchen.genRL("filling/pink_limeade"),
            CentralKitchen.genRL("filling/pomegranate_ice_cream"),
            CentralKitchen.genRL("filling/pomegranate_milkshake"),
            CentralKitchen.genRL("filling/pomegranate_smoothie"),
            CentralKitchen.genRL("mixing/berry_limeade"),
            CentralKitchen.genRL("mixing/berry_limeade_from_limeade"),
            CentralKitchen.genRL("mixing/lime_green_tea"),
            CentralKitchen.genRL("mixing/lime_ice_cream"),
            CentralKitchen.genRL("mixing/lime_milkshake"),
            CentralKitchen.genRL("mixing/lime_milkshake_from_ice_cream"),
            CentralKitchen.genRL("mixing/limeade"),
            CentralKitchen.genRL("mixing/mint_limeade"),
            CentralKitchen.genRL("mixing/mint_limeade_from_limeade"),
            CentralKitchen.genRL("mixing/pink_limeade"),
            CentralKitchen.genRL("mixing/pink_limeade_from_limeade"),
            CentralKitchen.genRL("mixing/pomegranate_black_tea"),
            CentralKitchen.genRL("mixing/pomegranate_ice_cream"),
            CentralKitchen.genRL("mixing/pomegranate_milkshake"),
            CentralKitchen.genRL("mixing/pomegranate_milkshake_from_ice_cream"),
            CentralKitchen.genRL("mixing/pomegranate_smoothie"),
            CentralKitchen.genRL("sequenced_assembly/lime_pie"),
            CentralKitchen.genRL("sequenced_assembly/portobello_burger"),
            CentralKitchen.genRL("sequenced_assembly/portobello_quiche"),
            Mods.cr("integration/create/emptying/lime_green_tea"),
            Mods.cr("integration/create/emptying/pomegranate_black_tea"),
            Mods.cr("integration/create/filling/lime_green_tea"),
            Mods.cr("integration/create/filling/pomegranate_black_tea"));
    private static final List<ResourceLocation> PECULIARS_RECIPES = List.of(
            CentralKitchen.genRL("compacting/aloe_cake"),
            CentralKitchen.genRL("compacting/passion_fruit_cake"),
            CentralKitchen.genRL("compacting/yucca_cake"),
            CentralKitchen.genRL("crafting/aloe_cake_from_dough"),
            CentralKitchen.genRL("crafting/passion_fruit_cake_from_dough"),
            CentralKitchen.genRL("crafting/yucca_cake_from_dough"),
            CentralKitchen.genRL("emptying/aloe_ice_cream"),
            CentralKitchen.genRL("emptying/aloe_milkshake"),
            CentralKitchen.genRL("emptying/passion_fruit_ice_cream"),
            CentralKitchen.genRL("emptying/passion_fruit_milkshake"),
            CentralKitchen.genRL("emptying/yucca_ice_cream"),
            CentralKitchen.genRL("emptying/yucca_milkshake"),
            CentralKitchen.genRL("filling/aloe_ice_cream"),
            CentralKitchen.genRL("filling/aloe_milkshake"),
            CentralKitchen.genRL("filling/passion_fruit_ice_cream"),
            CentralKitchen.genRL("filling/passion_fruit_milkshake"),
            CentralKitchen.genRL("filling/yucca_fudge"),
            CentralKitchen.genRL("filling/yucca_ice_cream"),
            CentralKitchen.genRL("filling/yucca_milkshake"),
            CentralKitchen.genRL("mixing/aloe_ice_cream"),
            CentralKitchen.genRL("mixing/aloe_milkshake"),
            CentralKitchen.genRL("mixing/aloe_milkshake_from_ice_cream"),
            CentralKitchen.genRL("mixing/passion_fruit_ice_cream"),
            CentralKitchen.genRL("mixing/passion_fruit_milkshake"),
            CentralKitchen.genRL("mixing/passion_fruit_milkshake_from_ice_cream"),
            CentralKitchen.genRL("mixing/yucca_ice_cream"),
            CentralKitchen.genRL("mixing/yucca_milkshake"),
            CentralKitchen.genRL("mixing/yucca_milkshake_from_ice_cream"));
    private static final List<ResourceLocation> SEASONALS_RECIPES = List.of(
            CentralKitchen.genRL("compacting/pumpkin_cake"),
            CentralKitchen.genRL("compacting/sweet_berry_cake"),
            CentralKitchen.genRL("crafting/chocolate_pumpkin_muffin_from_dough"),
            CentralKitchen.genRL("crafting/pumpkin_cake_from_dough"),
            CentralKitchen.genRL("crafting/sweet_berry_cake_from_dough"),
            CentralKitchen.genRL("emptying/pumpkin_ice_cream"),
            CentralKitchen.genRL("emptying/pumpkin_milkshake"),
            CentralKitchen.genRL("emptying/sweet_berry_ice_cream"),
            CentralKitchen.genRL("emptying/sweet_berry_milkshake"),
            CentralKitchen.genRL("filling/pumpkin_ice_cream"),
            CentralKitchen.genRL("filling/pumpkin_milkshake"),
            CentralKitchen.genRL("filling/sweet_berry_ice_cream"),
            CentralKitchen.genRL("filling/sweet_berry_milkshake"),
            CentralKitchen.genRL("mixing/chocolate_pumpkin_muffin"),
            CentralKitchen.genRL("mixing/pumpkin_ice_cream"),
            CentralKitchen.genRL("mixing/pumpkin_milkshake"),
            CentralKitchen.genRL("mixing/pumpkin_milkshake_from_ice_cream"),
            CentralKitchen.genRL("mixing/sweet_berry_ice_cream"),
            CentralKitchen.genRL("mixing/sweet_berry_milkshake"),
            CentralKitchen.genRL("mixing/sweet_berry_milkshake_from_ice_cream"));
    private static final List<ResourceLocation> SEASONALS_ADVANCEMENTS = List.of(
            CentralKitchen.genRL("recipes/crafting/chocolate_pumpkin_muffin_from_dough"));
    private static final List<ResourceLocation> ATMOSPHERIC_ADVANCEMENTS = List.of(
            CentralKitchen.genRL("recipes/crafting/aloe_gel_block_from_bucket"),
            CentralKitchen.genRL("recipes/crafting/aloe_gel_bottles_from_bucket"),
            CentralKitchen.genRL("recipes/crafting/aloe_gel_bucket"),
            CentralKitchen.genRL("recipes/crafting/aloe_gel_bucket_from_block"));
    private static final List<ResourceLocation> AUTUMNITY_ADVANCEMENTS = List.of(
            CentralKitchen.genRL("recipes/crafting/pancake_from_dough"),
            CentralKitchen.genRL("recipes/crafting/sap_bottles_from_bucket"),
            CentralKitchen.genRL("recipes/crafting/sap_bucket_from_bottles"),
            CentralKitchen.genRL("recipes/crafting/sappy_maple_log_from_sap_bucket"),
            CentralKitchen.genRL("recipes/crafting/sappy_maple_wood_from_sap_bucket"),
            CentralKitchen.genRL("recipes/crafting/syrup_bottles_from_bucket"),
            CentralKitchen.genRL("recipes/crafting/syrup_bucket_from_bottles"),
            CentralKitchen.genRL("recipes/misc/syrup_bucket_from_sap_bucket_campfire"),
            CentralKitchen.genRL("recipes/misc/syrup_bucket_from_sap_bucket_smelting"),
            CentralKitchen.genRL("recipes/misc/syrup_bucket_from_sap_bucket_smoking"));
    private static final List<ResourceLocation> UPGRADE_AQUATIC_ADVANCEMENTS = List.of(
            CentralKitchen.genRL("recipes/crafting/mulberry_jam_block_from_bucket"),
            CentralKitchen.genRL("recipes/crafting/mulberry_jam_bottles_from_bucket"),
            CentralKitchen.genRL("recipes/crafting/mulberry_jam_bucket"),
            CentralKitchen.genRL("recipes/crafting/mulberry_jam_bucket_from_block"));
    private static final Map<String, List<ResourceLocation>> BUILTIN_RECIPES = Map.of(
            Mods.AD, ABNORMALS_DELIGHT_RECIPES,
            Mods.ATMOSPHERIC, ATMOSPHERIC_RECIPES);
    private static final Map<String, List<ResourceLocation>> RESTORED_RECIPES = new LinkedHashMap<>();
    private static final Map<String, List<ResourceLocation>> RESTORED_ADVANCEMENTS = Map.of(
            Mods.ATMOSPHERIC, ATMOSPHERIC_ADVANCEMENTS,
            Mods.AUTUMNITY, AUTUMNITY_ADVANCEMENTS,
            Mods.SEASONALS, SEASONALS_ADVANCEMENTS,
            Mods.UA, UPGRADE_AQUATIC_ADVANCEMENTS);

    static {
        RESTORED_RECIPES.put(Mods.FD, FARMERS_DELIGHT_RECIPES);
        RESTORED_RECIPES.put(Mods.ED, ENDS_DELIGHT_RECIPES);
        RESTORED_RECIPES.put(Mods.UA, UPGRADE_AQUATIC_RECIPES);
        RESTORED_RECIPES.put(Mods.AUTUMNITY, AUTUMNITY_RECIPES);
        RESTORED_RECIPES.put(Mods.CORN_DELIGHT, CORN_DELIGHT_RECIPES);
        RESTORED_RECIPES.put(Mods.SEASONALS, SEASONALS_RECIPES);
        RESTORED_RECIPES.put(Mods.PECULIARS, PECULIARS_RECIPES);
        RESTORED_RECIPES.put(Mods.CR, COLLECTORS_REAP_RECIPES);
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
        if (!Mods.isLoaded("culturaldelights"))
            helper.assertTrue(recipes.byKey(CentralKitchen.genRL("sequenced_assembly/veggie_wrap")).isEmpty(),
                    "The Collector's Reap pack loaded its Cultural Delights recipe without Cultural Delights");
        var advancements = helper.getLevel().getServer().getAdvancements();
        RESTORED_ADVANCEMENTS.forEach((modId, advancementIds) -> {
            if (!Mods.isLoaded(modId))
                return;
            for (ResourceLocation advancementId : advancementIds)
                helper.assertTrue(advancements.getAdvancement(advancementId) != null,
                        "The " + modId + " built-in pack did not load " + advancementId);
        });
        helper.succeed();
    }
}
