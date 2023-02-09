package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.data.recipe.ConditionedRecipes;
import plus.dragons.createcentralkitchen.modules.farmersdelight.FarmersDelightModule;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.CookingGuideItem;
import plus.dragons.createdragonlib.init.FillCreateItemGroupEvent;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FdItems {
    
    private static final CreateRegistrate REGISTRATE = CentralKitchen.REGISTRATE
        .startSection(AllSections.KINETICS)
        .creativeModeTab(() -> Create.BASE_CREATIVE_TAB);

    public static final ItemEntry<CookingGuideItem> COOKING_GUIDE = REGISTRATE.item("cooking_guide", CookingGuideItem::new)
        .properties(prop -> prop.stacksTo(1))
        .recipe((ctx, prov) -> ConditionedRecipes.shapeless(ctx.getEntry())
            .requires(FdTags.item(new ResourceLocation("forge", "plates/obsidian")))
            .requires(ModItems.CANVAS.get())
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
