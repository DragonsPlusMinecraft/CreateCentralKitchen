package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.cosmicgelatin.seasonals.core.registry.SeasonalsBlocks;
import com.cosmicgelatin.seasonals.core.registry.SeasonalsItems;
import com.simibubi.create.AllFluids;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.NeapolitanFluidEntries;
import plus.dragons.createcentralkitchen.entry.fluid.SeasonalsFluidEntries;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class SeasonalsRecipes extends DatapackRecipes {
    private final GeneratedRecipe
        MIXING_PUMPKIN_ICE_CREAM = add(mixing("pumpkin_ice_cream")
            .output(SeasonalsFluidEntries.PUMPKIN_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(SeasonalsItems.ROASTED_PUMPKIN.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_PUMPKIN_CAKE_FROM_DOUGH = add(shaped("pumpkin_cake_from_dough")
            .output(SeasonalsBlocks.PUMPKIN_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('#', SeasonalsItems.ROASTED_PUMPKIN.get())
            .define('w', ForgeTags.DOUGH_WHEAT)
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_PUMPKIN_CAKE = add(compacting("pumpkin_cake")
            .output(SeasonalsBlocks.PUMPKIN_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(SeasonalsItems.ROASTED_PUMPKIN.get())
            .require(SeasonalsItems.ROASTED_PUMPKIN.get())
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_CHOCOLATE_PUMPKIN_MUFFIN = add(mixing("chocolate_pumpkin_muffin")
            .output(SeasonalsItems.CHOCOLATE_PUMPKIN_MUFFIN.get(), 4)
            .require(SeasonalsItems.ROASTED_PUMPKIN.get())
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AllFluids.CHOCOLATE.get(), 250)),
        CRAFTING_CHOCOLATE_PUMPKIN_MUFFIN_FROM_DOUGH = add(shapeless("chocolate_pumpkin_muffin_from_dough")
            .output(SeasonalsItems.CHOCOLATE_PUMPKIN_MUFFIN.get(), 4)
            .require(SeasonalsItems.ROASTED_PUMPKIN.get())
            .require(Items.SUGAR)
            .require(ForgeItemTags.BARS__CHOCOLATE.tag)
            .require(ForgeTags.DOUGH_WHEAT)),
        MIXING_PUMPKIN_MILKSHAKE = add(mixing("pumpkin_milkshake")
            .output(SeasonalsFluidEntries.PUMPKIN_MILKSHAKE.get(), 750)
            .require(SeasonalsItems.ROASTED_PUMPKIN.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_PUMPKIN_MILKSHAKE_FROM_ICE_CREAM = add(mixing("pumpkin_milkshake_from_ice_cream")
            .output(SeasonalsFluidEntries.PUMPKIN_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(SeasonalsFluidEntries.PUMPKIN_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_SWEET_BERRY_ICE_CREAM = add(mixing("sweet_berry_ice_cream")
            .output(SeasonalsFluidEntries.SWEET_BERRY_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(Items.SWEET_BERRIES)
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_SWEET_BERRY_CAKE_FROM_DOUGH = add(shaped("sweet_berry_cake_from_dough")
            .output(SeasonalsBlocks.SWEET_BERRY_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('#', Items.SWEET_BERRIES)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_SWEET_BERRY_CAKE = add(compacting("sweet_berry_cake")
            .output(SeasonalsBlocks.SWEET_BERRY_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(Items.SWEET_BERRIES)
            .require(Items.SWEET_BERRIES)
            .require(Tags.Fluids.MILK, 1000)),
        MIXING_SWEET_BERRY_MILKSHAKE = add(mixing("sweet_berry_milkshake")
            .output(SeasonalsFluidEntries.SWEET_BERRY_MILKSHAKE.get(), 750)
            .require(Items.SWEET_BERRIES)
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250)),
        MIXING_SWEET_BERRY_MILKSHAKE_FROM_ICE_CREAM = add(mixing("sweet_berry_milkshake_from_ice_cream")
            .output(SeasonalsFluidEntries.SWEET_BERRY_MILKSHAKE.get(), 750)
            .require(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .require(SeasonalsFluidEntries.SWEET_BERRY_ICE_CREAM.get(), 500)
            .require(Tags.Fluids.MILK, 250));
    
    public SeasonalsRecipes(DataGenerator datagen) {
        super(Mods.SEASONALS, CentralKitchen.REGISTRATE, datagen);
    }
    
}
