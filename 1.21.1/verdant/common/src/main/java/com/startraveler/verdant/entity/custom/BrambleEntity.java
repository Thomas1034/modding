package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.VerdantGrower;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.timer.PrintForTestingTimer;
import com.startraveler.verdant.timer.TimerListSavedData;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class BrambleEntity extends AbstractGolem implements Enemy, VerdantGrower {

    public static final int BASE_INVULNERABLE_TICKS = 20 * 5;
    public static final byte HEALTH_STAGES = 3;
    protected static final EntityDataAccessor<Byte> DATA_HEALTH_ID = SynchedEntityData.defineId(
            BrambleEntity.class,
            EntityDataSerializers.BYTE
    );
    protected static final String DATA_HEALTH_NAME = "HealthStage";
    protected static final EntityDataAccessor<Integer> DATA_INVULNERABLE_TICKS_ID = SynchedEntityData.defineId(
            BrambleEntity.class,
            EntityDataSerializers.INT
    );
    protected static final String DATA_INVULNERABLE_TICKS_NAME = "InvulnerableTicks";

    public BrambleEntity(EntityType<? extends AbstractGolem> type, Level level) {
        super(type, level);
        this.setHealthStage(HEALTH_STAGES);
        this.setInvulnerableTicks(0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0);
    }

    protected void registerGoals() {


    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_HEALTH_ID, (byte) 0);
        builder.define(DATA_INVULNERABLE_TICKS_ID, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {

            // Clear all effects.
            this.removeAllEffects();

            // Restore all health.
            byte healthStage = this.getHealthStage();
            float health = this.getHealth();
            float maxHealth = this.getMaxHealth();
            if (health < maxHealth && healthStage > 0) {
                this.setHealth(maxHealth);
                this.markHurt();
            }

            // Handle invulnerability ticks.
            int invulnerabilityTicks = this.getInvulnerableTicks();
            if (invulnerabilityTicks > 0) {
                invulnerabilityTicks--;
                this.setInvulnerableTicks(invulnerabilityTicks);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(ValueOutput compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte(DATA_HEALTH_NAME, this.getHealthStage());
        compound.putInt(DATA_INVULNERABLE_TICKS_NAME, this.getInvulnerableTicks());
    }

    @Override
    public void readAdditionalSaveData(ValueInput compound) {
        super.readAdditionalSaveData(compound);
        this.setHealthStage(compound.getByteOr(DATA_HEALTH_NAME, HEALTH_STAGES));
        this.setInvulnerableTicks(compound.getIntOr(DATA_INVULNERABLE_TICKS_NAME, 0));
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        // TODO change so that it only takes one damage for all hits (i.e. pass damage = 1 to super)
        // then just track the health as the stage.
        if (source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return super.hurtServer(level, source, damage);
        } else {
            if (this.isInvulnerable()) {
                return false;
            }
            damage = Mth.clamp(damage, 0, this.getMaxHealth() / 2);
            boolean result = super.hurtServer(level, source, damage);

            byte healthStage = this.getHealthStage();
            if (result && healthStage > 0 && damage > 0) {
                healthStage--;
                this.setHealthStage(healthStage);
                this.triggerStageUpdate();
                this.resetInvulnerableTicks();
            }
            return result;
        }
    }

    // Do death effects like blast and beams of light?
    public void die(DamageSource cause) {
        super.die(cause);
    }

    public void triggerStageUpdate() {
        Constants.LOG.warn("Updating to stage {}", this.getHealthStage());

        if (this.level() instanceof ServerLevel serverLevel) {

            TimerListSavedData.addTimer(
                    serverLevel,
                    new PrintForTestingTimer("Finished timer for phase " + this.getHealthStage(), 200L)
            );
        }
    }

    @Override
    public boolean canBeCollidedWith(Entity entity) {
        return this.isAlive();
    }

    @Override
    public boolean isInvulnerable() {
        return super.isInvulnerable() || this.getInvulnerableTicks() > 0;
    }


    @Override
    public Vec3 getDeltaMovement() {
        return Vec3.ZERO;
    }

    @Override
    public void setDeltaMovement(Vec3 p_149804_) {
    }

    public byte getHealthStage() {
        return this.entityData.get(DATA_HEALTH_ID);
    }

    public void setHealthStage(byte health) {
        this.entityData.set(DATA_HEALTH_ID, health);
    }

    public int getInvulnerableTicksForCurrentDifficulty() {
        return this.level().getDifficulty().getId() * BASE_INVULNERABLE_TICKS;
    }

    public int getInvulnerableTicks() {
        return this.entityData.get(DATA_INVULNERABLE_TICKS_ID);
    }

    public void setInvulnerableTicks(int ticks) {
        this.entityData.set(DATA_INVULNERABLE_TICKS_ID, ticks);
    }

    public void resetInvulnerableTicks() {
        this.setInvulnerableTicks(this.getInvulnerableTicksForCurrentDifficulty());
    }

    public BlockState getModelForCurrentStage() {
        return BlockRegistry.BRAMBLE_FRAME.get()
                .defaultBlockState()
                .setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
    }

    public BlockState getShellModelForCurrentStage() {
        return BlockRegistry.SAP_BLOCK.get().defaultBlockState();
    }
}
