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

package plus.dragons.createcentralkitchen.integration.hearthandharvest.ponder;

import alabaster.hearthandharvest.common.block.entity.CaskBlockEntity;
import alabaster.hearthandharvest.common.registry.HHModItems;
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
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import plus.dragons.createcentralkitchen.common.CCKCommon;

public class HearthAndHarvestPonderScenes {
    public static void cask(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("hearth_and_harvest_cask", "Automating Hearth and Harvest Casks");
        scene.configureBasePlate(0, 0, 8);
        scene.scaleSceneView(0.77f);
        var caskPos = util.grid().at(1, 2, 2);
        var cask = util.select().position(caskPos);
        var packagerPos = util.grid().at(1, 2, 3);
        scene.showBasePlate();
        scene.idle(10);

        var caskStation = util.select().position(1, 1, 2).add(cask).add(util.select().position(packagerPos));
        scene.world().showSection(caskStation, Direction.DOWN);
        scene.overlay().showText(70)
                .text("Packages are accepted only when they contain exactly one complete Cask recipe")
                .pointAt(util.vector().centerOf(packagerPos))
                .placeNearTarget();
        scene.overlay().showOutline(PonderPalette.OUTPUT, cask, cask, 50);
        scene.idle(60);

        var storage = util.select().fromTo(4, 1, 4, 6, 3, 6);
        var recipePanels = util.select().fromTo(4, 1, 1, 6, 3, 2);
        var outgoingPackager = util.select().fromTo(3, 2, 5, 3, 3, 5);
        scene.world().showSection(storage, Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(recipePanels, Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(outgoingPackager, Direction.WEST);
        scene.idle(5);

        var firstBelt = util.select().fromTo(3, 1, 5, 2, 1, 5);
        var secondBelt = util.select().fromTo(1, 1, 3, 1, 1, 5);
        var beltFunnels = util.select().position(2, 2, 5).add(util.select().position(1, 2, 4));
        scene.world().showSection(firstBelt, Direction.EAST);
        scene.idle(5);
        scene.world().showSection(secondBelt, Direction.NORTH);
        scene.idle(5);
        scene.world().showSection(beltFunnels, Direction.DOWN);
        scene.world().setKineticSpeed(firstBelt, 64);
        scene.world().setKineticSpeed(secondBelt, -64);
        scene.idle(10);

        var outputPanelPos = util.grid().at(4, 2, 1);
        builder.world().modifyBlockEntity(outputPanelPos, FactoryPanelBlockEntity.class, blockEntity -> {
            var panel = blockEntity.panels.get(FactoryPanelBlock.PanelSlot.TOP_RIGHT);
            panel.addConnection(
                    new FactoryPanelPosition(util.grid().at(5, 2, 1), FactoryPanelBlock.PanelSlot.BOTTOM_RIGHT));
            panel.addConnection(
                    new FactoryPanelPosition(util.grid().at(5, 3, 1), FactoryPanelBlock.PanelSlot.BOTTOM_RIGHT));
            panel.addConnection(
                    new FactoryPanelPosition(util.grid().at(6, 2, 1), FactoryPanelBlock.PanelSlot.TOP_RIGHT));
            panel.addConnection(
                    new FactoryPanelPosition(util.grid().at(6, 3, 1), FactoryPanelBlock.PanelSlot.TOP_RIGHT));
        });
        scene.overlay().showText(70)
                .sharedText(CCKCommon.asResource("cask_recipe_panel"))
                .pointAt(util.vector().centerOf(outputPanelPos))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(50);
        builder.world().modifyBlockEntity(outputPanelPos, FactoryPanelBlockEntity.class, blockEntity -> {
            var panel = blockEntity.panels.get(FactoryPanelBlock.PanelSlot.TOP_RIGHT);
            panel.count = 3;
        });
        scene.idle(10);

        var salt = HHModItems.SALT.get().getDefaultInstance();
        var meat = Items.BEEF.getDefaultInstance();
        var pack = PackageItem.containing(List.of(salt.copy(), meat.copy(), meat.copy(), meat.copy()));
        var outgoingPackagerPos = util.grid().at(3, 2, 5);
        PonderHilo.linkEffect(scene, util.grid().at(3, 3, 5));
        PonderHilo.packagerCreate(scene, outgoingPackagerPos, pack);
        scene.idle(5);
        scene.world().createItemOnBelt(util.grid().at(3, 1, 5), Direction.EAST, pack);
        PonderHilo.packagerClear(scene, outgoingPackagerPos);
        scene.idle(30);
        scene.world().removeItemsFromBelt(util.grid().at(1, 1, 4));
        PonderHilo.packagerUnpack(scene, packagerPos, pack);
        scene.world().modifyBlockEntity(caskPos, CaskBlockEntity.class, blockEntity -> {
            var inventory = blockEntity.getInventory();
            inventory.setStackInSlot(0, salt.copy());
            inventory.setStackInSlot(1, meat.copy());
            inventory.setStackInSlot(2, meat.copy());
            inventory.setStackInSlot(3, meat.copy());
        });
        scene.idle(10);
        scene.overlay().showText(80)
                .text("Unpacking is atomic, and each repeated ingredient occupies its own input slot")
                .pointAt(util.vector().centerOf(caskPos))
                .attachKeyFrame()
                .placeNearTarget();
        scene.overlay()
                .showControls(util.vector().topOf(caskPos).add(-0.25, 0, 0), Pointing.DOWN, 60)
                .withItem(salt);
        scene.overlay()
                .showControls(util.vector().topOf(caskPos).add(0.25, 0, 0), Pointing.DOWN, 60)
                .withItem(meat.copyWithCount(3));
        scene.idle(80);

        scene.overlay().showText(80)
                .text("The Cask then uses its normal 1,200-tick aging time and processing rules")
                .pointAt(util.vector().centerOf(caskPos))
                .attachKeyFrame()
                .placeNearTarget();
        scene.overlay().showControls(util.vector().topOf(caskPos), Pointing.DOWN, 60).withItem(Items.CLOCK.getDefaultInstance());
        scene.idle(80);
        var jerky = new ItemStack(HHModItems.JERKY.get(), 3);
        scene.world().modifyBlockEntity(caskPos, CaskBlockEntity.class, blockEntity -> {
            var inventory = blockEntity.getInventory();
            for (int slot = 0; slot < 4; slot++)
                inventory.setStackInSlot(slot, ItemStack.EMPTY);
            inventory.setStackInSlot(4, jerky.copy());
        });
        scene.effects().indicateSuccess(caskPos);
        scene.idle(20);

        var armPos = util.grid().at(0, 1, 0);
        var arm = util.select().position(armPos);
        var outputPos = util.grid().at(1, 1, 0);
        var output = util.select().position(outputPos);
        scene.world().showSection(arm.add(output), Direction.DOWN);
        scene.world().setKineticSpeed(arm, 64);
        scene.overlay().showText(70)
                .text("Mechanical Arms ignore the aging inputs and extract only the finished output")
                .pointAt(util.vector().centerOf(caskPos))
                .attachKeyFrame()
                .placeNearTarget();
        scene.overlay().showOutline(PonderPalette.INPUT, cask, cask, 60);
        scene.overlay().showOutline(PonderPalette.OUTPUT, output, output, 60);
        scene.idle(30);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(caskPos, CaskBlockEntity.class, blockEntity -> blockEntity
                .getInventory()
                .setStackInSlot(4, ItemStack.EMPTY));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_OUTPUTS, jerky, -1);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, jerky, 0);
        scene.idle(20);
        scene.world().modifyBlockEntity(outputPos, DepotBlockEntity.class, depot -> depot.setHeldItem(jerky));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_INPUTS, ItemStack.EMPTY, -1);
        scene.idle(20);
    }
}
