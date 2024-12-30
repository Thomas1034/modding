package com.thomas.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.thomas.verdant.VerdantIFF;
import com.thomas.verdant.registry.DamageSourceRegistry;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class ThornBushBlock extends BushBlock {

    private final float damage;

    public ThornBushBlock(Properties properties, float damage) {
        super(properties);
        this.damage = damage;
    }


    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.getType() != EntityType.BEE && livingEntity.getType() != EntityType.RABBIT && !VerdantIFF.isFriend(
                livingEntity)) {
            double slowdownFactor = 0.2d;
            if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(VerdantTags.Items.VERDANT_FRIENDLY_ARMORS)) {
                slowdownFactor -= 0.1d;
            }
            if (livingEntity.getItemBySlot(EquipmentSlot.LEGS).is(VerdantTags.Items.VERDANT_FRIENDLY_ARMORS)) {
                slowdownFactor -= 0.1d;
            }
            slowdownFactor = 1 - slowdownFactor;
            entity.makeStuckInBlock(state, new Vec3(slowdownFactor, 0.75, slowdownFactor));
            if (level instanceof ServerLevel serverLevel) {
                Vec3 vec3 = entity.isControlledByClient() ? entity.getKnownMovement() : entity.oldPosition()
                        .subtract(entity.position());
                if (vec3.horizontalDistanceSqr() > (double) 0.0F) {
                    double dx = Math.abs(vec3.x());
                    double dy = Math.abs(vec3.z());
                    double dz = Math.abs(vec3.z());
                    float cumulativeDamage = 0;
                    if ((dx >= (double) 0.003F || dz >= (double) 0.003F) && !entity.isShiftKeyDown()) {
                        cumulativeDamage += this.damage / 2;
                    }
                    if (dy >= (double) 0.003F) {
                        cumulativeDamage += this.damage;
                    }

                    if (dx >= (double) 0.003F || dz >= (double) 0.003F) {
                        Holder<DamageType> type = DamageSourceRegistry.get(
                                level.registryAccess(),
                                DamageSourceRegistry.BRIAR
                        );
                        DamageSource source = new DamageSource(type, (Entity) null);
                        entity.hurtServer(serverLevel, source, this.damage);
                    }
                }

                return;

            }

        }
    }

    private int getCumulativeDamage(Entity entity) {
        double xMovement = Math.abs(entity.getX() - entity.xOld);
        double yMovement = Math.abs(entity.getY() - entity.yOld);
        double zMovement = Math.abs(entity.getZ() - entity.zOld);
        float cumulativeDamage = 0;
        if (yMovement >= (double) 0.003F) {
            cumulativeDamage += this.damage * 2;
        }
        if (zMovement >= (double) 0.003F || xMovement >= (double) 0.003F) {
            cumulativeDamage += this.damage;
        }
        return (int) cumulativeDamage;
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        throw new IllegalStateException("This block doesn't have a codec yet!");
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return this.damage > 0;
    }
}
