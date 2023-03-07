package plus.dragons.createcentralkitchen.core.loot;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
    
    public static <T extends Block, R extends AbstractRegistrate<R>> NonNullUnaryOperator<BlockBuilder<T, R>> register(Function<T, LootTable.Builder> func) {
        return builder -> {
            ResourceLocation id = new ResourceLocation(builder.getOwner().getModid(), "blocks/" + builder.getName());
            return builder
                .onRegister(block -> register(id, func.apply(block)))
                .loot((loot, block) -> loot.add(block, LootTable.lootTable().setParamSet(LootContextParamSets.BLOCK)));
        };
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void populateRuntimeLootTables(LootTableLoadEvent event) {
        ResourceLocation id = event.getName();
        if (REGISTRY.containsKey(id)) {
            event.setTable(REGISTRY.get(id).build());
        }
    }
    
}
