package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import plus.dragons.createcentralkitchen.CentralKitchen;

import java.util.function.Function;

public class CckTagsProvider<T> {

    private final RegistrateTagsProvider<T> provider;
    private final Function<T, ResourceKey<T>> keyExtractor;

    public CckTagsProvider(RegistrateTagsProvider<T> provider, Function<T, Holder.Reference<T>> refExtractor) {
        this.provider = provider;
        this.keyExtractor = refExtractor.andThen(Holder.Reference::key);
    }

    public TagGen.CreateTagAppender<T> tag(TagKey<T> tag) {
        TagBuilder tagbuilder = getOrCreateRawBuilder(tag);
        return new TagGen.CreateTagAppender<>(tagbuilder, keyExtractor, CentralKitchen.ID);
    }

    public TagBuilder getOrCreateRawBuilder(TagKey<T> tag) {
        return provider.addTag(tag).getInternalBuilder();
    }

}
