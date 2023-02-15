package plus.dragons.createcentralkitchen.core.ponder;

import com.simibubi.create.foundation.ponder.PonderStoryBoardEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

public class DeferredPonderRegister {
    boolean registered = false;
    final String namespace;
    private final List<PonderRegistryObject> objects = new ArrayList<>();
    
    public static DeferredPonderRegister create(String namespace) {
        return new DeferredPonderRegister(namespace);
    }
    
    private DeferredPonderRegister(String namespace) {
        this.namespace = namespace;
    }
    
    public PonderRegistryObject create(String schematic,
                                       PonderStoryBoardEntry.PonderStoryBoard storyBoard) {
        Validate.isTrue(!registered, "Cannot create new PonderRegistryObject with a registered DeferredPonderRegister");
        var object = new PonderRegistryObject(this, new ResourceLocation(namespace, schematic), storyBoard);
        objects.add(object);
        return object;
    }
    
    public void register(IEventBus bus) {
        bus.register(new EventDispatcher(this));
    }
    
    public static class EventDispatcher {
        private final DeferredPonderRegister register;
        
        public EventDispatcher(final DeferredPonderRegister register) {
            this.register = register;
        }
        
        //Set it to EventPriority.LOWEST so PonderRegistryObjects can be initialized before registered
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public void handleEvent(final FMLClientSetupEvent event) {
            event.enqueueWork(() -> register.objects.forEach(PonderRegistryObject::register));
        }
        
        //Set it to EventPriority.HIGHEST so PonderRegistryObjects can be registered before PonderLocalization runs
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public void handleEvent(final GatherDataEvent event) {
            if (event.includeClient() && event.getModContainer().getModId().equals(register.namespace)) {
                register.objects.forEach(PonderRegistryObject::register);
            }
        }
        
    }
    
}
