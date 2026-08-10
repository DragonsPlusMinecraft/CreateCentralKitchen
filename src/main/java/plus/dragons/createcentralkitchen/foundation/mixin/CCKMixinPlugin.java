package plus.dragons.createcentralkitchen.foundation.mixin;

import java.util.List;
import java.util.Set;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

public class CCKMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("farmersdelight"))
            return LoadingModList.get().getModFileById(Mods.FD) != null;
        if (mixinClassName.contains("farmersrespite"))
            return LoadingModList.get().getModFileById(Mods.FR) != null;
        if (mixinClassName.contains("minersdelight"))
            return LoadingModList.get().getModFileById(Mods.MD) != null;
        if (mixinClassName.contains("overweightfarming"))
            return LoadingModList.get().getModFileById(Mods.OF) != null;
        if (mixinClassName.contains("neapolitan"))
            return LoadingModList.get().getModFileById(Mods.NEAPOLITAN) != null;

        if (mixinClassName.contains("sleeptight"))
            return LoadingModList.get().getModFileById("sleep_tight") != null;
        if (mixinClassName.contains("arsbotania"))
            return LoadingModList.get().getModFileById("ars_botania") != null;
        if (mixinClassName.contains("botarium"))
            return LoadingModList.get().getModFileById("botarium") != null;
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
