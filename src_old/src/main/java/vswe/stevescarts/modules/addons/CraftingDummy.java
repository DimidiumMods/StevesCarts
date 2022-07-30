package vswe.stevescarts.modules.addons;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import javax.annotation.Nonnull;

public class CraftingDummy extends InventoryCrafting {
	private int inventoryWidth;
	private ModuleCrafter module;

	public CraftingDummy(final ModuleCrafter module) {
		super(null, 3, 3);
		inventoryWidth = 3;
		this.module = module;
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(final int par1) {
		return (par1 >= getSizeInventory()) ? ItemStack.EMPTY : module.getStack(par1);
	}

	@Override
	@Nonnull
	public ItemStack getStackInRowAndColumn(final int par1, final int par2) {
		if (par1 >= 0 && par1 < inventoryWidth) {
			final int k = par1 + par2 * inventoryWidth;
			return getStackInSlot(k);
		}
		return ItemStack.EMPTY;
	}

	@Nonnull
	public ItemStack getStackInSlotOnClosing(final int par1) {
		return ItemStack.EMPTY;
	}

	@Override
	@Nonnull
	public ItemStack decrStackSize(final int par1, final int par2) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(final int par1, @Nonnull ItemStack par2ItemStack) {
	}

	public void update() {
		module.setStack(9, getResult());
	}

	@Nonnull
	public ItemStack getResult() {
		return CraftingManager.findMatchingResult(this, module.getCart().world);
	}

	public IRecipe getRecipe() {
		for (IRecipe irecipe : CraftingManager.REGISTRY) {
			if (irecipe.matches(this, module.getCart().world)) {
				return irecipe;
			}
		}
		return null;
	}
}
