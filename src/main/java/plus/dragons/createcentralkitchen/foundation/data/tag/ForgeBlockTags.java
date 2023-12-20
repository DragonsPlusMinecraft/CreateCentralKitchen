package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public enum ForgeBlockTags {
    STORAGE_BLOCKS__CHOCOLATE,
    SLABS__CHOCOLATE,
    STAIRS__CHOCOLATE,
    WALLS__CHOCOLATE;
    
    public final TagKey<Block> tag;
    
    ForgeBlockTags() {
        String path = name().toLowerCase(Locale.ROOT).replace("__", "/");
        this.tag = create(path);
    }
    
    public static TagKey<Block> create(String name) {
        return TagKey.create(ForgeRegistries.Keys.BLOCKS, new ResourceLocation("forge", name));
    }
    
    public static void datagen(RegistrateTagsProvider<Block> provIn) {
        TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);
        prov.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(STORAGE_BLOCKS__CHOCOLATE.tag);
        prov.tag(BlockTags.SLABS).addTag(SLABS__CHOCOLATE.tag);
        prov.tag(BlockTags.STAIRS).addTag(STAIRS__CHOCOLATE.tag);
        prov.tag(BlockTags.WALLS).addTag(WALLS__CHOCOLATE.tag);
    }

    //FIXME
    /*
    Caused by: java.util.concurrent.CompletionException: java.lang.IllegalArgumentException: Couldn't define tag minecraft:slabs as it is missing following references: #forge:slabs/chocolate
	at java.base/java.util.concurrent.CompletableFuture.encodeThrowable(CompletableFuture.java:315)
	at java.base/java.util.concurrent.CompletableFuture.completeThrowable(CompletableFuture.java:320)
	at java.base/java.util.concurrent.CompletableFuture$UniCompose.tryFire(CompletableFuture.java:1159)
	at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:510)
	at java.base/java.util.concurrent.CompletableFuture.postFire(CompletableFuture.java:614)
	at java.base/java.util.concurrent.CompletableFuture.postFire(CompletableFuture.java:1261)
	at java.base/java.util.concurrent.CompletableFuture$BiApply.tryFire(CompletableFuture.java:1283)
	at java.base/java.util.concurrent.CompletableFuture$Completion.exec(CompletableFuture.java:483)
	at java.base/java.util.concurrent.ForkJoinTask.doExec$$$capture(ForkJoinTask.java:373)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1182)
	at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1655)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1622)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:165)
Caused by: java.lang.IllegalArgumentException: Couldn't define tag minecraft:slabs as it is missing following references: #forge:slabs/chocolate
	at TRANSFORMER/minecraft@1.20.1/net.minecraft.data.tags.TagsProvider.lambda$run$6(TagsProvider.java:116)
	at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
	at java.base/java.util.Iterator.forEachRemaining(Iterator.java:133)
	at java.base/java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1845)
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
	at java.base/java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
	at java.base/java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
	at TRANSFORMER/minecraft@1.20.1/net.minecraft.data.tags.TagsProvider.lambda$run$8(TagsProvider.java:123)
	at java.base/java.util.concurrent.CompletableFuture$UniCompose.tryFire(CompletableFuture.java:1150)
     */
    
}
