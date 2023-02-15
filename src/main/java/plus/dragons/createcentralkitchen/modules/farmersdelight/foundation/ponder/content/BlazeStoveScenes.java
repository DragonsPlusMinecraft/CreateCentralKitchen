package plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.content;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmTileEntity;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdBlocks;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdItems;
import vectorwing.farmersdelight.common.registry.ModItems;

public class BlazeStoveScenes {
    
    public static void intro(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("blaze_stove.intro", "Create a Blaze Stove");
        scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(0, 1, 0, 2, 1, 2), Direction.DOWN);

        scene.overlay.showText(50)
            .text("To create a Blaze Stove, Right-Click the Blaze Burner with a Cooking Guide in hand when sneaking.")
            .attachKeyFrame()
            .pointAt(util.vector.topOf(1, 1, 1))
            .placeNearTarget();
        scene.idle(60);
        
        ItemStack cookingGuide = FdItems.COOKING_GUIDE.asStack();
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(1, 1, 1), Pointing.DOWN)
            .whileSneaking()
            .rightClick()
            .withItem(cookingGuide), 30);
        scene.world.setBlock(util.grid.at(1, 1, 1), FdBlocks.BLAZE_STOVE.getDefaultState(), false);
        scene.world.modifyTileEntity(util.grid.at(1, 1, 1), BlazeStoveBlockEntity.class,
            stove -> stove.setGuide(cookingGuide));
        scene.idle(40);
        
        scene.overlay.showText(50)
            .text("Cooking Guides are for Cooking Pots, other guides for different kitchenware are also applicable.")
            .attachKeyFrame()
            .pointAt(util.vector.topOf(1, 1, 1))
            .placeNearTarget();
        scene.idle(60);
        
        scene.overlay.showText(50)
            .text("To retrieve the Cooking Guide, Right-Click the Blaze Stove with wrench when sneaking.")
            .attachKeyFrame()
            .pointAt(util.vector.topOf(1, 1, 1))
            .placeNearTarget();
        scene.idle(60);
        
        scene.world.setBlock(util.grid.at(1, 1, 1),
            AllBlocks.BLAZE_BURNER.getDefaultState()
                .setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING), false);
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(1, 1, 1), Pointing.DOWN)
            .rightClick()
            .withWrench()
            .whileSneaking(), 30);
        scene.idle(40);
    }
    
    public static void configure(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("blaze_stove.automation", "Configure the Blaze Stove");
        scene.configureBasePlate(1, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.world.setKineticSpeed(util.select.everywhere(), 0);
        scene.showBasePlate();
        scene.idle(5);
        //Stove and Cooking Pot
        BlockPos stovePos = util.grid.at(3, 1, 2);
        Selection stoveSel = util.select.position(stovePos);
        BlockPos potPos = util.grid.at(3, 2, 2);
        Selection stoveAndPotSel = util.select.fromTo(stovePos, potPos);
        scene.world.showSection(stoveAndPotSel, Direction.DOWN);
        scene.idle(10);
    
        scene.effects.indicateRedstone(stovePos);
        scene.overlay.showSelectionWithText(stoveSel, 70)
            .attachKeyFrame()
            .colored(PonderPalette.RED)
            .text("The Blaze Stove can provide mechanical arm support for kitchenware like Cooking Pot, but it needs to be configured to work.")
            .pointAt(util.vector.centerOf(stovePos))
            .placeNearTarget();
        scene.idle(80);
    
        scene.overlay.showText(50)
            .text("Right-Click the Blaze Stove to open the Cooking Guide menu.")
            .attachKeyFrame()
            .pointAt(util.vector.centerOf(stovePos))
            .placeNearTarget();
        scene.idle(60);
        
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(3, 1, 1), Pointing.DOWN)
            .rightClick(), 30);
        scene.idle(40);
    
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(3, 1, 1), Pointing.LEFT)
            .withItem(ModItems.GLOW_BERRY_CUSTARD.get().getDefaultInstance()), 30);
        scene.idle(40);
    
        scene.overlay.showText(50)
            .text("You may also Right-Click the Blaze Stove with any Guide to swap them.")
            .attachKeyFrame()
            .pointAt(util.vector.centerOf(stovePos))
            .placeNearTarget();
        scene.idle(60);
    
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(3, 1, 1), Pointing.DOWN)
            .withItem(FdItems.COOKING_GUIDE.asStack())
            .rightClick(), 30);
        scene.idle(40);
    
        scene.overlay.showText(50)
            .text("Once you assigned a valid recipe to the Cooking Guide, the Cooking Pot above will become interactive with Mechanical Arms.")
            .attachKeyFrame()
            .pointAt(util.vector.centerOf(stovePos))
            .placeNearTarget();
        scene.idle(60);
        //Basket
        scene.world.showSection(util.select.position(3, 1, 1), Direction.DOWN);
        scene.idle(10);
        //Right Mechanical Arm
        BlockPos rightArmPos = util.grid.at(2, 1, 2);
        Selection rightArmSel = util.select.fromTo(1, 1, 1, 2, 1, 2);
        scene.world.showSection(rightArmSel, Direction.EAST);
        scene.idle(10);
        scene.world.setKineticSpeed(util.select.fromTo(0, 0, 0, 2, 1, 2), 64);
        scene.idle(10);
    
        AABB basketShape = Shapes.block().move(3, 1, 1).bounds();
        AABB cookingPotShape = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D).move(3, 2, 2).bounds();
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.INPUT, new Object(), basketShape, 60);
        scene.idle(10);
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.OUTPUT, new Object(), cookingPotShape, 60);
        scene.idle(10);
        scene.overlay.showText(180)
            .text("For input, the Mechanical Arm will insert containers and defined ingredients.")
            .attachKeyFrame()
            .pointAt(util.vector.centerOf(rightArmPos))
            .placeNearTarget();
        
        ItemStack[] inputItems = {
            Items.GLOW_BERRIES.getDefaultInstance(),
            ModItems.MILK_BOTTLE.get().getDefaultInstance(),
            Items.EGG.getDefaultInstance(),
            Items.SUGAR.getDefaultInstance(),
            Items.GLASS_BOTTLE.getDefaultInstance()
        };
        for (var item : inputItems) {
            scene.world.instructArm(rightArmPos, ArmTileEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
            scene.idle(12);
            scene.world.instructArm(rightArmPos, ArmTileEntity.Phase.SEARCH_OUTPUTS, item, -1);
            scene.idle(8);
            scene.world.instructArm(rightArmPos, ArmTileEntity.Phase.MOVE_TO_OUTPUT, item, 0);
            scene.idle(12);
            scene.world.instructArm(rightArmPos, ArmTileEntity.Phase.SEARCH_INPUTS, ItemStack.EMPTY, -1);
            scene.idle(8);
        }
    
        //Left Mechanical Arm
        BlockPos leftArmPos = util.grid.at(4, 1, 2);
        Selection leftArmSel = util.select.fromTo(4, 1, 1, 5, 1, 2);
        scene.world.showSection(leftArmSel, Direction.WEST);
        scene.idle(10);
        scene.world.setKineticSpeed(util.select.fromTo(4, 0, 0, 6, 1, 2), 64);
        scene.idle(10);
        
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.INPUT, new Object(), cookingPotShape, 60);
        scene.idle(10);
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.OUTPUT, new Object(), basketShape, 60);
        scene.idle(10);
        scene.overlay.showText(40)
            .text("For output, the Mechanical Arm will extract result and invalid ingredients.")
            .attachKeyFrame()
            .pointAt(util.vector.centerOf(stovePos))
            .placeNearTarget();
        var result = ModItems.GLOW_BERRY_CUSTARD.get().getDefaultInstance();
        scene.world.instructArm(leftArmPos, ArmTileEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
        scene.idle(12);
        scene.world.instructArm(leftArmPos, ArmTileEntity.Phase.SEARCH_OUTPUTS, result, -1);
        scene.idle(8);
        scene.world.instructArm(leftArmPos, ArmTileEntity.Phase.MOVE_TO_OUTPUT, result, 0);
        scene.idle(12);
        scene.world.instructArm(leftArmPos, ArmTileEntity.Phase.SEARCH_INPUTS, ItemStack.EMPTY, -1);
        scene.idle(18);
    }
    
    public static void heat_source(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("blaze_stove.heat_source", "Use the Blaze Stove as a Heat Source");
        scene.configureBasePlate(0, 0, 5);
        scene.world.setKineticSpeed(util.select.everywhere(), 0);
        scene.showBasePlate();
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(0, 1, 0, 4, 2, 4), Direction.DOWN);
        
        scene.overlay.showText(40)
            .text("The Blaze Stove is an active heat source, the hotter the fire, the faster the cooking.")
            .placeNearTarget()
            .pointAt(util.vector.topOf(3, 1, 3));
        scene.idle(50);
        
        scene.overlay.showText(40)
            .text("But cooking can still be done with passive heat.")
            .attachKeyFrame()
            .placeNearTarget()
            .pointAt(util.vector.topOf(1, 1, 2));
        scene.idle(50);
        
        scene.world.setBlock(util.grid.at(1, 2, 2), Blocks.AIR.defaultBlockState(), false);
        scene.idle(5);
        scene.world.setBlock(util.grid.at(3, 2, 3), Blocks.AIR.defaultBlockState(), false);
        scene.idle(5);
    
        scene.overlay.showText(40)
            .text("Of course, you can also use the Blaze Stove to fry things directly.")
            .attachKeyFrame()
            .placeNearTarget()
            .pointAt(util.vector.topOf(3, 1, 1));
        scene.idle(10);
        
        ItemStack[] ingredients = {
            new ItemStack(ModItems.MINCED_BEEF.get()),
            new ItemStack(ModItems.MINCED_BEEF.get()),
            new ItemStack(ModItems.MINCED_BEEF.get()),
            new ItemStack(ModItems.BACON.get()),
            new ItemStack(ModItems.BACON.get()),
            new ItemStack(ModItems.BACON.get()),
            new ItemStack(ModItems.CHICKEN_CUTS.get()),
            new ItemStack(ModItems.CHICKEN_CUTS.get()),
            new ItemStack(ModItems.CHICKEN_CUTS.get()),
        };
        for (int i = 0; i < 9; ++i) {
            final int index = i;
            scene.world.modifyTileEntity(util.grid.at(1, 1, 2), BlazeStoveBlockEntity.class, be -> {
                var inv = be.getInventory();
                inv.insertItem(index, ingredients[index], false);
            });
            scene.idle(4);
        }
        scene.idle(4);
        
        scene.overlay.showText(40)
            .text("But please note that seething fire can burn your ingredients in an instant.")
            .attachKeyFrame()
            .placeNearTarget()
            .pointAt(util.vector.topOf(3, 1, 3));
        scene.idle(50);
    }
    
}
