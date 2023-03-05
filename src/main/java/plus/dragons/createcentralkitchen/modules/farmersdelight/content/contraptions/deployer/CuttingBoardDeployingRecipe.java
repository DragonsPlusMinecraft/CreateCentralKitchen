package plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.deployer;

import com.simibubi.create.content.contraptions.components.deployer.DeployerRecipeSearchEvent;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import plus.dragons.createcentralkitchen.core.config.CentralKitchenConfigs;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FarmersDelightModuleRecipeTypes;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CuttingBoardDeployingRecipe extends ProcessingRecipe<RecipeWrapper> {
    
    public CuttingBoardDeployingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(FarmersDelightModuleRecipeTypes.CUTTING_BOARD_DEPLOYING, params);
    }
    
    public static void onDeployerRecipeSearch(DeployerRecipeSearchEvent event) {
        if (!CentralKitchenConfigs.COMMON.automationConfig.enableCuttingBoardDeploying.get())
            return;
        Level level = event.getTileEntity().getLevel();
        assert level != null;
        RecipeManager recipes = level.getRecipeManager();
        RecipeWrapper inventory = event.getInventory();
        event.addRecipe(() -> recipes
            .getAllRecipesFor(ModRecipeTypes.CUTTING.get())
            .stream()
            .filter(recipe -> recipe.matches(inventory, level) && recipe.getTool().test(inventory.getItem(1)))
            .findFirst()
            .map(CuttingBoardDeployingRecipe::fromCuttingBoard), 50
        );
    }
    
    public static CuttingBoardDeployingRecipe fromCuttingBoard(CuttingBoardRecipe recipe) {
        var builder = new ProcessingRecipeBuilder<>(CuttingBoardDeployingRecipe::new,
            new ResourceLocation(
                recipe.getId().getNamespace(),
                recipe.getId().getPath() + "_using_deployer"
            ))
            .require(recipe.getIngredients().get(0))
            .require(recipe.getTool());
        for (var output : recipe.getRollableResults()) {
            builder.output(output.getChance(), output.getStack());
        }
        return builder.toolNotConsumed().build();
    }
    
    @Override
    protected int getMaxInputCount() {
        return 2;
    }
    
    @Override
    protected int getMaxOutputCount() {
        return 4;
    }
    
    @Override
    public boolean matches(RecipeWrapper inventory, Level level) {
        return ingredients.get(0).test(inventory.getItem(0))
            && ingredients.get(1).test(inventory.getItem(1));
    }
    
}