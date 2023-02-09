package plus.dragons.createcentralkitchen.modules.farmersrespite.integration.jei.transfer;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.common.network.CentralKitchenNetwork;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideSyncPacket;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideMenu;
import plus.dragons.createcentralkitchen.modules.farmersrespite.entry.FrMenuTypes;
import umpaz.farmersrespite.common.crafting.KettleRecipe;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BrewingGuideTransferHandler implements IRecipeTransferHandler<BrewingGuideMenu, KettleRecipe> {
    
    @Override
    public Class<BrewingGuideMenu> getContainerClass() {
        return BrewingGuideMenu.class;
    }

    @Override
    public Optional<MenuType<BrewingGuideMenu>> getMenuType() {
        return Optional.of(FrMenuTypes.BREWING_GUIDE.get());
    }

    @Override
    public RecipeType<KettleRecipe> getRecipeType() {
        return RecipeType.create(CentralKitchen.ID, "brewing", KettleRecipe.class);
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
            container.getSlot(i + 36).set(input);
        }
        CentralKitchenNetwork.CHANNEL.sendToServer(new BlazeStoveGuideSyncPacket(container));
        return null;
    }
    
}
