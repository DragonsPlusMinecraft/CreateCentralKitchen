package plus.dragons.createcentralkitchen.entry.item;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.FoodValues;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.CR)
public class CRItemEntries {
    
    public static final ItemEntry<SequencedAssemblyItem>
        INCOMPLETE_PORTOBELLO_BURGER = sequencedFood(CRItems.PORTOBELLO_BURGER, Foods.BREAD);
    
    public static final ItemEntry<SequencedAssemblyItem>
        INCOMPLETE_PORTOBELLO_QUICHE = sequencedFood(CRItems.PORTOBELLO_QUICHE, FoodValues.PIE_CRUST),
        INCOMPLETE_LIME_PIE = sequencedFood(CRItems.LIME_PIE, FoodValues.PIE_CRUST);
    
    private static ItemEntry<SequencedAssemblyItem> sequencedFood(String result, FoodProperties food) {
        return REGISTRATE.item("incomplete_" + result, SequencedAssemblyItem::new)
            .properties(prop -> prop.food(food))
            .register();
    }
    
    private static ItemEntry<SequencedAssemblyItem> sequencedFood(RegistryObject<? extends Item> result, FoodProperties food) {
        return sequencedFood(result.getId().getPath(), food);
    }
    
    private static ItemEntry<SequencedAssemblyItem> sequencedFood(ItemProviderEntry<? extends Item> result, FoodProperties food) {
        return sequencedFood(result.getId().getPath(), food);
    }

}
