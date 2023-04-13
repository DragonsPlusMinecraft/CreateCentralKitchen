package plus.dragons.createcentralkitchen.entry.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlock;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigs;
import plus.dragons.createcentralkitchen.foundation.data.loot.BlockLootTables;
import plus.dragons.createcentralkitchen.foundation.data.model.block.PieBlockStateGen;
import plus.dragons.createcentralkitchen.foundation.data.tag.OptionalTags;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.FD)
public class FDBlockEntries {
    
    public static final BlockEntry<BlazeStoveBlock> BLAZE_STOVE = REGISTRATE
        .block("blaze_stove", BlazeStoveBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p.color(MaterialColor.COLOR_GRAY).lightLevel(BlazeBurnerBlock::getLight))
        .transform(OptionalTags.block(
            BlockTags.MINEABLE_WITH_PICKAXE,
            AllTags.AllBlockTags.FAN_TRANSPARENT.tag,
            ModTags.HEAT_SOURCES))
        .transform(BlockLootTables.add(Mods.FD, block ->
            RegistrateBlockLootTables.createSingleItemTable(AllBlocks.BLAZE_BURNER.get())))
        .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
        .register();
    
    public static final BlockEntry<PieBlock> PUMPKIN_PIE = pie("pumpkin_pie", FDItemEntries.PUMPKIN_PIE_SLICE::get, true)
        .setData(ProviderType.LANG, NonNullBiConsumer.noop())
        .register();
    
    public static final BlockEntry<PieBlock> CHERRY_PIE = pie("cherry_pie", FDItemEntries.CHERRY_PIE_SLICE::get, false)
        .setData(ProviderType.LANG, NonNullBiConsumer.noop())
        .register();
    
    public static final BlockEntry<PieBlock> TRUFFLE_PIE = pie("truffle_pie", FDItemEntries.TRUFFLE_PIE_SLICE::get, false)
        .setData(ProviderType.LANG, NonNullBiConsumer.noop())
        .register();
    
    public static final BlockEntry<PieBlock> MULBERRY_PIE = pie("mulberry_pie", FDItemEntries.MULBERRY_PIE_SLICE::get, false)
        .setData(ProviderType.LANG, NonNullBiConsumer.noop())
        .register();
    
    private static BlockBuilder<PieBlock, CreateRegistrate> pie(String name, NonNullSupplier<Item> slice, boolean defaultTexture) {
        return REGISTRATE
            .block(name, prop -> new PieBlock(prop, slice))
            .initialProperties(() -> Blocks.CAKE)
            .properties(BlockLootTables::noLootGen)
            .transform(OptionalTags.block(ModTags.MINEABLE_WITH_KNIFE))
            .loot((loot, block) -> loot.add(block, BlockLoot.noDrop()))
            .blockstate((defaultTexture ? PieBlockStateGen.DEFAULT : PieBlockStateGen.CUSTOM)::generate);
    }
    
    @SubscribeEvent
    public static void registerItems(RegisterEvent event) {
        if (!event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS))
            return;
        
        if (!CentralKitchenConfigs.COMMON.integration.enablePieOverhaul.get())
            return;
        
        IForgeRegistry<Item> registry = Objects.requireNonNull(event.getForgeRegistry());
        var blackList = CentralKitchenConfigs.COMMON.integration.pieOverhaulBlackList.getIdList();
        
        ResourceLocation pumpkin_pie = new ResourceLocation("pumpkin_pie");
        if (!blackList.contains(pumpkin_pie)) {
            //Need to unregister and re-register compostable as vanilla data was assigned before this
            ComposterBlock.COMPOSTABLES.removeFloat(Items.PUMPKIN_PIE);
            BlockItem item = new ItemNameBlockItem(PUMPKIN_PIE.get(),
                new Item.Properties().tab(CreativeModeTab.TAB_FOOD));
            registry.register(pumpkin_pie, item);
            ComposterBlock.COMPOSTABLES.put(item, 1.0F);
        }
        
        ResourceLocation apple_pie = Mods.environmental("apple_pie");
        if (!blackList.contains(apple_pie) && Mods.isLoaded(Mods.ENVIRONMENTAL)) {
            //Need to override BlockItem#registerBlocks and BlockItem#removeFromBlockToItemMap
            //So Farmer's Delight's apple pie won't get overridden
            BlockItem item = new ItemNameBlockItem(ModBlocks.APPLE_PIE.get(),
                new Item.Properties().tab(CreativeModeTab.TAB_FOOD))
            {
                @Override
                @ParametersAreNonnullByDefault
                public void registerBlocks(Map<Block, Item> blockToItemMap, Item item) {}
                
                @Override
                @ParametersAreNonnullByDefault
                public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item item) {}
            };
            registry.register(apple_pie, item);
        }
        
        Map<ResourceLocation, Block> entries = new HashMap<>();
        entries.put(Mods.environmental("cherry_pie"), CHERRY_PIE.get());
        entries.put(Mods.environmental("truffle_pie"), TRUFFLE_PIE.get());
        entries.put(Mods.ua("mulberry_pie"), MULBERRY_PIE.get());
        entries.forEach((id, block) -> {
            if (!blackList.contains(id) && Mods.isLoaded(id.getNamespace())) {
                BlockItem blockItem = new ItemNameBlockItem(block,
                    new Item.Properties().tab(CreativeModeTab.TAB_FOOD));
                registry.register(id, blockItem);
            }
        });
    }
    
}
