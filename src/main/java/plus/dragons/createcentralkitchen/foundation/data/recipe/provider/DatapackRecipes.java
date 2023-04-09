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
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DatapackRecipes extends Recipes {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<AbstractRegistrate<?>, Map<String, DatapackRecipes>> RECIPES = new HashMap<>();
    private static final Map<AbstractRegistrate<?>, ArrayListMultimap<String, Consumer<DatapackRecipes>>> EXTERNAL_RECIPES = new HashMap<>();
    protected final String name;
    
    public DatapackRecipes(String name, AbstractRegistrate<?> registrate, DataGenerator generator) {
        super(registrate, generator);
        this.name = name;
        allFor(registrate).put(name, this);
        var external = allExternalFor(registrate);
        if (external.containsKey(name)) {
            external.get(name).forEach(consumer -> consumer.accept(this));
        }
    }
    
    public static void addRecipe(AbstractRegistrate<?> registrate, String name, Consumer<DatapackRecipes> consumer) {
        allExternalFor(registrate).put(name, consumer);
    }
    
    public static <R extends IForgeRegistryEntry<R>, T extends R, P, B extends Builder<R, T, P, ?>> NonNullUnaryOperator<B> addRecipe(String modid, BiConsumer<DataGenContext<R, T>, Recipes> biConsumer) {
        return builder -> {
            addRecipe(builder.getOwner(), modid,
                recipes -> biConsumer.accept(DataGenContext.from(builder, builder.getRegistryKey()), recipes));
            return builder;
        };
    }
    
    public static void buildAll(AbstractRegistrate<?> registrate, DataGenerator datagen) {
        Set<String> missing = Sets.difference(allExternalFor(registrate).keySet(), allFor(registrate).keySet());
        for (String modId : missing)
            datagen.addProvider(new DatapackRecipes(modId, registrate, datagen));
    }
    
    public static Map<String, DatapackRecipes> allFor(AbstractRegistrate<?> registrate) {
        return RECIPES.computeIfAbsent(registrate, it -> new HashMap<>());
    }
    
    public static ArrayListMultimap<String, Consumer<DatapackRecipes>> allExternalFor(AbstractRegistrate<?> registrate) {
        return EXTERNAL_RECIPES.computeIfAbsent(registrate, it -> ArrayListMultimap.create());
    }
    
    public void run(HashCache cache) {
        Path path = this.generator.getOutputFolder().resolve("datapacks/" + name + "/data/");
        Set<ResourceLocation> set = Sets.newHashSet();
        buildCraftingRecipes(recipe -> {
            if (!set.add(recipe.getId())) {
                throw new IllegalStateException("Duplicate recipe " + recipe.getId());
            } else {
                DatapackRecipes.saveRecipe(cache, recipe.serializeRecipe(), path.resolve(recipe.getId().getNamespace() + "/recipes/" + recipe.getId().getPath() + ".json"));
                JsonObject advancement = recipe.serializeAdvancement();
                if (advancement != null) {
                    saveAdvancement(cache, advancement, path.resolve(recipe.getId().getNamespace() + "/advancements/" + recipe.getAdvancementId().getPath() + ".json"));
                }
            }
        });
    }
    
    private static void saveRecipe(HashCache cache, JsonObject json, Path path) {
        try {
            String jsonString = GSON.toJson(json);
            String sha1 = SHA1.hashUnencodedChars(jsonString).toString();
            if (!Objects.equals(cache.getHash(path), sha1) || !Files.exists(path)) {
                Files.createDirectories(path.getParent());
                try(BufferedWriter bufferedwriter = Files.newBufferedWriter(path)) {
                    bufferedwriter.write(jsonString);
                }
            }
            cache.putNew(path, sha1);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save recipe {}", path, ioexception);
        }
    }
    
}
