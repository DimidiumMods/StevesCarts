package vswe.stevescarts.models.storages.chests;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vswe.stevescarts.helpers.ResourceHelper;
import vswe.stevescarts.models.ModelCartbase;
import vswe.stevescarts.modules.ModuleBase;
import vswe.stevescarts.modules.storages.chests.ModuleChest;

@SideOnly(Side.CLIENT)
public class ModelTopChest extends ModelCartbase {
	private static ResourceLocation texture;
	ModelRenderer lid;

	@Override
	public ResourceLocation getResource(final ModuleBase module) {
		return ModelTopChest.texture;
	}

	public ModelTopChest() {
		lid = AddChest();
	}

	private ModelRenderer AddChest() {
		final ModelRenderer chestAnchor = new ModelRenderer(this);
		AddRenderer(chestAnchor);
		chestAnchor.rotateAngleY = 4.712389f;
		chestAnchor.setRotationPoint(-2.5f, -3.0f, 2.0f);
		final ModelRenderer base = new ModelRenderer(this, 0, 19);
		fixSize(base);
		chestAnchor.addChild(base);
		base.addBox(6.0f, 2.0f, 8.0f, 12, 4, 16, 0.0f);
		base.setRotationPoint(-14.0f, -5.5f, -18.5f);
		final ModelRenderer lid = new ModelRenderer(this, 0, 0);
		fixSize(lid);
		chestAnchor.addChild(lid);
		lid.addBox(6.0f, -3.0f, -16.0f, 12, 3, 16, 0.0f);
		lid.setRotationPoint(-14.0f, -2.5f, 5.5f);
		final ModelRenderer lock = new ModelRenderer(this, 0, 39);
		fixSize(lock);
		lid.addChild(lock);
		lock.addBox(1.0f, 1.5f, 0.5f, 2, 3, 1, 0.0f);
		lock.setRotationPoint(10.0f, -3.0f, -17.5f);
		return lid;
	}

	@Override
	public void applyEffects(final ModuleBase module, final float yaw, final float pitch, final float roll) {
		lid.rotateAngleX = ((module == null) ? 0.0f : (-((ModuleChest) module).getChestAngle()));
	}

	static {
		ModelTopChest.texture = ResourceHelper.getResource("/models/topChestModel.png");
	}
}
