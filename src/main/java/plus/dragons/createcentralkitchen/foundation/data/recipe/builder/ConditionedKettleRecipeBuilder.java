package plus.dragons.createcentralkitchen.foundation.data.recipe.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import umpaz.farmersrespite.client.recipebook.KettleRecipeBookTab;
import umpaz.farmersrespite.data.builder.KettleRecipeBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConditionedKettleRecipeBuilder implements ConditionedRecipeBuilder<ConditionedKettleRecipeBuilder> {
    private final ResourceLocation id;
    private Item result;
    private KettleRecipeBookTab tab = KettleRecipeBookTab.DRINKS;
    private int count = 1;
    private String group = "";
    private int duration = 2400;
    private float experience = 0.35F;
    private Item container = null;
    private boolean needWater = false;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final List<ICondition> conditions = new ArrayList<>();
    
    public ConditionedKettleRecipeBuilder(ResourceLocation id) {
        this.id = id;
    }
    
    public ConditionedKettleRecipeBuilder require(TagKey<Item> tagIn) {
        return this.require(Ingredient.of(tagIn));
    }
    
    public ConditionedKettleRecipeBuilder require(ItemLike itemIn) {
        return this.require(itemIn, 1);
    }
    
    public ConditionedKettleRecipeBuilder require(ItemLike itemIn, int count) {
        for(int i = 0; i < count; ++i) {
            this.require(Ingredient.of(itemIn));
        }
        return this;
    }
    
    public ConditionedKettleRecipeBuilder require(Ingredient ingredientIn) {
        return this.require(ingredientIn, 1);
    }
    
    public ConditionedKettleRecipeBuilder require(Ingredient ingredientIn, int count) {
        for(int i = 0; i < count; ++i) {
            this.ingredients.add(ingredientIn);
        }
        return this;
    }
    
    public ConditionedKettleRecipeBuilder duration(int ticks) {
        this.duration = ticks;
        return this;
    }
    
    public ConditionedKettleRecipeBuilder experience(float experience) {
        this.experience = experience;
        return this;
    }
    
    public ConditionedKettleRecipeBuilder experience(double experience) {
        this.experience = (float) experience;
        return this;
    }
    
    public ConditionedKettleRecipeBuilder container(@Nullable Item container) {
        this.container = container;
        return this;
    }
    
    public ConditionedKettleRecipeBuilder needWater() {
        this.needWater = true;
        return this;
    }
    
    public ConditionedKettleRecipeBuilder output(ItemLike item, int count) {
        this.result = item.asItem();
        this.count = count;
        return this;
    }
    
    public ConditionedKettleRecipeBuilder output(ItemLike item) {
        return output(item, 1);
    }
    
    public ConditionedKettleRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }
    
    public ConditionedKettleRecipeBuilder setRecipeBookTab(KettleRecipeBookTab tab) {
        this.tab = tab;
        return this;
    }
    
    @Override
    public ConditionedKettleRecipeBuilder withCondition(ICondition condition) {
        this.conditions.add(condition);
        return this ;
    }
    
    @Override
    public void save(Consumer<FinishedRecipe> consumer) {
        this.save(consumer, this.id);
    }
    
    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        id = new ResourceLocation(id.getNamespace(), "brewing/" + id.getPath());
        this.ensureValid(id);
        consumer.accept(new Result(id,
            this.result, this.count, this.group,
            this.ingredients, this.duration, this.experience,
            this.needWater, this.container, this.tab, this.conditions));
    }
    
    /**
     * Makes sure that this recipe is valid
     */
    private void ensureValid(ResourceLocation id) {
        if (this.result == null) {
            throw new IllegalStateException("No result is defined for kettle brewing recipe " + id + "!");
        }
    }
    
    public static class Result extends KettleRecipeBuilder.Result {
        private final List<ICondition> conditions;
        
        public Result(ResourceLocation id,
                      Item result,
                      int count,
                      String group,
                      List<Ingredient> ingredient,
                      int duration,
                      float experience,
                      boolean needWater,
                      @Nullable Item container,
                      @Nullable KettleRecipeBookTab tab,
                      List<ICondition> conditions) {
            super(id, result, count, group, ingredient, duration, needWater, experience, container, tab);
            this.conditions = conditions;
        }
        
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            if (!conditions.isEmpty()) {
                JsonArray conds = new JsonArray();
                conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
                json.add("conditions", conds);
            }
        }
        
    }
    
}
