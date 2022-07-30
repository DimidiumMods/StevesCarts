package vswe.stevescarts.blocks.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vswe.stevescarts.blocks.BlockUpgrade;
import vswe.stevescarts.blocks.ModBlocks;
import vswe.stevescarts.containers.ContainerBase;
import vswe.stevescarts.containers.ContainerUpgrade;
import vswe.stevescarts.guis.GuiBase;
import vswe.stevescarts.guis.GuiUpgrade;
import vswe.stevescarts.helpers.storages.ITankHolder;
import vswe.stevescarts.helpers.storages.SCTank;
import vswe.stevescarts.helpers.storages.TransferHandler;
import vswe.stevescarts.upgrades.AssemblerUpgrade;
import vswe.stevescarts.upgrades.InterfaceEffect;
import vswe.stevescarts.upgrades.InventoryEffect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityUpgrade extends TileEntityBase implements IInventory, ISidedInventory, ITankHolder, ITickable {
	public SCTank tank = new SCTank(this, 0, 0);
	private TileEntityCartAssembler master;
	private int type;
	private boolean initialized;
	private NBTTagCompound comp;
	private NonNullList<ItemStack> inventoryStacks;
	private int[] slotsForSide;
	private BlockUpgrade blockUpgrade = (BlockUpgrade) ModBlocks.UPGRADE.getBlock();
	private boolean isCreativeBroken;

	@SideOnly(Side.CLIENT)
	@Override
	public GuiBase getGui(final InventoryPlayer inv) {
		return new GuiUpgrade(inv, this);
	}

	@Override
	public ContainerBase getContainer(final InventoryPlayer inv) {
		return new ContainerUpgrade(inv, this);
	}

	public void setMaster(final TileEntityCartAssembler master, EnumFacing side) {
		this.master = master;
		if (world.getBlockState(pos).getBlock() instanceof BlockUpgrade) {
			if (side != null) {
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUpgrade.FACING, side).withProperty(BlockUpgrade.TYPE, getType()).withProperty(BlockUpgrade.CONNECTED, master != null));
			} else {
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockUpgrade.TYPE, getType()).withProperty(BlockUpgrade.CONNECTED, master != null));
			}
			markDirty();
		}
	}

	public EnumFacing getSide() {
		return world.getBlockState(pos).getValue(BlockUpgrade.FACING);
	}

	public TileEntityCartAssembler getMaster() {
		return master;
	}

	public void setType(final int type) {
		setType(type, true);
	}

	public void setType(final int type, boolean setBlockState) {
		this.type = type;
		if (setBlockState) {
			world.setBlockState(pos, blockUpgrade.getDefaultState().withProperty(BlockUpgrade.FACING, getSide()).withProperty(BlockUpgrade.TYPE, type).withProperty(BlockUpgrade.CONNECTED, getMaster() != null));
		}
		if (!initialized) {
			initialized = true;
			final AssemblerUpgrade upgrade = getUpgrade();
			if (upgrade != null) {
				comp = new NBTTagCompound();
				slotsForSide = new int[upgrade.getInventorySize()];
				upgrade.init(this);
				if (upgrade.getInventorySize() > 0) {
					inventoryStacks = NonNullList.withSize(upgrade.getInventorySize(), ItemStack.EMPTY);
					for (int i = 0; i < slotsForSide.length; ++i) {
						slotsForSide[i] = i;
					}
				}
			} else {
				inventoryStacks = null;
			}
		}
	}

	public int getType() {
		return type;
	}

	public NBTTagCompound getCompound() {
		return comp;
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	public AssemblerUpgrade getUpgrade() {
		return AssemblerUpgrade.getUpgrade(type);
	}

	public boolean hasInventory() {
		return inventoryStacks != null;
	}

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		setType(tagCompound.getByte("Type"), false);
		ItemStackHelper.loadAllItems(tagCompound, inventoryStacks);
		markDirty();
		final AssemblerUpgrade upgrade = getUpgrade();
		if (upgrade != null) {
			upgrade.load(this, tagCompound);
		}

	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		if (inventoryStacks != null) {
			ItemStackHelper.saveAllItems(tagCompound, inventoryStacks);
		}
		tagCompound.setByte("Type", (byte) type);
		final AssemblerUpgrade upgrade = getUpgrade();
		if (upgrade != null) {
			upgrade.save(this, tagCompound);
		}
		return tagCompound;
	}

	@Override
	public boolean isUsableByPlayer(final EntityPlayer entityplayer) {
		return world.getTileEntity(pos) == this && entityplayer.getDistanceSqToCenter(pos) <= 64.0;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public void updateEntity() {
		if (getUpgrade() != null && getMaster() != null) {
			getUpgrade().update(this);
		}
	}

	@Override
	public void initGuiData(final Container con, final IContainerListener crafting) {
		if (getUpgrade() != null) {
			final InterfaceEffect gui = getUpgrade().getInterfaceEffect();
			if (gui != null) {
				gui.checkGuiData(this, (ContainerUpgrade) con, crafting, true);
			}
		}
	}

	@Override
	public void checkGuiData(final Container con, final IContainerListener crafting) {
		if (getUpgrade() != null) {
			final InterfaceEffect gui = getUpgrade().getInterfaceEffect();
			if (gui != null) {
				gui.checkGuiData(this, (ContainerUpgrade) con, crafting, false);
			}
		}
	}

	@Override
	public void receiveGuiData(final int id, final short data) {
		if (getUpgrade() != null) {
			final InterfaceEffect gui = getUpgrade().getInterfaceEffect();
			if (gui != null) {
				gui.receiveGuiData(this, id, data);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		if (inventoryStacks != null) {
			return inventoryStacks.size();
		}
		if (master == null) {
			return 0;
		}
		return master.getSizeInventory();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : inventoryStacks) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(final int i) {
		if (inventoryStacks == null) {
			if (master == null) {
				return ItemStack.EMPTY;
			}
			return master.getStackInSlot(i);
		} else {
			if (i < 0 || i >= getSizeInventory()) {
				return ItemStack.EMPTY;
			}
			return inventoryStacks.get(i);
		}
	}

	@Override
	@Nonnull
	public ItemStack decrStackSize(final int i, final int j) {
		if (inventoryStacks == null) {
			if (master == null) {
				return ItemStack.EMPTY;
			}
			return master.decrStackSize(i, j);
		} else {
			if (i < 0 || i >= getSizeInventory()) {
				return ItemStack.EMPTY;
			}
			if (inventoryStacks.get(i).isEmpty()) {
				return ItemStack.EMPTY;
			}
			if (inventoryStacks.get(i).getCount() <= j) {
				@Nonnull
				ItemStack itemstack = inventoryStacks.get(i);
				inventoryStacks.set(i, ItemStack.EMPTY);
				markDirty();
				return itemstack;
			}
			@Nonnull
			ItemStack itemstack2 = inventoryStacks.get(i).splitStack(j);
			if (inventoryStacks.get(i).getCount() == 0) {
				inventoryStacks.set(i, ItemStack.EMPTY);
			}
			markDirty();
			return itemstack2;
		}
	}

	@Nullable
	@Override
	@Nonnull
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(final int i, @Nonnull ItemStack itemstack) {
		if (inventoryStacks == null) {
			if (master != null) {
				master.setInventorySlotContents(i, itemstack);
			}
		} else {
			if (i < 0 || i >= getSizeInventory()) {
				return;
			}
			inventoryStacks.set(i, itemstack);
			if (!itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit()) {
				itemstack.setCount(getInventoryStackLimit());
			}
			markDirty();
		}
	}

	public String getInventoryName() {
		return "container.assemblerupgrade";
	}

	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public void closeInventory() {
	}

	public void openInventory() {
	}

	@Nonnull
	public ItemStack getStackInSlotOnClosing(final int i) {
		if (inventoryStacks == null) {
			if (master == null) {
				return ItemStack.EMPTY;
			}
			return master.getStackInSlot(i);
		} else {
			@Nonnull
			ItemStack item = getStackInSlot(i);
			if (!item.isEmpty()) {
				setInventorySlotContents(i, ItemStack.EMPTY);
				return item;
			}
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (getUpgrade() != null) {
			final InventoryEffect inv = getUpgrade().getInventoryEffect();
			if (inv != null) {
				inv.onInventoryChanged(this);
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(final int slot, @Nonnull ItemStack item) {
		if (getUpgrade() != null) {
			final InventoryEffect inv = getUpgrade().getInventoryEffect();
			if (inv != null) {
				return inv.isItemValid(slot, item);
			}
		}
		return getMaster() != null && getMaster().isItemValidForSlot(slot, item);
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean canInsertItem(final int slot, @Nonnull ItemStack item, EnumFacing side) {
		if (getUpgrade() != null) {
			final InventoryEffect inv = getUpgrade().getInventoryEffect();
			if (inv != null) {
				return isItemValidForSlot(slot, item);
			}
		}
		return getMaster() != null && getMaster().canInsertItem(slot, item, side);
	}

	@Override
	public boolean canExtractItem(final int slot, @Nonnull ItemStack item, EnumFacing side) {
		if (getUpgrade() != null) {
			final InventoryEffect inv = getUpgrade().getInventoryEffect();
			if (inv != null) {
				return true;
			}
		}
		return getMaster() != null && getMaster().canExtractItem(slot, item, side);
	}

	@Override
	@Nonnull
	public ItemStack getInputContainer(final int tankid) {
		return getStackInSlot(0);
	}

	@Override
	public void setInputContainer(final int tankid, ItemStack stack) {
		setInventorySlotContents(0, stack);
	}

	@Override
	public void addToOutputContainer(final int tankid, @Nonnull ItemStack item) {
		TransferHandler.TransferItem(item, this, 1, 1, new ContainerUpgrade(null, this), Slot.class, null, -1);
	}

	@Override
	public void onFluidUpdated(final int tankid) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawImage(int tankid, GuiBase gui, TextureAtlasSprite sprite, int targetX, int targetY, int srcX, int srcY, int width, int height) {
		gui.drawIcon(sprite, gui.getGuiLeft() + targetX, gui.getGuiTop() + targetY, width / 16.0f, height / 16.0f, srcX / 16.0f, srcY / 16.0f);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) tank;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (getUpgrade() != null) {
			final InventoryEffect inv = getUpgrade().getInventoryEffect();
			if (inv != null) {
				return slotsForSide;
			}
		}
		if (getMaster() != null) {
			return getMaster().getSlotsForFace(side);
		}
		return new int[0];
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

	public void setCreativeBroken() {
		isCreativeBroken = true;
	}

	public boolean isCreativeBroken() {
		return isCreativeBroken;
	}
}
