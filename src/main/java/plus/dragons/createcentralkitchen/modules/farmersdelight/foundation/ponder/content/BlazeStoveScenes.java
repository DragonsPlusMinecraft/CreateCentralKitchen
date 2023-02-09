package plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.content;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdBlocks;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdItems;

public class BlazeStoveScenes {
    
    public static void intro(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("blaze_stove.intro", ""); // We do not use PonderLocalization. For title only
        scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(0, 1, 0, 2, 1, 2), Direction.DOWN);

        scene.overlay.showText(40)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 1));

        scene.idle(40);
        scene.world.setBlock(util.grid.at(1,1,1), FdBlocks.BLAZE_STOVE.getDefaultState(),false);
        scene.world.modifyTileEntity(util.grid.at(1,1,1), BlazeStoveBlockEntity.class, be-> be.setGuide(new ItemStack(FdItems.COOKING_GUIDE.get())));
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(1, 1, 1), Pointing.DOWN).whileSneaking().rightClick()
                .withItem(FdItems.COOKING_GUIDE.asStack()), 40);

        scene.idle(50);
        scene.overlay.showText(40)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 1));
        scene.idle(40);
        scene.world.setBlock(util.grid.at(1,1,1), AllBlocks.BLAZE_BURNER.getDefaultState().setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING),false);
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(1, 1, 1), Pointing.DOWN).rightClick().withWrench().whileSneaking(), 40);
        scene.idle(40);
    }
    
    public static void processing(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("blaze_stove.processing", ""); // We do not use PonderLocalization. For title only
        scene.configureBasePlate(0, 0, 5);
        scene.world.setKineticSpeed(util.select.everywhere(), 0);
        scene.showBasePlate();
        scene.world.showSection(util.select.fromTo(0, 1, 0, 4, 2, 4), Direction.DOWN);
        scene.idle(50);
        scene.overlay.showText(40)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(3, 1, 1));

        scene.idle(70);
        scene.overlay.showText(60)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 2));

        scene.idle(70);
        scene.overlay.showText(60)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(3, 1, 3));

        scene.idle(50);
        scene.world.setBlock(util.grid.at(3,2,1), Blocks.AIR.defaultBlockState(),false);
        scene.world.setBlock(util.grid.at(1,2,2), Blocks.AIR.defaultBlockState(),false);
        scene.world.setBlock(util.grid.at(3,2,3), Blocks.AIR.defaultBlockState(),false);

        scene.world.modifyTileEntity(util.grid.at(1,1,2), BlazeStoveBlockEntity.class, be-> {
            var inv = be.getInventory();
            inv.insertItem(0,new ItemStack(Items.BEEF),false);
            inv.insertItem(1,new ItemStack(Items.BEEF),false);
            inv.insertItem(2,new ItemStack(Items.BEEF),false);
            inv.insertItem(3,new ItemStack(Items.PORKCHOP),false);
            inv.insertItem(4,new ItemStack(Items.PORKCHOP),false);
            inv.insertItem(5,new ItemStack(Items.PORKCHOP),false);
        });

        scene.overlay.showText(40)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 2));

        scene.idle(70);
        scene.overlay.showText(60)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(3, 1, 3));
    }
    
}
