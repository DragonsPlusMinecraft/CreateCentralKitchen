package plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideMenu;

public class BrewingGuideMenu extends BlazeStoveGuideMenu<BrewingGuide> {

    public BrewingGuideMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    public BrewingGuideMenu(MenuType<?> type, int id, Inventory inv, ItemStack cookingGuide) {
        super(type, id, inv, cookingGuide);
    }
    
    public BrewingGuideMenu(MenuType<?> type, int id, Inventory inv, BlazeStoveBlockEntity blazeStove) {
        super(type, id, inv, blazeStove);
    }
    
    @Override
    public BrewingGuide createGuide(ItemStack contentHolder) {
        return BrewingGuide.of(contentHolder);
    }
    
    //TODO: Adjust slots' position when texture available
    @Override
    protected void addSlots() {
        addPlayerSlots(52, 96);
        this.addSlot(new CookingIngredientSlot(0, 81, 27));
        this.addSlot(new CookingIngredientSlot(1, 81, 45));
        this.addSlot(new DisplaySlot(2, 184, 36));
    }
    
}
