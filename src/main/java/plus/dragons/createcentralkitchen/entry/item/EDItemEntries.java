package plus.dragons.createcentralkitchen.entry.item;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.food.FoodProperties;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.FoodValues;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.ED)
public class EDItemEntries {
    public static final ItemEntry<SequencedAssemblyItem>
            INCOMPLETE_CHORUS_FRUIT_PIE = sequencedFood("chorus_fruit_pie", FoodValues.PIE_CRUST);

    public static final ItemEntry<SequencedAssemblyItem>
            INCOMPLETE_CHORUS_FLOWER_PIE = sequencedFood("chorus_flower_pie", FoodValues.PIE_CRUST);

    private static ItemEntry<SequencedAssemblyItem> sequencedFood(String result, FoodProperties food) {
        return REGISTRATE.item("incomplete_" + result, SequencedAssemblyItem::new)
                .properties(prop -> prop.food(food))
                .register();
    }
}
