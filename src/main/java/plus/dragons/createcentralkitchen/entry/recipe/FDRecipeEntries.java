package plus.dragons.createcentralkitchen.entry.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.MinecraftForge;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.content.contraptions.deployer.CuttingBoardDeployingRecipe;
import plus.dragons.createcentralkitchen.foundation.utility.Lang;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import java.util.function.Supplier;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.FD)
public enum FDRecipeEntries implements IRecipeTypeInfo {
    CUTTING_BOARD_DEPLOYING(CuttingBoardDeployingRecipe::new);
    static {
        MinecraftForge.EVENT_BUS.addListener(CuttingBoardDeployingRecipe::onDeployerRecipeSearch);
    }
    
    private final ResourceLocation id;
    private final Supplier<RecipeSerializer<?>> serializer;
    private final Supplier<RecipeType<?>> type;
    
    FDRecipeEntries(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
        String name = Lang.asId(name());
        id = CentralKitchen.genRL(name);
        serializer = CentralKitchen.RECIPE_SERIALIZER_REGISTER.register(name, serializerSupplier);
        type = registerType
            ? CentralKitchen.RECIPE_TYPE_REGISTER.register(name, typeSupplier)
            : NonNullSupplier.lazy(typeSupplier);
        REGISTRATE.addDataGenerator(ProviderType.LANG, prov -> prov.add(
            Util.makeDescriptionId("recipe", id),
            RegistrateLangProvider.toEnglishName(id.getPath())));
    }
    
    FDRecipeEntries(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = CentralKitchen.genRL(name);
        serializer = CentralKitchen.RECIPE_SERIALIZER_REGISTER.register(name, serializerSupplier);
        type = CentralKitchen.RECIPE_TYPE_REGISTER.register(name, () -> simpleType(id));
        REGISTRATE.addDataGenerator(ProviderType.LANG, prov -> prov.add(
            Util.makeDescriptionId("recipe", id),
            RegistrateLangProvider.toEnglishName(id.getPath())));
    }
    
    FDRecipeEntries(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
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