package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.CornDelightFluidEntries;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class CornDelightRecipes extends DatapackRecipes {

    private final CreateRecipeProvider.GeneratedRecipe
            MIXING_CORN_SOUP = add(mixing("cord_soup")
                    .output(CornDelightFluidEntries.CORN_SOUP.get(), 250)
                    .require(Tags.Fluids.MILK, 250)
                    .require(ForgeItemTags.CROPS__CORN.tag)
                    .require(ForgeTags.CROPS_CABBAGE)
                    .require(Items.SUGAR)
                    .requiresHeat(HeatCondition.HEATED)),

            MIXING_CREAM_CORN_DRINK = add(mixing("cream_corn_drink")
                    .output(CornDelightFluidEntries.CREAMY_CORN_DRINK.get(), 250)
                    .require(Tags.Fluids.MILK, 250)
                    .require(ForgeItemTags.CROPS__CORN.tag)
                    .require(Items.SUGAR)
                    .requiresHeat(HeatCondition.HEATED));

    public CornDelightRecipes(DataGenerator generator) {
        super(Mods.CORN_DELIGHT, CentralKitchen.REGISTRATE, generator);
    }
}
