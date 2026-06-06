package plus.dragons.createcentralkitchen.entry.item;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

import com.tterrag.registrate.util.entry.ItemEntry;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.minersCooking.MinersCookingGuideItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.MD)
public class MDItemEntries {
    public static final ItemEntry<MinersCookingGuideItem> MINERS_COOKING_GUIDE = REGISTRATE.item("miners_cooking_guide", MinersCookingGuideItem::new)
            .lang("Miner's Cooking Guide")
            .properties(prop -> prop.stacksTo(1))
            .register();
}
