package com.thomas.zirconmod.item.custom;

import com.thomas.zirconmod.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

// Copied from the Snowball class, with a few changes.
public class FlamingPineconeItem extends FuelItem {

	public FlamingPineconeItem(Item.Properties properties, int burnTime) {
		super(properties, burnTime);
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW,
				SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!level.isClientSide) {
			// Anonymous class to override certain qualities.
			Snowball pinecone = new Snowball(level, player) {
				protected Item getDefaultItem() {
					return ModItems.PINE_CONE.get();
				}

				@SuppressWarnings("unused")
				private ParticleOptions getParticle() {
					ItemStack itemstack = this.getItemRaw();
					return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.FLAME
							: new ItemParticleOption(ParticleTypes.ITEM, itemstack));
				}

				protected void onHitEntity(EntityHitResult hitResult) {
					super.onHitEntity(hitResult);
					Entity entity = hitResult.getEntity();
					// Does not harm blazes
					// However, does harm other entities.
					// It also lights them on fire for 60 more ticks.
					// This means you can stack fire time for a very, very long time.
					entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float) 0);
					entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 60);
				}

				// Lights the location it hits on fire.
				@SuppressWarnings("resource")
				protected void onHit(HitResult hitResult) {
					super.onHit(hitResult);
					if (!this.level().isClientSide) {
						this.level().broadcastEntityEvent(this, (byte) 3);
						// Lights the hit location on fire.
						// Gets the position of the hit.
						// bumpVector(hitResult.getLocation(), this.getDeltaMovement());
						Vec3 loc = hitResult.getLocation();
						Vec3 vel = this.getDeltaMovement();
						BlockPos pos = getBlock(loc, vel);
						Direction dir = getFace(pos, loc).getOpposite();
						// Get the block state at the position of the arrow.
						BlockPos hitPos = pos.relative(dir);
						BlockState at = this.level().getBlockState(hitPos);
						// Set the block on fire if it's air
						if (at.isAir())
							this.level().setBlockAndUpdate(hitPos, BaseFireBlock.getState(level, hitPos));
						// Sets the block on fire if it's replacable.
						else if (at.canBeReplaced() && this.level().getFluidState(hitPos).getFluidType().isAir())
							this.level().setBlockAndUpdate(hitPos, BaseFireBlock.getState(level, hitPos));
						// Update the neighbors.
						// Removes the pinecone on impact.
						this.discard();
					}

				}

			};
			pinecone.setItem(itemstack);
			pinecone.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			level.addFreshEntity(pinecone);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.getAbilities().instabuild) {
			itemstack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	}

	// Gets the block that was hit at the given coordinates with the given velocity.
	private static BlockPos getBlock(Vec3 pos, Vec3 vel) {
		// The coordinates that will be returned eventually.
		int a = (int) pos.x - 1, b = (int) pos.y, c = (int) pos.z - 1;

		if (isWholeNumber(pos.x)) {
			if (vel.x > 0) {
				a++;
			}
		}
		if (isWholeNumber(pos.y)) {
			b--;
			if (vel.y > 0) {
				b++;
			}
		} 
		if (isWholeNumber(pos.z)) {
			if (vel.z > 0) {
				c++;
			}
		}

		return new BlockPos(a, b, c);
	}

	// Gets which face of the block b the position i is on.
	// If it is not on a block, returns UP.
	private static Direction getFace(BlockPos b, Vec3 i) {
		// First, checks which axis i is on.
		if (isWholeNumber(i.x)) {
			if (b.getX() >= i.x) {
				return Direction.EAST;
			} else {
				return Direction.WEST;
			}
		} else if (isWholeNumber(i.y)) {
			if (b.getY() >= i.y) {
				return Direction.UP;
			} else {
				return Direction.DOWN;
			}
		} else if (isWholeNumber(i.z)) {
			if (b.getZ() >= i.z) {
				return Direction.SOUTH;
			} else {
				return Direction.NORTH;
			}
		}
		return Direction.UP;
	}

	// Check if the absolute difference between the number and its rounded value is
	// within the tolerance
	private static boolean isWholeNumber(double num) {
		double tolerance = 0.0001;
		return Math.abs(num - Math.round(num)) < tolerance;
	}
}