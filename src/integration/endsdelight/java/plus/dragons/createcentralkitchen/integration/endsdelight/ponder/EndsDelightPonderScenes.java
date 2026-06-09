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

package plus.dragons.createcentralkitchen.integration.endsdelight.ponder;

import cn.foggyhillside.ends_delight.block.DragonLegBlock;
import cn.foggyhillside.ends_delight.registry.ModItems;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EndsDelightPonderScenes {
    public static void dragonLeg(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("dragon_leg", "Automating with Create: Serving Dragon Leg");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(10);

        var armPos = util.grid().at(2, 1, 1);
        var arm = util.select().position(2, 1, 1);
        var input = util.select().position(0, 1, 2);
        var inputPos = util.grid().at(0, 1, 2);
        var output = util.select().position(4, 1, 2);
        var outputPos = util.grid().at(4, 1, 2);
        var leg = util.select().fromTo(2, 1, 3, 2, 1, 4);
        var legHeadPos = util.grid().at(2, 1, 3);
        var legFootPos = util.grid().at(2, 1, 4);

        scene.world().showSection(leg.add(arm).add(input).add(output).add(util.select().position(3, 1, 2)), Direction.DOWN);
        scene.world().setKineticSpeed(arm, 64);
        scene.world().modifyBlockEntity(inputPos, DepotBlockEntity.class, be -> be.setHeldItem(Items.BOWL.getDefaultInstance()));
        scene.idle(10);
        scene.overlay().showText(60)
                .text("Dragon Legs can be served by Mechanical Arms with Bowls")
                .pointAt(util.vector().centerOf(2, 1, 3))
                .placeNearTarget();
        scene.idle(10);
        scene.overlay().showOutline(PonderPalette.INPUT, input, input, 40);
        scene.overlay().showOutline(PonderPalette.INPUT, leg, leg, 40);
        scene.overlay().showOutline(PonderPalette.OUTPUT, output, output, 40);
        scene.idle(40);

        var serving = ModItems.DRAGON_LEG_WITH_SAUCE.get().getDefaultInstance();
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, ItemStack.EMPTY, 0);
        scene.idle(24);
        scene.world().modifyBlockEntity(inputPos, DepotBlockEntity.class, be -> be.setHeldItem(ItemStack.EMPTY));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_INPUTS, Items.BOWL.getDefaultInstance(), -1);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_INPUT, Items.BOWL.getDefaultInstance(), 1);
        scene.idle(24);
        scene.world().modifyBlock(legHeadPos, state -> state.setValue(DragonLegBlock.SERVINGS, 5), false);
        scene.world().modifyBlock(legFootPos, state -> state.setValue(DragonLegBlock.SERVINGS, 5), false);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_OUTPUTS, serving, -1);
        scene.idle(20);
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.MOVE_TO_OUTPUT, serving, 0);
        scene.idle(24);
        scene.world().modifyBlockEntity(outputPos, DepotBlockEntity.class, be -> be.setHeldItem(serving));
        scene.world().instructArm(armPos, ArmBlockEntity.Phase.SEARCH_INPUTS, ItemStack.EMPTY, -1);
        scene.idle(20);

        scene.world().modifyBlock(legHeadPos, state -> state.setValue(DragonLegBlock.SERVINGS, 1), false);
        scene.world().modifyBlock(legFootPos, state -> state.setValue(DragonLegBlock.SERVINGS, 1), false);
        scene.overlay().showText(60)
                .text("The last serving is kept in place")
                .pointAt(util.vector().centerOf(2, 1, 3))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(60);
    }
}
