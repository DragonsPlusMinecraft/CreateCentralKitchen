package plus.dragons.createcentralkitchen.data.recipe.builder;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.function.Consumer;

public interface ConditionedRecipeBuilder<S extends ConditionedRecipeBuilder<S>> {
    
    S withCondition(ICondition condition);
    
    default S whenModLoaded(String modid) {
        return withCondition(new ModLoadedCondition(modid));
    }
    
    default S whenModMissing(String modid) {
        return withCondition(new NotCondition(new ModLoadedCondition(modid)));
    }
    
    void save(Consumer<FinishedRecipe> consumer);
    
    void save(Consumer<FinishedRecipe> consumer, ResourceLocation overrideId);
    
}
