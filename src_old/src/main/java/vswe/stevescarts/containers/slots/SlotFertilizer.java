package vswe.stevescarts.containers.slots;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotFertilizer extends SlotBase {
	public SlotFertilizer(final IInventory iinventory, final int i, final int j, final int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(
		@Nonnull
			ItemStack itemstack) {
		return itemstack.getItem() == Items.BONE || (itemstack.getItem() == Items.DYE && itemstack.getItemDamage() == 15);
	}
}
