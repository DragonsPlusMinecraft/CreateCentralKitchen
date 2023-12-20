package plus.dragons.createcentralkitchen.foundation.data.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConditionedCookingRecipeBuilder implements ConditionedRecipeBuilder<ConditionedCookingRecipeBuilder> {
    private final Item result;
    private final DataIngredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;
    private final List<ICondition> conditions = new ArrayList<>();
    
    private ConditionedCookingRecipeBuilder(ItemLike result, DataIngredient ingredient, float experience, int cookingTime, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        this.result = result.asItem();
        this.ingredient = ingredient;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.serializer = serializer;
    }
    
    public static ConditionedCookingRecipeBuilder cooking(DataIngredient ingredient, ItemLike result, float experience, int cookingTime, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        return new ConditionedCookingRecipeBuilder(result, ingredient, experience, cookingTime, serializer);
    }
    
    public static ConditionedCookingRecipeBuilder campfireCooking(DataIngredient ingredient, ItemLike result, float experience, int cookingTime) {
        return cooking(ingredient, result, experience, cookingTime, RecipeSerializer.CAMPFIRE_COOKING_RECIPE);
    }
    
    public static ConditionedCookingRecipeBuilder blasting(DataIngredient ingredient, ItemLike result, float experience, int cookingTime) {
        return cooking(ingredient, result, experience, cookingTime, RecipeSerializer.BLASTING_RECIPE);
    }
    
    public static ConditionedCookingRecipeBuilder smelting(DataIngredient ingredient, ItemLike result, float experience, int cookingTime) {
        return cooking(ingredient, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE);
    }
    
    public static ConditionedCookingRecipeBuilder smoking(DataIngredient ingredient, ItemLike result, float experience, int cookingTime) {
        return cooking(ingredient, result, experience, cookingTime, RecipeSerializer.SMOKING_RECIPE);
    }
    
    public ConditionedCookingRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }
    
    public ConditionedCookingRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }
    
    public Item getResult() {
        return this.result;
    }
    
    @Override
    public ConditionedCookingRecipeBuilder withCondition(ICondition condition) {
        this.conditions.add(condition);
        return this;
    }
    
    @Override
    public void save(Consumer<FinishedRecipe> consumer) {
        this.save(consumer, ForgeRegistries.ITEMS.getKey(this.result));
    }
    
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        id = new ResourceLocation(id.getNamespace(), ForgeRegistries.RECIPE_SERIALIZERS.getKey(this.serializer).getPath() + "/" + id.getPath());
        if (consumer instanceof RegistrateRecipeProvider prov)
            this.unlockedBy("has_" + prov.safeName(this.ingredient.getId()), this.ingredient.getCritereon(prov));
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        consumer.accept(new ConditionedCookingRecipeBuilder.Result(id,
            this.group == null ? "" : this.group, CookingBookCategory.FOOD,
            this.ingredient, this.result,
            this.experience, this.cookingTime,
            this.advancement, new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath()),
            this.serializer, this.conditions));
    }
    
    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final CookingBookCategory category;
        private final Ingredient ingredient;
        private final Item result;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;
        private final boolean serializeAdvancement;
        private final List<ICondition> conditions;
        
        public Result(ResourceLocation id, String group, CookingBookCategory category, Ingredient ingredient, Item result, float experience, int cookingTime, Advancement.Builder advancement, ResourceLocation advancementId, RecipeSerializer<? extends AbstractCookingRecipe> serializer, List<ICondition> conditions) {
            this.id = id;
            this.group = group;
            this.category = category;
            this.ingredient = ingredient;
            this.result = result;
            this.experience = experience;
            this.cookingTime = cookingTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.serializer = serializer;
            this.serializeAdvancement = !advancement.getCriteria().isEmpty() && conditions.isEmpty();
            this.conditions = conditions;
        }
    
        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", group);
            }
            json.addProperty("category", category.getSerializedName());
            json.add("ingredient", ingredient.toJson());
            json.addProperty("result", BuiltInRegistries.ITEM.getKey(result).toString());
            json.addProperty("experience", experience);
            json.addProperty("cookingtime", cookingTime);
            if (!conditions.isEmpty()) {
                JsonArray conds = new JsonArray();
                conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
                json.add("conditions", conds);
            }
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return serializeAdvancement ?  advancement.serializeToJson() : null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }

    }
    
}
