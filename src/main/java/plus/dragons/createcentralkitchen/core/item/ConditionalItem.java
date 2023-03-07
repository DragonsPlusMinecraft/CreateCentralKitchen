package plus.dragons.createcentralkitchen.core.item;

import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ConditionalItem extends Item {
    private final Supplier<Boolean> condition;
    
    public ConditionalItem(Properties properties, Supplier<Boolean> condition) {
        super(properties);
        this.condition = condition;
    }
    
    public static ConditionalItem requireMod(Properties properties, String mod) {
        boolean enabled = ModList.get().isLoaded(mod);
        return new ConditionalItem(properties, () -> enabled);
    }
    
    public static ConditionalItem requireMods(Properties properties, String... mods) {
        ModList modList = ModList.get();
        for (var mod : mods) {
            if (!modList.isLoaded(mod)) {
                return new ConditionalItem(properties, () -> false);
            }
        }
        return new ConditionalItem(properties, () -> true);
    }
    
    public static ConditionalItem configured(Properties properties, ConfigBase.ConfigBool config) {
        return new ConditionalItem(properties, config::get);
    }
    
    public static ConditionalItem configured(Properties properties, ConfigBase.ConfigBool... configs) {
        return new ConditionalItem(properties, () -> {
            for (var config : configs) {
                if (!config.get())
                    return false;
            }
            return true;
        });
    }
    
    public static ConditionalItem configuredAndRequireMod(Properties properties, ConfigBase.ConfigBool config, String mod) {
        boolean enabled = ModList.get().isLoaded(mod);
        return new ConditionalItem(properties, enabled ? config::get : () -> false);
    }
    
    public static ConditionalItem configuredAndRequireMods(Properties properties, ConfigBase.ConfigBool config, String... mods) {
        ModList modList = ModList.get();
        for (var mod : mods) {
            if (!modList.isLoaded(mod)) {
                return new ConditionalItem(properties, () -> false);
            }
        }
        return new ConditionalItem(properties, config::get);
    }
    
    public boolean enabled() {
        return condition.get();
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag advanced) {
        super.appendHoverText(stack, level, tooltip, advanced);
        if (!enabled()) {
            tooltip.add(Components.translatable("create_central_kitchen.generic.unobtainable"));
        }
    }
    
    @Override
    protected boolean allowdedIn(CreativeModeTab tab) {
        return enabled() && super.allowdedIn(tab);
    }
    
}
