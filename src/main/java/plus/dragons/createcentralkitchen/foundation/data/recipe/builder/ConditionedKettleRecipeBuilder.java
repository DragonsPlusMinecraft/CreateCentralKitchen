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
import net.minecraftforge.fluids.FluidStack;
import umpaz.farmersrespite.data.builder.KettleRecipeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConditionedKettleRecipeBuilder implements ConditionedRecipeBuilder<ConditionedKettleRecipeBuilder> {
    private final ResourceLocation id;
    private FluidStack fluidIn;
    private FluidStack result;
    private int duration = 2400;
    private float experience = 0.35F;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final List<ICondition> conditions = new ArrayList<>();
    
    public ConditionedKettleRecipeBuilder(ResourceLocation id) {
        this.id = id;
    }

    public ConditionedKettleRecipeBuilder requireFluid(FluidStack fluid) {
        this.fluidIn = fluid;
        return this;
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

    
    public ConditionedKettleRecipeBuilder output(FluidStack result) {
        this.result = result;
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
        consumer.accept(new Result(id,this.fluidIn, this.result, this.ingredients, this.duration, this.experience, this.conditions));
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
                      FluidStack fluidIn,
                      FluidStack result,
                      List<Ingredient> ingredient,
                      int duration,
                      float experience,
                      List<ICondition> conditions) {
            super(id, fluidIn, result, ingredient, duration, experience);
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
