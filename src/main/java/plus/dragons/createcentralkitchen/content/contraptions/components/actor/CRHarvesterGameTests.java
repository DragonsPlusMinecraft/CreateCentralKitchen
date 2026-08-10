package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import static net.brdle.collectorsreap.common.block.FruitBushBlock.AGE;
import static net.brdle.collectorsreap.common.block.FruitBushBlock.MAX_AGE;

import net.brdle.collectorsreap.common.block.CRBlocks;
import net.brdle.collectorsreap.common.block.FruitBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.CR)
@PrefixGameTestTemplate(false)
public class CRHarvesterGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        // The template belongs to Create, while the game test run filters other mods' tests by namespace.
        GameTestRegistry.register(CRHarvesterGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void resetsCollectorsReapFruitBushes(GameTestHelper helper) {
        var lowerPos = new BlockPos(1, 2, 1);
        assertReset(helper, lowerPos, CRBlocks.LIME_BUSH.get().defaultBlockState(), false);
        assertReset(helper, lowerPos, CRBlocks.POMEGRANATE_BUSH.get().defaultBlockState(), true);
        helper.succeed();
    }

    private static void assertReset(GameTestHelper helper, BlockPos lowerPos, BlockState defaultState,
            boolean harvestLowerHalf) {
        var lowerState = defaultState.setValue(AGE, MAX_AGE)
                .setValue(FruitBushBlock.HALF, DoubleBlockHalf.LOWER);
        var upperState = lowerState.setValue(FruitBushBlock.HALF, DoubleBlockHalf.UPPER);
        var absoluteLowerPos = helper.absolutePos(lowerPos);
        var level = helper.getLevel();
        level.setBlock(absoluteLowerPos.below(), Blocks.FARMLAND.defaultBlockState(), 3);
        level.setBlock(absoluteLowerPos, lowerState, 2);
        level.setBlock(absoluteLowerPos.above(), upperState, 2);

        var harvestPos = harvestLowerHalf ? lowerPos : lowerPos.above();
        var harvestState = helper.getBlockState(harvestPos);
        CRHarvesterMovementBehaviorExtensions.resetFruitBush(
                level, helper.absolutePos(harvestPos), harvestState, harvestLowerHalf);

        helper.assertBlockPresent(defaultState.getBlock(), lowerPos);
        helper.assertBlockPresent(defaultState.getBlock(), lowerPos.above());
        helper.assertBlockProperty(lowerPos, AGE, MAX_AGE - 2);
        helper.assertBlockProperty(lowerPos.above(), AGE, MAX_AGE - 2);
        helper.assertBlockProperty(lowerPos, FruitBushBlock.HALF, DoubleBlockHalf.LOWER);
        helper.assertBlockProperty(lowerPos.above(), FruitBushBlock.HALF, DoubleBlockHalf.UPPER);
    }
}
