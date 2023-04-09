package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.DatapackRecipes;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.data.tag.OptionalTags;
import plus.dragons.createcentralkitchen.foundation.fluid.NoColorFluidAttributes;
import plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem;
import plus.dragons.createcentralkitchen.foundation.item.FluidBucketItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.HashMap;
import java.util.Map;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.FD, bus = Bus.FORGE)
public class FDFluidEntries {
    
    public static final FluidEntry<VirtualFluid>
        APPLE_CIDER = VirtualFluidFromItem.watery(ModItems.APPLE_CIDER, 0xC68A47)
        .register(),
        HOT_COCOA = VirtualFluidFromItem.milky(ModItems.HOT_COCOA, 0xAF6C4C)
            .register(),
        MELON_JUICE = VirtualFluidFromItem.watery(ModItems.MELON_JUICE, 0xE24334)
            .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing>
        TOMATO_SAUCE = REGISTRATE.fluid("tomato_sauce",
            CentralKitchen.genRL("fluid/tomato_sauce_still"),
            CentralKitchen.genRL("fluid/tomato_sauce_flow"),
            NoColorFluidAttributes::new)
        .lang("Tomato Sauce")
        .transform(OptionalTags.fluid(FluidTags.WATER))
        .attributes(b -> b.viscosity(2000).density(1400))
        .properties(p -> p.levelDecreasePerBlock(2)
            .tickRate(25)
            .slopeFindDistance(3)
            .explosionResistance(100f))
        .source(ForgeFlowingFluid.Source::new) // TODO: remove when Registrate fixes FluidBuilder
        .transform(Recipes.fluidHandling(ModItems.TOMATO_SAUCE, 250))
        .bucket(FluidBucketItem::new)
        .properties(prop -> prop.tab(CreativeModeTab.TAB_MATERIALS))
        .transform(DatapackRecipes.addRecipe(Mods.FD, (ctx, prov) -> {
            DataIngredient tomatoSauce = DataIngredient.items(ModItems.TOMATO_SAUCE.get());
            DataIngredient tomatoSauceBucket = DataIngredient.items(ctx);
            DataIngredient bowl = DataIngredient.items(Items.BOWL);
            DataIngredient bucket = DataIngredient.items(Items.BUCKET);
            prov.add(Recipes.shapeless("tomato_sauce_bucket_from_bowls")
                .output(ctx.getEntry(), 1)
                .requiredToUnlock(prov, bucket)
                .requiredToUnlock(prov, tomatoSauce, 4));
            prov.add(Recipes.shapeless("tomato_sauce_from_bucket")
                .output(ModItems.TOMATO_SAUCE.get(), 4)
                .requiredToUnlock(prov, tomatoSauceBucket)
                .requiredToUnlock(prov, bowl, 4)
                .whenModLoaded(Mods.FD));
        }))
        .build()
        .register();
    
    @SubscribeEvent
    public static void remap(RegistryEvent.MissingMappings<Fluid> event) {
        Map<ResourceLocation, FluidEntry<?>> remap = new HashMap<>();
        remap.put(Mods.fd("apple_cider"), APPLE_CIDER);
        remap.put(Mods.fd("hot_cocoa"), HOT_COCOA);
        remap.put(Mods.fd("melon_juice"), MELON_JUICE);
        remap.put(Mods.fd("tomato_sauce"), TOMATO_SAUCE);
        for (var mapping : event.getMappings(Mods.FD)) {
            if (remap.containsKey(mapping.key)) {
                var fluid = remap.get(mapping.key);
                mapping.remap(fluid.get());
                CentralKitchen.LOGGER.warn("Remapping fluid '{}' to '{}'...", mapping.key, fluid.getId());
            }
        }
    }
    
}
