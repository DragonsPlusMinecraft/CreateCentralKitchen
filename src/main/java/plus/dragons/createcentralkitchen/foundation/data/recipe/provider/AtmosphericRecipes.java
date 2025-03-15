package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.atmospheric.core.registry.AtmosphericItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.AtmosphericFluidEntries;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

public class AtmosphericRecipes extends DatapackRecipes {
    private final GeneratedRecipe
        COMPACTING_ALOE_GEL = add(compacting("aloe_gel")
            .output(AtmosphericFluidEntries.ALOE_GEL.get(), 250)
            .require(AtmosphericItems.ALOE_LEAVES.get())
            .require(AtmosphericItems.ALOE_LEAVES.get())
            .require(AtmosphericItems.ALOE_LEAVES.get())
            .require(AtmosphericItems.ALOE_LEAVES.get())),
        COMPACTING_ALOE_GEL_BLOCK = add(compacting("aloe_gel_block")
            .output(AtmosphericBlocks.ALOE_GEL_BLOCK.get())
            .require(AtmosphericFluidEntries.ALOE_GEL.get(), 1000)),
        MIXING_ALOE_GEL_FROM_BLOCK = add(mixing("aloe_gel_from_block")
            .output(AtmosphericFluidEntries.ALOE_GEL.get(), 1000)
            .require(AtmosphericBlocks.ALOE_GEL_BLOCK.get())),
        MIXING_YUCCA_GATEAU = add(mixing("yucca_gateau")
            .output(AtmosphericItems.YUCCA_GATEAU.get())
            .require(AtmosphericItems.ROASTED_YUCCA_FRUIT.get())
            .require(AtmosphericItems.ROASTED_YUCCA_FRUIT.get())
            .require(AtmosphericBlocks.YUCCA_FLOWER.get())
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AtmosphericFluidEntries.ALOE_GEL.get(), 250)),
        COMPACTING_PASSIONFRUIT_TART = add(compacting("passionfruit_tart")
            .output(AtmosphericItems.PASSION_FRUIT_TART.get(),8)
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(AtmosphericItems.PASSION_FRUIT.get())
            .require(Tags.Items.EGGS));
    
    public AtmosphericRecipes(DataGenerator datagen) {
        super(Mods.ATMOSPHERIC, CentralKitchen.REGISTRATE, datagen);
    }
    
}
