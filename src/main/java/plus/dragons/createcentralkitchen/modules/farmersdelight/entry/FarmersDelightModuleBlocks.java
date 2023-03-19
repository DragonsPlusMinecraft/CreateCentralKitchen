package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.AllSections;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
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

import java.util.Map;
import java.util.function.Supplier;

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
        .loot((loot, block) -> loot.add(block, BlockLoot.noDrop()))
        .addLayer(() -> RenderType::cutoutMipped)
        .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
        .register();
    
    static {
        REGISTRATE.startSection(AllSections.UNASSIGNED).creativeModeTab(() -> CreativeModeTab.TAB_FOOD);
    }
    
    public static final BlockEntry<PieBlock> PUMPKIN_PIE = pie("pumpkin_pie",
        FarmersDelightModuleItems.PUMPKIN_PIE_SLICE::get, true).register();
    
    public static final BlockEntry<PieBlock> CHERRY_PIE = pie("cherry_pie",
        FarmersDelightModuleItems.CHERRY_PIE_SLICE::get, false).register();
    
    public static final BlockEntry<PieBlock> TRUFFLE_PIE = pie("truffle_pie",
        FarmersDelightModuleItems.TRUFFLE_PIE_SLICE::get, false).register();
    
    public static final BlockEntry<PieBlock> MULBERRY_PIE = pie("mulberry_pie",
        FarmersDelightModuleItems.MULBERRY_PIE_SLICE::get, false).register();
    
    private static BlockBuilder<PieBlock, CreateRegistrate> pie(String name, NonNullSupplier<Item> slice, boolean defaultTexture) {
        return REGISTRATE
            .block(name, prop -> new PieBlock(prop, slice))
            .initialProperties(() -> Blocks.CAKE)
            .properties(RuntimeLootTables::suppressLootGen)
            .transform(OptionalTags.block(ModTags.MINEABLE_WITH_KNIFE))
            .loot((loot, block) -> loot.add(block, BlockLoot.noDrop()))
            .blockstate((defaultTexture ? PieBlockStateGen.DEFAULT : PieBlockStateGen.CUSTOM)::generate);
    }
    
    private static final ImmutableMap<ResourceLocation, Supplier<? extends Block>> PIE_BLOCK_ITEMS = ImmutableMap
        .<ResourceLocation, Supplier<? extends Block>>builder()
        .put(new ResourceLocation("environmental", "apple_pie"), ModBlocks.APPLE_PIE)
        .put(new ResourceLocation("environmental", "cherry_pie"), CHERRY_PIE)
        .put(new ResourceLocation("environmental", "truffle_pie"), TRUFFLE_PIE)
        .put(new ResourceLocation("upgrade_aquatic", "mulberry_pie"), MULBERRY_PIE)
        .build();
    
    public static void replacePieItems(RegistryEvent.Register<Item> event) {
        if (!CentralKitchenConfigs.COMMON.integration.enablePieOverhaul.get())
            return;
        var registry = event.getRegistry();
        var blackList = CentralKitchenConfigs.COMMON.integration.pieOverhaulBlackList.getIdList();
        
        ResourceLocation id = new ResourceLocation("pumpkin_pie");
        if (!blackList.contains(id)) {
            //Need to unregister and re-register compostable as vanilla data was assigned before this
            ComposterBlock.COMPOSTABLES.removeFloat(Items.PUMPKIN_PIE);
            BlockItem item = new BlockItem(PUMPKIN_PIE.get(), new Item.Properties().tab(CreativeModeTab.TAB_FOOD));
            registry.register(item.setRegistryName(id));
            ComposterBlock.COMPOSTABLES.put(item, 1.0F);
        }
        
        ModList modList = ModList.get();
        for (var entry : PIE_BLOCK_ITEMS.entrySet()) {
            id = entry.getKey();
            if (blackList.contains(id) || !modList.isLoaded(id.getNamespace()))
                continue;
            registry.register(new BlockItem(entry.getValue().get(), new Item.Properties().tab(CreativeModeTab.TAB_FOOD))
                .setRegistryName(id));
        }
    }
    
    public static void register() {
        RuntimeLootTables.register(BLAZE_STOVE.getId(), RegistrateBlockLootTables.createSingleItemTable(AllBlocks.BLAZE_BURNER.get()));
    }
    
}
