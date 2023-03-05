package plus.dragons.createcentralkitchen.core.config;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.simibubi.create.foundation.config.ConfigBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class CentralKitchenConfigBase extends ConfigBase {
    private final Map<String, ResourceLocation> resourceLocationCache = new HashMap<>();
    
    @Override
    protected void registerAll(ForgeConfigSpec.Builder builder) {
        super.registerAll(builder);
    }
    
    @Override
    public void onReload() {
        super.onReload();
        resourceLocationCache.clear();
    }
    
    protected <E extends Enum<E>> ConfigList<Enum<E>> le(Class<E> clazz, List<E> current, String name, String... comments) {
        return new ConfigList<>(name, current, clazz::isInstance, comments);
    }
    
    protected ConfigList<String> ls(Supplier<List<? extends String>> supplier, String name, String... comments) {
        return new ConfigList<>(name, supplier, value -> value instanceof String, comments);
    }
    
    protected ConfigList<String> ls(List<String> current, String name, String... comments) {
        return new ConfigList<>(name, current, value -> value instanceof String, comments);
    }
    
    protected ConfigIdList lid(Supplier<List<ResourceLocation>> supplier, String name, String... comments) {
        return new ConfigIdList(name, supplier, comments);
    }
    
    protected ConfigIdList lid(List<ResourceLocation> current, String name, String... comments) {
        return new ConfigIdList(name, current, comments);
    }
    
    public class ConfigList<T> extends CValue<List<? extends T>, ForgeConfigSpec.ConfigValue<List<? extends T>>> {
    
        public ConfigList(String name, Supplier<List<? extends T>> supplier, Predicate<Object> validator, String... comments) {
            super(name, builder -> builder.defineListAllowEmpty(split(name), supplier, validator), comments);
        }
    
        public ConfigList(String name, List<? extends T> def, Predicate<Object> validator, String... comments) {
            super(name, builder -> builder.defineListAllowEmpty(split(name), () -> def, validator), comments);
        }
        
    }
    
    public class ConfigIdList extends ConfigList<String> {
        
        public ConfigIdList(String name, Supplier<List<ResourceLocation>> supplier, String... comment) {
            super(name, () -> supplier.get().stream().map(Object::toString).collect(Collectors.toList()),
                value -> value instanceof String string && ResourceLocation.isValidResourceLocation(string),
                comment
            );
        }
    
        public ConfigIdList(String name, List<ResourceLocation> def, String... comment) {
            super(name, def.stream().map(Object::toString).collect(Collectors.toList()),
                value -> value instanceof String string && ResourceLocation.isValidResourceLocation(string), comment);
        }
    
        public List<ResourceLocation> getIds() {
            return get()
                .stream()
                .map(string -> resourceLocationCache.getOrDefault(string, new ResourceLocation(string)))
                .collect(Collectors.toList());
        }
        
        public void setIds(List<ResourceLocation> rls) {
            this.set(rls.stream().map(Objects::toString).collect(Collectors.toList()));
        }
        
    }
    
    private static final Splitter DOT_SPLITTER = Splitter.on(".");
    private static List<String> split(String path) {
        return Lists.newArrayList(DOT_SPLITTER.split(path));
    }
    
}
