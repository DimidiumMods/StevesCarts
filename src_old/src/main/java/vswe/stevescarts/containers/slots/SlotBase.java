package vswe.stevescarts.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotBase extends Slot {
	private int x;
	private int y;

	public SlotBase(final IInventory inventory, final int i, final int j, final int k) {
		super(inventory, i, j, k);
		x = j;
		y = k;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean containsValidItem() {
		return !getStack().isEmpty() && isItemValid(getStack());
	}
}
