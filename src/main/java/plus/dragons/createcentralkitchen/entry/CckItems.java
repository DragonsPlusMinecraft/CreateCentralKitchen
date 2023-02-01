package plus.dragons.createcentralkitchen.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.api.FillCreateItemGroupEvent;
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.CookingGuideItem;

public class CckItems {
    private static final CreateRegistrate REGISTRATE = CentralKitchen.REGISTRATE.startSection(AllSections.KINETICS);

    public static final ItemEntry<CookingGuideItem> COOKING_GUIDE = REGISTRATE.item("cooking_guide", CookingGuideItem::new)
            .properties(prop -> prop.stacksTo(16))
            .register();

    public static void fillCreateItemGroup(FillCreateItemGroupEvent event) {
        if (event.getItemGroup() == Create.BASE_CREATIVE_TAB) {
            event.addInsertion(AllBlocks.BLAZE_BURNER.get(), COOKING_GUIDE.asStack());
        }
    }

    public static void register() {}
}
