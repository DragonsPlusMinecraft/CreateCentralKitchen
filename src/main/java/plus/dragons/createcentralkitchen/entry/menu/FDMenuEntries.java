package plus.dragons.createcentralkitchen.entry.menu;

import com.tterrag.registrate.util.entry.MenuEntry;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuideMenu;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuideScreen;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;


@ModLoadSubscriber(modid = Mods.FD)
public class FDMenuEntries {
    
    public static final MenuEntry<CookingGuideMenu> COOKING_GUIDE = REGISTRATE.menu("cooking_guide",
        CookingGuideMenu::new, () -> CookingGuideScreen::new).register();
    
}
