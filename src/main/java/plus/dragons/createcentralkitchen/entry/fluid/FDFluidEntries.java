package plus.dragons.createcentralkitchen.entry.fluid;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.DatapackRecipes;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.data.tag.OptionalTags;
import plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem;
import plus.dragons.createcentralkitchen.foundation.item.FluidBucketItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import plus.dragons.createdragonlib.fluid.NoTintFluidType;
import vectorwing.farmersdelight.common.registry.ModItems;

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
            NoTintFluidType::new)
        .lang("Tomato Sauce")
        .transform(OptionalTags.fluid(FluidTags.WATER))
        .properties(b -> b
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .viscosity(2000)
            .density(1400)
            .canExtinguish(true))
        .fluidProperties(p -> p
            .levelDecreasePerBlock(2)
            .tickRate(25)
            .slopeFindDistance(3)
            .explosionResistance(100f))
        .source(ForgeFlowingFluid.Source::new)
        .transform(Recipes.fluidHandling(ModItems.TOMATO_SAUCE, 250))
        .bucket(FluidBucketItem::new)
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
    
}
