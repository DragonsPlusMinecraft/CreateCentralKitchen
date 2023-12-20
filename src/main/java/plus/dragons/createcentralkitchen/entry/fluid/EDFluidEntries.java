package plus.dragons.createcentralkitchen.entry.fluid;

import cn.foggyhillside.ends_delight.registry.ItemRegistry;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.Recipes;
import plus.dragons.createcentralkitchen.foundation.fluid.VirtualFluidFromItem;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.ED)
public class EDFluidEntries {

    private static final ResourceLocation BUBBLE_TEA_STILL = CentralKitchen.genRL("fluid/bubble_tea_still");
    private static final ResourceLocation BUBBLE_TEA_FLOW = CentralKitchen.genRL("fluid/bubble_tea_flow");
    public static final FluidEntry<VirtualFluid>
        DRAGONS_BREATH_SODA = VirtualFluidFromItem.watery(ItemRegistry.DragonBreathSoda, 0xd16191)
            .register(),
        CHORUS_FLOWER_TEA = VirtualFluidFromItem.watery(ItemRegistry.ChorusFlowerTea, 0xa27da1)
            .register(),
        CHORUS_FRUIT_WINE = VirtualFluidFromItem.watery(ItemRegistry.ChorusFruitWine, 0xd39cd3)
            .register(),
        CHORUS_FRUIT_MILK_TEA = VirtualFluidFromItem.milky(ItemRegistry.ChorusFruitMilkTea, 0xffa3ff)
            .register(),
        CHORUS_FRUIT_BUBBLE_TEA = bubbleTea("chorus_fruit_bubble_tea",ItemRegistry.BubbleTea, 0xffa3ff, 250)
            .register();

    public static FluidBuilder<VirtualFluid, CreateRegistrate> bubbleTea(String name, RegistryObject<? extends ItemLike> container, int colorIn, int amount) {
        final int color = 0xFF000000 | colorIn;
        return REGISTRATE.virtualFluid(name, BUBBLE_TEA_STILL, BUBBLE_TEA_FLOW, ((properties, stillTexture, flowingTexture) ->
                        new AllFluids.TintedFluidType(properties, stillTexture, flowingTexture) {
                            @Override
                            protected int getTintColor(FluidStack stack) {
                                return color;
                            }

                            @Override
                            protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                                return color;
                            }
                        }), VirtualFluid::new)
                .defaultLang()
                .properties(builder -> builder.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL))
                .transform(Recipes.fluidHandling(container, amount));
    }
    
}
