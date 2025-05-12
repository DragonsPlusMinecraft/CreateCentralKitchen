package plus.dragons.createcentralkitchen.entry.item;

import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import plus.dragons.createcentralkitchen.entry.fluid.AtmosphericFluidEntries;
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
        .properties(prop -> prop.stacksTo(1).craftRemainder(Items.BUCKET))
        .register();
    
}
