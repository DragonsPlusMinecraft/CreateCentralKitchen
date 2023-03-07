package plus.dragons.createcentralkitchen.modules.farmersdelight.integration.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.config.CentralKitchenConfigs;
import plus.dragons.createcentralkitchen.core.integration.jei.AbstractJeiPlugin;
import plus.dragons.createcentralkitchen.core.integration.jei.RecipeCategoryBuilder;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.deployer.CuttingBoardDeployingRecipe;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide.CookingGuideScreen;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FarmersDelightModuleRecipeTypes;
import plus.dragons.createcentralkitchen.modules.farmersdelight.integration.jei.category.CuttingBoardDeployingCategory;
import plus.dragons.createcentralkitchen.modules.farmersdelight.integration.jei.transfer.BlazeStoveGuideGhostIngredientHandler;
import plus.dragons.createcentralkitchen.modules.farmersdelight.integration.jei.transfer.CookingGuideTransferHandler;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.integration.jei.FDRecipeTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FarmersDelightModuleJeiPlugin extends AbstractJeiPlugin {
    private static final ResourceLocation ID = CentralKitchen.genRL("farmersdelight");
    
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
    protected void populateCategories(IRecipeCategoryRegistration registration) {
        if (CentralKitchenConfigs.COMMON.automation.enableCuttingBoardDeploying.get()) {
            categories.add(new RecipeCategoryBuilder<>(CentralKitchen.ID, CuttingBoardDeployingRecipe.class)
                .addTypedRecipes(FarmersDelightModuleRecipeTypes.CUTTING_BOARD_DEPLOYING)
                .addTransformedRecipes(ModRecipeTypes.CUTTING, CuttingBoardDeployingRecipe::fromCuttingBoard)
                .catalyst(AllBlocks.DEPLOYER::get)
                .catalyst(AllBlocks.DEPOT::get)
                .catalyst(AllItems.BELT_CONNECTOR::get)
                .doubleItemIcon(AllBlocks.DEPLOYER.get(), ModBlocks.CUTTING_BOARD.get())
                .emptyBackground(177, 70)
                .build("cutting_board_deploying", CuttingBoardDeployingCategory::new)
            );
        }
    }
    
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(CookingGuideScreen.class, new BlazeStoveGuideGhostIngredientHandler<>());
        registration.addRecipeClickArea(CookingGuideScreen.class, 124, 24, 42, 30, FDRecipeTypes.COOKING);
    }
    
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new CookingGuideTransferHandler(), FDRecipeTypes.COOKING);
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        super.onRuntimeAvailable(jeiRuntime);
    }
}