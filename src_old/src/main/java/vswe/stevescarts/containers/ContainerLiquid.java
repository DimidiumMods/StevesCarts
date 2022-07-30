package vswe.stevescarts.containers;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.FluidStack;
import vswe.stevescarts.blocks.tileentities.TileEntityLiquid;
import vswe.stevescarts.containers.slots.SlotLiquidFilter;
import vswe.stevescarts.containers.slots.SlotLiquidManagerInput;
import vswe.stevescarts.containers.slots.SlotLiquidOutput;

public class ContainerLiquid extends ContainerManager {
	public FluidStack[] oldLiquids;

	public ContainerLiquid(final IInventory invPlayer, final TileEntityLiquid liquid) {
		super(liquid);
		oldLiquids = new FluidStack[4];
		for (int i = 0; i < 4; ++i) {
			final int x = i % 2;
			final int y = i / 2;
			addSlotToContainer(new SlotLiquidManagerInput(liquid, i, i * 3, (x == 0) ? 6 : 208, (y == 0) ? 17 : 80));
			addSlotToContainer(new SlotLiquidOutput(liquid, i * 3 + 1, (x == 0) ? 6 : 208, (y == 0) ? 42 : 105));
			addSlotToContainer(new SlotLiquidFilter(liquid, i * 3 + 2, (x == 0) ? 66 : 148, (y == 0) ? 12 : 110));
		}
		addPlayer(invPlayer);
	}

	@Override
	protected int offsetX() {
		return 35;
	}
}
