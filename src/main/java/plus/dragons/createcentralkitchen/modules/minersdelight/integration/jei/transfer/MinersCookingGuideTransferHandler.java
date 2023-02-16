package plus.dragons.createcentralkitchen.modules.minersdelight.integration.jei.transfer;

import com.simibubi.create.foundation.utility.Components;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.core.network.CentralKitchenNetwork;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideSyncPacket;
import plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.item.guide.MinersCookingGuideMenu;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MinersCookingGuideTransferHandler implements IRecipeTransferHandler<MinersCookingGuideMenu, CookingPotRecipe> {
    private final IRecipeTransferHandlerHelper helper;
    
    public MinersCookingGuideTransferHandler(IRecipeTransferHandlerHelper helper) {
        this.helper = helper;
    }
    
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
        if (inputs.size() > 4)
            return helper.createUserErrorWithTooltip(Components.translatable("create_central_kitchen.jei.transfer.copper_pot.exceed_capacity"));
        if (!doTransfer)
            return null;
        for (int i = 0; i < 4; ++i) {
            ItemStack input;
            if (i < inputs.size()) {
                var items = inputs.get(i).getItems();
                input = items.length == 0 ? ItemStack.EMPTY : items[0];
            } else input = ItemStack.EMPTY;
            container.getSlot(i).set(input);
        }
        CentralKitchenNetwork.CHANNEL.sendToServer(new BlazeStoveGuideSyncPacket(container));
        return null;
    }
    
}
