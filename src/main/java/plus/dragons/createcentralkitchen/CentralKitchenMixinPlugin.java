package plus.dragons.createcentralkitchen;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CentralKitchenMixinPlugin implements IMixinConfigPlugin {
    private final Set<String> dependencies = new HashSet<>();
    private final BiMap<String, String> modPackage = HashBiMap.create();
    
    private boolean isTargetClassLoaded(String targetClassName) {
        for (var entry : modPackage.entrySet()) {
            //Use ".<packageName>." here to match, so we can be sure that it belongs to that mod
            if (targetClassName.contains("." + entry.getValue() + "."))
                return dependencies.contains(entry.getKey());
        }
        return true;
    }
    
    @Override
    public void onLoad(String mixinPackage) {
        String[] modids = {
            "create",
            "farmersdelight",
            "farmersrespite",
            "miners_delight",
            "brewinandchewin",
            "neapolitan",
            "jei"
        };
        LoadingModList mods = LoadingModList.get();
        for (String modid : modids) {
            modPackage.put(modid, modid);
            if (mods.getModFileById(modid) != null)
                dependencies.add(modid);
        }
        modPackage.put("miners_delight", "minersdelight");
    }
    
    @Override
    public String getRefMapperConfig() {
        return null;
    }
    
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String[] splitMixinClassName = mixinClassName.split("\\.");
        String mixinPackage = splitMixinClassName[splitMixinClassName.length - 2];
        String modid = modPackage.inverse().getOrDefault(mixinPackage, mixinPackage);
        return dependencies.contains(modid) && isTargetClassLoaded(targetClassName);
    }
    
    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
    
    @Override
    public List<String> getMixins() {
        return null;
    }
    
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    
    }
    
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    
    }
    
}
