package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.cosmicgelatin.peculiars.core.PeculiarsConfig;
import com.cosmicgelatin.peculiars.core.registry.PeculiarsItems;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.teamabnormals.atmospheric.core.registry.AtmosphericItems;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.NeapolitanFluidEntries;
import plus.dragons.createcentralkitchen.entry.fluid.PeculiarsFluidEntries;
import plus.dragons.createcentralkitchen.foundation.data.recipe.builder.ConditionedRecipeBuilder;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.Map;

public class PeculiarsRecipes extends DatapackRecipes {
    private final GeneratedRecipe
        FILLING_YUCCA_FUDGE = atmospheric(filling("yucca_fudge")
            .output(PeculiarsItems.YUCCA_FUDGE.get())
            .require(AtmosphericItems.YUCCA_FRUIT.get())
            .require(AllFluids.CHOCOLATE.get(), 250)),
        MIXING_YUCCA_ICE_CREAM = atmospheric(mixing("yucca_ice_cream")
            .output(PeculiarsFluidEntries.YUCCA_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(AtmosphericItems.YUCCA_FRUIT.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_YUCCA_CAKE_FROM_DOUGH = atmospheric(shaped("yucca_cake_from_dough")
            .output(PeculiarsItems.YUCCA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', AtmosphericItems.YUCCA_FRUIT.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_YUCCA_CAKE = atmospheric(compacting("yucca_cake")
            .output(PeculiarsItems.YUCCA_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AtmosphericItems.YUCCA_FRUIT.get())
            .require(AtmosphericItems.YUCCA_FRUIT.get())
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_YUCCA_MILKSHAKE = atmospheric(mixing("yucca_milkshake")
            .output(PeculiarsFluidEntries.YUCCA_MILKSHAKE.get(), 750)
            .require(AtmosphericItems.YUCCA_FRUIT.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_YUCCA_MILKSHAKE_FROM_ICE_CREAM = atmospheric(mixing("yucca_milkshake_from_ice_cream")
            .output(PeculiarsFluidEntries.YUCCA_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(PeculiarsFluidEntries.YUCCA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_ALOE_ICE_CREAM = atmospheric(mixing("aloe_ice_cream")
            .output(PeculiarsFluidEntries.ALOE_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(AtmosphericItems.ALOE_LEAVES.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_ALOE_CAKE_FROM_DOUGH = atmospheric(shaped("aloe_cake_from_dough")
            .output(PeculiarsItems.ALOE_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', AtmosphericItems.ALOE_LEAVES.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_ALOE_CAKE = atmospheric(compacting("aloe_cake")
            .output(PeculiarsItems.ALOE_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AtmosphericItems.ALOE_LEAVES.get())
            .require(AtmosphericItems.ALOE_LEAVES.get())
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_ALOE_MILKSHAKE = atmospheric(mixing("aloe_milkshake")
            .output(PeculiarsFluidEntries.ALOE_MILKSHAKE.get(), 750)
            .require(AtmosphericItems.ALOE_LEAVES.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_ALOE_MILKSHAKE_FROM_ICE_CREAM = atmospheric(mixing("aloe_milkshake_from_ice_cream")
            .output(PeculiarsFluidEntries.ALOE_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(PeculiarsFluidEntries.ALOE_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_PASSIONFRUIT_ICE_CREAM = atmospheric(mixing("passionfruit_ice_cream")
            .output(PeculiarsFluidEntries.PASSIONFRUIT_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_PASSIONFRUIT_CAKE_FROM_DOUGH = atmospheric(shaped("passionfruit_cake_from_dough")
            .output(PeculiarsItems.PASSIONFRUIT_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', AtmosphericItems.PASSION_FRUIT.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_PASSIONFRUIT_CAKE = atmospheric(compacting("passionfruit_cake")
            .output(PeculiarsItems.PASSIONFRUIT_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_PASSIONFRUIT_MILKSHAKE = atmospheric(mixing("passionfruit_milkshake")
            .output(PeculiarsFluidEntries.PASSIONFRUIT_MILKSHAKE.get(), 750)
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_PASSIONFRUIT_MILKSHAKE_FROM_ICE_CREAM = atmospheric(mixing("passionfruit_milkshake_from_ice_cream")
            .output(PeculiarsFluidEntries.PASSIONFRUIT_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(PeculiarsFluidEntries.PASSIONFRUIT_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250));
    
    public PeculiarsRecipes(DataGenerator datagen) {
        super(Mods.PECULIARS, CentralKitchen.REGISTRATE, datagen);
    }
    
    protected GeneratedRecipe atmospheric(ConditionedRecipeBuilder<?> builder) {
        GeneratedRecipe generated = consumer -> builder
            .whenModLoaded(Mods.ATMOSPHERIC)
            .withCondition(new ConfigValueCondition(Mods.peculiars("config"),
                PeculiarsConfig.COMMON.enableAtmosphericFood,
                "enable_atmospheric_food", Map.of(), false))
            .save(consumer);
        recipes.add(generated);
        return generated;
    }
    
    protected GeneratedRecipe atmospheric(ProcessingRecipeBuilder<?> builder) {
        GeneratedRecipe generated = consumer -> builder
            .whenModLoaded(Mods.ATMOSPHERIC)
            .withCondition(new ConfigValueCondition(Mods.peculiars("config"),
                PeculiarsConfig.COMMON.enableAtmosphericFood,
                "enable_atmospheric_food", Map.of(), false))
            .build(consumer);
        recipes.add(generated);
        return generated;
    }
    
}
