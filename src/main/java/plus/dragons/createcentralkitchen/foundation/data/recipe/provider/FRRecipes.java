package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.FRFluidEntries;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.tag.FRTags;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class FRRecipes extends DatapackRecipes {
    private final GeneratedRecipe
        HAUNTING_YELLOW_TEA_LEAVES = add(haunting("yellow_tea_leaves")
            .output(FRItems.YELLOW_TEA_LEAVES.get())
            .require(FRItems.GREEN_TEA_LEAVES.get())),
        HAUNTING_BLACK_TEA_LEAVES = add(haunting("black_tea_leaves")
            .output(FRItems.BLACK_TEA_LEAVES.get())
            .require(FRItems.YELLOW_TEA_LEAVES.get())),
        MILLING_WILD_TEA_BUSH = add(milling("wild_tea_bush")
            .output(FRItems.TEA_SEEDS.get())
            .output(Items.STICK)
            .require(FRItems.WILD_TEA_BUSH.get())),
        MILLING_COFFEE_BERRIES = add(milling("coffee_berries")
            .output(FRItems.COFFEE_BEANS.get())
            .output(Items.RED_DYE)
            .require(FRItems.COFFEE_BERRIES.get())),
        MIXING_GREEN_TEA = add(mixing("green_tea")
            .output(FRFluidEntries.GREEN_TEA.get(), 250)
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_YELLOW_TEA = add(mixing("yellow_tea")
            .output(FRFluidEntries.YELLOW_TEA.get(), 250)
            .require(FRItems.YELLOW_TEA_LEAVES.get())
            .require(FRItems.YELLOW_TEA_LEAVES.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_BLACK_TEA = add(mixing("black_tea")
            .output(FRFluidEntries.BLACK_TEA.get(), 250)
            .require(FRItems.BLACK_TEA_LEAVES.get())
            .require(FRItems.BLACK_TEA_LEAVES.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_DANDELION_TEA = add(mixing("dandelion_tea")
            .output(FRFluidEntries.DANDELION_TEA.get(), 250)
            .require(FRTags.TEA_LEAVES)
            .require(Items.DANDELION)
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_PURULENT_TEA = add(mixing("purulent_tea")
            .output(FRFluidEntries.PURULENT_TEA.get(), 250)
            .require(Items.NETHER_WART)
            .require(Items.FERMENTED_SPIDER_EYE)
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_ROSE_HIP_TEA = add(mixing("rose_hip_tea")
            .output(FRFluidEntries.ROSE_HIP_TEA.get(), 250)
            .require(FRItems.ROSE_HIPS.get())
            .require(FRItems.ROSE_HIPS.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_COFFEE = add(mixing("coffee")
            .output(FRFluidEntries.COFFEE.get(), 250)
            .require(FRItems.COFFEE_BEANS.get())
            .require(FRItems.COFFEE_BEANS.get())
            .require(Fluids.WATER, 250)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_BUILDERS_TEA_FROM_TEA_LEAVES = add(mixing("builders_tea_from_tea_leaves")
            .output(AllFluids.TEA.get(), 500)
            .require(Fluids.WATER, 250)
            .require(AllTags.forgeFluidTag("milk"), 250)
            .require(FRTags.TEA_LEAVES)
            .requiresHeat(HeatCondition.HEATED)),
        KETTLE_BUILDERS_TEA = add(kettle("builders_tea")
            .output(AllItems.BUILDERS_TEA.get(), 2)
            .require(FRTags.TEA_LEAVES)
            .require(AllTags.forgeItemTag("milk"))
            .container(Items.GLASS_BOTTLE)
            .needWater()),
        COMPACTING_GREEN_TEA_COOKIE = add(compacting("green_tea_cookie")
            .output(FRItems.GREEN_TEA_COOKIE.get(), 8)
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        CRAFTING_COFFEE_CAKE_FROM_DOUGH = add(shaped("coffee_cake_from_dough")
            .output(FRItems.COFFEE_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', FRItems.COFFEE_BEANS.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        MIXING_COFFEE_CAKE = add(mixing("coffee_cake")
            .output(FRItems.COFFEE_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(FRItems.COFFEE_BEANS.get())
            .require(FRItems.COFFEE_BEANS.get())
            .require(Tags.Fluids.MILK, 1000));
    
    public FRRecipes(DataGenerator datagen) {
        super(Mods.FR, CentralKitchen.REGISTRATE, datagen);
    }
    
}
