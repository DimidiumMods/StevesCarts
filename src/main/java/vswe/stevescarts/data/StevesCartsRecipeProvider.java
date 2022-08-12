package vswe.stevescarts.data;

import java.util.function.Consumer;

import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.module.ModuleType;

import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import static vswe.stevescarts.item.StevesCartsItems.ADVANCED_PCB;
import static vswe.stevescarts.item.StevesCartsItems.ADVANCED_SOLAR_PANEL;
import static vswe.stevescarts.item.StevesCartsItems.CHEST_LOCK;
import static vswe.stevescarts.item.StevesCartsItems.CHEST_PANE;
import static vswe.stevescarts.item.StevesCartsItems.FUSE;
import static vswe.stevescarts.item.StevesCartsItems.GALGADORIAN_WHEELS;
import static vswe.stevescarts.item.StevesCartsItems.GLASS_O_MAGIC;
import static vswe.stevescarts.item.StevesCartsItems.GRAPHICAL_INTERFACE;
import static vswe.stevescarts.item.StevesCartsItems.HUGE_CHEST_PANE;
import static vswe.stevescarts.item.StevesCartsItems.HUGE_DYNAMIC_PANE;
import static vswe.stevescarts.item.StevesCartsItems.HUGE_IRON_PANE;
import static vswe.stevescarts.item.StevesCartsItems.HUGE_TANK_PANE;
import static vswe.stevescarts.item.StevesCartsItems.IRON_WHEELS;
import static vswe.stevescarts.item.StevesCartsItems.LARGE_CHEST_PANE;
import static vswe.stevescarts.item.StevesCartsItems.LARGE_DYNAMIC_PANE;
import static vswe.stevescarts.item.StevesCartsItems.LARGE_IRON_PANE;
import static vswe.stevescarts.item.StevesCartsItems.LARGE_TANK_PANE;
import static vswe.stevescarts.item.StevesCartsItems.REFINED_HANDLE;
import static vswe.stevescarts.item.StevesCartsItems.REINFORCED_METAL;
import static vswe.stevescarts.item.StevesCartsItems.REINFORCED_WHEELS;
import static vswe.stevescarts.item.StevesCartsItems.SIMPLE_PCB;
import static vswe.stevescarts.item.StevesCartsItems.SOLAR_PANEL;
import static vswe.stevescarts.item.StevesCartsItems.TANK_PANE;
import static vswe.stevescarts.item.StevesCartsItems.TANK_VALVE;
import static vswe.stevescarts.item.StevesCartsItems.TRI_TORCH;
import static vswe.stevescarts.item.StevesCartsItems.WOODEN_WHEELS;
import static vswe.stevescarts.module.StevesCartsModules.ADVANCED_SOLAR_ENGINE;
import static vswe.stevescarts.module.StevesCartsModules.ADVANCED_TANK;
import static vswe.stevescarts.module.StevesCartsModules.ADVANCED_THERMAL_ENGINE;
import static vswe.stevescarts.module.StevesCartsModules.BRAKE;
import static vswe.stevescarts.module.StevesCartsModules.BRIDGE_BUILDER;
import static vswe.stevescarts.module.StevesCartsModules.COAL_ENGINE;
import static vswe.stevescarts.module.StevesCartsModules.EXTRACTING_CHESTS;
import static vswe.stevescarts.module.StevesCartsModules.FIREWORK_DISPLAY;
import static vswe.stevescarts.module.StevesCartsModules.FRONT_CHEST;
import static vswe.stevescarts.module.StevesCartsModules.FRONT_TANK;
import static vswe.stevescarts.module.StevesCartsModules.GALGADORIAN_HULL;
import static vswe.stevescarts.module.StevesCartsModules.HYDRATOR;
import static vswe.stevescarts.module.StevesCartsModules.INVISIBILITY_CORE;
import static vswe.stevescarts.module.StevesCartsModules.LARGE_RAILER;
import static vswe.stevescarts.module.StevesCartsModules.MECHANICAL_PIG;
import static vswe.stevescarts.module.StevesCartsModules.OPEN_TANK;
import static vswe.stevescarts.module.StevesCartsModules.RAILER;
import static vswe.stevescarts.module.StevesCartsModules.REINFORCED_HULL;
import static vswe.stevescarts.module.StevesCartsModules.SEAT;
import static vswe.stevescarts.module.StevesCartsModules.SIDE_CHESTS;
import static vswe.stevescarts.module.StevesCartsModules.SIDE_TANKS;
import static vswe.stevescarts.module.StevesCartsModules.SOLAR_ENGINE;
import static vswe.stevescarts.module.StevesCartsModules.STANDARD_HULL;
import static vswe.stevescarts.module.StevesCartsModules.THERMAL_ENGINE;
import static vswe.stevescarts.module.StevesCartsModules.TINY_COAL_ENGINE;
import static vswe.stevescarts.module.StevesCartsModules.TOP_CHEST;
import static vswe.stevescarts.module.StevesCartsModules.TOP_TANK;
import static vswe.stevescarts.module.StevesCartsModules.TORCH_PLACER;
import static vswe.stevescarts.module.StevesCartsModules.WOODEN_HULL;

