package plus.dragons.createcentralkitchen.modules.minersdelight.entry;

import com.sammy.minersdelight.setup.MDItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.item.FillCreateItemGroupEvent;
import plus.dragons.createcentralkitchen.data.recipe.RecipeGen;
import plus.dragons.createcentralkitchen.data.tag.CentralKitchenTags;
import plus.dragons.createcentralkitchen.modules.minersdelight.MinersDelightModule;
import plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.item.guide.MinersCookingGuideItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class MinersDelightModuleItems {
    
    private static final CreateRegistrate REGISTRATE = CentralKitchen.REGISTRATE
        .startSection(AllSections.KINETICS)
        .creativeModeTab(() -> Create.BASE_CREATIVE_TAB);

    public static final ItemEntry<MinersCookingGuideItem> MINERS_COOKING_GUIDE = REGISTRATE.item("miners_cooking_guide", MinersCookingGuideItem::new)
        .lang("Miner's Cooking Guide")
        .properties(prop -> prop.stacksTo(1))
        .recipe((ctx, prov) -> RecipeGen.shapeless(ctx.getId())
            .output(ctx.get())
            .requires(CentralKitchenTags.item(new ResourceLocation("forge", "plates/obsidian")))
            .requires(ModItems.CANVAS.get())
            .requires(MDItems.CAVE_CARROT.get())
            .whenModLoaded(MinersDelightModule.ID)
            .save(prov)
        )
        .register();

    public static void fillCreateItemGroup(FillCreateItemGroupEvent event) {
        if (event.getItemGroup() == Create.BASE_CREATIVE_TAB) {
            event.addInsertion(AllBlocks.BLAZE_BURNER.get(), MINERS_COOKING_GUIDE.asStack());
        }
    }

    public static void register() {}
    
}
