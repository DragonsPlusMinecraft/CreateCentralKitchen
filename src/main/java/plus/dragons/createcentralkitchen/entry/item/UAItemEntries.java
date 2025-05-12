package plus.dragons.createcentralkitchen.entry.item;

import com.teamabnormals.upgrade_aquatic.core.registry.UABlocks;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import plus.dragons.createcentralkitchen.entry.fluid.UAFluidEntries;
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
        .properties(prop -> prop.stacksTo(1).craftRemainder(Items.BUCKET))
        .register();
    
}
