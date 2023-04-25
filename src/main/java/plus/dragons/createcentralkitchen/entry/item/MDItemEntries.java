package plus.dragons.createcentralkitchen.entry.item;

import com.sammy.minersdelight.setup.MDItems;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.tterrag.registrate.util.entry.ItemEntry;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.minersCooking.MinersCookingGuideItem;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.DatapackRecipes;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.registry.ModItems;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.MD)
public class MDItemEntries {
    static {
        REGISTRATE.startSection(AllSections.KINETICS).creativeModeTab(() -> Create.BASE_CREATIVE_TAB);
    }

    public static final ItemEntry<MinersCookingGuideItem> MINERS_COOKING_GUIDE = REGISTRATE.item("miners_cooking_guide", MinersCookingGuideItem::new)
        .lang("Miner's Cooking Guide")
        .properties(prop -> prop.stacksTo(1))
        .transform(DatapackRecipes.addRecipe(Mods.MD, (ctx, prov) -> prov.add(Recipes.shapeless(ctx.getId())
            .output(ctx.get())
            .require(ForgeItemTags.create("plates/obsidian"))
            .require(ModItems.CANVAS.get())
            .require(MDItems.CAVE_CARROT.get()))))
        .register();

}
