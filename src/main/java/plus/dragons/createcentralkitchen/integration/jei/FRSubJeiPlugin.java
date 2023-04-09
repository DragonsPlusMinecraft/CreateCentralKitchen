package plus.dragons.createcentralkitchen.integration.jei;

import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing.BrewingGuideScreen;
import plus.dragons.createcentralkitchen.integration.jei.transfer.BlazeStoveGuideGhostIngredientHandler;
import plus.dragons.createcentralkitchen.integration.jei.transfer.BrewingGuideTransferHandler;
import umpaz.farmersrespite.integration.jei.JEIFRRecipeTypes;

public class FRSubJeiPlugin extends AbstractJeiPlugin {
    
    @Override
    public ResourceLocation getPluginUid() {
        return CentralKitchenJeiPlugin.ID;
    }
    
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(BrewingGuideScreen.class, new BlazeStoveGuideGhostIngredientHandler<>());
        registration.addRecipeClickArea(BrewingGuideScreen.class, 108, 24, 42, 30, JEIFRRecipeTypes.BREWING);
    }
    
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new BrewingGuideTransferHandler(), JEIFRRecipeTypes.BREWING);
    }
    
}