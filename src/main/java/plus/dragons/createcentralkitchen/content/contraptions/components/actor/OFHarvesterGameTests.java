package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.overweightfarming.init.OFBlocks;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.OF)
@PrefixGameTestTemplate(false)
public class OFHarvesterGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(OFHarvesterGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void registersSnowySpiritGingerSupport(GameTestHelper helper) {
        helper.assertTrue(Mods.isLoaded(Mods.SNOWY_SPIRIT),
                "The full integration test profile did not load Snowy Spirit");
        helper.assertTrue(ForgeRegistries.BLOCKS.getValue(Mods.snowySpirit("ginger")) != null,
                "Snowy Spirit did not register its ginger crop");
        helper.assertTrue(REGISTRY.containsKey(OFBlocks.OVERWEIGHT_GINGER.get()),
                "Overweight ginger has no mechanical harvester behavior");
        helper.succeed();
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void nonReplantingHarvestRemovesOverweightCrop(GameTestHelper helper) {
        var level = helper.getLevel();
        var relativePos = new BlockPos(1, 2, 1);
        var absolutePos = helper.absolutePos(relativePos);
        var crop = OFBlocks.OVERWEIGHT_CARROT.get();
        var state = crop.defaultBlockState();
        level.setBlock(absolutePos.below(), Blocks.FARMLAND.defaultBlockState(), 3);
        level.setBlock(absolutePos, state, 2);

        var drops = new ArrayList<ItemStack>();
        var behaviour = new HarvesterMovementBehaviour() {
            @Override
            public void dropItem(MovementContext ignored, ItemStack stack) {
                drops.add(stack.copy());
            }
        };
        var context = new MovementContext(level,
                new StructureTemplate.StructureBlockInfo(BlockPos.ZERO, state, null), null);
        OFHarvesterMovementBehaviorExtensions.harvest(
                behaviour, context, absolutePos, state, false, Blocks.CARROTS::defaultBlockState);

        helper.assertBlockNotPresent(crop, relativePos);
        int cropDrops = drops.stream()
                .filter(stack -> stack.is(crop.asItem()))
                .mapToInt(ItemStack::getCount)
                .sum();
        helper.assertTrue(cropDrops == 1,
                "Expected one harvested overweight carrot, got " + cropDrops);
        helper.succeed();
    }
}
