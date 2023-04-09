package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.farmersrespite.core.registry.FRItems;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.NeapolitanFluidEntries;
import plus.dragons.createcentralkitchen.entry.fluid.RespitefulFluidEntries;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import plus.dragons.respiteful.entries.RespitefulBlocks;
import plus.dragons.respiteful.entries.RespitefulItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class RespitefulRecipes extends DatapackRecipes {
    private final GeneratedRecipe
        MIXING_GREEN_TEA_ICE_CREAM = add(mixing("green_tea_ice_cream")
            .output(RespitefulFluidEntries.GREEN_TEA_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_GREEN_TEA_CAKE_FROM_DOUGH = add(shaped("green_tea_cake_from_dough")
            .output(RespitefulBlocks.GREEN_TEA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', FRItems.GREEN_TEA_LEAVES.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_GREEN_TEA_CAKE = add(compacting("green_tea_cake")
            .output(RespitefulBlocks.GREEN_TEA_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(FRItems.GREEN_TEA_LEAVES.get())
            .require(Tags.Fluids.MILK, 1000)),
        FILLING_SNOW_TOP_GREEN_TEA = add(filling("snow_top_green_tea")
            .output(RespitefulItems.SNOW_TOP_GREEN_TEA.get())
            .require(FRItems.GREEN_TEA.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 250)),
        MIXING_YELLOW_TEA_ICE_CREAM = add(mixing("yellow_tea_ice_cream")
            .output(RespitefulFluidEntries.YELLOW_TEA_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(FRItems.YELLOW_TEA_LEAVES.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_YELLOW_TEA_CAKE_FROM_DOUGH = add(shaped("yellow_tea_cake_from_dough")
            .output(RespitefulBlocks.YELLOW_TEA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', FRItems.YELLOW_TEA_LEAVES.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_YELLOW_TEA_CAKE = add(compacting("yellow_tea_cake")
            .output(RespitefulBlocks.YELLOW_TEA_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(FRItems.YELLOW_TEA_LEAVES.get())
            .require(FRItems.YELLOW_TEA_LEAVES.get())
            .require(Tags.Fluids.MILK, 1000)),
        FILLING_SNOW_TOP_YELLOW_TEA = add(filling("snow_top_yellow_tea")
            .output(RespitefulItems.SNOW_TOP_YELLOW_TEA.get())
            .require(FRItems.YELLOW_TEA.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 250)),
        MIXING_BLACK_TEA_ICE_CREAM = add(mixing("black_tea_ice_cream")
            .output(RespitefulFluidEntries.BLACK_TEA_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(FRItems.BLACK_TEA_LEAVES.get())
            .require(Tags.Fluids.MILK, 250)),
        CRAFTING_BLACK_TEA_CAKE_FROM_DOUGH = add(shaped("black_tea_cake_from_dough")
            .output(RespitefulBlocks.BLACK_TEA_CAKE.get())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeTags.DOUGH_WHEAT)
            .define('#', FRItems.BLACK_TEA_LEAVES.get())
            .pattern("mmm")
            .pattern("ses")
            .pattern("#w#")),
        COMPACTING_BLACK_TEA_CAKE = add(compacting("black_tea_cake")
            .output(RespitefulBlocks.BLACK_TEA_CAKE.get())
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(FRItems.BLACK_TEA_LEAVES.get())
            .require(FRItems.BLACK_TEA_LEAVES.get())
            .require(Tags.Fluids.MILK, 1000)),
        FILLING_SNOW_TOP_BLACK_TEA = add(filling("snow_top_black_tea")
            .output(RespitefulItems.SNOW_TOP_BLACK_TEA.get())
            .require(FRItems.BLACK_TEA.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 250)),
        MIXING_COFFEE_ICE_CREAM = add(mixing("coffee_ice_cream")
            .output(RespitefulFluidEntries.COFFEE_ICE_CREAM.get(), 500)
            .require(NeapolitanItems.ICE_CUBES.get())
            .require(Items.SUGAR)
            .require(FRItems.COFFEE_BEANS.get())
            .require(Tags.Fluids.MILK, 250)),
        FILLING_SNOW_TOP_COFFEE = add(filling("snow_top_coffee")
            .output(RespitefulItems.SNOW_TOP_COFFEE.get())
            .require(FRItems.COFFEE.get())
            .require(NeapolitanFluidEntries.VANILLA_ICE_CREAM.get(), 250));
    
    public RespitefulRecipes(DataGenerator datagen) {
        super(Mods.RESPITEFUL, CentralKitchen.REGISTRATE, datagen);
    }
    
}
