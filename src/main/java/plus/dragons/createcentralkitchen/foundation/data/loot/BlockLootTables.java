package plus.dragons.createcentralkitchen.foundation.data.loot;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.loading.DatagenModLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class BlockLootTables implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Map<String, Map<ResourceLocation, Supplier<LootTable.Builder>>> BLOCK_LOOTS = new HashMap<>();
    private final DataGenerator generator;
    
    public BlockLootTables(DataGenerator generator) {
        this.generator = generator;
    }
    
    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        Path outputFolder = this.generator.getPackOutput().getOutputFolder();
        return CompletableFuture.runAsync(() -> BLOCK_LOOTS.forEach((modid, suppliers) -> {
            Path datapackFolder = outputFolder.resolve("datapacks/" + modid);
            Map<ResourceLocation, LootTable> map = Maps.newHashMap();
            suppliers.forEach((id, supplier) -> {
                if (map.put(id, supplier.get().setParamSet(LootContextParamSets.BLOCK).build())  != null) {
                    throw new IllegalStateException("Duplicate loot table " + id);
                }
            });
            ValidationContext validationContext = new ValidationContext(LootContextParamSets.ALL_PARAMS, new LootDataResolver() {
                @Nullable
                @Override
                public <T> T getElement(@NotNull LootDataId<T> pId) {
                    return (T)(pId.type() == LootDataType.TABLE ? map.get(pId.location()) : null);
                }
            });
            map.forEach((id, lootTable) -> lootTable.validate(validationContext));
            Multimap<String, String> problems = validationContext.getProblems();
            if (!problems.isEmpty()) {
                problems.forEach((key, value) -> LOGGER.warn("Found validation problem in {}: {}", key, value));
                throw new IllegalStateException("Failed to validate loot tables, see logs");
            } else {
                map.forEach((id, lootTable) -> {
                    Path path = createPath(datapackFolder, id);
                    DataProvider.saveStable(cache, LootDataType.TABLE.parser().toJsonTree(lootTable), path);
                });
            }
        }), Util.backgroundExecutor());
    }
    
    private static Path createPath(Path path, ResourceLocation id) {
        return path.resolve("data/" + id.getNamespace() + "/loot_tables/" + id.getPath() + ".json");
    }
    
    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> add(String modId, NonNullFunction<T, LootTable.Builder> function) {
        Map<ResourceLocation, Supplier<LootTable.Builder>> map = BLOCK_LOOTS.computeIfAbsent(modId, it -> new HashMap<>());
        return builder -> builder
            .properties(BlockLootTables::noLootGen)
            .onRegister(block -> map.put(
                new ResourceLocation(builder.getOwner().getModid(), "blocks/" + builder.getName()),
                () -> function.apply(block)
            ));
    }
    
    /**
     * <p>Cancels loot gen and suppress {@code LootTableProvider#validate(Map, ValidationContext)} for blocks.</p>
     * <p>Use this in {@link com.tterrag.registrate.builders.BlockBuilder#properties(NonNullUnaryOperator)}.</p>
     */
    public static BlockBehaviour.Properties noLootGen(BlockBehaviour.Properties properties) {
        if (DatagenModLoader.isRunningDataGen()) {
            properties.noLootTable();
        }
        return properties;
    }
    
    @Override
    public String getName() {
        return "LootTables";
    }
    
}
