package plus.dragons.createcentralkitchen.entry.item;

import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.atmospheric.core.registry.AtmosphericItems;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import plus.dragons.createcentralkitchen.entry.fluid.AtmosphericFluidEntries;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.DatapackRecipes;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.item.SolidBlockFluidBucketItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.ATMOSPHERIC)
public class AtmosphericItemEntries {
    
    public static final ItemEntry<SolidBlockFluidBucketItem>
        ALOE_GEL_BUCKET = REGISTRATE.item("aloe_gel_bucket", prop -> new SolidBlockFluidBucketItem(
            () -> AtmosphericFluidEntries.ALOE_GEL.get().getSource(),
            AtmosphericBlocks.ALOE_GEL_BLOCK.get(),
            SoundEvents.SLIME_BLOCK_PLACE, prop))
        .properties(prop -> prop.stacksTo(1).craftRemainder(Items.BUCKET).tab(CreativeModeTab.TAB_MATERIALS))
        .transform(DatapackRecipes.addRecipe(Mods.ATMOSPHERIC, (ctx, prov) -> {
            DataIngredient gelBlock = DataIngredient.items(AtmosphericBlocks.ALOE_GEL_BLOCK.get());
            DataIngredient gelBottle = DataIngredient.items(AtmosphericItems.ALOE_GEL_BOTTLE.get());
            DataIngredient gelBucket = DataIngredient.items(ctx);
            DataIngredient bottle = DataIngredient.items(Items.GLASS_BOTTLE);
            DataIngredient bucket = DataIngredient.items(Items.BUCKET);
            prov.add(Recipes.filling("aloe_gel_bucket")
                .output(ctx.getEntry())
                .require(Items.BUCKET)
                .require(AtmosphericFluidEntries.ALOE_GEL.get(), 1000));
            prov.add(Recipes.shapeless("aloe_gel_bucket_from_block")
                .output(ctx.getEntry())
                .requiredToUnlock(prov, bucket)
                .requiredToUnlock(prov, gelBlock));
            prov.add(Recipes.shapeless("aloe_gel_block_from_bucket")
                .output(AtmosphericBlocks.ALOE_GEL_BLOCK.get())
                .requiredToUnlock(prov, gelBucket));
            prov.add(Recipes.shapeless("aloe_gel_bucket")
                .output(ctx.getEntry())
                .requiredToUnlock(prov, bucket)
                .requiredToUnlock(prov, gelBottle, 4));
            prov.add(Recipes.shapeless("aloe_gel_bottles_from_bucket")
                .output(AtmosphericItems.ALOE_GEL_BOTTLE.get(), 4)
                .requiredToUnlock(prov, gelBucket)
                .requiredToUnlock(prov, bottle, 4));
        }))
        .register();
    
}
