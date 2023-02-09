package plus.dragons.createcentralkitchen.modules.farmersrespite.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.data.recipe.ConditionedRecipes;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdTags;
import plus.dragons.createcentralkitchen.modules.farmersrespite.FarmersRespiteModule;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideItem;
import plus.dragons.createdragonlib.init.FillCreateItemGroupEvent;
import umpaz.farmersrespite.common.tag.FRTags;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FrItems {
    
    private static final CreateRegistrate REGISTRATE = CentralKitchen.REGISTRATE
        .startSection(AllSections.KINETICS)
        .creativeModeTab(() -> Create.BASE_CREATIVE_TAB);

    public static final ItemEntry<BrewingGuideItem> BREWING_GUIDE = REGISTRATE.item("brewing_guide", BrewingGuideItem::new)
        .properties(prop -> prop.stacksTo(1))
        .recipe((ctx, prov) -> ConditionedRecipes.shapeless(ctx.getEntry())
            .requires(FdTags.item(new ResourceLocation("forge", "plates/copper")))
            .requires(ModItems.CANVAS.get())
            .requires(FRTags.TEA_LEAVES)
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
