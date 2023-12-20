package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DatapackRecipes extends Recipes {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<AbstractRegistrate<?>, Map<String, DatapackRecipes>> RECIPES = new HashMap<>();
    private static final Map<AbstractRegistrate<?>, ArrayListMultimap<String, Consumer<DatapackRecipes>>> EXTERNAL_RECIPES = new HashMap<>();
    protected final String name;
    protected final DataGenerator generator;
    
    public DatapackRecipes(String name, AbstractRegistrate<?> registrate, DataGenerator generator) {
        super(registrate, generator);
        this.name = name;
        this.generator = generator;
        allFor(registrate).put(name, this);
        var external = allExternalFor(registrate);
        if (external.containsKey(name)) {
            external.get(name).forEach(consumer -> consumer.accept(this));
        }
    }
    
    public static void addRecipe(AbstractRegistrate<?> registrate, String name, Consumer<DatapackRecipes> consumer) {
        allExternalFor(registrate).put(name, consumer);
    }
    
    public static <R, T extends R, P, B extends Builder<R, T, P, ?>> NonNullUnaryOperator<B> addRecipe(String modid, BiConsumer<DataGenContext<R, T>, Recipes> biConsumer) {
        return builder -> {
            addRecipe(builder.getOwner(), modid,
                recipes -> biConsumer.accept(DataGenContext.from(builder, builder.getRegistryKey()), recipes));
            return builder;
        };
    }
    
    public static void buildAll(AbstractRegistrate<?> registrate, DataGenerator datagen) {
        Set<String> missing = Sets.difference(allExternalFor(registrate).keySet(), allFor(registrate).keySet());
        for (String modId : missing)
            datagen.addProvider(true, new DatapackRecipes(modId, registrate, datagen));
    }
    
    public static Map<String, DatapackRecipes> allFor(AbstractRegistrate<?> registrate) {
        return RECIPES.computeIfAbsent(registrate, it -> new HashMap<>());
    }
    
    public static ArrayListMultimap<String, Consumer<DatapackRecipes>> allExternalFor(AbstractRegistrate<?> registrate) {
        return EXTERNAL_RECIPES.computeIfAbsent(registrate, it -> ArrayListMultimap.create());
    }
    
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        Path path = this.generator.getPackOutput().getOutputFolder().resolve("datapacks/" + name + "/data/");
        Set<ResourceLocation> set = Sets.newHashSet();
        return CompletableFuture.runAsync(() -> {
            buildRecipes(recipe -> {
                if (!set.add(recipe.getId())) {
                    throw new IllegalStateException("Duplicate recipe " + recipe.getId());
                } else {
                    DatapackRecipes.saveRecipe(cache, recipe.serializeRecipe(), path.resolve(recipe.getId().getNamespace() + "/recipes/" + recipe.getId().getPath() + ".json"));
                    JsonObject advancement = recipe.serializeAdvancement();
                    if (advancement != null) {
                        saveAdvancement(cache, recipe, advancement);
                    }
                }
            });
            }, Util.backgroundExecutor());
    }
    
    private static void saveRecipe(CachedOutput pOutput, JsonObject pRecipeJson, Path pPath) {
        DataProvider.saveStable(pOutput, pRecipeJson, pPath);
    }
    
}
