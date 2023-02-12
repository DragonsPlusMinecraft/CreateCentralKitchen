package plus.dragons.createcentralkitchen.data.recipe;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.tag.FRTags;
import vectorwing.farmersdelight.common.registry.ModItems;

@MethodsReturnNonnullByDefault
public class FarmersRespiteRecipes extends RecipeGen {
    private final GeneratedRecipe
    COFFEE_BEAN_MILLING = modded(milling("coffee_beans")
        .output(FRItems.COFFEE_BEANS.get())
        .output(Items.RED_DYE)
        .require(FRItems.COFFEE_BERRIES.get())),
    YELLOW_TEA_LEAVES_HAUNTING = modded(haunting("yellow_tea_leaves")
        .output(FRItems.YELLOW_TEA_LEAVES.get())
        .require(FRItems.GREEN_TEA_LEAVES.get())),
    BLACK_TEA_LEAVES_HAUNTING = modded(haunting("black_tea_leaves")
        .output(FRItems.BLACK_TEA_LEAVES.get())
        .require(FRItems.YELLOW_TEA_LEAVES.get())),
    BUILDERS_TEA_FROM_TEA_LEAVES_MIXING = modded(mixing("builders_tea_from_tea_leaves")
        .output(AllFluids.TEA.get(), 500)
        .require(Fluids.WATER, 250)
        .require(Tags.Fluids.MILK, 250)
        .require(FRTags.TEA_LEAVES)
        .requiresHeat(HeatCondition.HEATED)),
    BUILDERS_TEA_KETTLE = modded(kettle("builders_tea")
        .output(AllItems.BUILDERS_TEA.get())
        .require(FRTags.TEA_LEAVES)
        .require(ModItems.MILK_BOTTLE.get())
        .container(Items.GLASS)
        .needWater()),
    FINISH = null;
    
    public FarmersRespiteRecipes(DataGenerator datagen) {
        super("farmersrespite", datagen);
    }
    
    @Override
    public String getName() {
        return "Farmer's Respite Recipes";
    }
}
