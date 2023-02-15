package plus.dragons.createcentralkitchen.core.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderStoryBoardEntry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PonderRegistryObject {
    private final DeferredPonderRegister owner;
    private final ResourceLocation schematic;
    private final PonderStoryBoardEntry.PonderStoryBoard storyBoard;
    private final List<ResourceLocation> components = new ArrayList<>();
    private final List<PonderTag> tags = new ArrayList<>();
    
    public PonderRegistryObject(DeferredPonderRegister owner,
                                ResourceLocation schematic,
                                PonderStoryBoardEntry.PonderStoryBoard storyBoard) {
        this.owner = owner;
        this.schematic = schematic;
        this.storyBoard = storyBoard;
    }
    
    public void register() {
        for (var component : components) {
            var entry = new PonderStoryBoardEntry(storyBoard, owner.namespace, schematic, component);
            entry.highlightTags(tags.toArray(PonderTag[]::new));
            PonderRegistry.addStoryBoard(entry);
        }
    }
    
    @SafeVarargs
    public final PonderRegistryObject addComponent(RegistryObject<? extends Item>... components) {
        Validate.isTrue(!owner.registered, "Cannot assign new components to registered PonderRegistryObject");
        Arrays.stream(components).map(RegistryObject::getId).forEach(this.components::add);
        return this;
    }
    
    public PonderRegistryObject addComponent(ItemProviderEntry<?>... components) {
        Validate.isTrue(!owner.registered, "Cannot assign new components to registered PonderRegistryObject");
        Arrays.stream(components).map(RegistryEntry::getId).forEach(this.components::add);
        return this;
    }
    
    public PonderRegistryObject addTag(PonderTag... tags) {
        Validate.isTrue(!owner.registered, "Cannot assign new tags to registered PonderRegistryObject");
        Collections.addAll(this.tags, tags);
        return this;
    }
    
}
