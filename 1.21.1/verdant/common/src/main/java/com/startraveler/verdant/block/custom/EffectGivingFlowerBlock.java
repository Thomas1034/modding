package com.startraveler.verdant.block.custom;

import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.registry.MobEffectRegistry;
import com.startraveler.verdant.registry.TriggerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EffectGivingFlowerBlock extends FlowerBlock {
    private static final Supplier<MobEffectInstance> PHOTOSENSITIVITY = () -> new MobEffectInstance(
            MobEffectRegistry.PHOTOSENSITIVITY.asHolder(),
            300,
            0
    );

    public EffectGivingFlowerBlock(SuspiciousStewEffects suspiciousStewEffects, BlockBehaviour.Properties properties) {
        super(suspiciousStewEffects, properties);
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity, @NotNull InsideBlockEffectApplier applier, boolean intersect) {
        super.entityInside(state, level, pos, entity, applier, intersect);
        if (entity instanceof LivingEntity livingEntity && VerdantIFF.isEnemy(livingEntity)) {
            if (!level.isClientSide()) {
                if (livingEntity instanceof ServerPlayer player) {
                    TriggerRegistry.VERDANT_PLANT_ATTACK_TRIGGER.get().trigger(player);
                }
                livingEntity.addEffect(PHOTOSENSITIVITY.get());
            }
        }
    }
}
