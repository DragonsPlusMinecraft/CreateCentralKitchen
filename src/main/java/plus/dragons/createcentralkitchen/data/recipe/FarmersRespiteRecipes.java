package plus.dragons.createcentralkitchen.data.recipe;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrFluids;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.tag.FRTags;

@MethodsReturnNonnullByDefault
public class FarmersRespiteRecipes extends RecipeGen {
    private final GeneratedRecipe
    WILD_TEA_BUSH_MILLING = modded(milling("wild_tea_bush")
        .output(FRItems.TEA_SEEDS.get())
        .output(Items.STICK)
        .require(FRItems.WILD_TEA_BUSH.get())),
    COFFEE_BERRIES_MILLING = modded(milling("coffee_berries")
        .output(FRItems.COFFEE_BEANS.get())
        .output(Items.RED_DYE)
        .require(FRItems.COFFEE_BERRIES.get())),
    YELLOW_TEA_LEAVES_HAUNTING = modded(haunting("yellow_tea_leaves")
        .output(FRItems.YELLOW_TEA_LEAVES.get())
        .require(FRItems.GREEN_TEA_LEAVES.get())),
    BLACK_TEA_LEAVES_HAUNTING = modded(haunting("black_tea_leaves")
        .output(FRItems.BLACK_TEA_LEAVES.get())
        .require(FRItems.YELLOW_TEA_LEAVES.get())),
    GREEN_TEA_MIXING = modded(mixing("green_tea")
        .output(FrFluids.GREEN_TEA.get(), 250)
        .require(FRItems.GREEN_TEA_LEAVES.get())
        .require(FRItems.GREEN_TEA_LEAVES.get())
        .require(Fluids.WATER, 250)
        .requiresHeat(HeatCondition.HEATED)),
    YELLOW_TEA_MIXING = modded(mixing("yellow_tea")
        .output(FrFluids.YELLOW_TEA.get(), 250)
        .require(FRItems.YELLOW_TEA_LEAVES.get())
        .require(FRItems.YELLOW_TEA_LEAVES.get())
        .require(Fluids.WATER, 250)
        .requiresHeat(HeatCondition.HEATED)),
    BLACK_TEA_MIXING = modded(mixing("black_tea")
        .output(FrFluids.BLACK_TEA.get(), 250)
        .require(FRItems.BLACK_TEA_LEAVES.get())
        .require(FRItems.BLACK_TEA_LEAVES.get())
        .require(Fluids.WATER, 250)
        .requiresHeat(HeatCondition.HEATED)),
    DANDELION_TEA_MIXING = modded(mixing("dandelion_tea")
        .output(FrFluids.DANDELION_TEA.get(), 250)
        .require(FRTags.TEA_LEAVES)
        .require(Items.DANDELION)
        .require(Fluids.WATER, 250)
        .requiresHeat(HeatCondition.HEATED)),
    PURULENT_TEA_MIXING = modded(mixing("purulent_tea")
        .output(FrFluids.PURULENT_TEA.get(), 250)
        .require(Items.NETHER_WART)
        .require(Items.FERMENTED_SPIDER_EYE)
        .require(Fluids.WATER, 250)
        .requiresHeat(HeatCondition.HEATED)),
    ROSE_HIP_TEA_MIXING = modded(mixing("rose_hip_tea")
        .output(FrFluids.ROSE_HIP_TEA.get(), 250)
        .require(FRItems.ROSE_HIPS.get())
        .require(FRItems.ROSE_HIPS.get())
        .require(Fluids.WATER, 250)
        .requiresHeat(HeatCondition.HEATED)),
    COFFEE_MIXING = modded(mixing("coffee")
        .output(FrFluids.COFFEE.get(), 250)
        .require(FRItems.COFFEE_BEANS.get())
        .require(FRItems.COFFEE_BEANS.get())
        .require(Fluids.WATER, 250)
        .requiresHeat(HeatCondition.HEATED)),
    BUILDERS_TEA_FROM_TEA_LEAVES_MIXING = modded(mixing("builders_tea_from_tea_leaves")
        .output(AllFluids.TEA.get(), 500)
        .require(Fluids.WATER, 250)
        .require(AllTags.forgeFluidTag("milk"), 250)
        .require(FRTags.TEA_LEAVES)
        .requiresHeat(HeatCondition.HEATED)),
    BUILDERS_TEA_KETTLE = modded(kettle("builders_tea")
        .output(AllItems.BUILDERS_TEA.get(), 2)
        .require(FRTags.TEA_LEAVES)
        .require(AllTags.forgeItemTag("milk"))
        .container(Items.GLASS_BOTTLE)
        .needWater()),
    GREEN_TEA_COOKIE_COMPACTING = modded(compacting("green_tea_cookie")
        .output(FRItems.GREEN_TEA_COOKIE.get(), 8)
        .require(FRItems.GREEN_TEA_LEAVES.get())
        .require(AllTags.forgeItemTag("flour/wheat"))
        .require(AllTags.forgeItemTag("flour/wheat"))),
    FINISH = null;
    
    public FarmersRespiteRecipes(DataGenerator datagen) {
        super("farmersrespite", datagen);
    }
    
    @Override
    public String getName() {
        return "Farmer's Respite Recipes";
    }
}
