package plus.dragons.createcentralkitchen.modules.neapolitan.entry;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class NeapolitanModuleItems {
    
    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_NEAPOLITAN_ICE_CREAM_CHOCOLATE = REGISTRATE
        .item("incomplete_neapolitan_ice_cream_chocolate", SequencedAssemblyItem::new)
        .lang("Incomplete Neapolitan Ice Cream")
        .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
        .properties(prop -> prop.stacksTo(16))
        .register();
    
    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_NEAPOLITAN_ICE_CREAM_STRAWBERRY = REGISTRATE
        .item("incomplete_neapolitan_ice_cream_strawberry", SequencedAssemblyItem::new)
        .lang("Incomplete Neapolitan Ice Cream")
        .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
        .properties(prop -> prop.stacksTo(16))
        .register();
    
    public static void register() {}
    
}
