package vswe.stevescarts.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import reborncore.common.util.FluidUtils;
import vswe.stevescarts.modules.engines.ModuleCoalBase;

import javax.annotation.Nonnull;

public class SlotFuel extends SlotBase {
	public SlotFuel(final IInventory iinventory, final int i, final int j, final int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(
		@Nonnull
			ItemStack itemstack) {
		return getItemBurnTime(itemstack) > 0;
	}

	private boolean isValid(
		@Nonnull
			ItemStack itemstack) {
		return FluidUtils.getFluidStackInContainer(itemstack) == null;
	}

	private int getItemBurnTime(
		@Nonnull
			ItemStack itemstack) {
		return isValid(itemstack) ? TileEntityFurnace.getItemBurnTime(itemstack) : 0;
	}

	public static int getItemBurnTime(final ModuleCoalBase engine,
	                                  @Nonnull
		                                  ItemStack itemstack) {
		return (int) (TileEntityFurnace.getItemBurnTime(itemstack) * engine.getFuelMultiplier());
	}
}
