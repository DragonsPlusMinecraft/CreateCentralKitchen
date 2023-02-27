package plus.dragons.createcentralkitchen.data.recipe;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import plus.dragons.createcentralkitchen.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FarmersDelightModuleFluids;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

@MethodsReturnNonnullByDefault
public class FarmersDelightRecipes extends RecipeGen {
    private final GeneratedRecipe
        MIXING_APPLE_CIDER = modded(mixing("apple_cider")
            .output(FarmersDelightModuleFluids.APPLE_CIDER.get(), 250)
            .require(Items.APPLE)
            .require(Items.APPLE)
            .require(Items.SUGAR)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_HOT_COCOA = modded(mixing("hot_cocoa")
            .output(FarmersDelightModuleFluids.HOT_COCOA.get(), 250)
            .require(AllTags.forgeFluidTag("milk"), 125)
            .require(AllTags.forgeFluidTag("chocolate"), 125)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_MELON_JUICE = modded(mixing("melon_juice")
            .output(FarmersDelightModuleFluids.MELON_JUICE.get(), 250)
            .require(Items.MELON_SLICE)
            .require(Items.MELON_SLICE)
            .require(Items.MELON_SLICE)
            .require(Items.MELON_SLICE)
            .require(Items.SUGAR)),
        MIXING_TOMATO_SAUCE = modded(mixing("tomato_sauce")
            .output(FarmersDelightModuleFluids.TOMATO_SAUCE.get(), 250)
            .require(ForgeTags.VEGETABLES_TOMATO)
            .require(ForgeTags.VEGETABLES_TOMATO)
            .requiresHeat(HeatCondition.HEATED)),
        COMPACTING_SWEET_BERRY_COOKIE = modded(compacting("sweet_berry_cookie")
            .output(ModItems.SWEET_BERRY_COOKIE.get(), 8)
            .require(Items.SWEET_BERRIES)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        COMPACTING_HONEY_COOKIE = modded(compacting("honey_cookie")
            .output(ModItems.HONEY_COOKIE.get(), 8)
            .require(AllTags.AllFluidTags.HONEY.tag, 250)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        END = null;
    
    public FarmersDelightRecipes(DataGenerator datagen) {
        super("farmersdelight", datagen);
    }
    
    @Override
    public String getName() {
        return "Farmer's Delight Recipes";
    }
    
}
