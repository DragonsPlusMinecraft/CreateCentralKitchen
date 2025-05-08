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

package plus.dragons.createcentralkitchen.client.ponder.scene;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlock;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlockEntity;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelPosition;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.PonderHilo;
import java.util.List;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FarmersDelightScene {
    public static void automate(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("cooking_pot.automate", "Automating with Create: Part One");
        scene.configureBasePlate(0, 0, 8);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(util.select().fromTo(1, 1, 2, 1, 2, 2).add(util.select().fromTo(0, 2, 3, 1, 2, 3)), Direction.DOWN);
        scene.overlay().showText(60)
                .text("Use Packager to automate ingredient insertion")
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
                .text("Factory gauges provide auto-filling for Cooking Pot ingredients")
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
                .text("Use Mechanical Arm to insert food container")
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
                .text("Mechanical Arm can take out cooked food")
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
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.SEARCH_OUTPUTS, ModItems.COOKED_RICE.get().getDefaultInstance(), -1);
        scene.idle(20);
        scene.world().instructArm(armPos2, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, ModItems.COOKED_RICE.get().getDefaultInstance(), 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(util.grid().at(1, 1, 0), DepotBlockEntity.class, depot -> depot.setHeldItem(ModItems.COOKED_RICE.get().getDefaultInstance()));
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
}
