package vswe.stevescarts.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vswe.stevescarts.helpers.ResourceHelper;
import vswe.stevescarts.modules.ModuleBase;
import vswe.stevescarts.modules.realtimers.ModuleSeat;

@SideOnly(Side.CLIENT)
public class ModelSeat extends ModelCartbase {
	private static ResourceLocation texture;
	ModelRenderer sit;

	@Override
	public ResourceLocation getResource(final ModuleBase module) {
		return ModelSeat.texture;
	}

	@Override
	protected int getTextureWidth() {
		return 32;
	}

	@Override
	protected int getTextureHeight() {
		return 32;
	}

	public ModelSeat() {
		AddRenderer(sit = new ModelRenderer(this, 0, 0));
		sit.addBox(-4.0f, -2.0f, -2.0f, 8, 4, 4, 0.0f);
		sit.setRotationPoint(0.0f, 1.0f, 0.0f);
		final ModelRenderer back = new ModelRenderer(this, 0, 8);
		sit.addChild(back);
		fixSize(back);
		back.addBox(-4.0f, -2.0f, -1.0f, 8, 12, 2, 0.0f);
		back.setRotationPoint(0.0f, -8.0f, 3.0f);
	}

	@Override
	public void applyEffects(final ModuleBase module, final float yaw, final float pitch, final float roll) {
		sit.rotateAngleY = ((module == null) ? 1.5707964f : (((ModuleSeat) module).getChairAngle() + (((ModuleSeat) module).useRelativeRender() ? 0.0f : yaw)));
	}

	static {
		ModelSeat.texture = ResourceHelper.getResource("/models/chairModel.png");
	}
}
