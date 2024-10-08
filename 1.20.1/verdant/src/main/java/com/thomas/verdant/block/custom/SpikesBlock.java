package com.thomas.verdant.block.custom;

import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.damage.ModDamageSources;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpikesBlock extends AmethystClusterBlock {

	private final float damage;

	public SpikesBlock(Properties properties, float damage) {
		super(5, 3, properties);
		this.damage = damage;
	}

	// Mostly from SweetBerryBushBlock, with a few tweaks.
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity
				&& (livingEntity.getMobType() != MobType.ARTHROPOD && livingEntity.getType() != EntityType.RABBIT)) {

			VoxelShape box = this.getShape(state, level, pos, CollisionContext.empty());
			AABB shiftedBounds = box.bounds().move(pos);
			if (shiftedBounds.intersects(entity.getBoundingBox())) {

				entity.makeStuckInBlock(state, new Vec3((double) 0.8F, 1D, (double) 0.8F));
				if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
					double zMovement = Math.abs(entity.getX() - entity.xOld);
					double yMovement = Math.abs(entity.getY() - entity.yOld);
					double xMovement = Math.abs(entity.getZ() - entity.zOld);
					if (zMovement >= (double) 0.003F || xMovement >= (double) 0.003F || yMovement >= (double) 0.003F) {
						DamageSource source = ModDamageSources.get(level, ModDamageSources.THORN_BUSH);
						entity.hurt(source, damage + (float) (1 + 5 * yMovement));
					}
				}

			}
			;
		}
	}

	// Fire!
	@Override
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return !state.getValue(WATERLOGGED);
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 20;
	}

	@Override
	@Nullable
	public BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
		return BlockPathTypes.DAMAGE_OTHER;
	}

}
