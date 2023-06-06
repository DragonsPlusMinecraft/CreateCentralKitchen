package plus.dragons.createcentralkitchen.foundation.config;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.simibubi.create.foundation.config.ConfigBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class CentralKitchenConfigBase extends ConfigBase {
    private final List<ConfigReloadListener> reloadListeners = new ArrayList<>();
    
    @Override
    public void registerAll(ForgeConfigSpec.Builder builder) {
        super.registerAll(builder);
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        reloadListeners.forEach(ConfigReloadListener::onLoad);
    }
    
    @Override
    public void onReload() {
        super.onReload();
        reloadListeners.forEach(ConfigReloadListener::onReload);
    }
    
    protected static boolean isValidResourceLocation(Object value) {
        return value instanceof String string && ResourceLocation.isValidResourceLocation(string);
    }
    
    protected static <V> List<RegistryObject<V>> createRegistryObjects(
        IForgeRegistry<V> registry,
        List<ResourceLocation> idList)
    {
        return idList.stream().map(id -> RegistryObject.create(id, registry)).toList();
    }
    
    protected ConfigList<Integer> intList(Supplier<List<? extends Integer>> supplier, String name, String... comments) {
        return new ConfigList<>(name, supplier, value -> value instanceof Integer, comments);
    }
    
    protected ConfigList<Integer> intList(List<Integer> def, String name, String... comments) {
        return new ConfigList<>(name, def, value -> value instanceof Integer, comments);
    }
    
    protected ConfigList<Double> doubleList(Supplier<List<? extends Double>> supplier, String name, String... comments) {
        return new ConfigList<>(name, supplier, value -> value instanceof Double, comments);
    }
    
    protected ConfigList<Double> doubleList(List<Double> def, String name, String... comments) {
        return new ConfigList<>(name, def, value -> value instanceof Double, comments);
    }
    
    protected <E extends Enum<E>> ConfigList<Enum<E>> enumList(Class<E> clazz, List<E> current, String name, String... comments) {
        return new ConfigList<>(name, current, clazz::isInstance, comments);
    }
    
    protected ConfigList<String> stringList(Supplier<List<? extends String>> supplier, String name, Predicate<Object> validator, String... comments) {
        return new ConfigList<>(name, supplier, validator, comments);
    }
    
    protected ConfigList<String> stringList(Supplier<List<? extends String>> supplier, String name, String... comments) {
        return new ConfigList<>(name, supplier, value -> value instanceof String, comments);
    }
    
    protected ConfigList<String> stringList(List<String> current, String name, Predicate<Object> validator, String... comments) {
        return new ConfigList<>(name, current, validator, comments);
    }
    
    protected ConfigList<String> stringList(List<String> current, String name, String... comments) {
        return new ConfigList<>(name, current, value -> value instanceof String, comments);
    }
    
    protected ConfigList<String> stringListAllowDefaultsOnly(List<String> current, String name, String... comments) {
        return new ConfigList<>(name, current,
            value -> value instanceof String string && current.contains(string), comments);
    }
    
    protected ConfigIdList idList(Supplier<List<ResourceLocation>> supplier, Predicate<Object> validator, String name, String... comments) {
        return new ConfigIdList(name, supplier, validator, comments);
    }
    
    protected ConfigIdList idList(Supplier<List<ResourceLocation>> supplier, String name, String... comments) {
        return new ConfigIdList(name, supplier, comments);
    }
    
    protected ConfigIdList idList(List<ResourceLocation> current, String name, Predicate<Object> validator, String... comments) {
        return new ConfigIdList(name, current, validator, comments);
    }
    
    protected ConfigIdList idList(List<ResourceLocation> current, String name, String... comments) {
        return new ConfigIdList(name, current, comments);
    }
    
    protected ConfigIdList idListAllowDefaultsOnly(List<ResourceLocation> current, String name, String... comments) {
        return new ConfigIdList(name, current,
            value -> isValidResourceLocation(value) && current.contains(new ResourceLocation(value.toString())),
            comments);
    }
    
    protected interface ConfigReloadListener {
        
        default void onLoad() {
            this.onReload();
        }
        
        void onReload();
        
    }
    
    public class ConfigList<T> extends CValue<List<? extends T>, ForgeConfigSpec.ConfigValue<List<? extends T>>> {
    
        public ConfigList(String name, Supplier<List<? extends T>> supplier, Predicate<Object> validator, String... comments) {
            super(name, builder -> builder.defineListAllowEmpty(split(name), supplier, validator), comments);
        }
    
        public ConfigList(String name, List<? extends T> def, Predicate<Object> validator, String... comments) {
            super(name, builder -> builder.defineListAllowEmpty(split(name), () -> def, validator), comments);
        }
        
    }
    
    public class ConfigIdList extends ConfigList<String> implements ConfigReloadListener {
        private List<ResourceLocation> idList;
    
        public ConfigIdList(String name, Supplier<List<ResourceLocation>> supplier, Predicate<Object> validator, String... comments) {
            super(name, () -> supplier.get().stream().map(Object::toString).toList(),
                validator, comments);
            this.idList = List.of();//Leave it empty here as the Supplier should stay lazy
            CentralKitchenConfigBase.this.reloadListeners.add(this);
        }
        
        public ConfigIdList(String name, Supplier<List<ResourceLocation>> supplier, String... comments) {
            this(name, supplier, CentralKitchenConfigBase::isValidResourceLocation, comments);
        }
    
        public ConfigIdList(String name, List<ResourceLocation> def, Predicate<Object> validator, String... comments) {
            super(name, def.stream().map(Object::toString).toList(),
                CentralKitchenConfigBase::isValidResourceLocation, comments);
            this.idList = def;
            CentralKitchenConfigBase.this.reloadListeners.add(this);
        }
    
        public ConfigIdList(String name, List<ResourceLocation> def, String... comments) {
            this(name, ImmutableList.copyOf(def), CentralKitchenConfigBase::isValidResourceLocation, comments);
        }
        
        private void updateIdList(List<? extends String> list) {
            this.idList = list.stream()
                .filter(CentralKitchenConfigBase::isValidResourceLocation)
                .map(ResourceLocation::new)
                .toList();
        }
    
        /**
         * @return An unmodifiable view of the current id list representing the current value
         */
        public @Unmodifiable List<ResourceLocation> getIdList() {
            return idList;
        }
    
        /**
         * @return A modifiable copy of the current id list,
         * should be used along with {@link #setIdList(List)} to modify values
         */
        public List<ResourceLocation> copyIdList() {
            return new ArrayList<>(idList);
        }
    
        /**
         * @param idList the id list to assign to the config
         */
        public void setIdList(List<ResourceLocation> idList) {
            this.idList = ImmutableList.copyOf(idList);
            this.set(this.idList.stream().map(Objects::toString).toList());
        }
    
        @Override
        public void set(List<? extends String> value) {
            super.set(value);
            this.updateIdList(value);
        }
    
        @Override
        public void onReload() {
            this.updateIdList(this.get());
        }
        
    }
    
    public class ConfigRegistryObjectList<V> extends ConfigList<String> implements ConfigReloadListener {
        private static final Map<IForgeRegistry<?>, Predicate<Object>> VALIDATORS = new ConcurrentHashMap<>();
        private final IForgeRegistry<V> registry;
        private List<RegistryObject<V>> objects;
        
        public ConfigRegistryObjectList(String name, IForgeRegistry<V> registry, List<RegistryObject<V>> def, Predicate<Object> validator, String... comments) {
            super(name, def.stream().map(entry -> entry.getId().toString()).toList(), validator, comments);
            this.registry = registry;
            this.setObjects(def);
            CentralKitchenConfigBase.this.reloadListeners.add(this);
        }
        
        public ConfigRegistryObjectList(String name, IForgeRegistry<V> registry, List<RegistryObject<V>> def, String... comments) {
            this(name, registry, def, validator(registry), comments);
        }
    
        private static Predicate<Object> validator(IForgeRegistry<?> registry) {
            return VALIDATORS.computeIfAbsent(registry, reg -> value -> {
                if (!(value instanceof String string))
                    return false;
                if (!ResourceLocation.isValidResourceLocation(string))
                    return false;
                if (reg instanceof IForgeRegistryModifiable<?> && ((IForgeRegistryModifiable<?>)reg).isLocked()) {
                    ResourceLocation id = new ResourceLocation(string);
                    return reg.containsKey(id);
                }
                return true;
            });
        }
        
        private void updateObjects(List<? extends String> list) {
            this.objects = createRegistryObjects(registry, list.stream()
                .filter(CentralKitchenConfigBase::isValidResourceLocation)
                .map(ResourceLocation::new)
                .toList());
        }
        
        public boolean contains(V value) {
            for (var object : this.objects) {
                if (object.isPresent() && object.get().equals(value))
                    return true;
            }
            return false;
        }
        
        public List<RegistryObject<V>> getObjects(boolean existing) {
            return existing
                ? this.objects.stream().filter(RegistryObject::isPresent).toList()
                : this.objects;
        }
        
        public void setObjects(List<RegistryObject<V>> objects) {
            this.objects = ImmutableList.copyOf(objects);
        }
    
        @Override
        public void set(List<? extends String> value) {
            super.set(value);
            this.updateObjects(value);
        }
    
        @Override
        public void onLoad() {}
    
        @Override
        public void onReload() {
            this.updateObjects(this.get());
        }
        
    }
    
    private static final Splitter DOT_SPLITTER = Splitter.on(".");
    private static List<String> split(String path) {
        return Lists.newArrayList(DOT_SPLITTER.split(path));
    }
    
}
