package plus.dragons.createcentralkitchen.data.recipe;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.teamabnormals.neapolitan.core.other.tags.NeapolitanItemTags;
import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.data.tag.IntegrationItemTags;
import plus.dragons.createcentralkitchen.modules.neapolitan.entry.NeapolitanModuleFluids;
import plus.dragons.createcentralkitchen.modules.neapolitan.entry.NeapolitanModuleItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class NeapolitanRecipes extends RecipeGen {
    private final GeneratedRecipe
        MIXING_CHOCOLATE_BAR_FROM_CHOCOLATE_BLOCK = modded(mixing("chocolate_bar_from_chocolate_block")
            .output(NeapolitanItems.CHOCOLATE_BAR.get(), 9)
            .require(NeapolitanBlocks.CHOCOLATE_BLOCK.get())),
        MIXING_MELT_CHOCOLATE_STORAGE_BLOCK = modded(mixing("melt_chocolate_storage_block")
            .output(AllFluids.CHOCOLATE.get(), 1000)
            .require(ForgeItemTags.STORAGE_BLOCKS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_MELT_CHOCOLATE_SLAB = modded(mixing("melt_chocolate_slab")
            .output(AllFluids.CHOCOLATE.get(), 500)
            .require(ForgeItemTags.SLABS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_MELT_CHOCOLATE_VERTICAL_SLAB = modded(mixing("melt_chocolate_vertical_slab")
            .output(AllFluids.CHOCOLATE.get(), 500)
            .require(IntegrationItemTags.VERTICAL_SLABS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)
            .whenModLoaded("quark")),
        MIXING_MELT_CHOCOLATE_STAIRS = modded(mixing("melt_chocolate_stairs")
            .output(AllFluids.CHOCOLATE.get(), 1000)
            .require(ForgeItemTags.STAIRS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_MELT_CHOCOLATE_WALL = modded(mixing("melt_chocolate_wall")
            .output(AllFluids.CHOCOLATE.get(), 1000)
            .require(ForgeItemTags.WALLS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)),
        FILLING_CHOCOLATE_SPIDER_EYE = modded(filling("chocolate_spider_eye")
            .output(NeapolitanItems.CHOCOLATE_SPIDER_EYE.get())
            .require(Items.SPIDER_EYE)
            .require(AllFluids.CHOCOLATE.get(), 125)),
        MIXING_CHOCOLATE_ICE_CREAM = modded(mixing("chocolate_ice_cream")
            .output(NeapolitanModuleFluids.CHOCOLATE_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(ForgeItemTags.BARS__CHOCOLATE.tag)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_CHOCOLATE_ICE_CREAM_FROM_FLUID_CHOCOLATE = modded(mixing("chocolate_ice_cream_from_fluid_chocolate")
            .output(NeapolitanModuleFluids.CHOCOLATE_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(AllFluids.CHOCOLATE.get(), 250)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_CHOCOLATE_CAKE_FROM_WHEAT_FLOUR = common(shaped("chocolate_cake_from_wheat_flour")
            .output(NeapolitanItems.CHOCOLATE_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('c', ForgeItemTags.BARS__CHOCOLATE.tag)
            .define('w', ForgeItemTags.FLOUR__WHEAT.tag)
            .pattern("mcm")
            .pattern("ses")
            .pattern("wcw")),
        MIXING_CHOCOLATE_CAKE = modded(mixing("chocolate_cake")
            .output(NeapolitanItems.CHOCOLATE_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.BARS__CHOCOLATE.tag)
            .require(ForgeItemTags.BARS__CHOCOLATE.tag)
            .require(Tags.Fluids.MILK, 500)),
        MIXING_CHOCOLATE_CAKE_FROM_FLUID_CHOCOLATE = modded(mixing("chocolate_cake_from_fluid_chocolate")
            .output(NeapolitanItems.CHOCOLATE_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AllFluids.CHOCOLATE.get(), 500)
            .require(Tags.Fluids.MILK, 500)),
        MIXING_CHOCOLATE_MILKSHAKE = modded(mixing("chocolate_milkshake")
            .output(NeapolitanModuleFluids.CHOCOLATE_MILKSHAKE.get(), 750)
            .require(ForgeItemTags.BARS__CHOCOLATE.tag)
            .require(NeapolitanModuleFluids.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_CHOCOLATE_MILKSHAKE_FROM_ICE_CREAM = modded(mixing("chocolate_milkshake_from_ice_cream")
            .output(NeapolitanModuleFluids.CHOCOLATE_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanModuleFluids.CHOCOLATE_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_STRAWBERRY_SCONES_FROM_WHEAT_FLOUR = modded(shapeless("strawberry_scones_from_wheat_flour")
            .output(NeapolitanItems.STRAWBERRY_SCONES.get(), 2)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        CRAFTING_STRAWBERRY_SCONES_FROM_WHEAT_DOUGH = modded(shapeless("strawberry_scones_from_wheat_dough")
            .output(NeapolitanItems.STRAWBERRY_SCONES.get(), 2)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(Items.SUGAR)
            .require(ForgeTags.DOUGH_WHEAT)),
        MIXING_STRAWBERRY_ICE_CREAM = modded(mixing("strawberry_ice_cream")
            .output(NeapolitanModuleFluids.STRAWBERRY_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_STRAWBERRY_CAKE_FROM_WHEAT_FLOUR = common(shaped("strawberry_cake_from_wheat_flour")
            .output(NeapolitanItems.STRAWBERRY_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('c', NeapolitanItemTags.FRUITS_STRAWBERRY)
            .define('w', ForgeItemTags.FLOUR__WHEAT.tag)
            .pattern("mcm")
            .pattern("ses")
            .pattern("wcw")),
        MIXING_STRAWBERRY_CAKE = modded(mixing("strawberry_cake")
            .output(NeapolitanItems.STRAWBERRY_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(Tags.Fluids.MILK, 500)),
        MIXING_STRAWBERRY_MILKSHAKE = modded(mixing("strawberry_milkshake")
            .output(NeapolitanModuleFluids.STRAWBERRY_MILKSHAKE.get(), 750)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(NeapolitanModuleFluids.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_STRAWBERRY_MILKSHAKE_FROM_ICE_CREAM = modded(mixing("strawberry_milkshake_from_ice_cream")
            .output(NeapolitanModuleFluids.STRAWBERRY_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanModuleFluids.STRAWBERRY_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_VANILLA_FUDGE = modded(mixing("vanilla_fudge")
            .output(NeapolitanItems.VANILLA_FUDGE.get(), 4)
            .require(Items.SUGAR)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(Tags.Fluids.MILK, 250)),
        MIXING_VANILLA_ICE_CREAM = modded(mixing("vanilla_ice_cream")
            .output(NeapolitanModuleFluids.VANILLA_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_VANILLA_CAKE_FROM_WHEAT_FLOUR = common(shaped("vanilla_cake_from_wheat_flour")
            .output(NeapolitanItems.VANILLA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('c', NeapolitanItems.DRIED_VANILLA_PODS.get())
            .define('w', ForgeItemTags.FLOUR__WHEAT.tag)
            .pattern("mcm")
            .pattern("ses")
            .pattern("wcw")),
        MIXING_VANILLA_CAKE = modded(mixing("vanilla_cake")
            .output(NeapolitanItems.VANILLA_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(Tags.Fluids.MILK, 500)),
        MIXING_VANILLA_MILKSHAKE = modded(mixing("vanilla_milkshake")
            .output(NeapolitanModuleFluids.VANILLA_MILKSHAKE.get(), 750)
            .require(NeapolitanModuleFluids.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_BANANA_BREAD_FROM_WHEAT_FLOUR = modded(mixing("banana_bread_from_wheat_flour")
            .output(NeapolitanItems.BANANA_BREAD.get())
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        CRAFTING_BANANA_BREAD_FROM_WHEAT_DOUGH = modded(mixing("banana_bread_from_wheat_dough")
            .output(NeapolitanItems.BANANA_BREAD.get())
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(Items.SUGAR)
            .require(ForgeTags.DOUGH_WHEAT)),
        MIXING_BANANA_ICE_CREAM = modded(mixing("banana_ice_cream")
            .output(NeapolitanModuleFluids.BANANA_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_BANANA_CAKE_FROM_WHEAT_FLOUR = common(shaped("banana_cake_from_wheat_flour")
            .output(NeapolitanItems.BANANA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('c', NeapolitanItemTags.FRUITS_BANANA)
            .define('w', ForgeItemTags.FLOUR__WHEAT.tag)
            .pattern("mcm")
            .pattern("ses")
            .pattern("wcw")),
        MIXING_BANANA_CAKE = modded(mixing("banana_cake")
            .output(NeapolitanItems.BANANA_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(Tags.Fluids.MILK, 500)),
        MIXING_BANANA_MILKSHAKE = modded(mixing("banana_milkshake")
            .output(NeapolitanModuleFluids.BANANA_MILKSHAKE.get(), 750)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(NeapolitanModuleFluids.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_BANANA_MILKSHAKE_FROM_ICE_CREAM = modded(mixing("banana_milkshake_from_ice_cream")
            .output(NeapolitanModuleFluids.BANANA_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanModuleFluids.BANANA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_MINT_ICE_CREAM = modded(mixing("mint_ice_cream")
            .output(NeapolitanModuleFluids.MINT_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_MINT_CAKE_FROM_WHEAT_FLOUR = common(shaped("mint_cake_from_wheat_flour")
            .output(NeapolitanItems.BANANA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('c', NeapolitanItems.MINT_LEAVES.get())
            .define('w', ForgeItemTags.FLOUR__WHEAT.tag)
            .pattern("mcm")
            .pattern("ses")
            .pattern("wcw")),
        MIXING_MINT_CAKE = modded(mixing("mint_cake")
            .output(NeapolitanItems.MINT_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(Tags.Fluids.MILK, 500)),
        MIXING_MINT_MILKSHAKE = modded(mixing("mint_milkshake")
            .output(NeapolitanModuleFluids.MINT_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(NeapolitanModuleFluids.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_MINT_MILKSHAKE_FROM_ICE_CREAM = modded(mixing("mint_milkshake_from_ice_cream")
            .output(NeapolitanModuleFluids.MINT_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanModuleFluids.MINT_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_ADZUKI_BUN = modded(mixing("adzuki_bun")
            .output(NeapolitanItems.ADZUKI_BUN.get())
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_ADZUKI_BUN_FROM_WHEAT_FLOUR = modded(shapeless("adzuki_bun_from_wheat_flour")
            .output(NeapolitanItems.ADZUKI_BUN.get())
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeTags.MILK)),
        CRAFTING_ADZUKI_BUN_FROM_WHEAT_DOUGH = modded(shapeless("adzuki_bun_wheat_dough")
            .output(NeapolitanItems.ADZUKI_BUN.get())
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(ForgeTags.DOUGH_WHEAT)),
        MIXING_ADZUKI_ICE_CREAM = modded(mixing("adzuki_ice_cream")
            .output(NeapolitanModuleFluids.ADZUKI_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_ADZUKI_CAKE_FROM_WHEAT_FLOUR = common(shaped("adzuki_cake_from_wheat_flour")
            .output(NeapolitanItems.BANANA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('c', NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .define('w', ForgeItemTags.FLOUR__WHEAT.tag)
            .pattern("mcm")
            .pattern("ses")
            .pattern("wcw")),
        MIXING_ADZUKI_CAKE = modded(mixing("adzuki_cake")
            .output(NeapolitanItems.ADZUKI_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(Tags.Fluids.MILK, 500)),
        MIXING_ADZUKI_MILKSHAKE = modded(mixing("adzuki_milkshake")
            .output(NeapolitanModuleFluids.ADZUKI_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(NeapolitanModuleFluids.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_ADZUKI_MILKSHAKE_FROM_ICE_CREAM = modded(mixing("adzuki_milkshake_from_ice_cream")
            .output(NeapolitanModuleFluids.ADZUKI_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanModuleFluids.ADZUKI_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        FILLING_CHOCOLATE_STRAWBERRIES = modded(filling("chocolate_strawberries")
            .output(NeapolitanItems.CHOCOLATE_STRAWBERRIES.get())
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(AllFluids.CHOCOLATE.get(), 250)),
        FILLING_VANILLA_CHOCOLATE_FINGERS = modded(filling("vanilla_chocolate_fingers")
            .output(NeapolitanItems.VANILLA_CHOCOLATE_FINGERS.get())
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(AllFluids.CHOCOLATE.get(), 250)),
        MIXING_STRAWBERRY_BANANA_SMOOTHIE = modded(mixing("strawberry_banana_smoothie")
            .output(NeapolitanModuleFluids.STRAWBERRY_BANANA_SMOOTHIE.get(), 250)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(NeapolitanItems.ICE_CUBES.get())),
        FILLING_MINT_CHOCOLATE = modded(filling("mint_chocolate")
            .output(NeapolitanItems.MINT_CHOCOLATE.get())
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(AllFluids.CHOCOLATE.get(), 250)),
        SEQUENCED_ASSEMBLY_NEAPOLITAN_ICE_CREAM_START_WITH_CHOCOLATE = modded(sequencedAssembly("neapolitan_ice_cream_start_with_chocolate")
            .transitionTo(NeapolitanModuleItems.INCOMPLETE_NEAPOLITAN_ICE_CREAM_CHOCOLATE.get())
            .addOutput(NeapolitanItems.NEAPOLITAN_ICE_CREAM.get(), 1)
            .loops(1)
            .require(NeapolitanItems.VANILLA_ICE_CREAM.get())
            .addStep(FillingRecipe::new, builder -> builder.require(AllFluids.CHOCOLATE.get(), 250))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(NeapolitanItemTags.FRUITS_STRAWBERRY))),
        SEQUENCED_ASSEMBLY_NEAPOLITAN_ICE_CREAM_START_WITH_STRAWBERRY = modded(sequencedAssembly("neapolitan_ice_cream_start_with_strawberry")
            .transitionTo(NeapolitanModuleItems.INCOMPLETE_NEAPOLITAN_ICE_CREAM_STRAWBERRY.get())
            .addOutput(NeapolitanItems.NEAPOLITAN_ICE_CREAM.get(), 1)
            .loops(1)
            .require(NeapolitanItems.VANILLA_ICE_CREAM.get())
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(NeapolitanItemTags.FRUITS_STRAWBERRY))
            .addStep(FillingRecipe::new, builder -> builder.require(AllFluids.CHOCOLATE.get(), 250))),
        END = null;
    
    public NeapolitanRecipes(DataGenerator datagen) {
        super("neapolitan", datagen);
    }
    
    @Override
    public String getName() {
        return "Neapolitan Recipes";
    }
}
