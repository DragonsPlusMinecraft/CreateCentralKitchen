package plus.dragons.createcentralkitchen.entry.item;

import com.farmersrespite.core.tag.FRTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.tterrag.registrate.util.entry.ItemEntry;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing.BrewingGuideItem;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.DatapackRecipes;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.registry.ModItems;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.FR)
public class FRItemEntries {
    static {
        REGISTRATE.startSection(AllSections.KINETICS).creativeModeTab(() -> Create.BASE_CREATIVE_TAB);
    }
    
    public static final ItemEntry<BrewingGuideItem> BREWING_GUIDE = REGISTRATE.item("brewing_guide", BrewingGuideItem::new)
        .properties(prop -> prop.stacksTo(1))
        .transform(DatapackRecipes.addRecipe(Mods.FR, (ctx, prov) -> prov.add(Recipes.shapeless(ctx.getId())
            .output(ctx.get())
            .require(ForgeItemTags.create("plates/obsidian"))
            .require(ModItems.CANVAS.get())
            .require(FRTags.TEA_LEAVES))))
        .register();
    
}
