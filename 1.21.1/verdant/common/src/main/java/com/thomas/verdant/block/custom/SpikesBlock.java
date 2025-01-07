package com.thomas.verdant.block.custom;


import com.thomas.verdant.registry.DamageSourceRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class SpikesBlock extends AmethystClusterBlock {

    private final float damage;

    public SpikesBlock(Properties properties, float damage) {
        super(5, 3, properties);
        this.damage = damage;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            double slowdownFactor = 0.2d;
            slowdownFactor = 1 - slowdownFactor;
            entity.makeStuckInBlock(state, new Vec3(slowdownFactor, 1, slowdownFactor));
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

                    if ((dx >= (double) 0.003F || dz >= (double) 0.003F) && cumulativeDamage > 0.5f) {
                        Holder<DamageType> type = DamageSourceRegistry.get(
                                level.registryAccess(),
                                DamageSourceRegistry.BRIAR
                        );
                        DamageSource source = new DamageSource(type, (Entity) null);
                        entity.hurtServer(serverLevel, source, cumulativeDamage);
                    }
                }
                return;
            }
        }
    }

}
