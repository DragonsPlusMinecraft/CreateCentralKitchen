package plus.dragons.createcentralkitchen.entry.item;

import com.teamabnormals.upgrade_aquatic.core.registry.UABlocks;
import com.teamabnormals.upgrade_aquatic.core.registry.UAItems;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import plus.dragons.createcentralkitchen.entry.fluid.UAFluidEntries;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.DatapackRecipes;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.item.SolidBlockFluidBucketItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.UA)
public class UAItemEntries {
    
    public static final ItemEntry<SolidBlockFluidBucketItem>
        MULBERRY_JAM_BUCKET = REGISTRATE.item("mulberry_jam_bucket", prop -> new SolidBlockFluidBucketItem(
            () -> UAFluidEntries.MULBERRY_JAM.get().getSource(),
            UABlocks.MULBERRY_JAM_BLOCK.get(),
            SoundEvents.SLIME_BLOCK_PLACE, prop))
        .properties(prop -> prop.stacksTo(1).craftRemainder(Items.BUCKET).tab(CreativeModeTab.TAB_MATERIALS))
        .transform(DatapackRecipes.addRecipe(Mods.UA, (ctx, prov) -> {
            DataIngredient jamBlock = DataIngredient.items(UABlocks.MULBERRY_JAM_BLOCK.get());
            DataIngredient jamBottle = DataIngredient.items(UAItems.MULBERRY_JAM_BOTTLE.get());
            DataIngredient jamBucket = DataIngredient.items(ctx);
            DataIngredient bottle = DataIngredient.items(Items.GLASS_BOTTLE);
            DataIngredient bucket = DataIngredient.items(Items.BUCKET);
            prov.add(Recipes.filling("mulberry_jam_bucket")
                .output(ctx.getEntry())
                .require(Items.BUCKET)
                .require(UAFluidEntries.MULBERRY_JAM.get(), 1000));
            prov.add(Recipes.shapeless("mulberry_jam_bucket_from_block")
                .output(ctx.getEntry())
                .requiredToUnlock(prov, bucket)
                .requiredToUnlock(prov, jamBlock));
            prov.add(Recipes.shapeless("mulberry_jam_block_from_bucket")
                .output(UABlocks.MULBERRY_JAM_BLOCK.get())
                .requiredToUnlock(prov, jamBucket));
            prov.add(Recipes.shapeless("mulberry_jam_bucket")
                .output(ctx.getEntry())
                .requiredToUnlock(prov, bucket)
                .requiredToUnlock(prov, jamBottle, 4));
            prov.add(Recipes.shapeless("mulberry_jam_bottles_from_bucket")
                .output(UAItems.MULBERRY_JAM_BOTTLE.get(), 4)
                .requiredToUnlock(prov, jamBucket)
                .requiredToUnlock(prov, bottle, 4));
        }))
        .register();
    
}
