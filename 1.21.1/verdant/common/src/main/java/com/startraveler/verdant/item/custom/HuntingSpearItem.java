package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.entity.custom.ThrownSpearEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;

public class HuntingSpearItem extends TridentItem {

    public static final int USE_DURATION = 10;

    public HuntingSpearItem(Properties properties) {
        super(properties);
    }

    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            int i = this.getUseDuration(stack, entity) - timeLeft;
            if (i < USE_DURATION) {
                return false;
            }
            SoundEvent soundevent = SoundEvents.ARROW_SHOOT;
            if (level instanceof ServerLevel serverlevel) {
                ThrownSpearEntity spear = Projectile.spawnProjectileFromRotation(
                        ThrownSpearEntity::new,
                        serverlevel,
                        stack,
                        player,
                        0.0F,
                        2.5F,
                        1.0F
                );
                if (player.hasInfiniteMaterials()) {
                    spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                } else {
                    stack.shrink(1);
                }
                level.playSound(null, spear, soundevent, SoundSource.PLAYERS, 1.0F, 0.5F);
                return true;
            }
        }
        return false;
    }


}
