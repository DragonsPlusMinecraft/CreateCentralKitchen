package plus.dragons.createcentralkitchen;

import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class CentralKitchenMixinPlugin implements IMixinConfigPlugin {
    private Object2BooleanOpenHashMap<String> dependencies = new Object2BooleanOpenHashMap<>();
    
    @Override
    public void onLoad(String mixinPackage) {
        String[] modids = {
            "create",
            "farmersdelight",
            "farmersrespite",
            "jei"
        };
        LoadingModList mods = LoadingModList.get();
        for (String modid : modids) {
            dependencies.put(modid, mods.getModFileById(modid) != null);
        }
    }
    
    @Override
    public String getRefMapperConfig() {
        return null;
    }
    
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String[] splitMixinClassName = mixinClassName.split("\\.");
        String modid = splitMixinClassName[splitMixinClassName.length - 2];
        return dependencies.getOrDefault(modid, true);
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
