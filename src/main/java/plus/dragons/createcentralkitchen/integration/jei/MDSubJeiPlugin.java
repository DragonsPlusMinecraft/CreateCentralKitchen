package plus.dragons.createcentralkitchen.integration.jei;

import com.sammy.minersdelight.jei.CopperPotCookingRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.minersCooking.MinersCookingGuideScreen;
import plus.dragons.createcentralkitchen.integration.jei.transfer.MinersCookingGuideTransferHandler;

public class MDSubJeiPlugin extends AbstractJeiPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return CentralKitchenJeiPlugin.ID;
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //registration.addGhostIngredientHandler(BrewingGuideScreen.class, new BlazeStoveGuideGhostIngredientHandler<>());
        registration.addRecipeClickArea(MinersCookingGuideScreen.class, 116, 24, 42, 30, CopperPotCookingRecipeCategory.COOKING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new MinersCookingGuideTransferHandler(), CopperPotCookingRecipeCategory.COOKING);
    }

}