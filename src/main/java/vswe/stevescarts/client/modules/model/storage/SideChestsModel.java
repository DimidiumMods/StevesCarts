package vswe.stevescarts.client.modules.model.storage;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.storage.ChestModule;

public class SideChestsModel extends ModuleModel {
	private final ModelPart lid;
	private final ModelPart lock;

	public SideChestsModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
		this.lid = this.getRoot().getChild("lid");
		this.lock = this.root.getChild("lock");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 7).cuboid(8.0f, 3.0f, 2.0f, 16, 6, 4), ModelTransform.pivot(-16.0f, -5.5f, -14.0f));
		modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 0).cuboid(8.0f, -3.0f, -4.0f, 16, 3, 4), ModelTransform.pivot(-16.0f, -1.5f, -8.0f));
		modelPartData.addChild("lock", ModelPartBuilder.create().uv(0, 17).cuboid(-15.0f, -1.5f, -7.5f, 2, 3, 1), ModelTransform.pivot(-2.0f, -4.5f, -13.5f));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void animateModel(MinecartModule module, float limbAngle, float limbDistance, float tickDelta) {
		super.animateModel(module, limbAngle, limbDistance, tickDelta);
		this.lid.pitch = this.lock.pitch = ((ChestModule) module).getOpenProgress(tickDelta) * 1.5707964f;
	}
}
