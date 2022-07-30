package vswe.stevescarts.containers.slots;

import net.minecraft.item.ItemStack;
import vswe.stevescarts.blocks.tileentities.TileEntityUpgrade;
import vswe.stevescarts.helpers.storages.SCTank;

import javax.annotation.Nonnull;

public class SlotLiquidUpgradeInput extends SlotLiquidInput {
	private TileEntityUpgrade upgrade;

	public SlotLiquidUpgradeInput(final TileEntityUpgrade upgrade, final SCTank tank, final int maxsize, final int i, final int j, final int k) {
		super(upgrade, tank, maxsize, i, j, k);
		this.upgrade = upgrade;
	}

	@Override
	public int getSlotStackLimit() {
		return super.getSlotStackLimit();
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack itemstack) {
		return super.isItemValid(itemstack);
	}
}
