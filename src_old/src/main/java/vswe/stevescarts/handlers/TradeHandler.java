package vswe.stevescarts.handlers;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import vswe.stevescarts.Constants;
import vswe.stevescarts.helpers.ComponentTypes;
import vswe.stevescarts.helpers.ResourceHelper;
import vswe.stevescarts.items.ModItems;

import java.util.Random;

public class TradeHandler implements ITradeList {

	public static VillagerProfession santaProfession;

	@SubscribeEvent
	public void registerVillagers(RegistryEvent.Register<VillagerProfession> event) {
		santaProfession = new VillagerProfession(Constants.MOD_ID + ":santa", ResourceHelper.getResource("/models/santa.png").toString(), ResourceHelper.getResource("/models/santa_zombie.png").toString());
		event.getRegistry().register(santaProfession);

		VillagerCareer career = new VillagerCareer(santaProfession, "sc_santa");
		career.addTrade(1, this);
	}

	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
		recipeList.add(new MerchantRecipe(new ItemStack(ModItems.COMPONENTS, 3, ComponentTypes.STOLEN_PRESENT.getId()), new ItemStack(ModItems.COMPONENTS, 1, ComponentTypes.GREEN_WRAPPING_PAPER.getId())));
	}
}
