package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class OptionalTags {
    
    @SafeVarargs
    public static <T extends Item, R extends AbstractRegistrate<R>> NonNullUnaryOperator<ItemBuilder<T, R>> item(TagKey<Item>... tags) {
        return builder -> builder.addMiscData(ProviderType.ITEM_TAGS, prov -> {
            ResourceLocation name = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            for (var tag : tags)
                prov.addTag(tag).addOptional(name);
        });
    }
    
    public static <T extends Item, R extends AbstractRegistrate<R>> NonNullUnaryOperator<ItemBuilder<T, R>> item(ResourceLocation... ids) {
        return builder -> builder.addMiscData(ProviderType.ITEM_TAGS, prov -> {
            ResourceLocation name = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            for (var id : ids) {
                var tag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), id);
                prov.addTag(tag).addOptional(name);
            }
        });
    }
    
    @SafeVarargs
    public static <T extends Block, R extends AbstractRegistrate<R>> NonNullUnaryOperator<BlockBuilder<T, R>> block(TagKey<Block>... tags) {
        return builder -> builder.addMiscData(ProviderType.BLOCK_TAGS, prov -> {
            ResourceLocation name = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            for (var tag : tags)
                prov.addTag(tag).addOptional(name);
        });
    }
    
    public static <T extends Block, R extends AbstractRegistrate<R>> NonNullUnaryOperator<BlockBuilder<T, R>> block(ResourceLocation... ids) {
        return builder -> builder.addMiscData(ProviderType.BLOCK_TAGS, prov -> {
            ResourceLocation name = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            for (var id : ids) {
                var tag = TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), id);
                prov.addTag(tag).addOptional(name);
            }
        });
    }
    
    @SafeVarargs
    public static <T extends ForgeFlowingFluid, R extends AbstractRegistrate<R>> NonNullUnaryOperator<FluidBuilder<T, R>> fluid(TagKey<Fluid>... tags) {
        return builder -> builder.removeTag(tags).addMiscData(ProviderType.FLUID_TAGS, prov -> {
            FluidEntry<?> entry = (FluidEntry<?>) builder.getOwner().get(builder.getName(), ForgeRegistries.Keys.FLUIDS);
            ResourceLocation flowing = entry.getId();
            ResourceLocation source = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(entry.get().getSource()));
            for (var tag : tags)
                prov.addTag(tag).addOptional(flowing).addOptional(source);
        });
    }
    
    public static <T extends ForgeFlowingFluid, R extends AbstractRegistrate<R>> NonNullUnaryOperator<FluidBuilder<T, R>> fluid(ResourceLocation... ids) {
        return builder -> builder.tag().addMiscData(ProviderType.FLUID_TAGS, prov -> {
            FluidEntry<?> entry = (FluidEntry<?>) builder.getOwner().get(builder.getName(), ForgeRegistries.Keys.FLUIDS);
            ResourceLocation flowing = entry.getId();
            ResourceLocation source = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(entry.get().getSource()));
            for (var id : ids) {
                var tag = TagKey.create(ForgeRegistries.FLUIDS.getRegistryKey(), id);
                prov.addTag(tag).addOptional(flowing).addOptional(source);
            }
        });
    }
    
}
