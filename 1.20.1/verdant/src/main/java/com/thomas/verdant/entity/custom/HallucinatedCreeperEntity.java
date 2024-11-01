package com.thomas.verdant.entity.custom;

import java.util.UUID;

import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.entity.ModEntityTypes;
import com.thomas.verdant.util.Utilities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HallucinatedCreeperEntity extends Creeper implements Hallucination {
	private static final EntityDataAccessor<String> PLAYER_ID = SynchedEntityData
			.defineId(HallucinatedCreeperEntity.class, EntityDataSerializers.STRING);
	private static final String PLAYER_ID_TAG = "playerId";
	private UUID playerId = null;
	private Player owner = null;

	public HallucinatedCreeperEntity(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	public HallucinatedCreeperEntity(Level level, Player player) {
		this(ModEntityTypes.HALLUCINATED_CREEPER.get(), level);
		this.playerId = player.getUUID();
		this.owner = player;
		this.entityData.set(PLAYER_ID, this.playerId.toString());
	}

	// Attributes
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR, 2.0D);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level() instanceof ServerLevel level) {
			// Try to get the owner.
			if (this.owner == null || this.playerId == null) {
				String id = this.entityData.get(PLAYER_ID);
				if (id == null) {
					this.updateOnDeath();
					return;
				}
				this.playerId = UUID.fromString(id);
				this.owner = this.level().getPlayerByUUID(this.playerId);
			}
			// Vanish if owner is not there.
			if (this.owner == null || this.playerId == null) {
				this.updateOnDeath();
				return;
			}
			MobEffectInstance effect = this.owner.getEffect(ModMobEffects.HALLUCINATING.get());
			if (effect == null) {
				this.updateOnDeath();
				return;
			}
			if (this.owner.position().distanceTo(this.position()) > this.getDespawnDistance()) {
				this.updateOnDeath();
				return;
			}
		}
	}

	private void updateOnDeath() {
		// System.out.println("vanishing at " + this.position() + "!");
		this.spawnPoof();
		this.dead = true;
		this.discard();
	}

	// Vanish when hurt, after being alive for a certain time.
	@Override
	public boolean hurt(DamageSource source, float damage) {
		if (this.tickCount > this.getImmunityTicks()) {
			this.updateOnDeath();
			return false;
		}
		return false;
	}

	// Hallucinations don't do any damage.
	@SuppressWarnings("resource")
	@Override
	public void explodeCreeper() {
		if (!this.level().isClientSide) {
			this.spawnLingeringCloud();
			this.updateOnDeath();
			return;
		}

	}

	// Hallucinations don't do any damage.
	// Maybe make a particle cloud?
	@Override
	public void spawnLingeringCloud() {
		// It's not real.
	}

	@Override
	public boolean shouldDropExperience() {
		return false;
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		this.checkDespawn();
		return entity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(entity);
	}

	public Boolean matchesPlayer(Player player) {
		return player.getUUID().equals(this.playerId);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PLAYER_ID, "");
	}

	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putString(PLAYER_ID_TAG, this.playerId.toString());

	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.playerId = UUID.fromString(tag.getString(PLAYER_ID_TAG));
		this.owner = this.level().getPlayerByUUID(this.playerId);
	}

	public void spawnPoof() {
		if (this.level() instanceof ServerLevel sl) {
			Utilities.addParticlesAroundEntity(sl, this, ParticleTypes.CLOUD, 0.5, 15);
		}
	}

	@Override
	public void onAddedToWorld() {
		super.onAddedToWorld();
		this.spawnPoof();
		this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 60, 0));
	}

}
