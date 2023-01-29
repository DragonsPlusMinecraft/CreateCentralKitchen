package plus.dragons.createfarmersautomation.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import plus.dragons.createfarmersautomation.FarmersAutomation;
import plus.dragons.createfarmersautomation.api.FillCreateItemGroupEvent;
import plus.dragons.createfarmersautomation.content.contraptions.components.stove.CookingGuideItem;

public class CfaItems {
    private static final CreateRegistrate REGISTRATE = FarmersAutomation.REGISTRATE.startSection(AllSections.KINETICS);

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
