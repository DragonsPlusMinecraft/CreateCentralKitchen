package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

public class StandardRecipes extends Recipes {
    private final GeneratedRecipe
        COMPACTING_COOKIE = add(compacting("cookie")
            .output(Items.COOKIE, 8)
            .require(Items.COCOA_BEANS)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
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
    @Override
    public final @NotNull String getName() {
        return "CCK Built-in Standard Recipes";
    }
    
}
