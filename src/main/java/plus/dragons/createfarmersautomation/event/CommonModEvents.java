package plus.dragons.createfarmersautomation.event;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import plus.dragons.createfarmersautomation.FarmersAutomation;
import plus.dragons.createfarmersautomation.entry.CfaFluids;

public class CommonModEvents {
    
    @Mod.EventBusSubscriber(modid = FarmersAutomation.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class FarmersDelightHandler {
        
        @SubscribeEvent
        public static void registerTomatoSauceFluid(RegistryEvent.Register<Fluid> event) {
            for (var entry : CfaFluids.values()) {
                entry.register(event);
            }
        }
        
    }
    
}