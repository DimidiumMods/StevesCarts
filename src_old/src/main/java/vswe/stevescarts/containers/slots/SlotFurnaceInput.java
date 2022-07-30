package vswe.stevescarts.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import javax.annotation.Nonnull;

public class SlotFurnaceInput extends SlotFake {
	public SlotFurnaceInput(final IInventory iinventory, final int i, final int j, final int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack itemstack) {
		return !FurnaceRecipes.instance().getSmeltingResult(itemstack).isEmpty();
	}
}
