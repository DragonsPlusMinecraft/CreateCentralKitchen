package plus.dragons.createfarmersautomation.event;

import com.simibubi.create.content.contraptions.components.deployer.DeployerRecipeSearchEvent;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import plus.dragons.createfarmersautomation.FarmersAutomation;
import plus.dragons.createfarmersautomation.content.contraptions.components.deployer.CuttingBoardDeployingRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

@Mod.EventBusSubscriber(modid = FarmersAutomation.ID)
public class CommonForgeEventHandler {
    
    @SubscribeEvent
    public static void addDeployerRecipes(DeployerRecipeSearchEvent event) {
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

}
