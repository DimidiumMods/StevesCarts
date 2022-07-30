package vswe.stevescarts.api.farms;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import vswe.stevescarts.entitys.EntityMinecartModular;
import vswe.stevescarts.modules.workers.tools.ModuleWoodcutter;

/**
 * Created by modmuss50 on 08/05/2017.
 */
public interface ITreeProduceModule extends ITreeModule {

	boolean harvest(IBlockState blockState, BlockPos pos, EntityMinecartModular cart, NonNullList<ItemStack> drops, boolean simulate, ModuleWoodcutter woodcutter);

}
