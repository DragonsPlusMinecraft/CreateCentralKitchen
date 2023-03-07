package plus.dragons.createcentralkitchen.modules.minersdelight.integration.jei.transfer;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideSyncPacket;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FarmersDelightModulePackets;
import plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.item.guide.MinersCookingGuideMenu;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MinersCookingGuideTransferHandler implements IRecipeTransferHandler<MinersCookingGuideMenu, CookingPotRecipe> {
    
    @Override
    public Class<MinersCookingGuideMenu> getContainerClass() {
        return MinersCookingGuideMenu.class;
    }
    
    @Override
    public Class<CookingPotRecipe> getRecipeClass() {
        return CookingPotRecipe.class;
    }
    
    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(MinersCookingGuideMenu container, CookingPotRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {
        var inputs = recipe.getIngredients();
        if (!doTransfer)
            return null;
        for (int i = 0; i < 4; ++i) {
            ItemStack input;
            if (i < inputs.size()) {
                var items = inputs.get(i).getItems();
                input = items.length == 0 ? ItemStack.EMPTY : items[0];
            } else input = ItemStack.EMPTY;
            container.getSlot(36 + i).set(input);
        }
        FarmersDelightModulePackets.CHANNEL.sendToServer(new BlazeStoveGuideSyncPacket(container));
        return null;
    }
    
}
