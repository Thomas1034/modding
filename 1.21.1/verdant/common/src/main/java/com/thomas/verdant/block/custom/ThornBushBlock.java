package com.thomas.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.thomas.verdant.VerdantIFF;
import com.thomas.verdant.registry.DamageSourceRegistry;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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

    // Mostly from SweetBerryBushBlock, with a few tweaks.
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && (livingEntity.getType() != EntityType.RABBIT && !VerdantIFF.isFriend(
                livingEntity))) {
            double slowdownFactor = 0.2d;
            if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(VerdantTags.Items.VERDANT_FRIENDLY_ARMORS)) {
                slowdownFactor -= 0.1d;
            }
            if (livingEntity.getItemBySlot(EquipmentSlot.LEGS).is(VerdantTags.Items.VERDANT_FRIENDLY_ARMORS)) {
                slowdownFactor -= 0.1d;
            }

            slowdownFactor = 1 - slowdownFactor;

            if (slowdownFactor < 0.999) {
                entity.makeStuckInBlock(state, new Vec3(slowdownFactor, slowdownFactor, slowdownFactor));
            }
            if (!level.isClientSide && (entity.xOld != entity.getX() || entity.yOld != entity.getY() || entity.zOld != entity.getZ())) {
                int cumulativeDamage = getCumulativeDamage(entity);
                if (cumulativeDamage >= 0) {
                    Holder<DamageType> type = DamageSourceRegistry.get(
                            level.registryAccess(),
                            DamageSourceRegistry.BRIAR
                    );
                    DamageSource source = new DamageSource(type, (Entity) null);
                    entity.hurt(source, cumulativeDamage);
                }
            }
        }
    }

    private int getCumulativeDamage(Entity entity) {
        double zMovement = Math.abs(entity.getX() - entity.xOld);
        double yMovement = Math.abs(entity.getY() - entity.yOld);
        double xMovement = Math.abs(entity.getZ() - entity.zOld);
        float cumulativeDamage = 0;
        if (yMovement >= (double) 0.003F) {
            cumulativeDamage += this.damage;
        }
        if (zMovement >= (double) 0.003F || xMovement >= (double) 0.003F) {
            cumulativeDamage += this.damage / 2;
        }
        return (int) cumulativeDamage;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return this.damage > 0;
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        throw new IllegalStateException("This block doesn't have a codec yet!");
    }
}
