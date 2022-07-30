package vswe.stevescarts.upgrades;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import vswe.stevescarts.blocks.tileentities.TileEntityUpgrade;
import vswe.stevescarts.helpers.Localization;

public class ThermalFuel extends TankEffect {
	public static final int LAVA_EFFICIENCY = 3;

	@Override
	public int getTankSize() {
		return 12000;
	}

	@Override
	public String getName() {
		return Localization.UPGRADES.THERMAL.translate();
	}

	@Override
	public void update(final TileEntityUpgrade upgrade) {
		super.update(upgrade);
		if (!upgrade.getWorld().isRemote && upgrade.getMaster() != null && upgrade.tank.getFluid() != null) {
			final int fuelspace = upgrade.getMaster().getMaxFuelLevel() - upgrade.getMaster().getFuelLevel();
			final int unitspace = Math.min(fuelspace / LAVA_EFFICIENCY, 200);
			if (unitspace > 100 && upgrade.tank.getFluid().getFluid().equals(FluidRegistry.LAVA)) {
				final FluidStack drain = upgrade.tank.drain(unitspace, false);
				if (drain != null && drain.amount > 0) {
					upgrade.getMaster().setFuelLevel(upgrade.getMaster().getFuelLevel() + drain.amount * LAVA_EFFICIENCY);
					upgrade.tank.drain(unitspace, true);
				}
			}
		}
	}
}
