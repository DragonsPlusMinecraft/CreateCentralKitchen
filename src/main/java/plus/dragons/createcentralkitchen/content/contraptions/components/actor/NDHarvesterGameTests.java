package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import umpaz.nethersdelight.common.block.PropelplantBerryStemBlock;
import umpaz.nethersdelight.common.block.util.PropelplantBlock;
import umpaz.nethersdelight.common.registry.NDBlocks;
import umpaz.nethersdelight.common.registry.NDItems;

@ModLoadSubscriber(modid = Mods.ND)
@PrefixGameTestTemplate(false)
public class NDHarvesterGameTests {
    private static final BlockPos CROP_POS = new BlockPos(1, 2, 1);

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(NDHarvesterGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void harvestsPropelpearlsFromBerryStems(GameTestHelper helper) {
        var level = helper.getLevel();
        var absolutePos = helper.absolutePos(CROP_POS);
        BlockState state = NDBlocks.PROPELPLANT_BERRY_STEM.get().defaultBlockState()
                .setValue(PropelplantBerryStemBlock.PEARL, true);
        level.setBlock(absolutePos, state, 2);
        List<ItemStack> drops = new ArrayList<>();

        NDHarvesterMovementBehaviorExtensions.harvestPropelplantStem(
                collectingBehaviour(drops), context(level, state), absolutePos, state, true, false);

        assertPropelpearlDrop(helper, drops);
        BlockState harvested = level.getBlockState(absolutePos);
        helper.assertTrue(harvested.is(NDBlocks.PROPELPLANT_BERRY_STEM.get()),
                "Harvesting replaced the propelplant berry stem");
        helper.assertTrue(!harvested.getValue(PropelplantBerryStemBlock.PEARL),
                "Harvesting did not clear the berry stem's pearl state");
        helper.succeed();
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void harvestsPropelpearlsFromBerryCanes(GameTestHelper helper) {
        var level = helper.getLevel();
        var absolutePos = helper.absolutePos(CROP_POS);
        BlockState state = NDBlocks.PROPELPLANT_BERRY_CANE.get().defaultBlockState()
                .setValue(PropelplantBlock.PEARL, true);
        level.setBlock(absolutePos, state, 2);
        List<ItemStack> drops = new ArrayList<>();

        NDHarvesterMovementBehaviorExtensions.harvestPropelplantCane(
                collectingBehaviour(drops), context(level, state), absolutePos, state, true, false);

        assertPropelpearlDrop(helper, drops);
        BlockState harvested = level.getBlockState(absolutePos);
        helper.assertTrue(harvested.is(NDBlocks.PROPELPLANT_BERRY_CANE.get()),
                "Harvesting replaced the propelplant berry cane");
        helper.assertTrue(!harvested.getValue(PropelplantBlock.PEARL),
                "Harvesting did not clear the berry cane's pearl state");
        helper.succeed();
    }

    private static HarvesterMovementBehaviour collectingBehaviour(List<ItemStack> drops) {
        return new HarvesterMovementBehaviour() {
            @Override
            public void dropItem(MovementContext ignored, ItemStack stack) {
                drops.add(stack.copy());
            }
        };
    }

    private static MovementContext context(net.minecraft.world.level.Level level, BlockState state) {
        return new MovementContext(level,
                new StructureTemplate.StructureBlockInfo(BlockPos.ZERO, state, null), null);
    }

    private static void assertPropelpearlDrop(GameTestHelper helper, List<ItemStack> drops) {
        int count = drops.stream()
                .filter(stack -> stack.is(NDItems.PROPELPEARL.get()))
                .mapToInt(ItemStack::getCount)
                .sum();
        helper.assertTrue(count >= 1 && count <= 2,
                "Expected one or two propelpearls, got " + count);
    }
}
