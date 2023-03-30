package plus.dragons.createcentralkitchen.foundation.ponder.scene;

import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.block.BasketBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

public class BasketScenes {
    
    public static void intro(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("basket.intro", "Collecting Dropped Items using the Basket");
        scene.configureBasePlate(0, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.idle(5);
        
        BlockPos upBasketPos = util.grid.at(3, 1, 1);
        BlockPos upChutePos = util.grid.at(3, 3, 1);
        BlockPos frontBasketPos = util.grid.at(2, 1, 3);
        BlockPos frontChutePos = util.grid.at(2, 3, 2);
        BlockPos chestBasketPos = util.grid.at(1, 1, 1);
        BlockPos chestPos = util.grid.at(1, 2, 1);
        
        Selection chestSel = util.select.fromTo(chestBasketPos, chestPos);
        
        scene.world.showSection(util.select.layer(1).substract(chestSel), Direction.DOWN);
        scene.idle(5);
        
        scene.overlay.showText(40)
            .text("Like the Hopper, the Basket can collect dropped items.")
            .attachKeyFrame()
            .placeNearTarget()
            .pointAt(util.vector.topOf(upBasketPos));
        
        scene.world.showSection(util.select.position(upChutePos), Direction.DOWN);
        scene.idle(10);
        
        ItemStack[] droppedItems = {
            new ItemStack(ModItems.CABBAGE.get()),
            new ItemStack(ModItems.TOMATO.get()),
            new ItemStack(ModItems.ONION.get()),
            new ItemStack(ModItems.RICE_PANICLE.get())
        };
        
        for (int i = 0; i < 4; ++i) {
            var item = scene.world.createItemEntity(util.vector.blockSurface(upChutePos, Direction.DOWN), Vec3.ZERO, droppedItems[i]);
            scene.idle(10);
            scene.world.modifyEntity(item, Entity::discard);
        }
        
        scene.overlay.showText(40)
            .text("What's more, Baskets can have different facings.")
            .attachKeyFrame()
            .placeNearTarget()
            .pointAt(util.vector.blockSurface(frontBasketPos, Direction.NORTH));
    
        scene.world.showSection(util.select.position(frontChutePos), Direction.DOWN);
        scene.idle(10);
    
        for (int i = 0; i < 4; ++i) {
            var item = scene.world.createItemEntity(util.vector.blockSurface(frontChutePos, Direction.DOWN), Vec3.ZERO, droppedItems[i]);
            scene.idle(10);
            scene.world.modifyEntity(item, Entity::discard);
        }
        
        scene.world.showSection(chestSel, Direction.DOWN);
        scene.idle(10);
    
        scene.effects.indicateRedstone(upBasketPos);
        scene.overlay.showSelectionWithText(util.select.position(chestBasketPos), 40)
            .attachKeyFrame()
            .colored(PonderPalette.RED)
            .text("However, the Basket can't extract items from containers.")
            .pointAt(util.vector.centerOf(chestPos))
            .placeNearTarget();
        scene.idle(50);
    }
    
    public static void belt_interaction(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("basket.belt_interaction", "Receiving Items from Belt using Basket");
        scene.configureBasePlate(0, 1, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.world.modifyKineticSpeed(util.select.everywhere(), f -> f / 2f);
        scene.idle(5);
        
        Selection funnel = util.select.position(3, 2, 2);
        Selection belt = util.select.fromTo(1, 1, 0, 5, 2, 2).substract(funnel);
        Selection basket = util.select.position(0, 1, 2);
    
        scene.world.showSection(belt, Direction.DOWN);
    
        scene.idle(10);
        scene.world.showSection(funnel, Direction.DOWN);
    
        scene.idle(10);
        scene.world.showSection(basket, Direction.DOWN);
    
        scene.overlay.showText(40)
            .text("The Basket can receive transported items from belt.")
            .attachKeyFrame()
            .placeNearTarget()
            .pointAt(util.vector.topOf(0, 1, 2));
        
        BlockPos entryBeltPos = util.grid.at(3, 1, 2);
        BlockPos exitBeltPos = util.grid.at(1, 1, 2);
    
        ItemStack[] vegetables = {
            new ItemStack(Items.CARROT),
            new ItemStack(ModItems.TOMATO.get()),
            new ItemStack(Items.BEETROOT),
            new ItemStack(ModItems.CABBAGE.get()),
            new ItemStack(ModItems.ONION.get()),
            new ItemStack(Items.POTATO),
        };
    
        scene.idle(24);
    
        for (int i = 0; i < 8; i++) {
            scene.idle(8);
            scene.world.removeItemsFromBelt(exitBeltPos);
            scene.world.flapFunnel(exitBeltPos.above(), false);
            if (i == 2)
                scene.rotateCameraY(70);
            if (i < 6)
                scene.world.createItemOnBelt(entryBeltPos, Direction.EAST, vegetables[i]);
        }
    
        scene.rotateCameraY(-70);
        
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
    
        scene.idle(24);
    
        for (int i = 0; i < 8; i++) {
            scene.idle(8);
            scene.world.removeItemsFromBelt(exitBeltPos);
            scene.world.flapFunnel(exitBeltPos.above(), false);
            if (i == 2)
                scene.rotateCameraY(70);
            if (i < 6)
                scene.world.createItemOnBelt(entryBeltPos, Direction.EAST, vegetables[i]);
        }
    
        scene.rotateCameraY(-70);
    }
    
}
