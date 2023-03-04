package plus.dragons.createcentralkitchen.data.recipe;

import com.farmersrespite.core.registry.FRItems;
import com.farmersrespite.core.tag.FRTags;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FarmersRespiteModuleFluids;
import vectorwing.farmersdelight.common.tag.ForgeTags;

@MethodsReturnNonnullByDefault
public class FarmersRespiteRecipes extends RecipeGen {
    private final GeneratedRecipe
        HAUNTING_YELLOW_TEA_LEAVES = modded(haunting("yellow_tea_leaves")
            .output(FRItems.YELLOW_TEA_LEAVES.get())
            .require(FRItems.GREEN_TEA_LEAVES.get())),
        HAUNTING_BLACK_TEA_LEAVES = modded(haunting("black_tea_leaves")
            .output(FRItems.BLACK_TEA_LEAVES.get())
            .require(FRItems.YELLOW_TEA_LEAVES.get())),
        MILLING_WILD_TEA_BUSH = modded(milling("wild_tea_bush")
            .output(FRItems.TEA_SEEDS.get())
            .output(Items.STICK)
            .require(FRItems.WILD_TEA_BUSH.get())),
        MILLING_COFFEE_BERRIES = modded(milling("coffee_berries")
            .output(FRItems.COFFEE_BEANS.get())
            .output(Items.RED_DYE)
            .require(FRItems.COFFEE_BERRIES.get())),
        MIXING_GREEN_TEA = modded(mixing("green_tea")
            .output(FarmersRespiteModuleFluids.GREEN_TEA.get(), 250)
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_YELLOW_TEA = modded(mixing("yellow_tea")
            .output(FarmersRespiteModuleFluids.YELLOW_TEA.get(), 250)
            .require(FRItems.YELLOW_TEA_LEAVES.get())
            .require(FRItems.YELLOW_TEA_LEAVES.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_BLACK_TEA = modded(mixing("black_tea")
            .output(FarmersRespiteModuleFluids.BLACK_TEA.get(), 250)
            .require(FRItems.BLACK_TEA_LEAVES.get())
            .require(FRItems.BLACK_TEA_LEAVES.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_DANDELION_TEA = modded(mixing("dandelion_tea")
            .output(FarmersRespiteModuleFluids.DANDELION_TEA.get(), 250)
            .require(FRTags.TEA_LEAVES)
            .require(Items.DANDELION)
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_PURULENT_TEA = modded(mixing("purulent_tea")
            .output(FarmersRespiteModuleFluids.PURULENT_TEA.get(), 250)
            .require(Items.NETHER_WART)
            .require(Items.FERMENTED_SPIDER_EYE)
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_ROSE_HIP_TEA = modded(mixing("rose_hip_tea")
            .output(FarmersRespiteModuleFluids.ROSE_HIP_TEA.get(), 250)
            .require(FRItems.ROSE_HIPS.get())
            .require(FRItems.ROSE_HIPS.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_COFFEE = modded(mixing("coffee")
            .output(FarmersRespiteModuleFluids.COFFEE.get(), 250)
            .require(FRItems.COFFEE_BEANS.get())
            .require(FRItems.COFFEE_BEANS.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_BUILDERS_TEA_FROM_TEA_LEAVES = modded(mixing("builders_tea_from_tea_leaves")
            .output(AllFluids.TEA.get(), 500)
            .require(Fluids.WATER, 250)
            .require(AllTags.forgeFluidTag("milk"), 250)
            .require(FRTags.TEA_LEAVES)
            .requiresHeat(HeatCondition.HEATED)),
        KETTLE_BUILDERS_TEA = modded(kettle("builders_tea")
            .output(AllItems.BUILDERS_TEA.get(), 2)
            .require(FRTags.TEA_LEAVES)
            .require(AllTags.forgeItemTag("milk"))
            .container(Items.GLASS_BOTTLE)
            .needWater()),
        COMPACTING_GREEN_TEA_COOKIE_FROM_WHEAT_FLOUR = modded(compacting("green_tea_cookie_from_wheat_flour")
            .output(FRItems.GREEN_TEA_COOKIE.get(), 8)
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        COMPACTING_GREEN_TEA_COOKIE_FROM_WHEAT_DOUGH = modded(compacting("green_tea_cookie_from_wheat_dough")
            .output(FRItems.GREEN_TEA_COOKIE.get(), 8)
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(ForgeTags.DOUGH_WHEAT)),
        CRAFTING_COFFEE_CAKE_FROM_WHEAT_FLOUR = common(shaped("coffee_cake_from_wheat_flour")
            .output(FRItems.COFFEE_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('c', FRItems.COFFEE_BEANS.get())
            .define('w', ForgeItemTags.FLOUR__WHEAT.tag)
            .pattern("msm")
            .pattern("cec")
            .pattern("www")),
        MIXING_COFFEE_CAKE = modded(mixing("coffee_cake")
            .output(FRItems.COFFEE_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(FRItems.COFFEE_BEANS.get())
            .require(FRItems.COFFEE_BEANS.get())
            .require(Tags.Fluids.MILK, 500)),
        END = null;
    
    public FarmersRespiteRecipes(DataGenerator datagen) {
        super("farmersrespite", datagen);
    }
    
    @Override
    public String getName() {
        return "Farmer's Respite Recipes";
    }
}
