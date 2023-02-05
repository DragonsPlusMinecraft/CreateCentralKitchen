package plus.dragons.createcentralkitchen.foundation.ponder.content;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.entry.CckBlocks;
import plus.dragons.createcentralkitchen.entry.CckItems;
import vectorwing.farmersdelight.common.block.BasketBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.List;

public class FarmersDelightScenes {

    public static void blazeStoveIntro(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("blaze_stove.intro", "Hire a cook");
        scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(0, 1, 0, 2, 1, 2), Direction.DOWN);

        scene.overlay.showText(40)
                .text("Right-click the Blaze Burner with an Cooking Guide in hand when sneaking.")
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
                .text("To retrieve the cooking guide, right-click the Blaze Stove with wrench when sneaking.")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 1));
        scene.idle(40);
        scene.world.setBlock(util.grid.at(1,1,1), AllBlocks.BLAZE_BURNER.getDefaultState().setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING),false);
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(1, 1, 1), Pointing.DOWN).rightClick().withWrench().whileSneaking(), 40);
        scene.idle(40);
    }

    public static void blazeStoveProcessing(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("blaze_stove.processing", "Expert chef"); // We do not use PonderLocalization. For title only
        scene.configureBasePlate(0, 0, 5);
        scene.world.setKineticSpeed(util.select.everywhere(), 0);
        scene.showBasePlate();
        scene.world.showSection(util.select.fromTo(0, 1, 0, 4, 2, 4), Direction.DOWN);
        scene.idle(50);
        scene.overlay.showText(40)
                .text("Blaze Stove can provide mechanical arm support for the Pot, so let it control the cooking!")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(3, 1, 1));

        scene.idle(70);
        scene.overlay.showText(60)
                .text("Cooking can still be done even without adding fuel to the Blaze Stove.")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 2));

        scene.idle(70);
        scene.overlay.showText(60)
                .text("The hotter the fire, the faster the cooking.")
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
                .text("Of course, you can also use Block Stove to fry things directly.")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 2));

        scene.idle(70);
        scene.overlay.showText(60)
                .text("But please note that seething fire can burn your ingredients directly.")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(3, 1, 3));
    }
    
    public static void basket(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("basket", "Collecting items with Basket"); // We do not use PonderLocalization. For title only
        scene.configureBasePlate(0, 1, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.world.modifyKineticSpeed(util.select.everywhere(), f -> f / 2f);
    
        scene.idle(10);
        
        Selection funnel = util.select.position(3, 2, 2);
        Selection belt = util.select.fromTo(1, 1, 0, 5, 2, 2).substract(funnel);
        Selection basket = util.select.position(0, 1, 2);
    
        scene.world.showSection(belt, Direction.DOWN);
    
        scene.idle(10);
        scene.world.showSection(funnel, Direction.DOWN);
    
        scene.idle(10);
        scene.world.showSection(basket, Direction.DOWN);
    
        scene.overlay.showText(40)
            .text("Basket can collect transported items from belt.")
            .attachKeyFrame()
            .placeNearTarget()
            .pointAt(util.vector.topOf(0, 1, 2));
        
        basket$addItems(scene, util);
        
        BlockPos basketPos = util.grid.at(0, 1, 2);
    
        scene.overlay.showText(40)
            .text("Besides facing upwards, corresponding horizontal facing is also suitable.")
            .attachKeyFrame()
            .placeNearTarget()
            .pointAt(util.vector.topOf(0, 1, 2));
        
        scene.idle(10);
        scene.world.setBlock(
            basketPos,
            ModBlocks.BASKET.get()
                .defaultBlockState()
                .setValue(BasketBlock.FACING, Direction.EAST),
            true);
        
        basket$addItems(scene, util);
    }
    
    private static void basket$addItems(SceneBuilder scene, SceneBuildingUtil util) {
        BlockPos entryBeltPos = util.grid.at(3, 1, 2);
        BlockPos exitBeltPos = util.grid.at(1, 1, 2);
    
        List<ItemStack> vegetables = List.of(
            new ItemStack(ModItems.CABBAGE.get()),
            new ItemStack(ModItems.ONION.get()),
            new ItemStack(Items.POTATO),
            new ItemStack(Items.CARROT),
            new ItemStack(ModItems.TOMATO.get()),
            new ItemStack(Items.BEETROOT)
        );
    
        scene.idle(24);
        
        for (int i = 0; i < 8; i++) {
            scene.idle(8);
            scene.world.removeItemsFromBelt(exitBeltPos);
            scene.world.flapFunnel(exitBeltPos.above(), false);
            if (i == 2)
                scene.rotateCameraY(70);
            if (i < 6)
                scene.world.createItemOnBelt(entryBeltPos, Direction.EAST, vegetables.get(i));
        }
        
        scene.rotateCameraY(-70);
    }
    
}
