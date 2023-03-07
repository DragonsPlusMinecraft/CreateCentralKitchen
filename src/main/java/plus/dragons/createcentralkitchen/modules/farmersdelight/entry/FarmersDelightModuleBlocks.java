package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.ModList;
import plus.dragons.createcentralkitchen.core.config.CentralKitchenConfigs;
import plus.dragons.createcentralkitchen.core.loot.RuntimeLootTables;
import plus.dragons.createcentralkitchen.data.tag.OptionalTags;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlock;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.curiosities.pie.PieBlockStateGen;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class FarmersDelightModuleBlocks {
    
    public static final BlockEntry<BlazeStoveBlock> BLAZE_STOVE = REGISTRATE
        .block("blaze_stove", BlazeStoveBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p.color(MaterialColor.COLOR_GRAY).lightLevel(BlazeBurnerBlock::getLight))
        .transform(OptionalTags.block(
            BlockTags.MINEABLE_WITH_PICKAXE,
            AllTags.AllBlockTags.FAN_TRANSPARENT.tag,
            ModTags.HEAT_SOURCES))
        .transform(RuntimeLootTables.register(block ->
            RegistrateBlockLootTables.createSingleItemTable(AllBlocks.BLAZE_BURNER.get())))
        .addLayer(() -> RenderType::cutoutMipped)
        .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
        .register();
    
    @SuppressWarnings("Convert2MethodRef")//Shouldn't be converted to method reference as we need lambda's laziness here
    public static final BlockEntry<PieBlock> PUMPKIN_PIE = pie("pumpkin_pie", () ->
        FarmersDelightModuleItems.PUMPKIN_PIE_SLICE.get(), true).register();
    
    @SuppressWarnings("Convert2MethodRef")//Shouldn't be converted to method reference as we need lambda's laziness here
    public static final BlockEntry<PieBlock> CHERRY_PIE = pie("cherry_pie", () ->
        FarmersDelightModuleItems.CHERRY_PIE_SLICE.get(), false).register();
    
    @SuppressWarnings("Convert2MethodRef")//Shouldn't be converted to method reference as we need lambda's laziness here
    public static final BlockEntry<PieBlock> TRUFFLE_PIE = pie("truffle_pie", () ->
        FarmersDelightModuleItems.TRUFFLE_PIE_SLICE.get(), false).register();
    
    @SuppressWarnings("Convert2MethodRef")//Shouldn't be converted to method reference as we need lambda's laziness here
    public static final BlockEntry<PieBlock> MULBERRY_PIE = pie("mulberry_pie", () ->
        FarmersDelightModuleItems.MULBERRY_PIE_SLICE.get(), false).register();
    
    private static BlockBuilder<PieBlock, CreateRegistrate> pie(String name, NonNullSupplier<Item> slice, boolean defaultTexture) {
        return REGISTRATE
            .block(name, prop -> new PieBlock(prop, slice))
            .initialProperties(() -> Blocks.CAKE)
            .transform(OptionalTags.block(ModTags.MINEABLE_WITH_KNIFE))
            .loot((loot, block) -> loot.add(block, BlockLoot.noDrop()))
            .blockstate((defaultTexture ? PieBlockStateGen.DEFAULT : PieBlockStateGen.CUSTOM)::generate);
    }
    
    public static void replacePieItems(RegistryEvent.Register<Item> event) {
        if (!CentralKitchenConfigs.COMMON.integration.enablePieOverhaul.get())
            return;
        ModList modList = ModList.get();
        var registry = event.getRegistry();
        var blackList = CentralKitchenConfigs.COMMON.integration.pieOverhaulBlackList.getIdList();
        //Pumpkin Pie
        ResourceLocation id = new ResourceLocation("pumpkin_pie");
        if (!blackList.contains(id)) {
            //Need to unregister and re-register compostable as vanilla data was assigned before this
            ComposterBlock.COMPOSTABLES.removeFloat(Items.PUMPKIN_PIE);
            BlockItem pumpkinPie = new BlockItem(PUMPKIN_PIE.get(), new Item.Properties().tab(CreativeModeTab.TAB_FOOD));
            registry.register(pumpkinPie.setRegistryName(id));
            ComposterBlock.COMPOSTABLES.put(pumpkinPie, 1.0F);
        }
        if (modList.isLoaded("environmental")) {
            //Apple Pie
            id = new ResourceLocation("environmental", "apple_pie");
            if (!blackList.contains(id))
                registry.register(new BlockItem(ModBlocks.APPLE_PIE.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_FOOD)
                ).setRegistryName(id));
            //Cherry Pie
            id = new ResourceLocation("environmental", "cherry_pie");
            if (!blackList.contains(id))
                registry.register(new BlockItem(CHERRY_PIE.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_FOOD)
                ).setRegistryName(id));
            //Truffle Pie
            id = new ResourceLocation("environmental", "truffle_pie");
            if (!blackList.contains(id))
                registry.register(new BlockItem(TRUFFLE_PIE.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_FOOD)
                ).setRegistryName(id));
        }
        if (modList.isLoaded("upgrade_aquatic")) {
            //Mulberry Pie
            id = new ResourceLocation("upgrade_aquatic", "mulberry_pie");
            registry.register(new BlockItem(MULBERRY_PIE.get(),
                new Item.Properties().tab(CreativeModeTab.TAB_FOOD)
            ).setRegistryName(id));
        }
    }
    
    public static void register() {}
    
}
