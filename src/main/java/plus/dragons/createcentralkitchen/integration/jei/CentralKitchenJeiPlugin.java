package plus.dragons.createcentralkitchen.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class CentralKitchenJeiPlugin implements IModPlugin {
    public static final ResourceLocation ID = CentralKitchen.genRL("jei_plugin");
    private final List<IModPlugin> plugins = new ArrayList<>();
    
    public CentralKitchenJeiPlugin() {
        ModList mods = ModList.get();
        if (Mods.isLoaded(Mods.FD)) plugins.add(new FDSubJeiPlugin());
        if (Mods.isLoaded(Mods.FR)) plugins.add(new FRSubJeiPlugin());
//        if (Mods.isLoaded(Mods.MD)) plugins.add(new MDSubJeiPlugin());
    }
    
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        plugins.forEach(plugin -> plugin.registerItemSubtypes(registration));
    }
    
    @Override
    public <T> void registerFluidSubtypes(ISubtypeRegistration registration, IPlatformFluidHelper<T> platformFluidHelper) {
        plugins.forEach(plugin -> plugin.registerFluidSubtypes(registration, platformFluidHelper));
    }
    
    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        plugins.forEach(plugin -> plugin.registerIngredients(registration));
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        plugins.forEach(plugin -> plugin.registerCategories(registration));
    }
    
    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        plugins.forEach(plugin -> plugin.registerVanillaCategoryExtensions(registration));
    }
    
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        plugins.forEach(plugin -> plugin.registerRecipes(registration));
    }
    
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        plugins.forEach(plugin -> plugin.registerRecipeTransferHandlers(registration));
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        plugins.forEach(plugin -> plugin.registerRecipeCatalysts(registration));
    }
    
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        plugins.forEach(plugin -> plugin.registerGuiHandlers(registration));
    }
    
    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {
        plugins.forEach(plugin -> plugin.registerAdvanced(registration));
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        plugins.forEach(plugin -> plugin.onRuntimeAvailable(jeiRuntime));
    }
    
}
