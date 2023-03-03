package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide.CookingGuideMenu;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide.CookingGuideScreen;

public class FarmersDelightModuleMenuTypes {

    public static final MenuEntry<CookingGuideMenu> COOKING_GUIDE =
            register("cooking_guide", CookingGuideMenu::new, () -> CookingGuideScreen::new);

    private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> register(
            String name, MenuBuilder.ForgeMenuFactory<C> factory, NonNullSupplier<MenuBuilder.ScreenFactory<C, S>> screenFactory) {
        return CentralKitchen.REGISTRATE
                .menu(name, factory, screenFactory)
                .register();
    }

    public static void register() {}
    
}
