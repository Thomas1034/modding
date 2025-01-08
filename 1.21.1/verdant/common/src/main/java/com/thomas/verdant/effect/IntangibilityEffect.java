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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class IntangibilityEffect extends MobEffect {

    public IntangibilityEffect(MobEffectCategory category, int color) {
        super(category, color);
        this.addAttributeModifier(
                Attributes.GRAVITY,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "intangibility/no_gravity"),
                -0.8,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

    }

    public static boolean canGoThroughBlockBeneath(Player player) {
        BlockPos belowPos = player.blockPosition().below();
        Level level = player.level();
        BlockState belowState = level.getBlockState(belowPos);
        return !belowState.is(VerdantTags.Blocks.BLOCKS_INTANGIBLE);
    }

    public static boolean isIntangible(Player player) {
        MobEffectInstance intangibilityInstance = player.getEffect(MobEffectRegistry.INTANGIBLE.asHolder());
        return intangibilityInstance != null;
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {

        if (entity instanceof Player player) {

            double downAcceleration = 0.05;
            double upAcceleration = 0.02 * player.getAttributeValue(Attributes.GRAVITY);
            double maxUpVelocity = upAcceleration * 10;
            double maxDownVelocity = downAcceleration * 10;
            boolean shouldSync = false;
            Vec3 playerMovement = player.getDeltaMovement();

            player.fallDistance = 0;

            if (player.isShiftKeyDown()) {
                double toAdd = -playerMovement.y > maxDownVelocity ? 0 : Math.min(
                        downAcceleration,
                        maxDownVelocity + playerMovement.y
                );
                if (toAdd > 0) {
                    playerMovement = new Vec3(playerMovement.x, playerMovement.y - toAdd, playerMovement.z);
                    shouldSync = true;
                }
            }
            if (isFeetInBlock(player) && canGoThroughBlockBeneath(player)) {
                double toAdd = playerMovement.y > maxUpVelocity ? 0 : Math.min(
                        upAcceleration,
                        maxUpVelocity - playerMovement.y
                );
                if (toAdd > 0) {
                    playerMovement = new Vec3(playerMovement.x, playerMovement.y + toAdd, playerMovement.z);
                    shouldSync = true;
                }

            }


            // TODO figure out how to send delta-movement, maybe?
            
            player.setDeltaMovement(playerMovement);

            if (shouldSync) {

                player.hurtMarked = true;
            }

        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    private boolean isHeadInBlock(Player player) {
        BlockPos headPos = player.blockPosition().above();
        Level level = player.level();
        BlockState blockState = level.getBlockState(headPos);
        return !blockState.isAir() && blockState.getFluidState().isEmpty();
    }

    private boolean isFeetInBlock(Player player) {
        BlockPos pos = player.blockPosition();
        Level level = player.level();
        BlockState blockState = level.getBlockState(pos);
        return !blockState.isAir() && blockState.getFluidState().isEmpty();
    }


}