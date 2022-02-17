package vswe.stevescarts.modules.tags;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.tag.Tag;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.modules.MinecartModuleType;

public class ModuleTags {
	public static final TagFactory<MinecartModuleType<?>> MODULE_TYPE = TagFactory.of(MinecartModuleType.REGISTRY_KEY, "tags/stevescarts/module_types");

	public static final Tag.Identified<MinecartModuleType<?>> HULLS = MODULE_TYPE.create(StevesCarts.id("hulls"));
	public static final Tag.Identified<MinecartModuleType<?>> NO_TOP_RENDER = MODULE_TYPE.create(StevesCarts.id("no_top_render"));

	public static void init() {
	}
}
