package plus.dragons.createcentralkitchen.modules.farmersrespite.entry;

import com.farmersrespite.core.tag.FRTags;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.item.FillCreateItemGroupEvent;
import plus.dragons.createcentralkitchen.data.recipe.RecipeGen;
import plus.dragons.createcentralkitchen.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.modules.farmersrespite.FarmersRespiteModule;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FarmersRespiteModuleItems {
    
    private static final CreateRegistrate REGISTRATE = CentralKitchen.REGISTRATE
        .startSection(AllSections.KINETICS)
        .creativeModeTab(() -> Create.BASE_CREATIVE_TAB);

    public static final ItemEntry<BrewingGuideItem> BREWING_GUIDE = REGISTRATE.item("brewing_guide", BrewingGuideItem::new)
        .properties(prop -> prop.stacksTo(1))
        .recipe((ctx, prov) -> RecipeGen.shapeless(ctx.getId())
            .output(ctx.get())
            .require(ForgeItemTags.create("plates/obsidian"))
            .require(ModItems.CANVAS.get())
            .require(FRTags.TEA_LEAVES)
            .whenModLoaded(FarmersRespiteModule.ID)
            .save(prov)
        )
        .register();

    public static void fillCreateItemGroup(FillCreateItemGroupEvent event) {
        if (event.getItemGroup() == Create.BASE_CREATIVE_TAB) {
            event.addInsertion(AllBlocks.BLAZE_BURNER.get(), BREWING_GUIDE.asStack());
        }
    }

    public static void register() {}
    
}
