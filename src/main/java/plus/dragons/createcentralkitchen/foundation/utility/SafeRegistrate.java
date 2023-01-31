package plus.dragons.createcentralkitchen.foundation.utility;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraftforge.eventbus.api.IEventBus;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SafeRegistrate extends CreateRegistrate {
    private boolean registered = false;
    
    public SafeRegistrate(String modid) {
        super(modid);
    }
    
    /**
     * Deprecate {@link CreateRegistrate#lazy(String)} as it'll register to the wrong mod bus when referenced by other mod. <br>
     * Use this to manually register the registrate at a correct timing.
     */
    @Override
    public CreateRegistrate registerEventListeners(IEventBus modEventBus) {
        if (!registered) {
            registered = true;
            return super.registerEventListeners(modEventBus);
        }
        throw new IllegalStateException("CckRegistrate for Mod [" + getModid() + "] is already registered");
    }
    
}