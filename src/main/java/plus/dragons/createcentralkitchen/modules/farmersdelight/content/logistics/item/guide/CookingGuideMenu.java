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
        addPlayerSlots(42, 97);
        this.addSlot(new CookingIngredientSlot(0, 54, 27));
        this.addSlot(new CookingIngredientSlot(1, 73, 27));
        this.addSlot(new CookingIngredientSlot(2, 92, 27));
        this.addSlot(new CookingIngredientSlot(3, 54, 45));
        this.addSlot(new CookingIngredientSlot(4, 73, 45));
        this.addSlot(new CookingIngredientSlot(5, 92, 45));
        this.addSlot(new DisplaySlot(6, 177, 34));
    }
    
}
