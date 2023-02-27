package plus.dragons.createcentralkitchen.data.recipe;

import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import plus.dragons.createcentralkitchen.data.tag.ForgeItemTags;

public class VanillaRecipes extends RecipeGen {
    private final GeneratedRecipe
        COMPACTING_COOKIE = common(compacting("cookie")
            .output(Items.COOKIE, 8)
            .require(Items.COCOA_BEANS)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        FINISH = null;
    
    public VanillaRecipes(DataGenerator datagen) {
        super("minecraft", datagen);
    }
    
}
