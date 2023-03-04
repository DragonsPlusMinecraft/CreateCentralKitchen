package plus.dragons.createcentralkitchen.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.NBTIngredient;
import plus.dragons.createcentralkitchen.data.tag.ForgeItemTags;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class VanillaRecipes extends RecipeGen {
    private final GeneratedRecipe
        CRAFTING_DOUGH_FROM_WATER_BUCKET = common(shapeless(Create.asResource("appliances/dough"))
            .output(AllItems.DOUGH.get(), 4)
            .require(Items.WATER_BUCKET)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        CRAFTING_DOUGH_FROM_WATER_BOTTLE = common(shapeless(Create.asResource("appliances/dough_from_water_bottle"))
            .output(AllItems.DOUGH.get())
            .require(NBTIngredient.of(PotionUtils.setPotion(Items.POTION.getDefaultInstance(), Potions.WATER)))
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        MIXING_DOUGH = common(mixing(Create.asResource("dough_by_mixing"))
            .output(AllItems.DOUGH.get())
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(Fluids.WATER, 250)),
        COMPACTING_COOKIE_FROM_WHEAT_FLOUR = common(compacting("cookie_from_wheat_flour")
            .output(Items.COOKIE, 8)
            .require(Items.COCOA_BEANS)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        COMPACTING_COOKIE_FROM_WHEAT_DOUGH = common(compacting("cookie_from_wheat_dough")
            .output(Items.COOKIE, 8)
            .require(Items.COCOA_BEANS)
            .require(ForgeTags.DOUGH_WHEAT)),
        CRAFTING_CAKE_FROM_WHEAT_FLOUR = common(shaped("cake_from_wheat_flour")
            .output(Items.CAKE)
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('e', ForgeTags.EGGS)
            .define('w', ForgeItemTags.FLOUR__WHEAT.tag)
            .pattern("mmm")
            .pattern("ses")
            .pattern("www")),
        MIXING_CAKE = common(mixing("cake")
            .output(Items.CAKE)
            .require(Tags.Items.EGGS)
            .require(Items.SUGAR)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(Tags.Fluids.MILK, 500)),
        FINISH = null;
    
    public VanillaRecipes(DataGenerator datagen) {
        super("minecraft", datagen);
    }
    
}
