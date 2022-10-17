package plus.dragons.createfarmersautomation.foundation.utility;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import plus.dragons.createfarmersautomation.FarmersAutomation;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SafeRegistrate extends CreateRegistrate {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FarmersAutomation.ID);
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, FarmersAutomation.ID);
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
            SafeRegistrate.SERIALIZER_REGISTER.register(modEventBus);
            SafeRegistrate.TYPE_REGISTER.register(modEventBus);
            return super.registerEventListeners(modEventBus);
        }
        throw new IllegalStateException("CfaRegistrate for Mod [" + getModid() + "] is already registered");
    }
    
}
