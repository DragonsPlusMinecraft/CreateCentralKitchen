package plus.dragons.createcentralkitchen.core.loot.modifier;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>A {@link GlobalLootModifierSerializer} which uses {@link ICondition}
 * to determine if the wrapped {@link IGlobalLootModifier} should be loaded.</p>
 * <p>Json format:</p>
 * <pre>
 * {
 *   "type": "create_central_kitchen:conditional_loaded",
 *   "load_conditions": [
 *     {
 *       "type": "forge:mod_loaded"
 *       "modid": "farmersdelight"
 *     }
 *   ],
 *   "modifier": {
 *     "type": "farmersdelight:pastry_slicing",
 *     "conditions": [
 *       {
 *         "condition": "minecraft:match_tool",
 *         "predicate": {
 *           "tag": "farmersdelight:tools/knives"
 *         }
 *       },
 *       {
 *         "condition": "minecraft:block_state_property",
 *         "block": "farmersdelight:chocolate_pie"
 *       }
 *     ],
 *     "slice": "farmersdelight:chocolate_pie_slice"
 *   }
 * }
 * </pre>
 * <p>Note that loot conditions outside the "modifier" object will always be loaded and combined with inner ones.</p>
 * @param <M> Type of the {@link IGlobalLootModifier}, only necessary when used in serializing.
 * @author LimonBlaze
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConditionalLoadedLootModifierSerializer<M extends IGlobalLootModifier> extends GlobalLootModifierSerializer<M> {
    private static final Gson GSON = Deserializers.createFunctionSerializer().create();
    private final Class<M> clazz;
    @Nullable
    private final GlobalLootModifierSerializer<M> serializer;
    private final List<ICondition> conditions;
    private ICondition.IContext context = ICondition.IContext.EMPTY;
    
    private ConditionalLoadedLootModifierSerializer(Class<M> clazz, @Nullable GlobalLootModifierSerializer<M> serializer, ICondition... conditions) {
        this.clazz = clazz;
        this.serializer = serializer;
        this.conditions = List.of(conditions);
    }
    
    /**
     * @return A deserialize-only instance, should be used in registry.
     */
    public static ConditionalLoadedLootModifierSerializer<IGlobalLootModifier> deserializer() {
        return new ConditionalLoadedLootModifierSerializer<>(IGlobalLootModifier.class, null);
    }
    
    /**
     * @param clazz Class of the modifier
     * @param serializer Serializer of the modifier
     * @param conditions Load Conditions of the modifier
     * @param <M> Type of the modifier
     * @return A serializer-only instance, should be used in {@link net.minecraftforge.common.data.GlobalLootModifierProvider}.
     */
    public static <M extends IGlobalLootModifier> ConditionalLoadedLootModifierSerializer<M> serializer(Class<M> clazz, GlobalLootModifierSerializer<M> serializer, ICondition... conditions) {
        return new ConditionalLoadedLootModifierSerializer<>(clazz, serializer, conditions);
    }
    
    public void updateContext(AddReloadListenerEvent event) {
        this.context = event.getConditionContext();
    }
    
    @Override
    @Nullable
    public M read(ResourceLocation id, JsonObject json, LootItemCondition[] extConditions) {
        if (serializer != null || CraftingHelper.processConditions(json, "load_conditions", context)) {
            return null;
        }
        JsonObject modifierJson = GsonHelper.getAsJsonObject(json, "modifier");
        List<LootItemCondition> conditions = new ArrayList<>();
        Collections.addAll(conditions, extConditions);
        Collections.addAll(conditions, GSON.fromJson(modifierJson.get("conditions"), LootItemCondition[].class));
        ResourceLocation type = new ResourceLocation(GsonHelper.getAsString(modifierJson, "type"));
        GlobalLootModifierSerializer<?> serializer = ForgeRegistries.LOOT_MODIFIER_SERIALIZERS.get().getValue(type);
        if (serializer == null)
            return null;
        IGlobalLootModifier modifier = serializer.read(id, modifierJson, conditions.toArray(LootItemCondition[]::new));
        return clazz.isInstance(modifier) ? clazz.cast(modifier) : null;
    }
    
    @Override
    public JsonObject write(M modifier) {
        if (serializer == null)
            throw new UnsupportedOperationException("ConditionedLootModifierSerializer without a serializer can not be used in serialization!");
        JsonObject json = serializer.write(modifier);
        if (!conditions.isEmpty()) {
            JsonArray conditionsJson = new JsonArray();
            for (var condition : conditions) {
                conditionsJson.add(CraftingHelper.serialize(condition));
            }
            json.add("load_conditions", conditionsJson);
        }
        return json;
    }
    
}
