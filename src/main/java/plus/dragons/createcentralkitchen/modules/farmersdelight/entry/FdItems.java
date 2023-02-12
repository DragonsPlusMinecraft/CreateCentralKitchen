package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.common.item.FillCreateItemGroupEvent;
import plus.dragons.createcentralkitchen.data.recipe.RecipeGen;
import plus.dragons.createcentralkitchen.data.tag.CentralKitchenTags;
import plus.dragons.createcentralkitchen.modules.farmersdelight.FarmersDelightModule;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide.CookingGuideItem;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class FdItems {
    
    private static final CreateRegistrate REGISTRATE = CentralKitchen.REGISTRATE
        .startSection(AllSections.KINETICS)
        .creativeModeTab(() -> Create.BASE_CREATIVE_TAB);

    public static final ItemEntry<CookingGuideItem> COOKING_GUIDE = REGISTRATE.item("cooking_guide", CookingGuideItem::new)
        .properties(prop -> prop.stacksTo(1))
        .recipe((ctx, prov) -> RecipeGen.shapeless(ctx.getId())
            .output(ctx.get())
            .requires(CentralKitchenTags.item(new ResourceLocation("forge", "plates/obsidian")))
            .requires(ModItems.CANVAS.get())
            .requires(ForgeTags.VEGETABLES)
            .whenModLoaded(FarmersDelightModule.ID)
            .save(prov)
        )
        .register();

    public static void fillCreateItemGroup(FillCreateItemGroupEvent event) {
        if (event.getItemGroup() == Create.BASE_CREATIVE_TAB) {
            event.addInsertion(AllBlocks.BLAZE_BURNER.get(), COOKING_GUIDE.asStack());
        }
    }

    public static void register() {}
    
}
