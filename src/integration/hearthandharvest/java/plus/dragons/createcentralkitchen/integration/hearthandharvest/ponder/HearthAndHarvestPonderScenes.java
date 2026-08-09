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
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModItems;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.PonderHilo;
import java.util.List;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HearthAndHarvestPonderScenes {
    public static void cask(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("hearth_and_harvest_cask", "Automating Hearth and Harvest Casks");
        scene.configureBasePlate(0, 0, 8);
        scene.scaleSceneView(0.77f);
        var caskPos = util.grid().at(1, 2, 2);
        var cask = util.select().position(caskPos);
        var packagerPos = util.grid().at(1, 2, 3);
        scene.world().modifyBlock(caskPos, state -> HHModBlocks.CASK.get().defaultBlockState(), false);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(
                util.select().fromTo(1, 1, 2, 1, 2, 3).add(util.select().fromTo(0, 2, 3, 1, 2, 3)),
                Direction.DOWN);
        scene.overlay().showText(70)
                .text("Packages are accepted only when they contain exactly one complete Cask recipe")
                .pointAt(util.vector().centerOf(packagerPos))
                .placeNearTarget();
        scene.idle(40);

        var salt = HHModItems.SALT.get().getDefaultInstance();
        var meat = new ItemStack(Items.BEEF, 3);
        var pack = PackageItem.containing(List.of(salt, meat));
        PonderHilo.packagerUnpack(scene, packagerPos, pack);
        scene.world().modifyBlockEntity(caskPos, CaskBlockEntity.class, blockEntity -> {
            var inventory = blockEntity.getInventory();
            inventory.setStackInSlot(0, salt.copy());
            inventory.setStackInSlot(1, meat.copyWithCount(1));
            inventory.setStackInSlot(2, meat.copyWithCount(1));
            inventory.setStackInSlot(3, meat.copyWithCount(1));
        });
        scene.idle(10);
        scene.overlay().showText(70)
                .text("Repeated ingredients are unpacked into separate input slots")
                .pointAt(util.vector().centerOf(caskPos))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(70);

        scene.overlay().showText(80)
                .text("The Cask still uses its normal aging time and processing rules")
                .pointAt(util.vector().centerOf(caskPos))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(80);
        var jerky = new ItemStack(HHModItems.JERKY.get(), 3);
        scene.world().modifyBlockEntity(caskPos, CaskBlockEntity.class, blockEntity -> {
            var inventory = blockEntity.getInventory();
            for (int slot = 0; slot < 4; slot++)
                inventory.setStackInSlot(slot, ItemStack.EMPTY);
            inventory.setStackInSlot(4, jerky.copy());
        });

        var armPos = util.grid().at(0, 1, 0);
        var arm = util.select().position(armPos);
        var outputPos = util.grid().at(1, 1, 0);
        var output = util.select().position(outputPos);
        scene.world().showSection(arm.add(output), Direction.DOWN);
        scene.world().setKineticSpeed(arm, 64);
        scene.overlay().showText(70)
                .text("Mechanical Arms extract finished items only from the output slot")
                .pointAt(util.vector().centerOf(armPos))
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
