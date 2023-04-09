package plus.dragons.createcentralkitchen.entry.menu;

import com.tterrag.registrate.util.entry.MenuEntry;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing.BrewingGuideMenu;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing.BrewingGuideScreen;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.FR)
public class FRMenuEntries {
    
    public static final MenuEntry<BrewingGuideMenu> BREWING_GUIDE = REGISTRATE.menu("brewing_guide",
        BrewingGuideMenu::new, () -> BrewingGuideScreen::new).register();
    
}
