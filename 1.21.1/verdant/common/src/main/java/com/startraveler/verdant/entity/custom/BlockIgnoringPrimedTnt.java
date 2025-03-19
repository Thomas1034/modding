package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.mixin.PrimedTntAccessors;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
public class BlockIgnoringPrimedTnt extends PrimedTnt {
    public BlockIgnoringPrimedTnt(EntityType<? extends PrimedTnt> entityType, Level level) {
        super(entityType, level);
    }

    public BlockIgnoringPrimedTnt(Level level, double x, double y, double z, LivingEntity owner) {
        this(EntityTypeRegistry.BLOCK_IGNORING_PRIMED_TNT.get(), level);
        this.setPos(x, y, z);
        double d0 = level.random.nextDouble() * (double) ((float) Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02, 0.2F, -Math.cos(d0) * 0.02);
        this.setFuse(80);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        ((PrimedTntAccessors) this).setOwner(owner);
    }

    public void tick() {
        this.handlePortal();
        this.applyGravity();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.applyEffectsFromBlocks();
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5F, 0.7));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level()
                        .addParticle(
                                ParticleTypes.SMOKE,
                                this.getX(),
                                this.getY() + (double) 0.5F,
                                this.getZ(),
                                0.0F,
                                0.0F,
                                0.0F
                        );
            }
        }
    }

    protected void explode() {
        this.level().explode(
                this,
                Explosion.getDefaultDamageSource(this.level(), this),
                ((PrimedTntAccessors) this).getUsedPortal() ? PrimedTntAccessors.getUsedPortalDamageCalculator() : null,
                this.getX(),
                this.getY(0.0625F),
                this.getZ(),
                ((PrimedTntAccessors) this).getExplosionPower(),
                false,
                Level.ExplosionInteraction.NONE
        );

    }

}
