package plus.dragons.createcentralkitchen.entry.item;

import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking.CookingGuideItem;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigs;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.DatapackRecipes;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.item.ConditionedItem;
import plus.dragons.createcentralkitchen.foundation.resource.condition.ConfigBoolCondition;
import plus.dragons.createcentralkitchen.foundation.resource.condition.ConfigListCondition;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.registry.ModEffects;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.FD)
public class FDItemEntries {
    static {
        REGISTRATE.startSection(AllSections.KINETICS).creativeModeTab(() -> Create.BASE_CREATIVE_TAB);
    }
    
    public static final ItemEntry<CookingGuideItem> COOKING_GUIDE = REGISTRATE.item("cooking_guide", CookingGuideItem::new)
        .properties(prop -> prop.stacksTo(1))
        .transform(DatapackRecipes.addRecipe(Mods.FD, (ctx, prov) -> prov.add(Recipes.shapeless(ctx.getId())
            .output(ctx.get())
            .require(ForgeItemTags.create("plates/obsidian"))
            .require(ModItems.CANVAS.get())
            .require(ForgeTags.VEGETABLES)
            .whenModLoaded(Mods.FD))))
        .register();
    
    static {
        REGISTRATE.startSection(AllSections.UNASSIGNED).creativeModeTab(() -> FarmersDelight.CREATIVE_TAB);
    }
    
    public static final ItemEntry<SequencedAssemblyItem>
        INCOMPLETE_EGG_SANDWICH = sequencedFood(ModItems.EGG_SANDWICH, Foods.BREAD),
        INCOMPLETE_CHICKEN_SANDWICH = sequencedFood(ModItems.CHICKEN_SANDWICH, Foods.BREAD),
        INCOMPLETE_HAMBURGER = sequencedFood(ModItems.HAMBURGER, Foods.BREAD),
        INCOMPLETE_BACON_SANDWICH = sequencedFood(ModItems.BACON_SANDWICH, Foods.BREAD),
        INCOMPLETE_MUTTON_WRAP = sequencedFood(ModItems.MUTTON_WRAP, Foods.BREAD);
    
    public static final ItemEntry<SequencedAssemblyItem>
        INCOMPLETE_APPLE_PIE = sequencedFood(ModItems.APPLE_PIE, FoodValues.PIE_CRUST),
        INCOMPLETE_SWEET_BERRY_CHEESECAKE = sequencedFood(ModItems.SWEET_BERRY_CHEESECAKE, FoodValues.PIE_CRUST),
        INCOMPLETE_PUMPKIN_PIE = sequencedFood("pumpkin_pie", FoodValues.PIE_CRUST),
        INCOMPLETE_CHERRY_PIE = sequencedFood("cherry_pie", FoodValues.PIE_CRUST),
        INCOMPLETE_TRUFFLE_PIE = sequencedFood("truffle_pie", FoodValues.PIE_CRUST),
        INCOMPLETE_MULBERRY_PIE = sequencedFood("mulberry_pie", FoodValues.PIE_CRUST);
    
    public static final ItemEntry<ConditionedItem>
        PUMPKIN_PIE_SLICE = pieSlice(new ResourceLocation("pumpkin_pie"))
            .model((ctx, prov) -> prov.getBuilder(ctx.getId().toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", Mods.fd("item/" + ctx.getName())))
            .register(),
        CHERRY_PIE_SLICE = pieSlice(Mods.environmental("cherry_pie")).register(),
        TRUFFLE_PIE_SLICE = pieSlice(Mods.environmental("truffle_pie"))
            .properties(prop -> prop.food(new FoodProperties.Builder()
                .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 1200), 1.0F)
                .nutrition(4).saturationMod(0.6F).fast().build()))
            .register(),
        MULBERRY_PIE_SLICE = pieSlice(Mods.ua("mulberry_pie")).register();
    
    public static final ItemEntry<ConditionedItem>
        CHOCOLATE_CAKE_SLICE = cakeSlice(Mods.ca("chocolate_cake"))
            .properties(prop -> prop.food(cakeSliceFood().nutrition(3).saturationMod(0.3F)
                .effect(() -> new MobEffectInstance(RegistryObject
                    .create(Mods.neapolitan("sugar_rush"), ForgeRegistries.MOB_EFFECTS)
                    .orElse(MobEffects.DIG_SPEED), 200), 1F).build()))
            .register(),
        HONEY_CAKE_SLICE = cakeSlice(Mods.ca("honey_cake"))
            .properties(prop -> prop.food(cakeSliceFood().nutrition(3).saturationMod(0.3F).build()))
            .register(),
        YUCCA_CAKE_SLICE = cakeSlice(Mods.peculiars("yucca_cake"))
            .properties(prop -> prop.food(cakeSliceFood().nutrition(1)
                .effect(() -> new MobEffectInstance(RegistryObject
                    .create(Mods.atmospheric("persistence"), ForgeRegistries.MOB_EFFECTS)
                    .orElse(ModEffects.COMFORT.get()), 200), 1F).build()))
            .register(),
        ALOE_CAKE_SLICE = cakeSlice(Mods.peculiars("aloe_cake"))
            .properties(prop -> prop.food(cakeSliceFood().nutrition(1)
                .effect(() -> new MobEffectInstance(RegistryObject
                    .create(Mods.atmospheric("relief"), ForgeRegistries.MOB_EFFECTS)
                    .orElse(MobEffects.DAMAGE_RESISTANCE), 200), 1F).build()))
            .register(),
        PASSIONFRUIT_CAKE_SLICE = cakeSlice(Mods.peculiars("passionfruit_cake"))
            .properties(prop -> prop.food(cakeSliceFood().nutrition(1)
                .effect(() -> new MobEffectInstance(RegistryObject
                    .create(Mods.atmospheric("spitting"), ForgeRegistries.MOB_EFFECTS)
                    .orElse(MobEffects.DAMAGE_BOOST), 200), 1F).build()))
            .register(),
        PUMPKIN_CAKE_SLICE = cakeSlice(Mods.seasonals("pumpkin_cake"))
            .properties(prop -> prop.food(cakeSliceFood().nutrition(1)
                .effect(() -> new MobEffectInstance(RegistryObject
                    .create(Mods.seasonals("fall_flavor"), ForgeRegistries.MOB_EFFECTS)
                    .orElse(ModEffects.NOURISHMENT.get()), 200), 1F).build()))
            .register(),
        SWEET_BERRY_CAKE_SLICE = cakeSlice(Mods.seasonals("sweet_berry_cake"))
            .properties(prop -> prop.food(cakeSliceFood().nutrition(1)
                .effect(() -> new MobEffectInstance(RegistryObject
                    .create(Mods.seasonals("thorn_resistance"), ForgeRegistries.MOB_EFFECTS)
                    .orElse(MobEffects.DAMAGE_RESISTANCE), 200), 1F).build()))
            .register();
    
