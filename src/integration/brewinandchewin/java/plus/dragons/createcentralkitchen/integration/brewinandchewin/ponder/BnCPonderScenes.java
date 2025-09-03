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

package plus.dragons.createcentralkitchen.integration.brewinandchewin.ponder;

import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlock;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlockEntity;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelPosition;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.PonderHilo;
import java.util.List;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.fluids.FluidStack;
import plus.dragons.createcentralkitchen.common.CCKCommon;
import umpaz.brewinandchewin.common.block.entity.KegBlockEntity;
import umpaz.brewinandchewin.common.registry.BnCFluids;
import umpaz.brewinandchewin.common.registry.BnCItems;

public class BnCPonderScenes {
    public static void kegAutomate1(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("keg.part_one", "Automating with Create: Keg - Part One");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(util.select().fromTo(1, 1, 1, 1, 2, 3), Direction.DOWN);
        scene.overlay().showText(60)
                .text("Pipe can insert fluids directly into Keg")
                .pointAt(util.vector().centerOf(1, 1, 2))
                .placeNearTarget();
        scene.idle(10);
        var pumpIn = util.select().position(1, 1, 2);
        scene.world().setKineticSpeed(pumpIn, 64);
        scene.idle(60);
        scene.world().setKineticSpeed(pumpIn, 0);
        scene.idle(10);

        if (!DatagenModLoader.isRunningDataGen()) {
            scene.world().modifyBlockEntityNBT(util.select().position(1, 1, 1), KegBlockEntity.class, nbt -> {
                var wineTag = new CompoundTag();
                var r = scene.world().getHolderLookupProvider(); // null in datagen
                var wine = new FluidStack(BnCFluids.STEEL_TOE_STOUT, 1000);
                wineTag.put("Fluid", wine.save(r));
                nbt.put("FluidTank", wineTag);
            });
        }
        scene.world().showSection(util.select().fromTo(2, 1, 1, 3, 2, 3), Direction.DOWN);
        scene.overlay().showText(60)
                .text("Pipe can also extract fluids from Keg")
                .pointAt(util.vector().centerOf(2, 1, 1))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(10);
        var pumpIn2 = util.select().position(2, 1, 1);
        scene.world().setKineticSpeed(pumpIn2, 64);
        scene.idle(60);
        scene.world().setKineticSpeed(pumpIn2, 0);
        scene.idle(10);

        scene.world().showSection(util.select().position(0, 1, 1), Direction.DOWN);
        scene.overlay().showText(60)
                .text("Funnel can extract special item product from Keg")
                .pointAt(util.vector().centerOf(0, 1, 1))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(60);
    }

    public static void kegAutomate2(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("keg.part_two", "Automating with Create: Keg - Part Two");
        scene.configureBasePlate(0, 0, 8);
        scene.scaleSceneView(0.77f);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(util.select().fromTo(0, 1, 1, 1, 2, 1).add(util.select().position(1, 2, 2)), Direction.DOWN);
        scene.overlay().showText(60)
                .sharedText(CCKCommon.asResource("package_automate_ingredient_insertion"))
                .pointAt(util.vector().centerOf(1, 2, 2))
                .placeNearTarget();
        scene.idle(40);
        scene.world().showSection(util.select().fromTo(4, 1, 4, 6, 3, 6), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(3, 2, 5), Direction.DOWN);
        scene.idle(5);
        var belt1 = util.select().fromTo(3, 1, 5, 2, 1, 5);
        scene.world().showSection(belt1, Direction.EAST);
        scene.idle(5);
        var belt2 = util.select().fromTo(1, 1, 2, 1, 1, 5);
        scene.world().showSection(belt2, Direction.NORTH);
        scene.idle(5);
        scene.world().showSection(util.select().position(1, 2, 3).add(util.select().position(2, 2, 5)), Direction.DOWN);
        scene.idle(5);
        scene.world().setKineticSpeed(belt1, 64);
        scene.idle(5);
        scene.world().setKineticSpeed(belt2, -64);
        scene.idle(5);

        scene.world().showSection(util.select().fromTo(4, 1, 1, 6, 3, 2).add(util.select().position(3, 3, 5)), Direction.DOWN);
        scene.overlay().showText(80)
                .sharedText(CCKCommon.asResource("useful_factory_gauges"))
                .pointAt(util.vector().centerOf(1, 2, 1))
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
        ItemStack pack = PackageItem.containing(List.of(Items.WHEAT.getDefaultInstance(), Items.NETHER_WART.getDefaultInstance(), Items.IRON_BARS.getDefaultInstance(), Items.CRIMSON_FUNGUS.getDefaultInstance()));
        var outPackager = util.grid().at(3, 2, 5);
        PonderHilo.packagerCreate(scene, outPackager, pack);
        scene.idle(5);
        scene.world().createItemOnBelt(util.grid().at(3, 1, 5), Direction.EAST, pack);
        PonderHilo.packagerClear(scene, outPackager);
        scene.idle(30);
        scene.world().removeItemsFromBelt(util.grid().at(1, 1, 3));
        PonderHilo.packagerUnpack(scene, util.grid().at(1, 2, 2), pack);
        scene.idle(10);

        scene.world().showSection(util.select().fromTo(2, 1, 0, 2, 2, 1), Direction.DOWN);
        scene.world().hideSection(util.select().position(2, 0, 1), Direction.DOWN);
        var through = scene.world().showIndependentSection(util.select().fromTo(2, 4, 1, 2, 5, 1), Direction.DOWN);
        scene.world().moveSection(through, new Vec3(0, -5, 0), 0);
        scene.world().setKineticSpeed(util.select().position(2, 1, 1), 128);
        scene.overlay().showText(60)
                .sharedText(CCKCommon.asResource("pipe_insert_liquid_ingredient"))
                .pointAt(util.vector().centerOf(2, 1, 1))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(60);
    }

    public static void kegPouring(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("keg.pouring", "Automating with Create: Drinks Production");
        scene.configureBasePlate(0, 0, 7);
        scene.scaleSceneView(0.8f);
        scene.world().showSection(util.select().everywhere(), Direction.DOWN);
        scene.world().setKineticSpeed(util.select().fromTo(1, 1, 2, 1, 1, 6).add(util.select().fromTo(1, 1, 1, 6, 1, 1)), -32);
        scene.idle(10);

        scene.world().setKineticSpeed(util.select().position(2, 3, 4), 128);
        scene.overlay().showText(60)
                .text("Use spout to fill Tankard with drink")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().centerOf(1, 3, 4));
        scene.idle(10);
        var tankard = scene.world().createItemOnBelt(util.grid().at(1, 1, 6), Direction.DOWN, BnCItems.TANKARD.getDefaultInstance());
        scene.idle(30);
        scene.world().stallBeltItem(tankard, true);
        scene.world().modifyBlockEntityNBT(util.select().position(1, 3, 4), SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().changeBeltItemTo(tankard, BnCItems.DREAD_NOG.getDefaultInstance());
        scene.world().stallBeltItem(tankard, false);
        scene.idle(25);

        scene.world().setKineticSpeed(util.select().position(4, 1, 2), -128);
        scene.overlay().showText(60)
                .text("Pour drink out of Tankard with Item Drain")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().centerOf(4, 1, 2));
        scene.idle(70);
    }
}
