package plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideMenu;

public class CookingGuideMenu extends BlazeStoveGuideMenu<CookingGuide> {

    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, ItemStack cookingGuide) {
        super(type, id, inv, cookingGuide);
    }
    
    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, BlazeStoveBlockEntity blazeStove) {
        super(type, id, inv, blazeStove);
    }
    
    @Override
    public CookingGuide createGuide(ItemStack contentHolder) {
        return CookingGuide.of(contentHolder);
    }
    
    @Override
    protected void addSlots() {
        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 3; ++column) {
                this.addSlot(new CookingIngredientSlot(row * 3 + column, 61 + column * 18, 31 + row * 18));
            }
        }
        this.addSlot(new DisplaySlot(6, 183, 41));
        addPlayerSlots(52, 102);
    }
    
}
