package plus.dragons.createcentralkitchen.modules.minersdelight.integration.jei;

import com.simibubi.create.compat.jei.GhostIngredientHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.integration.jei.AbstractJeiPlugin;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideScreen;
import plus.dragons.createcentralkitchen.modules.minersdelight.content.logistics.item.guide.MinersCookingGuideScreen;
import plus.dragons.createcentralkitchen.modules.minersdelight.integration.jei.transfer.CopperPotTransferInfo;
import plus.dragons.createcentralkitchen.modules.minersdelight.integration.jei.transfer.MinersCookingGuideTransferHandler;
import vectorwing.farmersdelight.integration.jei.FDRecipeTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MinersDelightJeiPlugin extends AbstractJeiPlugin {
    private static final ResourceLocation ID = CentralKitchen.genRL("miners_delight");
    
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(BrewingGuideScreen.class, new GhostIngredientHandler());
        registration.addRecipeClickArea(MinersCookingGuideScreen.class, 116, 24, 42, 30, FDRecipeTypes.COOKING);
    }
    
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        var helper = registration.getTransferHelper();
        registration.addRecipeTransferHandler(new CopperPotTransferInfo(helper));
        registration.addRecipeTransferHandler(new MinersCookingGuideTransferHandler(helper), FDRecipeTypes.COOKING);
    }
    
}