package plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.content;

import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.block.BasketBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.List;

public class BasketScenes {
    
    public static void intro(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("basket", ""); // We do not use PonderLocalization. For title only
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
            .text("") // We do not use PonderLocalization. For registerText only
            .attachKeyFrame()
            .placeNearTarget()
            .pointAt(util.vector.topOf(0, 1, 2));
        
        addItemsToBelt(scene, util);
        
        BlockPos basketPos = util.grid.at(0, 1, 2);
    
        scene.overlay.showText(40)
            .text("") // We do not use PonderLocalization. For registerText only
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
        
        addItemsToBelt(scene, util);
    }
    
    private static void addItemsToBelt(SceneBuilder scene, SceneBuildingUtil util) {
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
