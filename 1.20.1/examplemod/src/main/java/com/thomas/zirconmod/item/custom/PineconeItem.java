package com.thomas.zirconmod.item.custom;

import com.thomas.zirconmod.item.ModItems;

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
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

// Copied from the Snowball class, with a few changes.
public class PineconeItem extends FuelItem {
	
	public PineconeItem(Item.Properties properties, int burnTime) {
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
					return ModItems.FLAMING_PINE_CONE.get();
				}

				private ParticleOptions getParticle() {
					ItemStack itemstack = this.getItemRaw();
					return (ParticleOptions) (itemstack.isEmpty()
							? ParticleTypes.FLAME 
							: new ItemParticleOption(ParticleTypes.ITEM, itemstack));
				}

				protected void onHitEntity(EntityHitResult hitResult) {
					super.onHitEntity(hitResult);
					Entity entity = hitResult.getEntity();
					entity.hurt(this.damageSources().thrown(this, this.getOwner()), 0);
				}

				// Lights the location it hits on fire.
				protected void onHit(HitResult hitResult) {
					super.onHit(hitResult);
					if (!this.level().isClientSide) {
						this.level().broadcastEntityEvent(this, (byte) 3);
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
}