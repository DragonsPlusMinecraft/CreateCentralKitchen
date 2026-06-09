/*
 * Copyright (C) 2025  DragonsPlus
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package plus.dragons.createcentralkitchen.integration.farmersdelight.ponder;

import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlock;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlockEntity;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelPosition;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.PonderHilo;
import java.util.List;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.instruction.DisplayWorldSectionInstruction;
import net.createmod.ponder.foundation.instruction.FadeOutOfSceneInstruction;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FDPonderScenes {
    public static void portionableFoods(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("portionable_foods", "Automating with Create: Servings");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(10);

        var armPos = util.grid().at(1, 1, 2);
        var arm = util.select().position(1, 1, 2);
        var armPos2 = util.grid().at(3, 1, 2);
        var arm2 = util.select().position(3, 1, 2);
        var input = util.select().position(4, 1, 2);
        var inputPos = util.grid().at(4, 1, 2);
        var output = util.select().position(0, 1, 2);
        var outputPos = util.grid().at(0, 1, 2);
        var feast = util.select().position(2, 1, 4);
        var feastPos = util.grid().at(2, 1, 4);
        var pie = util.select().position(2, 1, 0);
        var piePos = util.grid().at(2, 1, 0);

        scene.world().showSection(feast.add(pie).add(output), Direction.DOWN);
        scene.world().setKineticSpeed(arm, 64);
        scene.overlay().showText(90)
                .text("Mechanical Arms can take servings from feasts and slices from pies")
                .independent()
                .placeNearTarget();
        scene.idle(10);
        scene.world().showSection(arm, Direction.DOWN);
        scene.idle(10);
        scene.world().setKineticSpeed(arm, 64);
        scene.overlay().showOutline(PonderPalette.INPUT, feast, feast, 40);
        scene.overlay().showOutline(PonderPalette.OUTPUT, output, output, 40);
        scene.idle(40);

        var roastChicken = ModItems.ROAST_CHICKEN.get().getDefaultInstance();
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 1);
        scene.idle(24);
        scene.world().modifyBlock(feastPos, state -> state.setValue(FeastBlock.SERVINGS, 3), false);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_OUTPUTS, roastChicken, -1);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, roastChicken, 0);
        scene.idle(24);
        scene.world().modifyBlockEntity(outputPos, DepotBlockEntity.class, be -> be.setHeldItem(roastChicken));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_INPUTS, ItemStack.EMPTY, -1);
        scene.idle(20);

        scene.world().modifyBlockEntity(outputPos, DepotBlockEntity.class, be -> be.setHeldItem(ItemStack.EMPTY));
        scene.overlay().showOutline(PonderPalette.INPUT, pie, pie, 40);
        scene.overlay().showOutline(PonderPalette.OUTPUT, output, output, 40);
        var pieSlice = ModItems.APPLE_PIE_SLICE.get().getDefaultInstance();
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
        scene.idle(24);
        scene.world().modifyBlock(piePos, state -> state.setValue(PieBlock.BITES, 1), false);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_OUTPUTS, pieSlice, -1);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, pieSlice, 0);
        scene.idle(24);
        scene.world().modifyBlockEntity(outputPos, DepotBlockEntity.class, be -> be.setHeldItem(pieSlice));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_INPUTS, ItemStack.EMPTY, -1);
        scene.idle(20);

        scene.rotateCameraY(180);
        scene.world().modifyBlockEntity(outputPos, DepotBlockEntity.class, be -> be.setHeldItem(ItemStack.EMPTY));
        scene.world().modifyBlock(feastPos, state -> state.setValue(FeastBlock.SERVINGS, 1), false);
        scene.overlay().showText(60)
                .text("The last serving is kept in place")
                .pointAt(util.vector().centerOf(2, 1, 4))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(65);

        scene.rotateCameraY(-180);
        scene.world().hideSection(arm.add(pie), Direction.UP);
        scene.world().showSection(input.add(arm2), Direction.DOWN);
        scene.idle(10);
        scene.world().setKineticSpeed(arm2, -64);
        scene.idle(10);
        var roastChickenBlock = ModItems.ROAST_CHICKEN_BLOCK.get().getDefaultInstance();
        scene.world().modifyBlockEntity(inputPos, DepotBlockEntity.class, be -> be.setHeldItem(roastChickenBlock));
        scene.overlay().showText(70)
                .text("A fresh whole food can replace it and return the last serving")
                .independent()
                .attachKeyFrame()
                .placeNearTarget();
        scene.overlay().showOutline(PonderPalette.INPUT, input, input.add(feast), 50);
        scene.overlay().showOutline(PonderPalette.OUTPUT, output, output, 50);
        scene.idle(30);
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 1);
        scene.idle(24);
        scene.world().modifyBlockEntity(inputPos, DepotBlockEntity.class, be -> be.setHeldItem(ItemStack.EMPTY));
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.SEARCH_INPUTS, roastChickenBlock, -1);
        scene.idle(20);
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.MOVE_TO_INPUT, roastChickenBlock, 0);
        scene.idle(24);
        scene.world().modifyBlock(feastPos, state -> state.setValue(FeastBlock.SERVINGS, 4), false);
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.SEARCH_OUTPUTS, roastChicken, -1);
        scene.idle(20);
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, roastChicken, 0);
        scene.idle(24);
        scene.world().modifyBlockEntity(outputPos, DepotBlockEntity.class, be -> be.setHeldItem(roastChicken));
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.SEARCH_INPUTS, ItemStack.EMPTY, -1);
        scene.idle(20);
    }

    public static void cookingPot(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("cooking_pot", "Automating with Create: Cooking Pot");
        scene.configureBasePlate(0, 0, 8);
        scene.scaleSceneView(0.77f);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(util.select().fromTo(1, 1, 2, 1, 2, 2).add(util.select().fromTo(0, 2, 3, 1, 2, 3)), Direction.DOWN);
        scene.overlay().showText(60)
                .sharedText(CCKCommon.asResource("package_automate_ingredient_insertion"))
                .pointAt(util.vector().centerOf(1, 2, 3))
                .placeNearTarget();
        scene.idle(40);
        scene.world().showSection(util.select().fromTo(4, 1, 4, 6, 3, 6), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(3, 2, 5), Direction.DOWN);
        scene.idle(5);
        var belt1 = util.select().fromTo(3, 1, 5, 2, 1, 5);
        scene.world().showSection(belt1, Direction.EAST);
        scene.idle(5);
        var belt2 = util.select().fromTo(1, 1, 3, 1, 1, 5);
        scene.world().showSection(belt2, Direction.NORTH);
        scene.idle(5);
        scene.world().showSection(util.select().position(1, 2, 4).add(util.select().position(2, 2, 5)), Direction.DOWN);
        scene.idle(5);
        scene.world().setKineticSpeed(belt1, 64);
        scene.idle(5);
        scene.world().setKineticSpeed(belt2, -64);
        scene.idle(5);

        scene.world().showSection(util.select().fromTo(4, 1, 1, 6, 3, 2).add(util.select().position(3, 3, 5)), Direction.DOWN);
        scene.overlay().showText(80)
                .sharedText(CCKCommon.asResource("useful_factory_gauges"))
                .pointAt(util.vector().centerOf(1, 2, 3))
                .attachKeyFrame()
                .placeNearTarget();
        var outFG = util.grid().at(4, 2, 1);
        builder.world().modifyBlockEntity(outFG, FactoryPanelBlockEntity.class, be -> {
            var panel = be.panels.get(FactoryPanelBlock.PanelSlot.TOP_RIGHT);
            panel.addConnection(new FactoryPanelPosition(util.grid().at(5, 2, 1), FactoryPanelBlock.PanelSlot.BOTTOM_RIGHT));
            panel.addConnection(new FactoryPanelPosition(util.grid().at(5, 3, 1), FactoryPanelBlock.PanelSlot.BOTTOM_RIGHT));
            panel.addConnection(new FactoryPanelPosition(util.grid().at(6, 2, 1), FactoryPanelBlock.PanelSlot.TOP_RIGHT));
            panel.addConnection(new FactoryPanelPosition(util.grid().at(6, 3, 1), FactoryPanelBlock.PanelSlot.TOP_RIGHT));
        });
        scene.idle(40);
        builder.world().modifyBlockEntity(outFG, FactoryPanelBlockEntity.class, be -> {
            var panel = be.panels.get(FactoryPanelBlock.PanelSlot.TOP_RIGHT);
            panel.count = 4;
        });
        scene.idle(10);
        PonderHilo.linkEffect(scene, util.grid().at(3, 3, 5));
        ItemStack pack = PackageItem.containing(List.of(Items.CARROT.getDefaultInstance(), ModItems.CABBAGE_LEAF.get().getDefaultInstance(),
                ModItems.ONION.get().getDefaultInstance(), ModItems.CHICKEN_CUTS.get().getDefaultInstance()));
        var outPackager = util.grid().at(3, 2, 5);
        PonderHilo.packagerCreate(scene, outPackager, pack);
        scene.idle(5);
        scene.world().createItemOnBelt(util.grid().at(3, 1, 5), Direction.EAST, pack);
        PonderHilo.packagerClear(scene, outPackager);
        scene.idle(20);
        scene.world().removeItemsFromBelt(util.grid().at(1, 1, 4));
        PonderHilo.packagerUnpack(scene, util.grid().at(1, 2, 3), pack);
        scene.idle(10);

        scene.world().showSection(util.select().position(2, 1, 2)
                .add(util.select().fromTo(3, 1, 4, 3, 2, 4)), Direction.DOWN);
        scene.world().setKineticSpeed(util.select().position(2, 1, 2), 128);
        scene.world().modifyBlockEntity(util.grid().at(3, 1, 4), DepotBlockEntity.class, depot -> depot.setHeldItem(Items.BOWL.getDefaultInstance()));
        scene.overlay().showText(60)
                .sharedText(CCKCommon.asResource("arm_automate_container_insertion"))
                .pointAt(util.vector().centerOf(2, 1, 2))
                .attachKeyFrame()
                .placeNearTarget();
        var armPos = util.grid().at(2, 1, 2);
        var inputDepot = util.select().position(3, 1, 4);
        var cookingPot = util.select().position(1, 2, 2);
        scene.overlay().showOutline(PonderPalette.INPUT, inputDepot, inputDepot, 40);
        scene.overlay().showOutline(PonderPalette.OUTPUT, cookingPot, cookingPot, 40);
        scene.idle(40);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(util.grid().at(3, 1, 4), DepotBlockEntity.class, depot -> depot.setHeldItem(ItemStack.EMPTY));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_OUTPUTS, Items.BOWL.getDefaultInstance(), -1);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, Items.BOWL.getDefaultInstance(), 0);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, -1);
        scene.idle(10);

        scene.world().showSection(util.select().fromTo(0, 1, 0, 1, 1, 0), Direction.DOWN);
        scene.world().setKineticSpeed(util.select().position(0, 1, 0), 128);
        scene.overlay().showText(60)
                .sharedText(CCKCommon.asResource("arm_take_out_food"))
                .pointAt(util.vector().centerOf(2, 1, 1))
                .attachKeyFrame()
                .placeNearTarget();
        var armPos2 = util.grid().at(0, 1, 0);
        var outputDepot = util.select().position(1, 1, 0);
        scene.overlay().showOutline(PonderPalette.INPUT, cookingPot, cookingPot, 40);
        scene.overlay().showOutline(PonderPalette.OUTPUT, outputDepot, outputDepot, 40);
        scene.idle(40);
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
        scene.idle(20);
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.SEARCH_OUTPUTS, ModItems.CHICKEN_SOUP.get().getDefaultInstance(), -1);
        scene.idle(20);
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, ModItems.CHICKEN_SOUP.get().getDefaultInstance(), 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(util.grid().at(1, 1, 0), DepotBlockEntity.class, depot -> depot.setHeldItem(ModItems.CHICKEN_SOUP.get().getDefaultInstance()));
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, -1);
        scene.idle(10);

        scene.world().showSection(util.select().position(0, 2, 2), Direction.EAST);
        scene.overlay().showText(60)
                .text("Funnel also can take out cooked food")
                .pointAt(util.vector().centerOf(0, 2, 2))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(60);
    }

    public static void heatSource(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("heat_source", "Heat sources for Cooking");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(10);

        var boiler = scene.world().showIndependentSection(util.select().fromTo(3, 4, 1, 3, 5, 1).add(util.select().position(2, 5, 1)).add(util.select().position(0, 5, 1)), Direction.DOWN);
        var blazeBurner1 = scene.world().showIndependentSection(util.select().position(3, 3, 1), Direction.DOWN);
        scene.world().moveSection(blazeBurner1, new Vec3(0, -2, 0), 0);
        scene.world().moveSection(boiler, new Vec3(0, -2, 0), 0);
        scene.overlay().showText(60)
                .text("This is a Blaze Burner, a common boiler heater")
                .pointAt(util.vector().centerOf(3, 1, 1))
                .placeNearTarget();
        scene.idle(70);

        scene.world().hideIndependentSection(boiler, Direction.UP);
        var blazeBurner2 = scene.world().showIndependentSection(util.select().position(1, 3, 1), Direction.DOWN);
        scene.world().moveSection(blazeBurner2, new Vec3(0, -2, 0), 0);
        scene.overlay().showText(60)
                .text("Boiler heaters are valid heat sources for cooking utensils")
                .pointAt(util.vector().centerOf(1, 1, 1))
                .attachKeyFrame();
        scene.idle(10);
        scene.overlay().showControls(util.vector().blockSurface(util.grid().at(3, 2, 1), Direction.UP), Pointing.DOWN, 20).rightClick().withItem(ModBlocks.COOKING_POT.get().asItem().getDefaultInstance());
        scene.idle(10);
        scene.addInstruction(new FadeOutOfSceneInstruction<>(0, Direction.DOWN, blazeBurner1));
        scene.addInstruction(new DisplayWorldSectionInstruction(0, Direction.DOWN, util.select().fromTo(3, 1, 1, 3, 2, 1), scene.getScene()::getBaseWorldSection));
        scene.idle(20);
        scene.overlay().showControls(util.vector().blockSurface(util.grid().at(1, 2, 1), Direction.UP), Pointing.DOWN, 20).whileCTRL().rightClick().withItem(ModBlocks.SKILLET.get().asItem().getDefaultInstance());
        scene.idle(10);
        scene.addInstruction(new FadeOutOfSceneInstruction<>(0, Direction.DOWN, blazeBurner2));
        scene.addInstruction(new DisplayWorldSectionInstruction(0, Direction.DOWN, util.select().fromTo(1, 1, 1, 1, 2, 1), scene.getScene()::getBaseWorldSection));
        scene.idle(20);

        scene.world().showSection(util.select().fromTo(1, 1, 3, 1, 2, 3), Direction.DOWN);
        scene.world().hideSection(util.select().fromTo(3, 1, 1, 3, 2, 1), Direction.UP);
        scene.idle(10);
        scene.world().showSection(util.select().fromTo(3, 1, 2, 3, 2, 2), Direction.DOWN);
        scene.overlay().showText(120)
                .text("Boiler heaters accelerate some cooking utensils according to its heat-level")
                .attachKeyFrame()
                .pointAt(util.vector().centerOf(1, 1, 3));
        scene.idle(10);
        scene.world().modifyBlockEntity(util.grid().at(1, 2, 1), SkilletBlockEntity.class, be -> be.getInventory().insertItem(0, Items.PORKCHOP.getDefaultInstance(), false));
        scene.world().modifyBlockEntity(util.grid().at(1, 2, 3), SkilletBlockEntity.class, be -> be.getInventory().insertItem(0, Items.PORKCHOP.getDefaultInstance(), false));
        scene.world().modifyBlockEntity(util.grid().at(3, 2, 2), SkilletBlockEntity.class, be -> be.getInventory().insertItem(0, Items.PORKCHOP.getDefaultInstance(), false));
        scene.idle(20);
        scene.world().createItemEntity(util.vector().of(1.5, 2.3, 3.5), new Vec3(0, 0.25, -0.08F), Items.COOKED_PORKCHOP.getDefaultInstance());
        scene.world().modifyBlockEntity(util.grid().at(1, 2, 3), SkilletBlockEntity.class, SkilletBlockEntity::removeItem);
        scene.idle(20);
        scene.world().createItemEntity(util.vector().of(3.5, 2.3, 2.5), new Vec3(0, 0.25, -0.08F), Items.COOKED_PORKCHOP.getDefaultInstance());
        scene.world().modifyBlockEntity(util.grid().at(3, 2, 2), SkilletBlockEntity.class, SkilletBlockEntity::removeItem);
        scene.idle(40);
        scene.world().createItemEntity(util.vector().of(1.5, 2.3, 1.5), new Vec3(0, 0.25, -0.08F), Items.COOKED_PORKCHOP.getDefaultInstance());
        scene.world().modifyBlockEntity(util.grid().at(1, 2, 1), SkilletBlockEntity.class, SkilletBlockEntity::removeItem);
    }

    public static void stoveAndSkillet(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("skillet_and_stove", "Automating with Create: Skillet and Stove");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(10);

        var armPos = util.grid().at(3, 1, 1);
        var arm = util.select().position(3, 1, 1);
        var depot = util.select().position(2, 1, 1);
        var depotPos = util.grid().at(2, 1, 1);
        var stove = util.select().position(3, 1, 3);
        var stovePos = util.grid().at(3, 1, 3);
        var skillet = util.select().position(1, 2, 3);
        var skilletPos = util.grid().at(1, 2, 3);
        scene.world().showSection(stove.add(util.select().fromTo(1, 1, 3, 1, 2, 3).add(arm)), Direction.DOWN);
        scene.overlay().showText(60)
                .text("Mechanical Arm can put raw ingredient onto Stove and Skillet")
                .pointAt(util.vector().centerOf(3, 1, 1))
                .placeNearTarget();
        scene.idle(10);
        scene.world().showSection(depot, Direction.DOWN);
        scene.world().setKineticSpeed(arm, 64);
        scene.overlay().showOutline(PonderPalette.INPUT, depot, depot, 40);
        scene.overlay().showOutline(PonderPalette.OUTPUT, stove, stove.add(skillet), 40);
        scene.idle(40);
        scene.world().modifyBlockEntity(depotPos, DepotBlockEntity.class, be -> be.setHeldItem(Items.BEEF.getDefaultInstance()));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(depotPos, DepotBlockEntity.class, be -> be.setHeldItem(ItemStack.EMPTY));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_OUTPUTS, Items.BEEF.getDefaultInstance(), -1);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, Items.BEEF.getDefaultInstance(), 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(skilletPos, SkilletBlockEntity.class, be -> be.getInventory().insertItem(0, Items.BEEF.getDefaultInstance(), false));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, -1);
        scene.idle(10);
        scene.world().modifyBlockEntity(depotPos, DepotBlockEntity.class, be -> be.setHeldItem(Items.BEEF.getDefaultInstance()));
        scene.idle(10);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(depotPos, DepotBlockEntity.class, be -> be.setHeldItem(ItemStack.EMPTY));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_OUTPUTS, Items.BEEF.getDefaultInstance(), -1);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, Items.BEEF.getDefaultInstance(), 1);
        scene.idle(20);
        scene.world().modifyBlockEntity(stovePos, StoveBlockEntity.class, be -> be.getItems().insertItem(0, Items.BEEF.getDefaultInstance(), false));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, -1);
        scene.idle(10);
    }

    public static void cuttingBoard(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("cutting_board", "Automating with Create: Cutting");
        scene.configureBasePlate(0, 0, 7);
        scene.scaleSceneView(0.85f);
        var belt = util.select().fromTo(5, 0, 0, 5, 0, 6);
        var belt2 = util.select().fromTo(1, 0, 0, 1, 0, 6);
        scene.world().showSection(util.select().layer(0).substract(belt).substract(belt2), Direction.DOWN);
        var tempGround = scene.world().showIndependentSection(util.select().fromTo(5, 3, 0, 5, 3, 6), Direction.DOWN);
        var tempGround2 = scene.world().showIndependentSection(util.select().fromTo(1, 3, 0, 1, 3, 6), Direction.DOWN);
        scene.world().moveSection(tempGround, new Vec3(0, -3, 0), 0);
        scene.world().moveSection(tempGround2, new Vec3(0, -3, 0), 0);
        var tempBoard = scene.world().showIndependentSection(util.select().position(3, 3, 3), Direction.DOWN);
        scene.world().moveSection(tempBoard, new Vec3(0, -2, 0), 0);
        scene.idle(10);

        scene.overlay().showText(40)
                .text("This is a Cutting Board. Previously, it could semi-automated with Deployer")
                .pointAt(util.vector().centerOf(3, 1, 3))
                .placeNearTarget();
        scene.idle(40);
        scene.addInstruction(new FadeOutOfSceneInstruction<>(0, Direction.UP, tempBoard));
        scene.idle(10);

        scene.world().showSection(util.select().fromTo(3, 1, 1, 3, 1, 5).substract(belt), Direction.DOWN);
        scene.overlay().showText(40)
                .text("Now, Mechanical Arm can put raw ingredient on Cutting Board")
                .pointAt(util.vector().centerOf(3, 1, 3))
                .attachKeyFrame()
                .placeNearTarget();
        var armPos = util.grid().at(3, 1, 5);
        var arm = util.select().position(3, 1, 5);
        var depot = util.select().position(3, 1, 1);
        var depotPos = util.grid().at(3, 1, 1);
        var cuttingBoard = util.select().position(3, 1, 3);
        var cuttingBoardPos = util.grid().at(3, 1, 3);
        scene.world().setKineticSpeed(arm, 64);
        scene.overlay().showOutline(PonderPalette.INPUT, depot, depot, 40);
        scene.overlay().showOutline(PonderPalette.OUTPUT, cuttingBoard, cuttingBoard, 40);
        scene.idle(40);
        scene.world().modifyBlockEntity(depotPos, DepotBlockEntity.class, be -> be.setHeldItem(Items.CHICKEN.getDefaultInstance()));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(depotPos, DepotBlockEntity.class, be -> be.setHeldItem(ItemStack.EMPTY));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_OUTPUTS, Items.CHICKEN.getDefaultInstance(), -1);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, Items.CHICKEN.getDefaultInstance(), 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(cuttingBoardPos, CuttingBoardBlockEntity.class, be -> be.getInventory().insertItem(0, Items.CHICKEN.getDefaultInstance(), false));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, -1);
        scene.idle(10);

        var deployer = util.select().position(5, 2, 3);
        var deployerPos = util.grid().at(5, 2, 3);
        scene.world().hideIndependentSection(tempGround, Direction.DOWN);
        scene.world().showSection(belt.add(deployer), Direction.DOWN);
        var tool = ModItems.IRON_KNIFE.get().getDefaultInstance();
        if (!DatagenModLoader.isRunningDataGen()) {
            scene.world().modifyBlockEntityNBT(deployer, DeployerBlockEntity.class, nbt -> {
                nbt.put("HeldItem", tool.saveOptional(scene.world().getHolderLookupProvider())); // null in datagen
                nbt.putString("Mode", "USE");
            });
        }
        scene.overlay().showText(60)
                .text("Deployer with a knife in Use Mode can cut raw ingredient")
                .pointAt(util.vector().centerOf(5, 2, 3))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(10);
        scene.world().setKineticSpeed(belt.add(deployer), 32);
        var chicken = scene.world().createItemOnBelt(util.grid().at(5, 0, 1), Direction.DOWN, ModItems.CABBAGE.get().getDefaultInstance());
        scene.idle(30);
        scene.world().stallBeltItem(chicken, true);
        scene.world().moveDeployer(deployerPos, 1, 30);
        scene.idle(30);
        scene.world().changeBeltItemTo(chicken, new ItemStack(ModItems.CABBAGE_LEAF.get(), 2));
        scene.world().moveDeployer(deployerPos, -1, 30);
        scene.idle(10);
        scene.world().stallBeltItem(chicken, false);
        scene.idle(10);

        var saw = util.select().position(1, 0, 3);
        scene.world().hideIndependentSection(tempGround2, Direction.DOWN);
        scene.world().showSection(belt2, Direction.DOWN);
        scene.overlay().showText(60)
                .text("Mechanical Saw also can cut raw ingredient")
                .pointAt(util.vector().centerOf(1, 0, 3))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(10);
        scene.world().setKineticSpeed(belt2.substract(saw), 32);
        scene.world().setKineticSpeed(saw, -128);
        scene.world().createItemOnBelt(util.grid().at(1, 0, 1), Direction.DOWN, Items.CHICKEN.getDefaultInstance());
        scene.idle(60);
    }
}
