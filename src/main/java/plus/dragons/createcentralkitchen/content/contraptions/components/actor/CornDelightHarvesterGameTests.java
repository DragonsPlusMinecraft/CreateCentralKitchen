package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

import cn.mcmod.corn_delight.block.BlockRegistry;
import cn.mcmod.corn_delight.block.CornCrop;
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
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.CORN_DELIGHT)
@PrefixGameTestTemplate(false)
public class CornDelightHarvesterGameTests {
    private static final BlockPos LOWER_POS = new BlockPos(1, 2, 1);

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(CornDelightHarvesterGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void cornHarvestHonorsDropsAndMaturity(GameTestHelper helper) {
        CornCrop crop = (CornCrop) BlockRegistry.CORN_CROP.get();
        helper.assertTrue(REGISTRY.containsKey(crop),
                "Corn Delight corn has no mechanical harvester behavior");
        var level = helper.getLevel();
        var absoluteLowerPos = helper.absolutePos(LOWER_POS);
        level.setBlock(absoluteLowerPos.below(), Blocks.FARMLAND.defaultBlockState(), 3);
        var matureLower = crop.getStateForAge(crop.getMaxAge())
                .setValue(CornDelightMovementBehaviorExtensions.UPPER, false);
        level.setBlock(absoluteLowerPos, matureLower, 2);
        helper.assertBlockPresent(crop, LOWER_POS);

        var drops = new ArrayList<ItemStack>();
        var behaviour = new HarvesterMovementBehaviour() {
            @Override
            public void dropItem(MovementContext ignored, ItemStack stack) {
                drops.add(stack.copy());
            }
        };
        var lowerContext = new MovementContext(level,
                new StructureTemplate.StructureBlockInfo(BlockPos.ZERO, matureLower, null), null);
        CornDelightMovementBehaviorExtensions.harvestCorn(
                behaviour, lowerContext, absoluteLowerPos, matureLower, true, false);

        helper.assertBlockPresent(crop, LOWER_POS);
        helper.assertTrue(crop.getAge(level.getBlockState(absoluteLowerPos)) == 0,
                "Replanted corn did not reset to age zero");
        helper.assertBlockProperty(LOWER_POS, CornDelightMovementBehaviorExtensions.UPPER, false);
        helper.assertTrue(!drops.isEmpty(),
                "Replanting a mature corn crop discarded all harvested drops");

        var secondLowerPos = LOWER_POS;
        var absoluteSecondLowerPos = absoluteLowerPos;
        level.setBlock(absoluteSecondLowerPos.below(), Blocks.FARMLAND.defaultBlockState(), 3);
        level.setBlock(absoluteSecondLowerPos, matureLower, 2);
        int immatureAge = crop.getMaxAge() - 1;
        var immatureUpper = crop.getStateForAge(immatureAge)
                .setValue(CornDelightMovementBehaviorExtensions.UPPER, true);
        level.setBlock(absoluteSecondLowerPos.above(), immatureUpper, 2);
        helper.assertBlockPresent(crop, secondLowerPos);
        helper.assertBlockPresent(crop, secondLowerPos.above());
        drops.clear();
        var upperContext = new MovementContext(level,
                new StructureTemplate.StructureBlockInfo(BlockPos.ZERO, immatureUpper, null), null);
        CornDelightMovementBehaviorExtensions.harvestCorn(
                behaviour, upperContext, absoluteSecondLowerPos.above(), immatureUpper, true, false);

        helper.assertBlockPresent(crop, secondLowerPos.above());
        helper.assertTrue(crop.getAge(level.getBlockState(absoluteSecondLowerPos.above())) == immatureAge,
                "A non-partial harvest changed an immature upper corn crop");
        helper.assertTrue(drops.isEmpty(),
                "A non-partial harvest destroyed an immature upper corn crop");
        helper.succeed();
    }
}
