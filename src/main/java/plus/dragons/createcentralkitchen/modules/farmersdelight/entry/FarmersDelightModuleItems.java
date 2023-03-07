package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.config.CentralKitchenConfigs;
import plus.dragons.createcentralkitchen.core.item.ConditionalItem;
import plus.dragons.createcentralkitchen.core.item.FillCreateItemGroupEvent;
import plus.dragons.createcentralkitchen.core.resources.condition.ConfigBoolCondition;
import plus.dragons.createcentralkitchen.core.resources.condition.ConfigListCondition;
import plus.dragons.createcentralkitchen.data.recipe.RecipeGen;
import plus.dragons.createcentralkitchen.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.modules.farmersdelight.FarmersDelightModule;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.item.guide.CookingGuideItem;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class FarmersDelightModuleItems {
    
    private static final CreateRegistrate REGISTRATE = CentralKitchen.REGISTRATE
        .startSection(AllSections.KINETICS)
        .creativeModeTab(() -> Create.BASE_CREATIVE_TAB);

    public static final ItemEntry<CookingGuideItem> COOKING_GUIDE = REGISTRATE.item("cooking_guide", CookingGuideItem::new)
        .properties(prop -> prop.stacksTo(1))
        .recipe((ctx, prov) -> RecipeGen.shapeless(ctx.getId())
            .output(ctx.get())
            .require(ForgeItemTags.create("plates/obsidian"))
            .require(ModItems.CANVAS.get())
            .require(ForgeTags.VEGETABLES)
            .whenModLoaded(FarmersDelightModule.ID)
            .save(prov)
        )
        .register();
    
    static {
        REGISTRATE.startSection(AllSections.UNASSIGNED).creativeModeTab(() -> FarmersDelight.CREATIVE_TAB);
    }
    
    public static final ItemEntry<ConditionalItem> PUMPKIN_PIE_SLICE = REGISTRATE
        .item("pumpkin_pie_slice", prop -> ConditionalItem.configured(prop,
            CentralKitchenConfigs.COMMON.integration.enablePieOverhaul))
        .properties(prop -> prop.food(FoodValues.PIE_SLICE))
        .onRegister(item -> ComposterBlock.COMPOSTABLES.putIfAbsent(item, 0.85F))
        .model((ctx, prov) -> prov.getBuilder(ctx.getId().toString())
            .parent(new ModelFile.UncheckedModelFile("item/generated"))
            .texture("layer0", new ResourceLocation(FarmersDelightModule.ID, "item/" + ctx.getName())))
        .recipe((ctx, prov) -> {
            DataIngredient pieSlice = DataIngredient.items(ctx);
            RecipeGen.cuttingBoard("pumpkin_pie")
                .output(ctx.get(), 4)
                .require(Items.PUMPKIN_PIE)
                .tool(ForgeTags.TOOLS_KNIVES)
                .whenModLoaded("farmersdelight")
                .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
                .withCondition(new ConfigListCondition("pie_overhaul_black_list", "minecraft:pumpkin_pie").blackList())
                .save(prov);
            RecipeGen.shapeless("pumpkin_pie_from_slices")
                .output(Items.PUMPKIN_PIE)
                .require(pieSlice, 4)
                .whenModLoaded("farmersdelight")
                .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
                .withCondition(new ConfigListCondition("pie_overhaul_black_list", "minecraft:pumpkin_pie").blackList())
                .unlockedBy("has_pumpkin_pie_slice", pieSlice.getCritereon(prov))
                .save(prov);
        })
        .register();
    
    public static final ItemEntry<ConditionalItem> CHERRY_PIE_SLICE = pieSlice(
        new ResourceLocation("environmental", "cherry_pie"), FoodValues.PIE_SLICE).register();
    
    public static final ItemEntry<ConditionalItem> TRUFFLE_PIE_SLICE = pieSlice(
        new ResourceLocation("environmental", "truffle_pie"), Foods.TRUFFLE_PIE_SLICE).register();
    
    public static final ItemEntry<ConditionalItem> MULBERRY_PIE_SLICE = pieSlice(
        new ResourceLocation("upgrade_aquatic", "mulberry_pie"), FoodValues.PIE_SLICE).register();
    
    private static ItemBuilder<ConditionalItem, CreateRegistrate> pieSlice(ResourceLocation pieId, FoodProperties food) {
        String mod = pieId.getNamespace();
        String pieName = pieId.getPath();
        String sliceName = pieName + "_slice";
        RegistryObject<Item> pieItem = RegistryObject.create(pieId, ForgeRegistries.ITEMS);
        return REGISTRATE.item(sliceName, prop -> ConditionalItem.configuredAndRequireMod(prop,
                CentralKitchenConfigs.COMMON.integration.enablePieOverhaul, mod))
            .properties(prop -> prop.food(food))
            .onRegister(item -> ComposterBlock.COMPOSTABLES.putIfAbsent(item, 0.85F))
            .recipe((ctx, prov) -> {
                if (!pieItem.isPresent())
                    return;
                DataIngredient pie = DataIngredient.items(pieItem.get());
                DataIngredient pieSlice = DataIngredient.items(ctx);
                RecipeGen.cuttingBoard(pieName)
                    .output(ctx.get(), 4)
                    .require(pie)
                    .tool(ForgeTags.TOOLS_KNIVES)
                    .whenModLoaded("farmersdelight")
                    .whenModLoaded(mod)
                    .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
                    .withCondition(new ConfigListCondition("pie_overhaul_black_list", pieId.toString()).blackList())
                    .save(prov);
                RecipeGen.shapeless(pieName + "_from_slices")
                    .output(pieItem.get())
                    .require(pieSlice, 4)
                    .whenModLoaded("farmersdelight")
                    .whenModLoaded(mod)
                    .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
                    .withCondition(new ConfigListCondition("pie_overhaul_black_list", pieId.toString()).blackList())
                    .unlockedBy("has_" + sliceName, pieSlice.getCritereon(prov))
                    .save(prov);
            });
    }
    
    public static void fillCreateItemGroup(FillCreateItemGroupEvent event) {
        if (event.getItemGroup() == Create.BASE_CREATIVE_TAB) {
            event.addInsertion(AllBlocks.BLAZE_BURNER.get(), COOKING_GUIDE.asStack());
        }
    }

    public static void register() {}
    
    public static class Foods {
        
        public static final FoodProperties PUMPKIN_PIE_SLICE = new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.3F)
            .fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0F)
            .build();
    
        public static final FoodProperties CHERRY_PIE_SLICE = new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.3F)
            .fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0F)
            .build();
        
        public static final FoodProperties TRUFFLE_PIE_SLICE = new FoodProperties.Builder()
            .nutrition(4)
            .saturationMod(0.6F)
            .fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 900), 1.0F)
            .build();
    
        public static final FoodProperties MULBERRY_PIE_SLICE = new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.6F)
            .fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0F)
            .build();
    
    }
    
}