    private static ItemEntry<SequencedAssemblyItem> sequencedFood(String result, FoodProperties food) {
        return REGISTRATE.item("incomplete_" + result, SequencedAssemblyItem::new)
            .properties(prop -> prop.food(food))
            .register();
    }
    
    private static ItemEntry<SequencedAssemblyItem> sequencedFood(RegistryObject<? extends Item> result, FoodProperties food) {
        return sequencedFood(result.getId().getPath(), food);
    }
    
    private static ItemEntry<SequencedAssemblyItem> sequencedFood(ItemProviderEntry<? extends Item> result,  FoodProperties food) {
        return sequencedFood(result.getId().getPath(), food);
    }
    
    private static ItemBuilder<ConditionedItem, CreateRegistrate> pieSlice(ResourceLocation pieId) {
        String mod = pieId.getNamespace();
        String pieName = pieId.getPath();
        String sliceName = pieName + "_slice";
        boolean minecraft = "minecraft".equals(mod);
        var config = CentralKitchenConfigs.COMMON.integration.enablePieOverhaul;
        return REGISTRATE.item(sliceName, prop -> minecraft
                ? new ConditionedItem(prop, config)
                : new ConditionedItem(prop, config, mod))
            .lang("Slice of " + RegistrateLangProvider.toEnglishName(pieName))
            .properties(prop -> prop.food(FoodValues.PIE_SLICE))
            .onRegister(item -> ComposterBlock.COMPOSTABLES.putIfAbsent(item, 0.85F))
            .transform(DatapackRecipes.addRecipe(Mods.FD, (ctx, prov) -> {
                RegistryObject<Item> pie = RegistryObject.create(pieId, ForgeRegistries.ITEMS);
                if (!pie.isPresent())
                    return;
                var cutting = Recipes.cuttingBoard(ctx.getId())
                    .require(pie.get())
                    .tool(ForgeTags.TOOLS_KNIVES)
                    .output(ctx.get(), 4)
                    .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
                    .withCondition(new ConfigListCondition("pie_overhaul_black_list", pieId.toString()).blackList());
                var crafting = Recipes.shapeless(pieName + "_from_slices")
                    .requiredToUnlock(prov, DataIngredient.items(ctx), 4)
                    .output(pie.get())
                    .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
                    .withCondition(new ConfigListCondition("pie_overhaul_black_list", pieId.toString()).blackList());
                if (!minecraft) {
                    cutting.whenModLoaded(mod);
                    crafting.whenModLoaded(mod);
                }
                prov.add(cutting);
                prov.add(crafting);
            }));
    }
    
    private static FoodProperties.Builder cakeSliceFood() {
        return new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400), 1.0F);
    }
    
    private static ItemBuilder<ConditionedItem, CreateRegistrate> cakeSlice(ResourceLocation cakeId) {
        String mod = cakeId.getNamespace();
        String cakeName = cakeId.getPath();
        String sliceName = cakeName + "_slice";
        return REGISTRATE.item(sliceName, prop -> new ConditionedItem(prop, mod))
            .lang("Slice of " + RegistrateLangProvider.toEnglishName(cakeName))
            .properties(prop -> prop.food(FoodValues.CAKE_SLICE))
            .onRegister(item -> ComposterBlock.COMPOSTABLES.putIfAbsent(item, 0.85F))
            .transform(DatapackRecipes.addRecipe(Mods.FD, (ctx, prov) -> {
                RegistryObject<Item> cake = RegistryObject.create(cakeId, ForgeRegistries.ITEMS);
                if (!cake.isPresent())
                    return;
                prov.add(Recipes.cuttingBoard(ctx.getId())
                    .require(cake.get())
                    .tool(ForgeTags.TOOLS_KNIVES)
                    .output(ctx.get(), 7)
                    .whenModLoaded(mod));
                prov.add(Recipes.shapeless(cakeName + "_from_slices")
                    .requiredToUnlock(prov, DataIngredient.items(ctx), 7)
                    .output(cake.get())
                    .whenModLoaded(mod));
            }));
    }
    
}
