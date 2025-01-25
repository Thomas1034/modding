package com.thomas.verdant.block.custom;

import com.thomas.verdant.VerdantIFF;
import com.thomas.verdant.registry.DamageSourceRegistry;
import com.thomas.verdant.registry.TriggerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

// TODO Implement damage types, with data generation!
// https://docs.neoforged.net/docs/resources/server/damagetypes/
public class ThornyStranglerLeavesBlock extends StranglerLeavesBlock {

    protected static final Supplier<MobEffectInstance> POISON = () -> new MobEffectInstance(MobEffects.POISON, 100, 0);

    public ThornyStranglerLeavesBlock(Properties properties) {
        super(properties);
    }

    // Harm players who step on it.
    // Copied from ThornBushBlock.
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity le && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE && !VerdantIFF.isFriend(
                le)) {
            entity.makeStuckInBlock(state, new Vec3((double) 0.9F, 1.0D, (double) 0.9F));
            if (level instanceof ServerLevel serverLevel) {
                Holder<DamageType> type = DamageSourceRegistry.get(level.registryAccess(), DamageSourceRegistry.BRIAR);
                DamageSource source = new DamageSource(type, (Entity) null);
                if (le instanceof ServerPlayer player) {
                    TriggerRegistry.VERDANT_PLANT_ATTACK_TRIGGER.get().trigger(player);
                }
                entity.hurtServer(serverLevel, source, 2.0F);
            }
        }
    }
}
