package plus.dragons.createcentralkitchen.foundation.data.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConditionedCuttingBoardRecipeBuilder implements ConditionedRecipeBuilder<ConditionedCuttingBoardRecipeBuilder> {
    private final ResourceLocation id;
    private final List<ChanceResult> results = new ArrayList<>(4);
    private Ingredient ingredient;
    private Ingredient tool;
    private String soundEventID = "";
    private final List<ICondition> conditions;
    
    public ConditionedCuttingBoardRecipeBuilder(ResourceLocation id) {
        this.id = id;
        this.conditions = new ArrayList<>();
    }
    
    public ConditionedCuttingBoardRecipeBuilder require(TagKey<Item> tagIn) {
        return this.require(Ingredient.of(tagIn));
    }
    
    public ConditionedCuttingBoardRecipeBuilder require(ItemLike itemIn) {
        return this.require(Ingredient.of(itemIn));
    }
    
    public ConditionedCuttingBoardRecipeBuilder require(Ingredient ingredientIn) {
        this.ingredient = ingredientIn;
        return this;
    }
    
    public ConditionedCuttingBoardRecipeBuilder tool(TagKey<Item> tagIn) {
        return this.tool(Ingredient.of(tagIn));
    }
    
    public ConditionedCuttingBoardRecipeBuilder tool(ItemLike itemIn) {
        return this.tool(Ingredient.of(itemIn));
    }
    
    public ConditionedCuttingBoardRecipeBuilder tool(Ingredient toolIn) {
        this.tool = toolIn;
        return this;
    }
    
    public ConditionedCuttingBoardRecipeBuilder output(ItemLike result, int count) {
        this.results.add(new ChanceResult(new ItemStack(result.asItem(), count), 1.0F));
        return this;
    }
    
    public ConditionedCuttingBoardRecipeBuilder output(ItemLike result, float chance) {
        return this.output(result, chance, 1);
    }
    
    public ConditionedCuttingBoardRecipeBuilder output(ItemLike result, float chance, int count) {
        this.results.add(new ChanceResult(new ItemStack(result.asItem(), count), chance));
        return this;
    }
    
    public ConditionedCuttingBoardRecipeBuilder sound(String soundEventID) {
        this.soundEventID = soundEventID;
        return this;
    }
    
    @Override
    public ConditionedCuttingBoardRecipeBuilder withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }
    
    @Override
    public void save(Consumer<FinishedRecipe> consumer) {
        this.save(consumer, id);
    }
    
    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        id = new ResourceLocation(id.getNamespace(), "cutting/" + id.getPath());
        this.ensureValid(id);
        consumer.accept(new Result(id, ingredient, tool, results, soundEventID, conditions));
    }
    
    /**
     * Makes sure that this recipe is valid
     */
    private void ensureValid(ResourceLocation id) {
        if (this.results.isEmpty())
            throw new IllegalStateException("No result is defined for cutting board recipe " + id + "!");
        if (this.results.size() > 4)
            throw new IllegalStateException("Too many results are defined for cutting board recipe " + id + "!");
        if (this.ingredient == null)
            throw new IllegalStateException("No ingredient is defined for cutting board recipe " + id + "!");
        if (this.tool == null)
            throw new IllegalStateException("No tool is defined for cutting board recipe " + id + "!");
    }
    
    public static class Result extends CuttingBoardRecipeBuilder.Result {
        private final List<ICondition> conditions;
        
        public Result(ResourceLocation idIn,
                      Ingredient ingredientIn,
                      Ingredient toolIn,
                      List<ChanceResult> resultsIn,
                      String soundEventIDIn,
                      List<ICondition> conditionsIn) {
            super(idIn, ingredientIn, toolIn, resultsIn, soundEventIDIn);
            this.conditions = conditionsIn;
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
    }
    
}
