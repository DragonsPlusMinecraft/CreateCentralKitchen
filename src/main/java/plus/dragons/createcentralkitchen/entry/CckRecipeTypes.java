package plus.dragons.createcentralkitchen.entry;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.content.contraptions.components.deployer.CuttingBoardDeployingRecipe;

import java.util.function.Supplier;

public enum CckRecipeTypes implements IRecipeTypeInfo {
    CUTTING_BOARD_DEPLOYING(CuttingBoardDeployingRecipe::new);
    
    private final ResourceLocation id;
    private final Supplier<RecipeSerializer<?>> serializer;
    private final Supplier<RecipeType<?>> type;
    
    CckRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
        String name = Lang.asId(name());
        id = CentralKitchen.genRL(name);
        serializer = CentralKitchen.SERIALIZER_REGISTER.register(name, serializerSupplier);
        type = registerType
            ? CentralKitchen.TYPE_REGISTER.register(name, typeSupplier)
            : NonNullSupplier.lazy(typeSupplier);
    }
    
    CckRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = CentralKitchen.genRL(name);
        serializer = CentralKitchen.SERIALIZER_REGISTER.register(name, serializerSupplier);
        type = CentralKitchen.TYPE_REGISTER.register(name, () -> simpleType(id));
    }
    
    CckRecipeTypes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
        this(() -> new ProcessingRecipeSerializer<>(processingFactory));
    }
    
    @Override
    public ResourceLocation getId() {
        return id;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializer.get();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T) type.get();
    }
    
    public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
        String stringId = id.toString();
        return new RecipeType<>() {
            @Override
            public String toString() {
                return stringId;
            }
        };
    }
    
}