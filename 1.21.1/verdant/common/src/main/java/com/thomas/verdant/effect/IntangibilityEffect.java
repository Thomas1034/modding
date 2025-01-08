package com.thomas.verdant.effect;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registry.MobEffectRegistry;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class IntangibilityEffect extends MobEffect {

    private static final int WEIGHTLESS_LENGTH = 200;

    public IntangibilityEffect(MobEffectCategory category, int color) {
        super(category, color);
        this.addAttributeModifier(
                Attributes.GRAVITY,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "intangibility/less_gravity"),
                -0.8,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

    }

    public static boolean canGoThroughBlockBeneath(Player player) {
        BlockPos atPos = player.blockPosition();
        Level level = player.level();
        BlockState belowState = level.getBlockState(atPos.below());
        BlockState atState = level.getBlockState(atPos);
        BlockState aboveState = level.getBlockState(atPos.above());
        return !(belowState.is(VerdantTags.Blocks.BLOCKS_INTANGIBLE) && atState.is(VerdantTags.Blocks.BLOCKS_INTANGIBLE) && aboveState.is(
                VerdantTags.Blocks.BLOCKS_INTANGIBLE));
    }

    public static boolean isIntangible(Player player) {
        MobEffectInstance intangibilityInstance = player.getEffect(MobEffectRegistry.INTANGIBLE.asHolder());
        return intangibilityInstance != null;
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {

        if (entity instanceof Player player) {

            double upAcceleration = 2 * player.getAttributeValue(Attributes.GRAVITY);
            double maxUpVelocity = upAcceleration * 10;
            boolean shouldSync = false;
            Vec3 playerMovement = player.getDeltaMovement();

            player.fallDistance = 0;

            boolean feetInBlock = isFeetInBlock(player);
            boolean headInBlock = isHeadInBlock(player);

            if (player.isShiftKeyDown()) {
                Vec3 playerLookVec = player.getLookAngle();
                playerMovement = playerMovement.add(playerLookVec.scale(2)).scale(0.25);
                shouldSync = true;

                player.addEffect(new MobEffectInstance(MobEffectRegistry.WEIGHTLESS.asHolder(), WEIGHTLESS_LENGTH, 0));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, WEIGHTLESS_LENGTH, 1));
            } else if ((feetInBlock || headInBlock) && canGoThroughBlockBeneath(player)) {
                double toAdd = playerMovement.y > maxUpVelocity ? 0 : Math.min(
                        upAcceleration,
                        maxUpVelocity - playerMovement.y
                );
                if (toAdd > 0) {
                    playerMovement = new Vec3(playerMovement.x, playerMovement.y + toAdd, playerMovement.z);
                    // Constants.LOG.warn("Setting motion to {}, added {}", playerMovement, toAdd);
                    shouldSync = true;
                }
            }


            // TODO figure out how to send delta-movement, maybe?


            if (shouldSync) {
                player.setDeltaMovement(playerMovement);
                player.hurtMarked = true;
            }

        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    private boolean isBlockAtRelative(Player player, int n) {
        BlockPos pos = player.blockPosition().above(n);
        Level level = player.level();
        BlockState blockState = level.getBlockState(pos);
        return !blockState.getCollisionShape(level, pos).isEmpty() && blockState.getFluidState().isEmpty();
    }

    private boolean isFeetInBlock(Player player) {
        return isBlockAtRelative(player, 0);
    }

    private boolean isHeadInBlock(Player player) {
        return isBlockAtRelative(player, (player.isVisuallyCrawling() || player.isFallFlying()) ? 0 : 1);
    }

    private boolean isHeadBelowBlock(Player player) {
        return isBlockAtRelative(player, 2);
    }


}