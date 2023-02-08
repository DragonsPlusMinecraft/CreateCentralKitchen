package plus.dragons.createcentralkitchen.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.minecraft.resources.ResourceLocation;

public class ProcessingRecipes {
    
    public static ProcessingRecipeBuilder<?> create(IRecipeTypeInfo type, ResourceLocation id) {
        ProcessingRecipeSerializer<?> serializer = type.getSerializer();
        return new ProcessingRecipeBuilder<>(serializer.getFactory(), id);
    }
    
    public static ProcessingRecipeBuilder<?> crushing(ResourceLocation id) {
        return create(AllRecipeTypes.CRUSHING, id);
    }
    
    public static ProcessingRecipeBuilder<?> milling(ResourceLocation id) {
        return create(AllRecipeTypes.MILLING, id);
    }
    
    public static ProcessingRecipeBuilder<?> mixing(ResourceLocation id) {
        return create(AllRecipeTypes.MIXING, id);
    }
    
    public static ProcessingRecipeBuilder<?> compacting(ResourceLocation id) {
        return create(AllRecipeTypes.COMPACTING, id);
    }
    
    public static ProcessingRecipeBuilder<?> pressing(ResourceLocation id) {
        return create(AllRecipeTypes.PRESSING, id);
    }
    
    public static ProcessingRecipeBuilder<?> polishing(ResourceLocation id) {
        return create(AllRecipeTypes.SANDPAPER_POLISHING, id);
    }
    
    public static ProcessingRecipeBuilder<?> splashing(ResourceLocation id) {
        return create(AllRecipeTypes.SPLASHING, id);
    }
    
    public static ProcessingRecipeBuilder<?> haunting(ResourceLocation id) {
        return create(AllRecipeTypes.HAUNTING, id);
    }
    
    public static ProcessingRecipeBuilder<?> deploying(ResourceLocation id) {
        return create(AllRecipeTypes.DEPLOYING, id);
    }
    
    public static ProcessingRecipeBuilder<?> filling(ResourceLocation id) {
        return create(AllRecipeTypes.FILLING, id);
    }
    
    public static ProcessingRecipeBuilder<?> emptying(ResourceLocation id) {
        return create(AllRecipeTypes.EMPTYING, id);
    }
    
    public static ProcessingRecipeBuilder<?> itemApplication(ResourceLocation id) {
        return create(AllRecipeTypes.ITEM_APPLICATION, id);
    }
    
}
