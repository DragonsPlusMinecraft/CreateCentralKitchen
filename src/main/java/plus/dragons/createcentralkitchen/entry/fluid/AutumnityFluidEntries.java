package plus.dragons.createcentralkitchen.entry.fluid;

import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.DatapackRecipes;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.data.tag.OptionalTags;
import plus.dragons.createcentralkitchen.foundation.fluid.NoColorFluidAttributes;
import plus.dragons.createcentralkitchen.foundation.item.FluidBucketItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.AUTUMNITY)
public class AutumnityFluidEntries {
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SAP = REGISTRATE.fluid("sap",
            CentralKitchen.genRL("fluid/sap_still"),
            CentralKitchen.genRL("fluid/sap_flow"),
            NoColorFluidAttributes::new)
        .lang("Sap")
        .transform(OptionalTags.fluid(FluidTags.WATER))
        .properties(p -> p.levelDecreasePerBlock(2)
            .tickRate(15)
            .slopeFindDistance(3)
            .explosionResistance(100f))
        .source(ForgeFlowingFluid.Source::new) // TODO: remove when Registrate fixes FluidBuilder
        .transform(Recipes.fluidHandling(AutumnityItems.SAP_BOTTLE, 250))
        .bucket(FluidBucketItem::new)
        .properties(prop -> prop.tab(CreativeModeTab.TAB_MATERIALS))
        .transform(DatapackRecipes.addRecipe(Mods.AUTUMNITY, (ctx, prov) -> {
            DataIngredient sapBottle = DataIngredient.items(AutumnityItems.SAP_BOTTLE.get());
            DataIngredient sapBucket = DataIngredient.items(ctx);
            DataIngredient bottle = DataIngredient.items(Items.GLASS_BOTTLE);
            DataIngredient bucket = DataIngredient.items(Items.BUCKET);
            DataIngredient mapleLog = DataIngredient.items(AutumnityBlocks.MAPLE_LOG.get());
            DataIngredient mapleWood = DataIngredient.items(AutumnityBlocks.MAPLE_WOOD.get());
            prov.add(Recipes.shapeless("sap_bucket_from_bottles")
                .output(ctx.getEntry())
                .requiredToUnlock(prov, bucket)
                .requiredToUnlock(prov, sapBottle, 4));
            prov.add(Recipes.shapeless("sap_bottles_from_bucket")
                .output(AutumnityItems.SAP_BOTTLE.get(), 4)
                .requiredToUnlock(prov, sapBucket)
                .requiredToUnlock(prov, bottle, 4));
            prov.add(Recipes.shapeless("sappy_maple_log_from_sap_bucket")
                .output(AutumnityBlocks.SAPPY_MAPLE_LOG.get(), 4)
                .requiredToUnlock(prov, sapBucket)
                .requiredToUnlock(prov, mapleLog, 4));
            prov.add(Recipes.shapeless("sappy_maple_wood_from_sap_bucket")
                .output(AutumnityBlocks.SAPPY_MAPLE_WOOD.get(), 4)
                .requiredToUnlock(prov, sapBucket)
                .requiredToUnlock(prov, mapleWood, 4));
        }))
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SYRUP = REGISTRATE.fluid("syrup",
            CentralKitchen.genRL("fluid/syrup_still"),
            CentralKitchen.genRL("fluid/syrup_flow"),
            NoColorFluidAttributes::new)
        .lang("Syrup")
        .transform(OptionalTags.fluid(FluidTags.WATER))
        .attributes(b -> b.viscosity(2000).density(1400))
        .properties(p -> p.levelDecreasePerBlock(2)
            .tickRate(25)
            .slopeFindDistance(3)
            .explosionResistance(100f))
        .source(ForgeFlowingFluid.Source::new) // TODO: remove when Registrate fixes FluidBuilder
        .transform(Recipes.fluidHandling(AutumnityItems.SYRUP_BOTTLE, 250))
        .bucket(FluidBucketItem::new)
        .properties(prop -> prop.tab(CreativeModeTab.TAB_MATERIALS))
        .transform(DatapackRecipes.addRecipe(Mods.AUTUMNITY, (ctx, prov) -> {
            DataIngredient sapBucket = DataIngredient.items(SAP.get().getBucket());
            DataIngredient syrupBottle = DataIngredient.items(AutumnityItems.SYRUP_BOTTLE.get());
            DataIngredient syrupBucket = DataIngredient.items(ctx);
            DataIngredient bottle = DataIngredient.items(Items.GLASS_BOTTLE);
            DataIngredient bucket = DataIngredient.items(Items.BUCKET);
            prov.campfire(sapBucket, ctx, 0.35F);
            prov.smelting(sapBucket, ctx, 0.35F);
            prov.smoking(sapBucket, ctx, 0.35F);
            prov.add(Recipes.shapeless("syrup_bucket_from_bottles")
                .output(ctx.getEntry(), 1)
                .requiredToUnlock(prov, bucket)
                .requiredToUnlock(prov, syrupBottle, 4));
            prov.add(Recipes.shapeless("syrup_bottles_from_bucket")
                .output(AutumnityItems.SYRUP_BOTTLE.get(), 4)
                .requiredToUnlock(prov, syrupBucket)
                .requiredToUnlock(prov, bottle, 4));
        }))
        .build()
        .register();
    
}
