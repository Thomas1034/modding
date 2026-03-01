package com.startraveler.verdant.block.custom;

import com.startraveler.verdant.registry.MobEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SapBlock extends HalfTransparentBlock {
    public SapBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity, @NotNull InsideBlockEffectApplier applier, boolean intersects) {
        super.entityInside(state, level, pos, entity, applier, intersects);
        entity.makeStuckInBlock(state, new Vec3(0.6, 0.6, 0.6));
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getEyePosition() instanceof Vec3 eyePos && level.getBlockState(BlockPos.containing(
                    eyePos.x,
                    eyePos.y,
                    eyePos.z
            )).is(this)) {
                livingEntity.addEffect(new MobEffectInstance(
                        MobEffectRegistry.SAPPY_VISION.asHolder(),
                        2,
                        0,
                        false,
                        false
                ));
                livingEntity.addEffect(new MobEffectInstance(
                        MobEffectRegistry.ASPHYXIATING.asHolder(),
                        2,
                        0,
                        false,
                        false
                ));
            }
            livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.SAPPY.asHolder(), 101, 0));
        }
    }

}
