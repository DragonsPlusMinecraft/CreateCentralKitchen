package plus.dragons.createcentralkitchen.modules.minersdelight.integration.jei.transfer;

import com.sammy.minersdelight.content.block.copper_pot.CopperPotMenu;
import com.simibubi.create.foundation.utility.Components;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.integration.jei.FDRecipeTypes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CopperPotTransferInfo implements IRecipeTransferInfo<CopperPotMenu, CookingPotRecipe> {
    private final IRecipeTransferHandlerHelper helper;
    
    public CopperPotTransferInfo(IRecipeTransferHandlerHelper helper) {
        this.helper = helper;
    }
    
    @Override
    public RecipeType<CookingPotRecipe> getRecipeType() {
        return FDRecipeTypes.COOKING;
    }
    
    @Override
    public Class<CopperPotMenu> getContainerClass() {
        return CopperPotMenu.class;
    }
    
    @Override
    public boolean canHandle(CopperPotMenu container, CookingPotRecipe recipe) {
        return recipe.getIngredients().size() <= 4;
    }
    
    @Nullable
    @Override
    public IRecipeTransferError getHandlingError(CopperPotMenu container, CookingPotRecipe recipe) {
        return helper.createUserErrorWithTooltip(Components.translatable("create_central_kitchen.jei.transfer.copper_pot.exceed_capacity"));
    }
    
    @Override
    public List<Slot> getRecipeSlots(CopperPotMenu container, CookingPotRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int index = 0; index < 5; ++index) {
            slots.add(container.getSlot(index));
        }
        return slots;
    }
    
    @Override
    public List<Slot> getInventorySlots(CopperPotMenu container, CookingPotRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int index = 7; index < 42; ++index) {
            slots.add(container.getSlot(index));
        }
        return slots;
    }
    
    //TODO: Remove these after bumping JEI version
    @Deprecated
    @SuppressWarnings("removal")
    public Class<CookingPotRecipe> getRecipeClass() {
        return CookingPotRecipe.class;
    }
    
    @Deprecated
    @SuppressWarnings("removal")
    public ResourceLocation getRecipeCategoryUid() {
        return FDRecipeTypes.COOKING.getUid();
    }
    
}
