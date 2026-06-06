package plus.dragons.createcentralkitchen.entry.fluid;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.dragonLibLegacy.fluid.NoTintFluidType;
import plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem;
import plus.dragons.createcentralkitchen.foundation.item.FluidBucketItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.registry.ModItems;

@ModLoadSubscriber(modid = Mods.FD, bus = Bus.FORGE)
public class FDFluidEntries {
    public static final FluidEntry<VirtualFluid> APPLE_CIDER = VirtualFluidFromItem.watery(ModItems.APPLE_CIDER, 0xC68A47)
            .register(),
            HOT_COCOA = VirtualFluidFromItem.milky(ModItems.HOT_COCOA, 0xAF6C4C)
                    .register(),
            MELON_JUICE = VirtualFluidFromItem.watery(ModItems.MELON_JUICE, 0xE24334)
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> TOMATO_SAUCE = REGISTRATE.fluid("tomato_sauce",
            CentralKitchen.genRL("fluid/tomato_sauce_still"),
            CentralKitchen.genRL("fluid/tomato_sauce_flow"),
            NoTintFluidType::new)
            .lang("Tomato Sauce")
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
            .bucket(FluidBucketItem::new)
            .build()
            .register();
}
