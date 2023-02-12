package plus.dragons.createcentralkitchen.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.data.recipe.builder.ConditionedKettleRecipeBuilder;
import plus.dragons.createcentralkitchen.data.recipe.builder.ConditionedRecipeBuilder;
import plus.dragons.createcentralkitchen.data.recipe.builder.ConditionedShapedRecipeBuilder;
import plus.dragons.createcentralkitchen.data.recipe.builder.ConditionedShapelessRecipeBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public abstract class RecipeGen extends RecipeProvider {
    protected final List<GeneratedRecipe> recipes = new ArrayList<>();
    protected final String modid;
    
    public RecipeGen(String modid, DataGenerator datagen) {
        super(datagen);
        this.modid = modid;
    }
    
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        for (var recipe : recipes)
            recipe.register(consumer);
    }
    
    protected GeneratedRecipe common(ConditionedRecipeBuilder<?> builder) {
        GeneratedRecipe generated = builder::save;
        recipes.add(generated);
        return generated;
    }
    
    protected GeneratedRecipe common(ProcessingRecipeBuilder<?> builder) {
        GeneratedRecipe generated = builder::build;
        recipes.add(generated);
        return generated;
    }
    
    protected GeneratedRecipe modded(ConditionedRecipeBuilder<?> builder) {
        GeneratedRecipe generated = consumer -> builder.whenModLoaded(modid).save(consumer);
        recipes.add(generated);
        return generated;
    }
    
    protected GeneratedRecipe modded(ProcessingRecipeBuilder<?> builder) {
        GeneratedRecipe generated = consumer -> builder.whenModLoaded(modid).build(consumer);
        recipes.add(generated);
        return generated;
    }
    
    public static ConditionedShapelessRecipeBuilder shapeless(ResourceLocation id) {
        return new ConditionedShapelessRecipeBuilder(id);
    }
    
    public static ConditionedShapelessRecipeBuilder shapeless(String path) {
        return new ConditionedShapelessRecipeBuilder(CentralKitchen.genRL(path));
    }
    
    public static ConditionedShapedRecipeBuilder shaped(ResourceLocation id) {
        return new ConditionedShapedRecipeBuilder(id);
    }
    
    public static ConditionedShapedRecipeBuilder shaped(String path) {
        return new ConditionedShapedRecipeBuilder(CentralKitchen.genRL(path));
    }
    
    public static ConditionedKettleRecipeBuilder kettle(ResourceLocation id) {
        return new ConditionedKettleRecipeBuilder(id);
    }
    
    public static ConditionedKettleRecipeBuilder kettle(String path) {
        return new ConditionedKettleRecipeBuilder(CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> processing(IRecipeTypeInfo type, ResourceLocation id) {
        ProcessingRecipeSerializer<?> serializer = type.getSerializer();
        return new ProcessingRecipeBuilder<>(serializer.getFactory(), id);
    }
    
    public static ProcessingRecipeBuilder<?> crushing(ResourceLocation id) {
        return processing(AllRecipeTypes.CRUSHING, id);
    }
    
    public static ProcessingRecipeBuilder<?> crushing(String path) {
        return processing(AllRecipeTypes.CRUSHING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> milling(ResourceLocation id) {
        return processing(AllRecipeTypes.MILLING, id);
    }
    
    public static ProcessingRecipeBuilder<?> milling(String path) {
        return processing(AllRecipeTypes.MILLING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> mixing(ResourceLocation id) {
        return processing(AllRecipeTypes.MIXING, id);
    }
    
    public static ProcessingRecipeBuilder<?> mixing(String path) {
        return processing(AllRecipeTypes.MIXING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> compacting(ResourceLocation id) {
        return processing(AllRecipeTypes.COMPACTING, id);
    }
    
    public static ProcessingRecipeBuilder<?> compacting(String path) {
        return processing(AllRecipeTypes.COMPACTING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> pressing(ResourceLocation id) {
        return processing(AllRecipeTypes.PRESSING, id);
    }
    
    public static ProcessingRecipeBuilder<?> pressing(String path) {
        return processing(AllRecipeTypes.PRESSING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> polishing(ResourceLocation id) {
        return processing(AllRecipeTypes.SANDPAPER_POLISHING, id);
    }
    
    public static ProcessingRecipeBuilder<?> polishing(String path) {
        return processing(AllRecipeTypes.SANDPAPER_POLISHING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> splashing(ResourceLocation id) {
        return processing(AllRecipeTypes.SPLASHING, id);
    }
    
    public static ProcessingRecipeBuilder<?> splashing(String path) {
        return processing(AllRecipeTypes.SPLASHING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> haunting(ResourceLocation id) {
        return processing(AllRecipeTypes.HAUNTING, id);
    }
    
    public static ProcessingRecipeBuilder<?> haunting(String path) {
        return processing(AllRecipeTypes.HAUNTING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> deploying(ResourceLocation id) {
        return processing(AllRecipeTypes.DEPLOYING, id);
    }
    
    public static ProcessingRecipeBuilder<?> deploying(String path) {
        return processing(AllRecipeTypes.DEPLOYING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> filling(ResourceLocation id) {
        return processing(AllRecipeTypes.FILLING, id);
    }
    
    public static ProcessingRecipeBuilder<?> filling(String path) {
        return processing(AllRecipeTypes.FILLING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> emptying(ResourceLocation id) {
        return processing(AllRecipeTypes.EMPTYING, id);
    }
    
    public static ProcessingRecipeBuilder<?> emptying(String path) {
        return processing(AllRecipeTypes.EMPTYING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> itemApplication(ResourceLocation id) {
        return processing(AllRecipeTypes.ITEM_APPLICATION, id);
    }
    
    public static ProcessingRecipeBuilder<?> itemApplication(String path) {
        return processing(AllRecipeTypes.ITEM_APPLICATION, CentralKitchen.genRL(path));
    }
    
}
