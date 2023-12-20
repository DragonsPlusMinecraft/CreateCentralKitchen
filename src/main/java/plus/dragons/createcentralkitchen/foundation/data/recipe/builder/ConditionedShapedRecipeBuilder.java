package plus.dragons.createcentralkitchen.foundation.data.recipe.builder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
// TODO
public class ConditionedShapedRecipeBuilder implements RecipeBuilder, ConditionedRecipeBuilder<ConditionedShapedRecipeBuilder> {
    private final ResourceLocation id;
    private Item result;
    private int count = 1;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();;
    @Nullable
    private String group = null;
    private final List<ICondition> conditions = new ArrayList<>();
    
    public ConditionedShapedRecipeBuilder(ResourceLocation id) {
        this.id = id;
    }
    
    /**
     * Adds a key to the recipe pattern.
     */
    public ConditionedShapedRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        return this.define(symbol, Ingredient.of(tag));
    }
    
    /**
     * Adds a key to the recipe pattern.
     */
    public ConditionedShapedRecipeBuilder define(Character symbol, ItemLike item) {
        return this.define(symbol, Ingredient.of(item));
    }
    
    /**
     * Adds a key to the recipe pattern.
     */
    public ConditionedShapedRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }
    
    /**
     * Adds a new entry to the patterns for this recipe.
     */
    public ConditionedShapedRecipeBuilder pattern(String pPattern) {
        if (!this.rows.isEmpty() && pPattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pPattern);
            return this;
        }
    }
    
    public ConditionedShapedRecipeBuilder output(ItemLike item, int count) {
        this.result = item.asItem();
        this.count = count;
        return this;
    }
    
    public ConditionedShapedRecipeBuilder output(ItemLike item) {
        return output(item, 1);
    }
    
    @Override
    public ConditionedShapedRecipeBuilder withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }
    
    @Override
    public ConditionedShapedRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(criterionName, trigger);
        return this;
    }
    
    @Override
    public ConditionedShapedRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }
    
    @Override
    public Item getResult() {
        return this.result;
    }
    
    @Override
    public void save(Consumer<FinishedRecipe> consumer) {
        this.save(consumer, this.id);
    }
    
    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        id = new ResourceLocation(id.getNamespace(), "crafting/" + id.getPath());
        this.ensureValid(id);
        if (!this.advancement.getCriteria().isEmpty()) {
            this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
        }
        consumer.accept(new Result(
            id, this.result, this.count,
            this.group == null ? "" : this.group,
            this.rows, this.key,
            this.advancement,
            new ResourceLocation(id.getNamespace(),
                "recipes/" + id.getPath()),
            this.conditions));
    }
    
    /**
     * Makes sure that this recipe is valid
     */
    private void ensureValid(ResourceLocation id) {
        if (this.result == null) {
            throw new IllegalStateException("No result is defined for shaped recipe " + id + "!");
        }
        if (this.rows.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');
            
            for(String s : this.rows) {
                for(int i = 0; i < s.length(); ++i) {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'");
                    }
                    
                    set.remove(c0);
                }
            }
            
            if (!set.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            } else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
                throw new IllegalStateException("Shaped recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
            }
        }
    }
    
    public static class Result extends ShapedRecipeBuilder.Result {
        private final boolean serializeAdvancement;
        private final List<ICondition> conditions;
        
        public Result(ResourceLocation id,
                      Item result,
                      int count,
                      String group,
                      List<String> pattern,
                      Map<Character, Ingredient> key,
                      Advancement.Builder advancement,
                      ResourceLocation advancementId,
                      List<ICondition> conditions) {
            super(id, result, count, group, pattern, key, advancement, advancementId);
            this.serializeAdvancement = !advancement.getCriteria().isEmpty() && conditions.isEmpty();
            this.conditions = conditions;
        }
    
        @Override
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            if (!conditions.isEmpty()) {
                JsonArray conds = new JsonArray();
                conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
                json.add("conditions", conds);
            }
        }
    
        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return serializeAdvancement ? super.serializeAdvancement() : null;
        }
        
    }
    
}
