package plus.dragons.createcentralkitchen.data.recipe;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import plus.dragons.createcentralkitchen.data.tag.CentralKitchenTags;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdFluids;
import vectorwing.farmersdelight.common.registry.ModItems;

@MethodsReturnNonnullByDefault
public class FarmersDelightRecipes extends RecipeGen {
    private final GeneratedRecipe
    APPLE_CIDER_MIXING = modded(mixing("apple_cider")
        .output(FdFluids.APPLE_CIDER.get(), 250)
        .require(Items.APPLE)
        .require(Items.APPLE)
        .require(Items.SUGAR)
        .requiresHeat(HeatCondition.HEATED)),
    HOT_COCOA_MIXING = modded(mixing("hot_cocoa")
        .output(FdFluids.HOT_COCOA.get(), 250)
        .require(CentralKitchenTags.fluid(new ResourceLocation("forge", "milk")), 125)
        .require(CentralKitchenTags.fluid(new ResourceLocation("forge", "chocolate")), 125)
        .requiresHeat(HeatCondition.HEATED)),
    MELON_JUICE_MIXING = modded(mixing("melon_juice")
        .output(FdFluids.MELON_JUICE.get(), 250)
        .require(Items.MELON_SLICE)
        .require(Items.MELON_SLICE)
        .require(Items.MELON_SLICE)
        .require(Items.MELON_SLICE)
        .require(Items.SUGAR)),
    TOMATO_SAUCE_MIXING = modded(mixing("tomato_sauce")
        .output(FdFluids.TOMATO_SAUCE.get(), 250)
        .require(ModItems.TOMATO.get())
        .require(ModItems.TOMATO.get())
        .requiresHeat(HeatCondition.HEATED)),
    COOKIE_COMPACTING = common(compacting("cookie")
        .output(Items.COOKIE, 8)
        .require(Items.COCOA_BEANS)
        .require(AllTags.forgeItemTag("flour/wheat"))
        .require(AllTags.forgeItemTag("flour/wheat"))),
    SWEET_BERRY_COOKIE_COMPACTING = modded(compacting("sweet_berry_cookie")
        .output(ModItems.SWEET_BERRY_COOKIE.get(), 8)
        .require(Items.SWEET_BERRIES)
        .require(AllTags.forgeItemTag("flour/wheat"))
        .require(AllTags.forgeItemTag("flour/wheat"))),
    HONEY_COOKIE_COMPACTING = modded(compacting("honey_cookie")
        .output(ModItems.HONEY_COOKIE.get(), 8)
        .require(AllFluids.HONEY.get(), 250)
        .require(AllTags.forgeItemTag("flour/wheat"))
        .require(AllTags.forgeItemTag("flour/wheat"))),
    FINISH = null;
    
    public FarmersDelightRecipes(DataGenerator datagen) {
        super("farmersdelight", datagen);
    }
    
    @Override
    public String getName() {
        return "Farmer's Delight Recipes";
    }
    
}
