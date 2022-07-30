package vswe.stevescarts.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.items.ModItems;

import javax.annotation.Nonnull;

public class SlotModule extends Slot {
	public SlotModule(final IInventory iinventory, final int i, final int j, final int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(
		@Nonnull
			ItemStack itemstack) {
		return itemstack.getItem() == ModItems.MODULES;
	}
}
