package plus.dragons.createcentralkitchen.modules.farmersrespite.integration.jei;

import com.farmersrespite.common.crafting.KettleRecipe;
import com.farmersrespite.integration.jei.category.BrewingRecipeCategory;
import com.mojang.logging.LogUtils;
import com.simibubi.create.compat.jei.GhostIngredientHandler;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.common.integration.jei.AbstractJeiPlugin;
import plus.dragons.createcentralkitchen.modules.farmersrespite.content.logistics.item.guide.BrewingGuideScreen;
import plus.dragons.createcentralkitchen.modules.farmersrespite.integration.jei.transfer.BrewingGuideTransferHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FarmersRespiteJeiPlugin extends AbstractJeiPlugin {
    private static final ResourceLocation ID = CentralKitchen.genRL("farmersrespite");
    
    public static final RecipeType<KettleRecipe> BREWING = new RecipeType<>(BrewingRecipeCategory.UID, KettleRecipe.class);
    
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(BrewingGuideScreen.class, new GhostIngredientHandler());
        registration.addRecipeClickArea(BrewingGuideScreen.class, 122, 26, 41, 26, BREWING);
    }
    
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new BrewingGuideTransferHandler(), BREWING);
    }
    
}