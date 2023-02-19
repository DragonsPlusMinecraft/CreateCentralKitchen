package plus.dragons.createcentralkitchen.modules.farmersdelight.integration.jei.transfer;

import com.simibubi.create.foundation.gui.container.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.gui.container.GhostItemContainer;
import com.simibubi.create.foundation.gui.container.GhostItemSubmitPacket;
import com.simibubi.create.foundation.networking.AllPackets;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideMenu;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/*
MIT License

Copyright (c) 2019 simibubi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlazeStoveGuideGhostIngredientHandler<M extends BlazeStoveGuideMenu<?>, S extends AbstractSimiContainerScreen<M>>
	implements IGhostIngredientHandler<S> {

	@Override
	public <I> List<Target<I>> getTargets(S gui, I ingredient, boolean doStart) {
		List<Target<I>> targets = new ArrayList<>();

		if (ingredient instanceof ItemStack) {
			for (int i = 36; i < 36 + gui.getMenu().getInputSize(); i++) {
				if (gui.getMenu().slots.get(i).isActive())
					targets.add(new GhostTarget<>(gui, i - 36));
			}
		}

		return targets;
	}

	@Override
	public void onComplete() {}

	@Override
	public boolean shouldHighlightTargets() {
		return true;
	}

	private static class GhostTarget<I, T extends GhostItemContainer<?>> implements Target<I> {

		private final Rect2i area;
		private final AbstractSimiContainerScreen<T> gui;
		private final int slotIndex;

		public GhostTarget(AbstractSimiContainerScreen<T> gui, int slotIndex) {
			this.gui = gui;
			this.slotIndex = slotIndex;
			Slot slot = gui.getMenu().slots.get(slotIndex + 36);
			this.area = new Rect2i(gui.getGuiLeft() + slot.x, gui.getGuiTop() + slot.y, 16, 16);
		}

		@Override
		public Rect2i getArea() {
			return area;
		}

		@Override
		public void accept(I ingredient) {
			ItemStack stack = ((ItemStack) ingredient).copy();
			stack.setCount(1);
			gui.getMenu().ghostInventory.setStackInSlot(slotIndex, stack);

			AllPackets.channel.sendToServer(new GhostItemSubmitPacket(stack, slotIndex));
		}
	}
	
}
