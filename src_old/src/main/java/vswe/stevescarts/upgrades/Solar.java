package vswe.stevescarts.upgrades;

import vswe.stevescarts.blocks.tileentities.TileEntityUpgrade;
import vswe.stevescarts.helpers.Localization;

public class Solar extends RechargerBase {
	@Override
	protected int getAmount(final TileEntityUpgrade upgrade) {
		if (upgrade.getPos().getY() > upgrade.getMaster().getPos().getY()) {
			return 400;
		}
		if (upgrade.getPos().getY() < upgrade.getMaster().getPos().getY()) {
			return 0;
		}
		return 240;
	}

	@Override
	protected boolean canGenerate(final TileEntityUpgrade upgrade) {
		return upgrade.getWorld().getLight(upgrade.getPos()) == 15 && upgrade.getWorld().canSeeSky(upgrade.getPos().up());
	}

	@Override
	public String getName() {
		return Localization.UPGRADES.SOLAR.translate();
	}
}
