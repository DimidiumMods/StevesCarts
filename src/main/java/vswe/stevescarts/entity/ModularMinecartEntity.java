package vswe.stevescarts.entity;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.network.SpawnPacket;
import vswe.stevescarts.entity.network.UpdatePacket;
import vswe.stevescarts.item.ModularMinecartItem;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleStorage;
import vswe.stevescarts.screen.ModularCartHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO
public class ModularMinecartEntity extends AbstractMinecartEntity {
	public Map<Integer, MinecartModule> modules = new LinkedHashMap<>();

	public ModularMinecartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	public ModularMinecartEntity(World world, double x, double y, double z, Collection<MinecartModule> modules) {
		super(StevesCarts.MODULAR_MINECART_ENTITY, world, x, y, z);
		modules.forEach(module -> this.addModule(module, false));
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return SpawnPacket.create(this);
	}

	@Override
	public Type getMinecartType() {
		return null;
	}

	@Override
	public ItemStack getPickBlockStack() {
		return ModularMinecartItem.create(this.getModuleList());
	}

	public Map<Integer, MinecartModule> getModules() {
		return this.modules;
	}

	public Collection<MinecartModule> getModuleList() {
		return this.modules.values();
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setModules(
				ModuleStorage.read(nbt, this)
						.stream()
						.collect(Collectors.toMap(MinecartModule::getId, Function.identity())),
				false
		);
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		ModuleStorage.write(nbt, this.getModuleList());
	}

	@Override
	public void tick() {
		super.tick();
		this.modules.values().forEach(MinecartModule::tick);
	}

	public void onScreenOpen() {
		this.modules.values().forEach(MinecartModule::onScreenOpen);
	}

	public void onScreenClose() {
		this.modules.values().forEach(MinecartModule::onScreenClose);
	}

	public void writeModuleData(PacketByteBuf buf) {
		buf.writeVarInt(this.modules.size());
		for (MinecartModule module : this.getModuleList()) {
			buf.writeVarInt(MinecartModuleType.REGISTRY.getRawId(module.getType()));
			buf.writeNbt(module.writeNbt(new NbtCompound()));
		}
	}

	public void setModuleData(Multimap<MinecartModuleType<?>, NbtCompound> moduleData, boolean update) {
		List<MinecartModule> modules = new ArrayList<>();
		moduleData.forEach((moduleType, nbt) -> {
			MinecartModule module = moduleType.createModule(this);
			module.readNbt(nbt);
			modules.add(module);
		});
		this.setModules(modules.stream().collect(Collectors.toMap(MinecartModule::getId, Function.identity())), update);
	}

	public static Multimap<MinecartModuleType<?>, NbtCompound> readModuleData(PacketByteBuf buf) {
		Multimap<MinecartModuleType<?>, NbtCompound> map = ArrayListMultimap.create();
		int size = buf.readVarInt();
		for (int i = 0; i < size; i++) {
			MinecartModuleType<?> type = MinecartModuleType.REGISTRY.get(buf.readVarInt());
			NbtCompound compound = buf.readNbt();
			map.put(type, compound);
		}
		return map;
	}

	private void setModules(Map<Integer, MinecartModule> modules, boolean update) {
		this.modules = modules;
		if (update) {
			UpdatePacket.sendToTrackers(this);
		}
	}

	public void addModule(int id, MinecartModule module, boolean update) {
		this.modules.put(id, module);
		module.setId(id);
		module.setMinecart(this);
		if (update) {
			UpdatePacket.sendToTrackers(this);
		}
	}

	public void addModule(MinecartModule module, boolean update) {
		addModule(nextId(), module, update);
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldRenderTop() {
		return this.getModuleList().stream().allMatch(module -> module.getType().shouldRenderTop());
	}

	private int nextId() {
		int id = 0;
		while (this.modules.containsKey(id)) {
			id++;
		}
		return id;
	}

	public void forceUpdate() {
		UpdatePacket.sendToTrackers(this);
	}

	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		if (!this.isAlive()) {
			return ActionResult.PASS;
		}
		if (!player.world.isClient) {
			player.openHandledScreen(this.new CartScreenHandlerFactory());
		}
		return ActionResult.success(player.world.isClient);
	}

	private class CartScreenHandlerFactory implements ExtendedScreenHandlerFactory {
		@Override
		public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
			buf.writeInt(ModularMinecartEntity.this.getId());
		}

		@Override
		public Text getDisplayName() {
			return ModularMinecartEntity.this.getDisplayName();
		}

		@Nullable
		@Override
		public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
			return new ModularCartHandler(syncId, inv, ModularMinecartEntity.this);
		}
	}
}
