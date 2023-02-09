package plus.dragons.createcentralkitchen.data.tag;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class OptionalTags {
    
    @SafeVarargs
    public static <T extends Item, R extends AbstractRegistrate<R>>
    NonNullFunction<ItemBuilder<T, R>, ItemBuilder<T, R>> item(TagKey<Item>... tags) {
        return builder -> builder.addMiscData(ProviderType.ITEM_TAGS, prov -> {
            ResourceLocation name = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            for (var tag : tags)
                prov.tag(tag).addOptional(name);
        });
    }
    
    public static <T extends Item, R extends AbstractRegistrate<R>>
    NonNullFunction<ItemBuilder<T, R>, ItemBuilder<T, R>> item(ResourceLocation... ids) {
        return builder -> builder.addMiscData(ProviderType.ITEM_TAGS, prov -> {
            ResourceLocation name = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            for (var id : ids) {
                var tag = TagKey.create(Registry.ITEM_REGISTRY, id);
                prov.tag(tag).addOptional(name);
            }
        });
    }
    
    @SafeVarargs
    public static <T extends Block, R extends AbstractRegistrate<R>>
    NonNullFunction<BlockBuilder<T, R>, BlockBuilder<T, R>> block(TagKey<Block>... tags) {
        return builder -> builder.addMiscData(ProviderType.BLOCK_TAGS, prov -> {
            ResourceLocation name = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            for (var tag : tags)
                prov.tag(tag).addOptional(name);
        });
    }
    
    public static <T extends Block, R extends AbstractRegistrate<R>>
    NonNullFunction<BlockBuilder<T, R>, BlockBuilder<T, R>> block(ResourceLocation... ids) {
        return builder -> builder.addMiscData(ProviderType.BLOCK_TAGS, prov -> {
            ResourceLocation name = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            for (var id : ids) {
                var tag = TagKey.create(Registry.BLOCK_REGISTRY, id);
                prov.tag(tag).addOptional(name);
            }
        });
    }
    
    @SafeVarargs
    public static <T extends ForgeFlowingFluid, R extends AbstractRegistrate<R>>
    NonNullFunction<FluidBuilder<T, R>, FluidBuilder<T, R>> fluid(TagKey<Fluid>... tags) {
        return builder -> builder.addMiscData(ProviderType.FLUID_TAGS, prov -> {
            String modid = builder.getOwner().getModid();
            String flowingName = builder.getName();
            String sourceName = flowingName.substring(8);
            ResourceLocation flowing = new ResourceLocation(modid, flowingName);
            ResourceLocation source = new ResourceLocation(modid, sourceName);
            for (var tag : tags) {
                prov.tag(tag).addOptional(flowing);
                prov.tag(tag).addOptional(source);
            }
        });
    }
    
    public static <T extends ForgeFlowingFluid, R extends AbstractRegistrate<R>>
    NonNullFunction<FluidBuilder<T, R>, FluidBuilder<T, R>> fluid(ResourceLocation... ids) {
        return builder -> builder.addMiscData(ProviderType.FLUID_TAGS, prov -> {
            String modid = builder.getOwner().getModid();
            String flowingName = builder.getName();
            String sourceName = flowingName.substring(8);
            ResourceLocation flowing = new ResourceLocation(modid, flowingName);
            ResourceLocation source = new ResourceLocation(modid, sourceName);
            for (var id : ids) {
                var tag = TagKey.create(Registry.FLUID_REGISTRY, id);
                prov.tag(tag).addOptional(flowing);
                prov.tag(tag).addOptional(source);
            }
        });
    }
    
}
