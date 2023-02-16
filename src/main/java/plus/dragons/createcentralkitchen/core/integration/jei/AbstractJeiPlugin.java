package plus.dragons.createcentralkitchen.core.integration.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class AbstractJeiPlugin implements IModPlugin {
    protected final List<CreateRecipeCategory<?>> categories = new ArrayList<>();
    
    protected void populateCategories(IRecipeCategoryRegistration registration) {}
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        categories.clear();
        populateCategories(registration);
        if (categories.isEmpty())
            return;
        registration.addRecipeCategories(categories.toArray(IRecipeCategory[]::new));
    }
    
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        categories.forEach(c -> c.registerRecipes(registration));
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        categories.forEach(c -> c.registerCatalysts(registration));
    }
    
}
