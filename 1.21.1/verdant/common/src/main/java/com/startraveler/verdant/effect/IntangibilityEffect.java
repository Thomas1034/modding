/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.effect;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.MobEffectRegistry;
import com.startraveler.verdant.util.VerdantTags;
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

    private static final int WEIGHTLESS_LENGTH = 15;

    public IntangibilityEffect(MobEffectCategory category, int color) {
        super(category, color);
        // Decrease gravity by 80%
        this.addAttributeModifier(
                Attributes.GRAVITY,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "intangibility/less_gravity"),
                -0.8,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    // TODO: Cool plant, causes blindness: https://en.wikipedia.org/wiki/Euphorbia_tirucalli

    // Returns true if the player can go through the blocks beneath and at their position.
    // This prevents the player from falling out of the world, and enables them to stand on
    // intangible blocks.
    public static boolean canGoThroughBlockBeneath(Player player) {
        BlockPos atPos = player.blockPosition();
        Level level = player.level();
        BlockState belowState = level.getBlockState(atPos.below());
        BlockState atState = level.getBlockState(atPos);
        BlockState aboveState = level.getBlockState(atPos.above());
        return !(belowState.is(VerdantTags.Blocks.BLOCKS_INTANGIBLE) || atState.is(VerdantTags.Blocks.BLOCKS_INTANGIBLE) || aboveState.is(
                VerdantTags.Blocks.BLOCKS_INTANGIBLE));
    }

    public static boolean isIntangible(Player player) {
        return player.hasEffect(MobEffectRegistry.INTANGIBLE.asHolder());
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {

        if (entity instanceof Player player) {

            // Reset player fall distance, preventing any fall damage while intangible.
            player.fallDistance = 0;
            // Calculate the acceleration with which the player should be propelled upwards when inside blocks.
            double upAcceleration = 2 * player.getAttributeValue(Attributes.GRAVITY);
            double maxUpVelocity = upAcceleration * 10;
            // Track whether the motion should sync to the client - this will overwrite the client's motion, slowing the player significantly.
            boolean shouldSync = false;

            // Cache values to prevent excessive read operations.
            boolean feetInBlock = isBlockAtRelative(player, 0);
            boolean headInBlock = isBlockAtRelative(player, 1);
            boolean blockBelow = isBlockAtRelative(player, -1);
            boolean headBelowBlock = isBlockAtRelative(player, 2);
            boolean canGoThroughBeneath = canGoThroughBlockBeneath(player);

            // Get the player's current movement, likely 0 because this is on the server.
            Vec3 playerMovement = player.getDeltaMovement();

            if (player.isShiftKeyDown() && (headBelowBlock || headInBlock || feetInBlock || blockBelow) && !player.hasEffect(
                    MobEffectRegistry.WEIGHTLESS.asHolder())) {
                // Handle dashing.
                Vec3 playerLookVec = player.getLookAngle();
                playerMovement = playerMovement.add(playerLookVec.scale(3)).scale(0.25);
                shouldSync = true;
                player.addEffect(new MobEffectInstance(MobEffectRegistry.WEIGHTLESS.asHolder(), WEIGHTLESS_LENGTH, 0));
            } else if ((feetInBlock || headInBlock) && canGoThroughBeneath) {
                // Handle upward propulsion when in blocks.
                double toAdd = playerMovement.y > maxUpVelocity ? 0 : Math.min(
                        upAcceleration,
                        maxUpVelocity - playerMovement.y
                );
                if (toAdd > 0) {
                    playerMovement = new Vec3(playerMovement.x, playerMovement.y + toAdd, playerMovement.z);
                    shouldSync = true;
                }
            }

            // Sync motion to the client if necessary
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

}
