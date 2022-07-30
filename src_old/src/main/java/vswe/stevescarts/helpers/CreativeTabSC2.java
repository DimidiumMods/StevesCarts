package vswe.stevescarts.helpers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class CreativeTabSC2 extends CreativeTabs {
	@Nonnull
	private ItemStack item;

	public CreativeTabSC2(final String label) {
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	public ItemStack getIcon() {
		return item;
	}

	public void setIcon(@Nonnull ItemStack item) {
		this.item = item;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	public ItemStack createIcon() {
		return ItemStack.EMPTY;
	}
	
}
