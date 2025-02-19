package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

public class BlowgunItem extends ProjectileWeaponItem {
    public static final Predicate<ItemStack> DART_ONLY = (stack) -> stack.is(VerdantTags.Items.DARTS);
    public static final int DEFAULT_RANGE = 15;
    public static final int MAX_BLOW_DURATION = 5;
    public static final int AIR_TO_TAKE = 60;

    public BlowgunItem(Properties properties) {
        super(properties);
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        // Constants.LOG.warn("Player air {}, max {}", player.getAirSupply(), player.getMaxAirSupply());
        boolean hasAmmo = !player.getProjectile(itemStack).isEmpty();
        boolean hasAir = player.getAirSupply() >= this.getAirToTake(itemStack) / 4;
        if (!player.hasInfiniteMaterials() && (!hasAmmo || !hasAir)) {
            return InteractionResult.FAIL;
        } else {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (user instanceof Player player) {
            ItemStack itemstack = player.getProjectile(stack);
            if (!itemstack.isEmpty()) {
                float power = 1;
                List<ItemStack> list = draw(stack, itemstack, player);
                if (level instanceof ServerLevel serverLevel) {
                    if (!list.isEmpty()) {
                        this.shoot(
                                serverLevel,
                                player,
                                player.getUsedItemHand(),
                                stack,
                                list,
                                power * 3.0F,
                                1.0F,
                                false,
                                null
                        );
                    }
                }

                level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.ARROW_SHOOT,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F
                );


                // Drain air.
                if (level instanceof ServerLevel serverLevel) {
                    int airToTake = getAirToTake(stack);
                    int newAirSupply = player.getAirSupply() - airToTake;
                    if (newAirSupply <= 0) {
                        boolean hasAirlessBreathing = false;
                        for (MobEffectInstance effectInstance : player.getActiveEffects()) {
                            if (effectInstance.getEffect().is(VerdantTags.MobEffects.AIRLESS_BREATHING)) {
                                hasAirlessBreathing = true;
                                break;
                            }
                        }
                        if (!hasAirlessBreathing) {
                            player.hurtServer(serverLevel, player.damageSources().drown(), 2.0f);
                        }
                        player.setAirSupply(0);
                    } else {
                        player.setAirSupply(player.getAirSupply() - airToTake);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
            return stack;
        } else {
            return stack;
        }
    }

    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.TOOT_HORN;
    }

    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return MAX_BLOW_DURATION;
    }

    public int getAirToTake(ItemStack stack) {
        return AIR_TO_TAKE;
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return DART_ONLY;
    }

    public int getDefaultProjectileRange() {
        return DEFAULT_RANGE;
    }

    protected void shootProjectile(LivingEntity entity, Projectile projectile, int p_330631_, float velocity, float inaccuracy, float yRotationOffset, LivingEntity unknown) {
        projectile.shootFromRotation(
                entity,
                entity.getXRot(),
                entity.getYRot() + yRotationOffset,
                0.0F,
                velocity,
                inaccuracy
        );
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Item ammoItem = ammo.getItem();
        DartItem dart;
        if (ammoItem instanceof DartItem dartItem) {
            dart = dartItem;
        } else {
            dart = (DartItem) ItemRegistry.DART.get();
        }
        AbstractArrow dartProjectile = dart.createArrow(level, ammo, shooter, weapon);
        if (isCrit) {
            dartProjectile.setCritArrow(true);
        }

        return dartProjectile;
    }
}
