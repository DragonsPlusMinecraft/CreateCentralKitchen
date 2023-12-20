package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import cn.foggyhillside.ends_delight.registry.ItemRegistry;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.CckFluidEntries;
import plus.dragons.createcentralkitchen.entry.fluid.EDFluidEntries;
import plus.dragons.createcentralkitchen.entry.item.EDItemEntries;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.registry.ModItems;

public class EDRecipes extends DatapackRecipes {
    private final GeneratedRecipe
        MIXING_DRAGONS_BREATH_SODA = add(mixing("dragon_breath_soda")
            .output(EDFluidEntries.DRAGONS_BREATH_SODA.get(),250)
            .require(CckFluidEntries.DRAGONS_BREATH.get(),250)
            .require(Items.SUGAR)),
        MIXING_CHORUS_FLOWER_TEA = add(mixing("chorus_flower_tea")
            .output(EDFluidEntries.CHORUS_FLOWER_TEA.get(),250)
            .require(Items.GHAST_TEAR)
            .require(Fluids.WATER,250)
            .require(ItemRegistry.DriedChorusFlower.get())
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_CHORUS_FRUIT_WINE = add(mixing("chorus_fruit_wine")
            .output(EDFluidEntries.CHORUS_FRUIT_WINE.get(),250)
            .require(Fluids.WATER,250)
            .require(Items.SUGAR)
            .require(Items.CHORUS_FRUIT)
            .require(Items.CHORUS_FRUIT)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_CHORUS_FRUIT_MILK_TEA = add(mixing("chorus_fruit_milk_tea")
            .output(EDFluidEntries.CHORUS_FRUIT_MILK_TEA.get(),250)
            .require(Tags.Fluids.MILK,250)
            .require(Ingredient.of(Items.CHORUS_FRUIT,ItemRegistry.ChorusFruitGrain.get()))
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_CHORUS_FRUIT_BUBBLE_TEA = add(mixing("chorus_fruit_bubble_tea")
            .output(EDFluidEntries.CHORUS_FRUIT_BUBBLE_TEA.get(),250)
            .require(Tags.Fluids.MILK,250)
            .require(Ingredient.of(Items.CHORUS_FRUIT,ItemRegistry.ChorusFruitGrain.get()))
            .require(ItemRegistry.EnderPearlGrain.get())
            .requiresHeat(HeatCondition.HEATED)),

        UPGRADING_CHORUS_FRUIT_BUBBLE_TEA = add(mixing("upgrade_to_chorus_fruit_bubble_tea")
            .output(EDFluidEntries.CHORUS_FRUIT_BUBBLE_TEA.get(),250)
            .require(EDFluidEntries.CHORUS_FRUIT_MILK_TEA.get(),250)
            .require(ItemRegistry.EnderPearlGrain.get())),
        COMPACTING_CHORUS_COOKIE = add(compacting("chorus_cookie")
            .output(ItemRegistry.ChorusCookie.get(), 8)
            .require(Items.CHORUS_FRUIT)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),

        SEQUENCED_ASSEMBLY_CHORUS_FRUIT_PIE = add(sequencedAssembly("chorus_fruit_pie")
            .require(ModItems.PIE_CRUST.get())
            .transitionTo(EDItemEntries.INCOMPLETE_CHORUS_FRUIT_PIE.get())
            .addOutput(ItemRegistry.ChorusFruitPie.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.WHEAT))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.WHEAT))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.WHEAT))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.POPPY))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Ingredient.of(Items.CHORUS_FRUIT,ItemRegistry.ChorusFruitGrain.get())))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.CHORUS_FLOWER))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SUGAR))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SUGAR))),

        SEQUENCED_ASSEMBLY_CHORUS_FLOWER_PIE = add(sequencedAssembly("chorus_flower_pie")
            .require(ModItems.WHEAT_DOUGH.get())
            .transitionTo(EDItemEntries.INCOMPLETE_CHORUS_FLOWER_PIE.get())
            .addOutput(ItemRegistry.ChorusFlowerPie.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ItemRegistry.DriedChorusFlower.get()))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ItemRegistry.DriedChorusFlower.get()))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.POPPY))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SUGAR)));

    public EDRecipes(DataGenerator datagen) {
        super(Mods.ED, CentralKitchen.REGISTRATE, datagen);
    }
    
}
