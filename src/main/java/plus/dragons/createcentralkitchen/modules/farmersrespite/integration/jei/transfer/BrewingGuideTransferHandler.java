package plus.dragons.createcentralkitchen.modules.farmersrespite.integration.jei.transfer;

import com.farmersrespite.common.crafting.KettleRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.core.network.CentralKitchenNetwork;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideSyncPacket;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideMenu;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BrewingGuideTransferHandler implements IRecipeTransferHandler<BrewingGuideMenu, KettleRecipe> {
    
    @Override
    public Class<BrewingGuideMenu> getContainerClass() {
        return BrewingGuideMenu.class;
    }
    
    @Override
    public Class<KettleRecipe> getRecipeClass() {
        return KettleRecipe.class;
    }
    
    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(BrewingGuideMenu container, KettleRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {
        if (!doTransfer)
            return null;
        var inputs = recipe.getIngredients();
        for (int i = 0; i < 2; ++i) {
            ItemStack input;
            if (i < inputs.size()) {
                var items = inputs.get(i).getItems();
                input = items.length == 0 ? ItemStack.EMPTY : items[0];
            } else input = ItemStack.EMPTY;
            container.getSlot(36 + i).set(input);
        }
        CentralKitchenNetwork.CHANNEL.sendToServer(new BlazeStoveGuideSyncPacket(container));
        return null;
    }
    
}
