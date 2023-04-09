package plus.dragons.createcentralkitchen.foundation.data.recipe;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.tag.IntegrationItemTags;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Mod.EventBusSubscriber(modid = CentralKitchen.ID)
public class IgnoreAutomaticShapelessRecipe {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Cache<Recipe<?>, Boolean> CACHED_IGNORED_RECIPES = CacheBuilder.newBuilder().build();
    public static final ResourceManagerReloadListener LISTENER = resourceManager -> {
        long size = CACHED_IGNORED_RECIPES.size();
        CACHED_IGNORED_RECIPES.invalidateAll();
        LOGGER.debug("Invalidated IgnoreAutoShapelessRecipe's cache of size: {}", size);
    };
    
    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(LISTENER);
    }
    
    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        long size = CACHED_IGNORED_RECIPES.size();
        CACHED_IGNORED_RECIPES.invalidateAll();
        LOGGER.debug("Invalidated IgnoreAutoShapelessRecipe's cache of size: {}", size);
    }
    
    public static boolean get(Recipe<?> recipe) {
        try {
            return CACHED_IGNORED_RECIPES.get(recipe, () -> shouldIgnoreShapelessRecipe(recipe));
        } catch (ExecutionException exception) {
            LOGGER.warn("Exception while computing if recipe {} should be ignored in automation", recipe.getId(), exception);
            return false;
        }
    }
    
    private static boolean shouldIgnoreShapelessRecipe(Recipe<?> recipe) {
        if (shouldIgnoreItemInAutomation(recipe.getResultItem())) {
            return true;
        }
        List<Ingredient> ingredients = recipe.getIngredients();
        int ingredientSize = ingredients.size();
        if (ingredientSize == 0) {
            return false;
        } else if (ingredientSize == 1) {
            return shouldIgnoreIngredientInAutomation(ingredients.get(0));
        } else {
            for (Ingredient ingredient : recipe.getIngredients())
                if (shouldIgnoreIngredientInAutomation(ingredient))
                    return true;
        }
        return false;
    }
    
    private static boolean shouldIgnoreIngredientInAutomation(Ingredient ingredient) {
        ItemStack[] items = ingredient.getItems();
        if (items.length == 0) {
            return false;
        } else if (items.length == 1) {
            return shouldIgnoreItemInAutomation(items[0]);
        } else {
            for (ItemStack stack : items)
                if (!shouldIgnoreItemInAutomation(items[0]))
                    return false;
        }
        return true;
    }
    
    private static boolean shouldIgnoreItemInAutomation(ItemStack item) {
        return item.is(IntegrationItemTags.IGNORED_IN_AUTOMATIC_SHAPELESS.tag) ||
            (item.hasCraftingRemainingItem() &&
             item.getCraftingRemainingItem().is(IntegrationItemTags.IGNORED_IN_AUTOMATIC_SHAPELESS.tag));
    }
    
}
