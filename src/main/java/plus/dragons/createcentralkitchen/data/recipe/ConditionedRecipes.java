package plus.dragons.createcentralkitchen.data.recipe;

import net.minecraft.world.level.ItemLike;

public class ConditionedRecipes {
    
    public static ConditionedShapelessRecipeBuilder shapeless(ItemLike result, int count) {
        return new ConditionedShapelessRecipeBuilder(result, count);
    }
    
    public static ConditionedShapelessRecipeBuilder shapeless(ItemLike result) {
        return shapeless(result, 1);
    }
    
    public static ConditionedShapedRecipeBuilder shaped(ItemLike result, int count) {
        return new ConditionedShapedRecipeBuilder(result, count);
    }
    
    public static ConditionedShapedRecipeBuilder shaped(ItemLike result) {
        return shaped(result, 1);
    }
    
}
