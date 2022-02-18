package vswe.stevescarts.client.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import vswe.stevescarts.client.StevesCartsClient;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.ModuleStorage;

public class ModularMinecartRenderer extends EntityRenderer<ModularMinecartEntity> {
	public ModularMinecartRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public Identifier getTexture(ModularMinecartEntity entity) {
		return null;
	}

    // TODO
    @Override
    public void render(ModularMinecartEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
		matrices.push();
		long l = (long) entity.getId() * 493286711L;
		l = l * l * 4392167121L + l * 98761L;
		float h = (((float)(l >> 16 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		float j = (((float)(l >> 20 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		float k = (((float)(l >> 24 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		matrices.translate(h, j, k);
		double renderX = MathHelper.lerp(tickDelta, entity.lastRenderX, entity.getX());
		double renderY = MathHelper.lerp(tickDelta, entity.lastRenderY, entity.getY());
		double renderZ = MathHelper.lerp(tickDelta, entity.lastRenderZ, entity.getZ());
		double offset = 0.3f;
		Vec3d vec3d = entity.snapPositionToRail(renderX, renderY, renderZ);
		float o = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());
		if (vec3d != null) {
			Vec3d vec3d2 = entity.snapPositionToRailWithOffset(renderX, renderY, renderZ, offset);
			Vec3d vec3d3 = entity.snapPositionToRailWithOffset(renderX, renderY, renderZ, -offset);
			if (vec3d2 == null) {
				vec3d2 = vec3d;
			}
			if (vec3d3 == null) {
				vec3d3 = vec3d;
			}
			matrices.translate(vec3d.x - renderX, (vec3d2.y + vec3d3.y) / 2.0 - renderY, vec3d.z - renderZ);
			Vec3d vec3d4 = vec3d3.add(-vec3d2.x, -vec3d2.y, -vec3d2.z);
			if (vec3d4.length() != 0.0) {
				vec3d4 = vec3d4.normalize();
				yaw = (float)(Math.atan2(vec3d4.z, vec3d4.x) * 180.0 / Math.PI);
				o = (float)(Math.atan(vec3d4.y) * 73.0);
			}
		}
		matrices.translate(0.0, 0.375, 0.0);
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - yaw));
		matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-o));
		float damageWobbleTicks = (float) entity.getDamageWobbleTicks() - tickDelta;
		float damageWobbleStrength = MathHelper.clamp(entity.getDamageWobbleStrength() - tickDelta, 0, Float.MAX_VALUE);
		if (damageWobbleTicks > 0.0f) {
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(damageWobbleTicks) * damageWobbleTicks * damageWobbleStrength / 10.0f * entity.getDamageWobbleSide()));
		}
		matrices.scale(-1.0f, -1.0f, 1.0f);
		for (MinecartModule module : entity.getModuleList()) {
			StevesCartsClient.getModuleRenderDispatcher().render(module, yaw, tickDelta, matrices, vertexConsumers, light);
		}
		matrices.pop();
    }

	public static void renderAsItem(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.scale(-1.0f, -1.0f, 1.0f);
		for (MinecartModule module : ModuleStorage.read(stack)) {
			StevesCartsClient.getModuleRenderDispatcher().render(module, 0, 0, matrices, vertexConsumers, light);
		}
		matrices.pop();
	}
}
