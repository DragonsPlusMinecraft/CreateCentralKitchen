package plus.dragons.createcentralkitchen.foundation.ponder.content;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.entry.CckBlocks;
import plus.dragons.createcentralkitchen.entry.CckItems;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class CookScenes {

    public static void transformBlazeBurner(SceneBuilder scene, SceneBuildingUtil util){
        scene.title("transform", ""); // We do not use PonderLocalization. For title only
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
        scene.world.setBlock(util.grid.at(1,1,1), CckBlocks.BLAZE_STOVE.getDefaultState(),false);
        scene.world.modifyTileEntity(util.grid.at(1,1,1), BlazeStoveBlockEntity.class, be-> be.setCookingGuide(new ItemStack(CckItems.COOKING_GUIDE.get())));
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(1, 1, 1), Pointing.DOWN).whileSneaking().rightClick()
                .withItem(CckItems.COOKING_GUIDE.asStack()), 40);

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

    public static void stoveAndPot(SceneBuilder scene, SceneBuildingUtil util){
        scene.title("stove_and_pot", ""); // We do not use PonderLocalization. For title only
        scene.configureBasePlate(0, 0, 5);
        scene.scaleSceneView(.60f);
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
        scene.world.setBlock(util.grid.at(3,2,1), ModBlocks.SKILLET.get().defaultBlockState(),false);
        scene.world.setBlock(util.grid.at(1,2,2), Blocks.AIR.defaultBlockState(),false);
        scene.world.setBlock(util.grid.at(3,2,3), Blocks.AIR.defaultBlockState(),false);

        scene.world.modifyTileEntity(util.grid.at(1,1,2), BlazeStoveBlockEntity.class, be-> {
            be.addFuelOrIngredient(new ItemStack(Items.BEEF),false,false);
            be.addFuelOrIngredient(new ItemStack(Items.BEEF),false,false);
            be.addFuelOrIngredient(new ItemStack(Items.BEEF),false,false);
            be.addFuelOrIngredient(new ItemStack(Items.PORKCHOP),false,false);
            be.addFuelOrIngredient(new ItemStack(Items.PORKCHOP),false,false);
            be.addFuelOrIngredient(new ItemStack(Items.PORKCHOP),false,false);
        });
        scene.overlay.showText(40)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 2));

        scene.idle(70);
        scene.world.modifyTileEntity(util.grid.at(3,1,3), BlazeStoveBlockEntity.class, be-> {
            be.addFuelOrIngredient(new ItemStack(Items.BEEF),false,false);
        });
        scene.overlay.showText(60)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(3, 2, 3));
    }
}
