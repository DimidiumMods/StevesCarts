package vswe.stevescarts.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.modules.workers.tools.ModuleFarmer;

import javax.annotation.Nonnull;

public class SlotSeed extends SlotBase {
	private ModuleFarmer module;

	public SlotSeed(final IInventory iinventory, final ModuleFarmer module, final int i, final int j, final int k) {
		super(iinventory, i, j, k);
		this.module = module;
	}

	@Override
	public boolean isItemValid(
		@Nonnull
			ItemStack itemstack) {
		return module.isSeedValidHandler(itemstack);
	}
}
