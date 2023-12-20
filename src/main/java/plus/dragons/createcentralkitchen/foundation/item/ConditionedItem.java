package plus.dragons.createcentralkitchen.foundation.item;

import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import java.util.List;
import java.util.function.Supplier;
// TODO
public class ConditionedItem extends Item {
    private final Supplier<Boolean> condition;
    
    public ConditionedItem(Properties properties, Supplier<Boolean> condition) {
        super(properties);
        this.condition = condition;
    }
    
    public ConditionedItem(Properties properties, String modId) {
        this(properties, Lazy.of(() -> Mods.isLoaded(modId)));
    }
    
    public ConditionedItem(Properties properties, String... modIds) {
        this(properties, Lazy.of(() -> Mods.isLoaded(modIds)));
    }
    
    public ConditionedItem(Properties properties, ConfigBase.ConfigBool config) {
        this(properties, config::get);
    }
    
    public ConditionedItem(Properties properties, ConfigBase.ConfigBool config, String modId) {
        this(properties, Mods.isLoaded(modId) ? config::get : () -> false);
    }
    
    public boolean enabled() {
        return condition.get();
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag advanced) {
        super.appendHoverText(stack, level, tooltip, advanced);
        if (!enabled()) {
            tooltip.add(Components.translatable("create_central_kitchen.generic.unobtainable").withStyle(ChatFormatting.GRAY));
        }
    }
    
    @Override
    protected boolean allowedIn(CreativeModeTab category) {
        return enabled() && super.allowedIn(category);
    }
    
}
