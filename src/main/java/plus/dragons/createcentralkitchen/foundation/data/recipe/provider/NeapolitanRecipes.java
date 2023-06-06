package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.teamabnormals.neapolitan.core.other.tags.NeapolitanItemTags;
import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.NeapolitanFluidEntries;
import plus.dragons.createcentralkitchen.entry.item.NeapolitanItemEntries;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.data.tag.IntegrationItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class NeapolitanRecipes extends DatapackRecipes {
    private final GeneratedRecipe
        MIXING_CHOCOLATE_BAR_FROM_CHOCOLATE_BLOCK = add(mixing("chocolate_bar_from_chocolate_block")
            .output(NeapolitanItems.CHOCOLATE_BAR.get(), 9)
            .require(NeapolitanBlocks.CHOCOLATE_BLOCK.get())),
        MIXING_MELT_CHOCOLATE_STORAGE_BLOCK = add(mixing("melt_chocolate_storage_block")
            .output(AllFluids.CHOCOLATE.get(), 1000)
            .require(ForgeItemTags.STORAGE_BLOCKS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_MELT_CHOCOLATE_SLAB = add(mixing("melt_chocolate_slab")
            .output(AllFluids.CHOCOLATE.get(), 500)
            .require(ForgeItemTags.SLABS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_MELT_CHOCOLATE_VERTICAL_SLAB = add(mixing("melt_chocolate_vertical_slab")
            .output(AllFluids.CHOCOLATE.get(), 500)
            .require(IntegrationItemTags.VERTICAL_SLABS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)
            .withCondition(new NotCondition(new TagEmptyCondition(IntegrationItemTags.VERTICAL_SLABS__CHOCOLATE.tag.location())))),
        MIXING_MELT_CHOCOLATE_STAIRS = add(mixing("melt_chocolate_stairs")
            .output(AllFluids.CHOCOLATE.get(), 1000)
            .require(ForgeItemTags.STAIRS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_MELT_CHOCOLATE_WALL = add(mixing("melt_chocolate_wall")
            .output(AllFluids.CHOCOLATE.get(), 1000)
            .require(ForgeItemTags.WALLS__CHOCOLATE.tag)
            .requiresHeat(HeatCondition.HEATED)),
        FILLING_CHOCOLATE_SPIDER_EYE = add(filling("chocolate_spider_eye")
            .output(NeapolitanItems.CHOCOLATE_SPIDER_EYE.get())
            .require(Items.SPIDER_EYE)
            .require(AllFluids.CHOCOLATE.get(), 125)),
        MIXING_CHOCOLATE_ICE_CREAM = add(mixing("chocolate_ice_cream")
            .output(NeapolitanFluidEntries.CHOCOLATE_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(ForgeItemTags.BARS__CHOCOLATE.tag)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_CHOCOLATE_ICE_CREAM_FROM_FLUID_CHOCOLATE = add(mixing("chocolate_ice_cream_from_fluid_chocolate")
            .output(NeapolitanFluidEntries.CHOCOLATE_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(AllFluids.CHOCOLATE.get(), 250)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_CHOCOLATE_CAKE_FROM_DOUGH = add(shaped("chocolate_cake_from_dough")
            .output(NeapolitanItems.CHOCOLATE_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', ForgeItemTags.BARS__CHOCOLATE.tag)
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_CHOCOLATE_CAKE = add(compacting("chocolate_cake")
            .output(NeapolitanItems.CHOCOLATE_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.BARS__CHOCOLATE.tag)
            .require(ForgeItemTags.BARS__CHOCOLATE.tag)
            .require(Tags.Fluids.MILK, 1000)),
        COMPACTING_CHOCOLATE_CAKE_FROM_FLUID_CHOCOLATE = add(compacting("chocolate_cake_from_fluid_chocolate")
            .output(NeapolitanItems.CHOCOLATE_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AllFluids.CHOCOLATE.get(), 500)
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_CHOCOLATE_MILKSHAKE = add(mixing("chocolate_milkshake")
            .output(NeapolitanFluidEntries.CHOCOLATE_MILKSHAKE.get(), 750)
            .require(ForgeItemTags.BARS__CHOCOLATE.tag)
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_CHOCOLATE_MILKSHAKE_FROM_ICE_CREAM = add(mixing("chocolate_milkshake_from_ice_cream")
            .output(NeapolitanFluidEntries.CHOCOLATE_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanFluidEntries.CHOCOLATE_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        HAUNTING_WHITE_STRAWBERRIES = add(haunting("white_strawberries")
            .output(NeapolitanItems.WHITE_STRAWBERRIES.get())
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)),
        MIXING_STRAWBERRY_SCONES_FROM_FLOUR = add(mixing("strawberry_scones_from_flour")
            .output(NeapolitanItems.STRAWBERRY_SCONES.get(), 2)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        CRAFTING_STRAWBERRY_SCONES_FROM_DOUGH = add(shapeless("strawberry_scones_from_dough")
            .output(NeapolitanItems.STRAWBERRY_SCONES.get(), 2)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(Items.SUGAR)
            .require(ForgeTags.DOUGH_WHEAT)),
        MIXING_STRAWBERRY_ICE_CREAM = add(mixing("strawberry_ice_cream")
            .output(NeapolitanFluidEntries.STRAWBERRY_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_STRAWBERRY_CAKE_FROM_DOUGH = add(shaped("strawberry_cake_from_dough")
            .output(NeapolitanItems.STRAWBERRY_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', NeapolitanItemTags.FRUITS_STRAWBERRY)
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_STRAWBERRY_CAKE = add(compacting("strawberry_cake")
            .output(NeapolitanItems.STRAWBERRY_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_STRAWBERRY_MILKSHAKE = add(mixing("strawberry_milkshake")
            .output(NeapolitanFluidEntries.STRAWBERRY_MILKSHAKE.get(), 750)
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_STRAWBERRY_MILKSHAKE_FROM_ICE_CREAM = add(mixing("strawberry_milkshake_from_ice_cream")
            .output(NeapolitanFluidEntries.STRAWBERRY_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanFluidEntries.STRAWBERRY_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_VANILLA_FUDGE = add(mixing("vanilla_fudge")
            .output(NeapolitanItems.VANILLA_FUDGE.get(), 4)
            .require(Items.SUGAR)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(Tags.Fluids.MILK, 250)),
        MIXING_VANILLA_ICE_CREAM = add(mixing("vanilla_ice_cream")
            .output(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_VANILLA_CAKE_FROM_DOUGH = add(shaped("vanilla_cake_from_dough")
            .output(NeapolitanItems.VANILLA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', NeapolitanItems.DRIED_VANILLA_PODS.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_VANILLA_CAKE = add(compacting("vanilla_cake")
            .output(NeapolitanItems.VANILLA_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_VANILLA_MILKSHAKE = add(mixing("vanilla_milkshake")
            .output(NeapolitanFluidEntries.VANILLA_MILKSHAKE.get(), 750)
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_BANANA_BREAD_FROM_DOUGH = add(shapeless("banana_bread_from_dough")
            .output(NeapolitanItems.BANANA_BREAD.get())
            .require(ForgeTags.DOUGH_WHEAT)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(Items.SUGAR)),
        MIXING_BANANA_ICE_CREAM = add(mixing("banana_ice_cream")
            .output(NeapolitanFluidEntries.BANANA_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_BANANA_CAKE_FROM_DOUGH = add(shaped("banana_cake_from_dough")
            .output(NeapolitanItems.BANANA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', NeapolitanItemTags.FRUITS_BANANA)
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_BANANA_CAKE = add(compacting("banana_cake")
            .output(NeapolitanItems.BANANA_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_BANANA_MILKSHAKE = add(mixing("banana_milkshake")
            .output(NeapolitanFluidEntries.BANANA_MILKSHAKE.get(), 750)
            .require(NeapolitanItemTags.FRUITS_BANANA)
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_BANANA_MILKSHAKE_FROM_ICE_CREAM = add(mixing("banana_milkshake_from_ice_cream")
            .output(NeapolitanFluidEntries.BANANA_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanFluidEntries.BANANA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_MINT_ICE_CREAM = add(mixing("mint_ice_cream")
            .output(NeapolitanFluidEntries.MINT_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_MINT_CAKE_FROM_DOUGH = add(shaped("mint_cake_from_dough")
            .output(NeapolitanItems.MINT_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', NeapolitanItems.MINT_LEAVES.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_MINT_CAKE = add(compacting("mint_cake")
            .output(NeapolitanItems.MINT_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_MINT_MILKSHAKE = add(mixing("mint_milkshake")
            .output(NeapolitanFluidEntries.MINT_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_MINT_MILKSHAKE_FROM_ICE_CREAM = add(mixing("mint_milkshake_from_ice_cream")
            .output(NeapolitanFluidEntries.MINT_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanFluidEntries.MINT_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        HAUNTING_MAGIC_BEANS = add(haunting("magic_beans")
            .output(NeapolitanItems.MAGIC_BEANS.get())
            .require(NeapolitanItems.ADZUKI_BEANS.get())),
        MIXING_ADZUKI_BUN = add(mixing("adzuki_bun")
            .output(NeapolitanItems.ADZUKI_BUN.get())
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_ADZUKI_BUN_FROM_DOUGH = add(shapeless("adzuki_bun_from_dough")
            .output(NeapolitanItems.ADZUKI_BUN.get())
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(ForgeTags.DOUGH_WHEAT)),
        MIXING_ADZUKI_ICE_CREAM = add(mixing("adzuki_ice_cream")
            .output(NeapolitanFluidEntries.ADZUKI_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_ADZUKI_CAKE_FROM_DOUGH = add(shaped("adzuki_cake_from_dough")
            .output(NeapolitanItems.ADZUKI_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_ADZUKI_CAKE = add(compacting("adzuki_cake")
            .output(NeapolitanItems.ADZUKI_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_ADZUKI_MILKSHAKE = add(mixing("adzuki_milkshake")
            .output(NeapolitanFluidEntries.ADZUKI_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_ADZUKI_MILKSHAKE_FROM_ICE_CREAM = add(mixing("adzuki_milkshake_from_ice_cream")
            .output(NeapolitanFluidEntries.ADZUKI_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(NeapolitanFluidEntries.ADZUKI_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        FILLING_CHOCOLATE_STRAWBERRIES = add(filling("chocolate_strawberries")
            .output(NeapolitanItems.CHOCOLATE_STRAWBERRIES.get())
            .require(NeapolitanItemTags.FRUITS_STRAWBERRY)
            .require(AllFluids.CHOCOLATE.get(), 250)),
        FILLING_VANILLA_CHOCOLATE_FINGERS = add(filling("vanilla_chocolate_fingers")
            .output(NeapolitanItems.VANILLA_CHOCOLATE_FINGERS.get())
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(AllFluids.CHOCOLATE.get(), 250)),
        FILLING_MINT_CHOCOLATE = add(filling("mint_chocolate")
            .output(NeapolitanItems.MINT_CHOCOLATE.get())
            .require(NeapolitanItems.MINT_LEAVES.get())
            .require(AllFluids.CHOCOLATE.get(), 250)),
        SEQUENCED_ASSEMBLY_NEAPOLITAN_ICE_CREAM_START_WITH_CHOCOLATE = add(sequencedAssembly("neapolitan_ice_cream_start_with_chocolate")
            .require(NeapolitanItems.VANILLA_ICE_CREAM.get())
            .transitionTo(NeapolitanItemEntries.INCOMPLETE_NEAPOLITAN_ICE_CREAM_CHOCOLATE.get())
            .addOutput(NeapolitanItems.NEAPOLITAN_ICE_CREAM.get(), 1)
            .loops(1)
            .addStep(FillingRecipe::new, builder -> builder.require(AllFluids.CHOCOLATE.get(), 250))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(NeapolitanItemTags.FRUITS_STRAWBERRY))),
        SEQUENCED_ASSEMBLY_NEAPOLITAN_ICE_CREAM_START_WITH_STRAWBERRY = add(sequencedAssembly("neapolitan_ice_cream_start_with_strawberry")
            .require(NeapolitanItems.VANILLA_ICE_CREAM.get())
            .transitionTo(NeapolitanItemEntries.INCOMPLETE_NEAPOLITAN_ICE_CREAM_STRAWBERRY.get())
            .addOutput(NeapolitanItems.NEAPOLITAN_ICE_CREAM.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(NeapolitanItemTags.FRUITS_STRAWBERRY))
            .addStep(FillingRecipe::new, builder -> builder.require(AllFluids.CHOCOLATE.get(), 250)));
    
    public NeapolitanRecipes(DataGenerator datagen) {
        super(Mods.NEAPOLITAN, CentralKitchen.REGISTRATE, datagen);
    }
    
}
