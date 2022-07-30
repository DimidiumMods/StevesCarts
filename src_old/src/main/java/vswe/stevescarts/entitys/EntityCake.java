package vswe.stevescarts.entitys;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityCake extends EntityEgg {
	public EntityCake(final World world) {
		super(world);
	}

	public EntityCake(final World world, final EntityLiving thrower) {
		super(world, thrower);
	}

	public EntityCake(final World world, final double x, final double y, final double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(final RayTraceResult data) {
		BlockPos pos = getPosition();
		if (data.entityHit != null) {
			data.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0f);
			if (data.entityHit instanceof EntityPlayer) {
				final EntityPlayer player = (EntityPlayer) data.entityHit;
				player.getFoodStats().addStats(14, 0.7f);
			}
		} else if (world.isAirBlock(pos) && world.isSideSolid(pos.down(), EnumFacing.UP)) {
			world.setBlockState(pos, Blocks.CAKE.getDefaultState());
		}
		for (int j = 0; j < 8; ++j) {
			world.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY, posZ, 0.0, 0.0, 0.0);
		}
		if (!world.isRemote) {
			setDead();
		}
	}
}
