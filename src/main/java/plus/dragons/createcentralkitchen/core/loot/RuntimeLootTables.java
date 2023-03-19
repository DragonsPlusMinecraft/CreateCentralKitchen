package plus.dragons.createcentralkitchen.core.loot;

import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>For registering {@link LootTable} at runtime.</p>
 * <p>Useful for creating {@link LootTable} for {@link Block} that may not be available at runtime.</p>
 * @author LimonBlaze
 */
@Mod.EventBusSubscriber
public class RuntimeLootTables {
    private static final Map<ResourceLocation, LootTable.Builder> REGISTRY = new HashMap<>();
    
    public static void register(ResourceLocation id, LootTable.Builder lootTable) {
        synchronized (REGISTRY) {
            REGISTRY.put(id, lootTable);
        }
    }
    
    /**
     * <p>A hacky way to suppress loot gen check for blocks.</p>
     * <p>Use this in {@link com.tterrag.registrate.builders.BlockBuilder#properties(NonNullUnaryOperator)}.</p>
     */
    public static BlockBehaviour.Properties suppressLootGen(BlockBehaviour.Properties properties) {
        if (DatagenModLoader.isRunningDataGen()) {
            properties.noDrops();
        }
        return properties;
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void populateRuntimeLootTables(LootTableLoadEvent event) {
        ResourceLocation id = event.getName();
        if (REGISTRY.containsKey(id)) {
            event.setTable(REGISTRY.get(id).build());
        }
    }
    
}
