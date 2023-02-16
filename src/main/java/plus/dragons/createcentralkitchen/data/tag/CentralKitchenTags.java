package plus.dragons.createcentralkitchen.data.tag;

import com.farmersrespite.core.registry.FRBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import plus.dragons.createcentralkitchen.CentralKitchen;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class CentralKitchenTags {
    public static final TagKey<Item> UPRIGHT_ON_DEPLOYER = item(CentralKitchen.genRL("upright_on_deployer"));
    
    public static TagKey<Item> item(ResourceLocation id) {
        return TagKey.create(Registry.ITEM_REGISTRY, id);
    }

    public static TagKey<Block> block(ResourceLocation id) {
        return TagKey.create(Registry.BLOCK_REGISTRY, id);
    }
    
    public static TagKey<Fluid> fluid(ResourceLocation id) {
        return TagKey.create(Registry.FLUID_REGISTRY, id);
    }
    
    @SuppressWarnings("unchecked")
    public static void genItemTags(RegistrateItemTagsProvider prov) {
        prov.tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .addOptional(ModItems.APPLE_CIDER.getId())
            .addOptional(ModItems.HOT_COCOA.getId())
            .addOptional(ModItems.MELON_JUICE.getId())
            .addOptional(ModItems.TOMATO_SAUCE.getId());
        prov.tag(UPRIGHT_ON_DEPLOYER).addTag(ForgeTags.TOOLS);
        prov.tag(ForgeTags.TOOLS).addTags(ForgeTags.TOOLS_AXES, ForgeTags.TOOLS_PICKAXES, ForgeTags.TOOLS_SHOVELS);
        prov.tag(ForgeTags.TOOLS_AXES).add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.GOLDEN_AXE, Items.NETHERITE_AXE);
        prov.tag(ForgeTags.TOOLS_PICKAXES).add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE, Items.GOLDEN_PICKAXE, Items.NETHERITE_PICKAXE);
        prov.tag(ForgeTags.TOOLS_SHOVELS).add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.DIAMOND_SHOVEL, Items.GOLDEN_SHOVEL, Items.NETHERITE_SHOVEL);
    }

    public static void genBlockTags(RegistrateTagsProvider<Block> prov) {
        prov.tag(ModTags.TRAY_HEAT_SOURCES)
            .add(AllBlocks.LIT_BLAZE_BURNER.get())
            .add(AllBlocks.BLAZE_BURNER.get());
        prov.tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .addOptional(ModBlocks.COOKING_POT.getId())
            .addOptional(FRBlocks.KETTLE.getId());
    }
    
    public static void register() {
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CentralKitchenTags::genItemTags);
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, CentralKitchenTags::genBlockTags);
    }
    
}
