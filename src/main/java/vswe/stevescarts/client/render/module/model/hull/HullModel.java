package vswe.stevescarts.client.render.module.model.hull;

import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.util.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import vswe.stevescarts.client.render.module.model.ModuleModel;

@Environment(EnvType.CLIENT)
public class HullModel extends ModuleModel {
	public HullModel(Identifier texture) {
		super(MinecartEntityModel.getTexturedModelData().createModel(), texture);
	}
}
