package plus.dragons.createcentralkitchen.entry.item;

import com.tterrag.registrate.util.entry.ItemEntry;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing.BrewingGuideItem;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

//@ModLoadSubscriber(modid = Mods.FR)
public class FRItemEntries {
    // TODO Need change. Kettle changed a lot.
    
    public static final ItemEntry<BrewingGuideItem> BREWING_GUIDE = REGISTRATE.item("brewing_guide", BrewingGuideItem::new)
        .properties(prop -> prop.stacksTo(1))
        .register();
    
}
