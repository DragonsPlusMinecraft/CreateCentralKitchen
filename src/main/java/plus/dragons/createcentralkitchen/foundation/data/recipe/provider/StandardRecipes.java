package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class StandardRecipes extends Recipes {
    private final GeneratedRecipe
        COMPACTING_COOKIE_FROM_FLOUR = add(compacting("cookie_from_flour")
            .output(Items.COOKIE, 8)
            .require(Items.COCOA_BEANS)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        COMPACTING_COOKIE_FROM_DOUGH = add(compacting("cookie_from_dough")
            .output(Items.COOKIE, 8)
            .require(Items.COCOA_BEANS)
            .require(ForgeTags.DOUGH_WHEAT)),
        COMPACTING_CAKE = add(compacting("cake")
            .output(Items.CAKE)
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(Tags.Fluids.MILK, 1000)
            .whenModMissing(Mods.CA));
    
    public StandardRecipes(DataGenerator datagen) {
        super(CentralKitchen.REGISTRATE, datagen);
    }
    
}
