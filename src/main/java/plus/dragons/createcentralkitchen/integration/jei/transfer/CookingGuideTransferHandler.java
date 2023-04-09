package plus.dragons.createcentralkitchen.integration.jei.transfer;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuideSyncPacket;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuideMenu;
import plus.dragons.createcentralkitchen.entry.menu.FDMenuEntries;
import plus.dragons.createcentralkitchen.entry.network.FDNetworkEntries;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.integration.jei.FDRecipeTypes;

import java.util.Optional;

public class CookingGuideTransferHandler implements IRecipeTransferHandler<CookingGuideMenu, CookingPotRecipe> {
    
    @Override
    public Class<CookingGuideMenu> getContainerClass() {
        return CookingGuideMenu.class;
    }
    
    @Override
    public Optional<MenuType<CookingGuideMenu>> getMenuType() {
        return Optional.of(FDMenuEntries.COOKING_GUIDE.get());
    }
    
    @Override
    public RecipeType<CookingPotRecipe> getRecipeType() {
        return FDRecipeTypes.COOKING;
    }
    
    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(CookingGuideMenu container, CookingPotRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {
        if (!doTransfer)
            return null;
        var inputs = recipe.getIngredients();
        for (int i = 0; i < 6; ++i) {
            ItemStack input;
            if (i < inputs.size()) {
                var items = inputs.get(i).getItems();
                input = items.length == 0 ? ItemStack.EMPTY : items[0];
            } else input = ItemStack.EMPTY;
            container.getSlot(36 + i).set(input);
        }
        FDNetworkEntries.CHANNEL.sendToServer(new BlazeStoveGuideSyncPacket(container));
        return null;
    }
    
}
