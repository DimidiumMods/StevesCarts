package vswe.stevescarts.models.pig;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vswe.stevescarts.models.ModelCartbase;
import vswe.stevescarts.modules.ModuleBase;
import vswe.stevescarts.modules.hull.ModulePig;

@SideOnly(Side.CLIENT)
public class ModelPigHelmet extends ModelCartbase {
	private boolean isOverlay;

	@Override
	public ResourceLocation getResource(final ModuleBase module) {
		final ModulePig pig = (ModulePig) module;
		return pig.getHelmetResource(isOverlay);
	}

	@Override
	protected int getTextureHeight() {
		return 32;
	}

	public ModelPigHelmet(final boolean isOverlay) {
		this.isOverlay = isOverlay;
		final ModelRenderer Headwear = new ModelRenderer(this, 0, 0);
		AddRenderer(Headwear);
		Headwear.addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8, 0.0f);
		Headwear.setRotationPoint(-12.2f + (isOverlay ? 0.2f : 0.0f), -5.4f, 0.0f);
		Headwear.rotateAngleY = 1.5707964f;
	}

	@Override
	public void render(final Render render, final ModuleBase module, final float yaw, final float pitch, final float roll, final float mult, final float partialtime) {
		if (render == null || module == null) {
			return;
		}
		final ModulePig pig = (ModulePig) module;
		if (!pig.hasHelment() || (isOverlay && !pig.getHelmetMultiRender())) {
			return;
		}
		final float sizemult = 1.09375f + (isOverlay ? 0.020833334f : 0.0f);
		GlStateManager.scale(sizemult, sizemult, sizemult);
		if (pig.hasHelmetColor(isOverlay)) {
			final int color = pig.getHelmetColor(isOverlay);
			final float red = (color >> 16 & 0xFF) / 255.0f;
			final float green = (color >> 8 & 0xFF) / 255.0f;
			final float blue = (color & 0xFF) / 255.0f;
			GlStateManager.color(red, green, blue);
		}
		super.render(render, module, yaw, pitch, roll, mult, partialtime);
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		GlStateManager.scale(1.0f / sizemult, 1.0f / sizemult, 1.0f / sizemult);
	}
}
