package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.VirtualFluidBuilder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.data.recipe.ProcessingRecipes;
import plus.dragons.createcentralkitchen.data.tag.OptionalTags;
import vectorwing.farmersdelight.common.registry.ModItems;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class FdFluids {
    
    public static final FluidEntry<VirtualFluid> APPLE_CIDER = fromFluidContainer(ModItems.APPLE_CIDER,
        mixing -> mixing
            .require(Items.APPLE)
            .require(Items.APPLE)
            .require(Items.SUGAR)
            .requiresHeat(HeatCondition.HEATED))
        .tag()
        .register();
    
    public static final FluidEntry<VirtualFluid> HOT_COCOA = fromFluidContainer(ModItems.HOT_COCOA,
        mixing -> mixing
            .require(Items.COCOA_BEANS)
            .require(FdTags.fluid(new ResourceLocation("forge", "chocolate")), 250)
            .requiresHeat(HeatCondition.HEATED))
        .register();
    
    public static final FluidEntry<VirtualFluid> MELON_JUICE = fromFluidContainer(ModItems.MELON_JUICE,
        mixing -> mixing
            .require(Items.MELON_SLICE)
            .require(Items.MELON_SLICE)
            .require(Items.MELON_SLICE)
            .require(Items.MELON_SLICE)
            .require(Items.SUGAR))
        .register();
    
    public static final FluidEntry<VirtualFluid> TOMATO_SAUCE = fromFluidContainer(ModItems.TOMATO_SAUCE,
        mixing -> mixing
            .require(ModItems.TOMATO.get())
            .require(ModItems.TOMATO.get())
            .requiresHeat(HeatCondition.HEATED))
        .register();
    
    @SuppressWarnings({"deprecation", "NullableProblems"})
    public static FluidBuilder<VirtualFluid, CreateRegistrate> fromFluidContainer(
        RegistryObject<? extends Item> item,
        NonNullUnaryOperator<ProcessingRecipeBuilder<?>> mixingIngredients)
    {
        String mod = item.getId().getNamespace();
        String name = item.getId().getPath();
        ResourceLocation tagId = new ResourceLocation("forge", name);
        return REGISTRATE.entry(name, callback -> new VirtualFluidBuilder<>(
                REGISTRATE, REGISTRATE, name, callback,
                CentralKitchen.genRL("fluid/" + name + "_still"),
                CentralKitchen.genRL("fluid/" + name + "_flow"),
                CreateRegistrate::defaultFluidType,
                VirtualFluid::new)
            )
            .defaultLang()
            .transform(OptionalTags.fluid(tagId))
            .setData(ProviderType.RECIPE, (ctx, prov) -> {
                Fluid source = ctx.get().getSource();
                ProcessingRecipes.emptying(CentralKitchen.genRL(name))
                    .require(item.get())
                    .output(source, 250)
                    .whenModLoaded(mod)
                    .build(prov);
                ProcessingRecipes.filling(CentralKitchen.genRL(name))
                    .require(item.get().getCraftingRemainingItem())
                    .require(source, 250)
                    .output(item.get())
                    .whenModLoaded(mod)
                    .build(prov);
                mixingIngredients.apply(ProcessingRecipes.mixing(CentralKitchen.genRL(name)))
                    .output(source, 250)
                    .whenModLoaded(mod)
                    .build(prov);
            });
    }
    
    public static void register() {}
    
}