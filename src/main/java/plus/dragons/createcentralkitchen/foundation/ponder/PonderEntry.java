package plus.dragons.createcentralkitchen.foundation.ponder;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderStoryBoardEntry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PonderEntry {
    private final ResourceLocation schematic;
    private final PonderStoryBoardEntry.PonderStoryBoard storyBoard;
    private final List<ResourceLocation> components = new ArrayList<>();
    private final List<PonderTag> tags = new ArrayList<>();
    
    public PonderEntry(ResourceLocation schematic, PonderStoryBoardEntry.PonderStoryBoard storyBoard) {
        this.schematic = schematic;
        this.storyBoard = storyBoard;
    }
    
    public void register() {
        for (var component : components) {
            var entry = new PonderStoryBoardEntry(storyBoard, CentralKitchen.ID, schematic, component);
            entry.highlightTags(tags.toArray(PonderTag[]::new));
            PonderRegistry.addStoryBoard(entry);
        }
    }
    
    @CanIgnoreReturnValue
    public PonderEntry addComponent(ResourceLocation... components) {
        Collections.addAll(this.components, components);
        return this;
    }
    
    @SafeVarargs
    @CanIgnoreReturnValue
    public final PonderEntry addComponent(RegistryObject<? extends Item>... components) {
        Arrays.stream(components).map(RegistryObject::getId).forEach(this.components::add);
        return this;
    }
    
    @CanIgnoreReturnValue
    public PonderEntry addComponent(ItemProviderEntry<?>... components) {
        Arrays.stream(components).map(RegistryEntry::getId).forEach(this.components::add);
        return this;
    }
    
    @CanIgnoreReturnValue
    public PonderEntry addTag(PonderTag... tags) {
        Collections.addAll(this.tags, tags);
        return this;
    }
    
}
