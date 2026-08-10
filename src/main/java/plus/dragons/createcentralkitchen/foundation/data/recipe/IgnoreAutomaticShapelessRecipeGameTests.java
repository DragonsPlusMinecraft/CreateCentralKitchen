package plus.dragons.createcentralkitchen.foundation.data.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.CentralKitchen;

@Mod.EventBusSubscriber(modid = CentralKitchen.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@PrefixGameTestTemplate(false)
public class IgnoreAutomaticShapelessRecipeGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(IgnoreAutomaticShapelessRecipeGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void checksEveryAlternativeIngredientItem(GameTestHelper helper) {
        ShapelessRecipe mixedAlternatives = recipe("mixed_alternatives",
                Ingredient.of(Items.BUCKET, Items.STONE));
        helper.assertTrue(!IgnoreAutomaticShapelessRecipe.get(mixedAlternatives,
                helper.getLevel().registryAccess()),
                "A recipe was ignored even though one ingredient alternative is safe for automation");

        ShapelessRecipe ignoredAlternatives = recipe("ignored_alternatives",
                Ingredient.of(Items.BUCKET, Items.BOWL));
        helper.assertTrue(IgnoreAutomaticShapelessRecipe.get(ignoredAlternatives,
                helper.getLevel().registryAccess()),
                "A recipe with only ignored ingredient alternatives was not ignored");
        helper.succeed();
    }

    private static ShapelessRecipe recipe(String path, Ingredient ingredient) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(ingredient);
        return new ShapelessRecipe(ResourceLocation.fromNamespaceAndPath(CentralKitchen.ID, "gametest/" + path), "",
                CraftingBookCategory.MISC, new ItemStack(Items.DIRT), ingredients);
    }
}
