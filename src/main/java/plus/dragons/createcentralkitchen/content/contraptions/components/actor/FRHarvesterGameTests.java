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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import umpaz.farmersrespite.common.block.CoffeeDoubleStemBlock;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRItems;

@ModLoadSubscriber(modid = Mods.FR)
@PrefixGameTestTemplate(false)
public class FRHarvesterGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(FRHarvesterGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void harvestsBothMatureCoffeeBranches(GameTestHelper helper) {
        helper.assertTrue(REGISTRY.containsKey(FRBlocks.SMALL_TEA_BUSH.get()),
                "Small tea bush has no mechanical harvester behavior");
        helper.assertTrue(REGISTRY.containsKey(FRBlocks.TEA_BUSH.get()),
                "Tea bush has no mechanical harvester behavior");
        helper.assertTrue(REGISTRY.containsKey(FRBlocks.COFFEE_STEM.get()),
                "Coffee stem has no mechanical harvester behavior");
        helper.assertTrue(REGISTRY.containsKey(FRBlocks.COFFEE_STEM_MIDDLE.get()),
                "Middle coffee stem has no mechanical harvester behavior");
        helper.assertTrue(REGISTRY.containsKey(FRBlocks.COFFEE_STEM_DOUBLE.get()),
                "Double coffee stem has no mechanical harvester behavior");

        var relativePos = new BlockPos(1, 2, 1);
        var absolutePos = helper.absolutePos(relativePos);
        var state = FRBlocks.COFFEE_STEM_DOUBLE.get().defaultBlockState()
                .setValue(CoffeeDoubleStemBlock.AGE, 2)
                .setValue(CoffeeDoubleStemBlock.AGE1, 2);
        var level = helper.getLevel();
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
        FRHarvesterMovementBehaviourExtensions.harvestCoffeeDoubleStem(
                behaviour, context, absolutePos, state, true, false);

        helper.assertBlockProperty(relativePos, CoffeeDoubleStemBlock.AGE, 0);
        helper.assertBlockProperty(relativePos, CoffeeDoubleStemBlock.AGE1, 0);
        int berryCount = drops.stream()
                .filter(stack -> stack.is(FRItems.COFFEE_BERRIES.get()))
                .mapToInt(ItemStack::getCount)
                .sum();
        helper.assertTrue(berryCount == 2,
                "Expected two coffee berries from two mature branches, got " + berryCount);
        helper.succeed();
    }
}
