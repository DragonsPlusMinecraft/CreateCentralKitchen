package plus.dragons.createcentralkitchen.data.recipe.builder;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class ConditionedSequencedAssemblyRecipeBuilder extends SequencedAssemblyRecipeBuilder implements ConditionedRecipeBuilder<ConditionedSequencedAssemblyRecipeBuilder> {
    
    public ConditionedSequencedAssemblyRecipeBuilder(ResourceLocation id) {
        super(id);
    }
    
    @Override
    public <T extends ProcessingRecipe<?>> ConditionedSequencedAssemblyRecipeBuilder addStep(ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory, UnaryOperator<ProcessingRecipeBuilder<T>> builder) {
        super.addStep(factory, builder);
        return this;
    }
    
    @Override
    public ConditionedSequencedAssemblyRecipeBuilder require(TagKey<Item> tag) {
        super.require(tag);
        return this;
    }
    
    @Override
    public ConditionedSequencedAssemblyRecipeBuilder require(ItemLike ingredient) {
        super.require(ingredient);
        return this;
    }
    
    @Override
    public ConditionedSequencedAssemblyRecipeBuilder require(Ingredient ingredient) {
        super.require(ingredient);
        return this;
    }
    
    @Override
    public ConditionedSequencedAssemblyRecipeBuilder transitionTo(ItemLike item) {
        super.transitionTo(item);
        return this;
    }
    
    @Override
    public ConditionedSequencedAssemblyRecipeBuilder loops(int loops) {
        super.loops(loops);
        return this;
    }
    
    @Override
    public ConditionedSequencedAssemblyRecipeBuilder addOutput(ItemLike item, float weight) {
        super.addOutput(item, weight);
        return this;
    }
    
    @Override
    public ConditionedSequencedAssemblyRecipeBuilder addOutput(ItemStack item, float weight) {
        super.addOutput(item, weight);
        return this;
    }
    
    @Override
    public ConditionedSequencedAssemblyRecipeBuilder withCondition(ICondition condition) {
        recipeConditions.add(condition);
        return this;
    }
    
    @Override
    public void save(Consumer<FinishedRecipe> consumer) {
        this.build(consumer);
    }
    
    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation overrideId) {
        throw new UnsupportedOperationException("ConditionedSequencedAssemblyRecipeBuilder does not support id override!");
    }
    
}
