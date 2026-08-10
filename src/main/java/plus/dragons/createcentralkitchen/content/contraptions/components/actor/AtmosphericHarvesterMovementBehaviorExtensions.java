/*
 * Copyright (C) 2025 DragonsPlus
 * SPDX-License-Identifier: LGPL-3.0-or-later
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.ATMOSPHERIC)
public class AtmosphericHarvesterMovementBehaviorExtensions {
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            REGISTRY.put(AtmosphericBlocks.ORANGE.get(),
                    AtmosphericHarvesterMovementBehaviorExtensions::harvestOrange);
            REGISTRY.put(AtmosphericBlocks.BLOOD_ORANGE.get(),
                    AtmosphericHarvesterMovementBehaviorExtensions::harvestOrange);
        });
    }

    public static void harvestOrange(HarvesterMovementBehaviour behaviour,
            MovementContext context,
            BlockPos pos, BlockState state,
            boolean replant, boolean partial) {
        harvestOrange(context.world, pos, stack -> behaviour.dropItem(context, stack));
    }

    static void harvestOrange(Level level, BlockPos pos, Consumer<ItemStack> drop) {
        BlockHelper.destroyBlock(level, pos, 1, drop);
    }
}
