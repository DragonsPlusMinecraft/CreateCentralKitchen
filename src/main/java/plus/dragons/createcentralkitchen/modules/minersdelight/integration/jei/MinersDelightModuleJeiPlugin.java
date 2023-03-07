package plus.dragons.createcentralkitchen.modules.minersdelight.integration.jei;

import com.sammy.minersdelight.jei.CopperPotCookingRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.integration.jei.AbstractJeiPlugin;
import plus.dragons.createcentralkitchen.modules.farmersdelight.integration.jei.transfer.BlazeStoveGuideGhostIngredientHandler;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideScreen;
import plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.item.guide.MinersCookingGuideScreen;
import plus.dragons.createcentralkitchen.modules.minersdelight.integration.jei.transfer.MinersCookingGuideTransferHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MinersDelightModuleJeiPlugin extends AbstractJeiPlugin {
    private static final ResourceLocation ID = CentralKitchen.genRL("miners_delight");
    
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(BrewingGuideScreen.class, new BlazeStoveGuideGhostIngredientHandler<>());
        registration.addRecipeClickArea(MinersCookingGuideScreen.class, 116, 24, 42, 30, CopperPotCookingRecipeCategory.COOKING);
    }
    
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new MinersCookingGuideTransferHandler(), CopperPotCookingRecipeCategory.COOKING);
    }
    
}