public class StevesCartsRecipeProvider extends FabricRecipeProvider {
	public StevesCartsRecipeProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		createHull(exporter, WOODEN_HULL, ItemTags.PLANKS, WOODEN_WHEELS);
		createHull(exporter, STANDARD_HULL, Tags.IRON_INGOTS, IRON_WHEELS);
		createHull(exporter, REINFORCED_HULL, StevesCartsItems.REINFORCED_METAL, REINFORCED_WHEELS);
		createHull(exporter, MECHANICAL_PIG, Items.PORKCHOP, IRON_WHEELS);
		createHull(exporter, GALGADORIAN_HULL, StevesCartsItems.GALGADORIAN_METAL, GALGADORIAN_WHEELS);
		ShapedRecipeJsonBuilder.create(FRONT_CHEST).pattern("X#X").pattern("XCX").pattern("###").input('X', CHEST_PANE).input('#', LARGE_CHEST_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(TOP_CHEST).pattern("###").pattern("XCX").pattern("###").input('X', CHEST_PANE).input('#', LARGE_CHEST_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(SIDE_CHESTS).pattern("#X#").pattern("LCL").pattern("#X#").input('X', CHEST_PANE).input('L', LARGE_CHEST_PANE).input('#', HUGE_CHEST_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(EXTRACTING_CHESTS).pattern("###").pattern("LCL").pattern("XDX").input('X', HUGE_DYNAMIC_PANE).input('D', LARGE_DYNAMIC_PANE).input('L', LARGE_IRON_PANE).input('#', HUGE_IRON_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(TOP_TANK).pattern("###").pattern("XCX").pattern("###").input('X', TANK_PANE).input('#', HUGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(FRONT_TANK).pattern("X#X").pattern("XCX").pattern("###").input('X', TANK_PANE).input('#', LARGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(SIDE_TANKS).pattern("#X#").pattern("LCL").pattern("#X#").input('X', TANK_PANE).input('L', LARGE_TANK_PANE).input('#', HUGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(OPEN_TANK).pattern("X X").pattern("XCX").pattern("###").input('X', TANK_PANE).input('#', HUGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(ADVANCED_TANK).pattern("XXX").pattern("XCX").pattern("XXX").input('X', HUGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(SEAT).pattern(" #").pattern(" #").pattern("X#").input('X', ItemTags.WOODEN_SLABS).input('#', ItemTags.PLANKS).criterion("has_base_item", RecipeProvider.conditionsFromTag(ItemTags.WOODEN_SLABS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(TINY_COAL_ENGINE).pattern("#X#").pattern(" P ").input('#', Tags.IRON_INGOTS).input('P', Items.PISTON).input('X', Items.FURNACE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.FURNACE)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(COAL_ENGINE).pattern("###").pattern("#X#").pattern(" P ").input('#', Tags.IRON_INGOTS).input('P', Items.PISTON).input('X', Items.FURNACE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.FURNACE)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(SOLAR_ENGINE).pattern("#I#").pattern("IXI").pattern(" P ").input('I', Tags.IRON_INGOTS).input('#', SOLAR_PANEL).input('P', Items.PISTON).input('X', SIMPLE_PCB).criterion("has_base_item", RecipeProvider.conditionsFromItem(SOLAR_PANEL)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(ADVANCED_SOLAR_ENGINE).pattern("I#I").pattern("#X#").pattern("P#P").input('I', Tags.IRON_INGOTS).input('#', ADVANCED_SOLAR_PANEL).input('P', Items.PISTON).input('X', ADVANCED_PCB).criterion("has_base_item", RecipeProvider.conditionsFromItem(ADVANCED_SOLAR_PANEL)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(THERMAL_ENGINE).pattern("OOO").pattern("#X#").pattern("P P").input('O', Items.NETHER_BRICK).input('#', Items.OBSIDIAN).input('X', Items.FURNACE).input('P', Items.PISTON).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.OBSIDIAN)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(ADVANCED_THERMAL_ENGINE).pattern("OOO").pattern("#X#").pattern("P P").input('O', Items.NETHER_BRICK).input('#', REINFORCED_METAL).input('X', THERMAL_ENGINE).input('P', Items.PISTON).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.OBSIDIAN)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(BRAKE).pattern("  R").pattern("#S ").pattern("X# ").input('R', Tags.RED_DYES).input('S', REFINED_HANDLE).input('#', Tags.IRON_INGOTS).input('X', Tags.REDSTONE_DUSTS).criterion("has_base_item", RecipeProvider.conditionsFromItem(REFINED_HANDLE)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(INVISIBILITY_CORE).pattern(" X ").pattern("XEX").pattern(" # ").input('E', Items.ENDER_EYE).input('#', Items.GOLDEN_CARROT).input('X', GLASS_O_MAGIC).criterion("has_base_item", RecipeProvider.conditionsFromItem(REFINED_HANDLE)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(FIREWORK_DISPLAY).pattern("#D#").pattern("CFC").pattern("XSX").input('X', SIMPLE_PCB).input('F', FUSE).input('#', ItemTags.WOODEN_FENCES).input('D', Items.DISPENSER).input('S', Items.FLINT_AND_STEEL).input('C', Items.CRAFTING_TABLE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.FIREWORK_ROCKET)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(TORCH_PLACER).pattern("X X").pattern("# #").pattern("###").input('X', TRI_TORCH).input('#', Tags.IRON_INGOTS).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.TORCH)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RAILER).pattern("###").pattern("XRX").pattern("###").input('X', Tags.IRON_INGOTS).input('#', Tags.COBBLESTONE).input('R', Items.RAIL).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.RAIL)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(LARGE_RAILER).pattern("###").pattern("XRX").pattern("###").input('#', Tags.IRON_INGOTS).input('X', RAILER).input('R', Items.RAIL).criterion("has_base_item", RecipeProvider.conditionsFromItem(RAILER)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(BRIDGE_BUILDER).pattern(" R ").pattern("X#X").pattern(" P ").input('R', Tags.REDSTONE_DUSTS).input('X', Items.BRICKS).input('P', Items.PISTON).input('#', SIMPLE_PCB).criterion("has_base_item", RecipeProvider.conditionsFromItem(RAILER)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(HYDRATOR).pattern("I#I").pattern(" X ").input('I', Tags.IRON_INGOTS).input('#', Items.GLASS_BOTTLE).input('X', ItemTags.WOODEN_FENCES).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS_BOTTLE)).offerTo(exporter);
	}

	public void createHull(Consumer<RecipeJsonProvider> exporter, ModuleType<?> output, TagKey<Item> body, Item wheels) {
		ShapedRecipeJsonBuilder.create(output)
				.input('#', body)
				.input('X', wheels)
				.pattern("# #")
				.pattern("###")
				.pattern("X X")
				.criterion("has_base_item", RecipeProvider.conditionsFromTag(body))
				.offerTo(exporter);
	}

	public void createHull(Consumer<RecipeJsonProvider> exporter, ModuleType<?> output, Item body, Item wheels) {
		ShapedRecipeJsonBuilder.create(output)
				.input('#', body)
				.input('X', wheels)
				.pattern("# #")
				.pattern("###")
				.pattern("X X")
				.criterion("has_base_item", RecipeProvider.conditionsFromItem(body))
				.offerTo(exporter);
	}

	public static class Tags {
		public static final TagKey<Item> IRON_INGOTS = c("iron_ingots");
		public static final TagKey<Item> REDSTONE_DUSTS = c("redstone_dusts");
		public static final TagKey<Item> RED_DYES = c("red_dyes");
		public static final TagKey<Item> COBBLESTONE = c("cobblestone");

		private static TagKey<Item> c(String name) {
			return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
		}
	}
}
