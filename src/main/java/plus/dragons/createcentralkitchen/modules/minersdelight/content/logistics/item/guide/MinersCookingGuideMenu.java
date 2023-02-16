package plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.item.guide;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideMenu;

public class MinersCookingGuideMenu extends BlazeStoveGuideMenu<MinersCookingGuide> {

    public MinersCookingGuideMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    public MinersCookingGuideMenu(MenuType<?> type, int id, Inventory inv, ItemStack cookingGuide) {
        super(type, id, inv, cookingGuide);
    }
    
    public MinersCookingGuideMenu(MenuType<?> type, int id, Inventory inv, BlazeStoveBlockEntity blazeStove) {
        super(type, id, inv, blazeStove);
    }
    
    @Override
    public MinersCookingGuide createGuide(ItemStack contentHolder) {
        return MinersCookingGuide.of(contentHolder);
    }
    
    @Override
    protected void addSlots() {
        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 2; ++column) {
                this.addSlot(new CookingIngredientSlot(row * 2 + column, 69 + column * 18, 31 + row * 18));
            }
        }
        this.addSlot(new DisplaySlot(4, 175, 41));
        addPlayerSlots(52, 102);
    }
    
}
