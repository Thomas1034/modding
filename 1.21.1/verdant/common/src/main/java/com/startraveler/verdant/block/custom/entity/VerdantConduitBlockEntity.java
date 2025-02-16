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
package com.startraveler.verdant.block.custom.entity;

import com.google.common.collect.Lists;
import com.startraveler.verdant.block.VerdantGrower;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import com.startraveler.verdant.registry.MobEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Supplier;

public class VerdantConduitBlockEntity extends BlockEntity implements VerdantGrower {
    public static final Supplier<MobEffectInstance> VERDANT_ENERGY = () -> new MobEffectInstance(
            MobEffectRegistry.VERDANT_ENERGY.asHolder(),
            205,
            0
    );
    private static final int RADIUS = 16;
    private static final int ATTEMPTS_PER_TICK = 6;
    private static final long REEVALUATE_EVERY = 40;
    private final List<BlockPos> effectBlocks = Lists.newArrayList();
    public int tickCount;
    public int animationTickCount;
    private boolean isActive;
    private long nextAmbientSoundActivation;
    private int range;

    public VerdantConduitBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityTypeRegistry.VERDANT_CONDUIT_BLOCK_ENTITY.get(), pos, blockState);
        this.isActive = false;
        this.tickCount = 0;
        this.animationTickCount = 0;
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, VerdantConduitBlockEntity blockEntity) {
        ++blockEntity.tickCount;
        long i = level.getGameTime();
        if (i % REEVALUATE_EVERY == 0L) {
            // Keeping a list here prevents having to allocate a new one.
            blockEntity.isActive = updateShape(level, pos, blockEntity.effectBlocks);
        }

        if (blockEntity.isActive()) {
            ++blockEntity.animationTickCount;
        }

    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, VerdantConduitBlockEntity blockEntity) {

        ++blockEntity.tickCount;
        long gameTime = level.getGameTime();
        if (gameTime % REEVALUATE_EVERY == 0L) {
            boolean flag = updateShape(level, pos, blockEntity.effectBlocks);
            blockEntity.range = updateRange(level, pos);
            if (flag != blockEntity.isActive) {
                SoundEvent sound = flag ? SoundEvents.CONDUIT_ACTIVATE : SoundEvents.CONDUIT_DEACTIVATE;
                level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            blockEntity.isActive = flag;
        }

        if (blockEntity.isActive()) {
            applyEffects(level, pos, RADIUS + (-1 + (1 + blockEntity.range) / 5));
            if (gameTime % 80L == 0L) {
                level.playSound(null, pos, SoundEvents.CONDUIT_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            if (gameTime > blockEntity.nextAmbientSoundActivation) {
                blockEntity.nextAmbientSoundActivation = gameTime + 60L + (long) level.getRandom().nextInt(40);
                level.playSound(null, pos, SoundEvents.CONDUIT_AMBIENT_SHORT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            if (level instanceof ServerLevel serverLevel) {
                for (int j = 0; j < ATTEMPTS_PER_TICK; j++) {
                    int[] offset = randomPointInSphere(level.random, RADIUS);
                    BlockPos growAt = pos.offset(offset[0], offset[1], offset[2]);
                    blockEntity.erodeOrGrow(serverLevel, growAt, false);

                    BlockState forBoneMealing = level.getBlockState(growAt);
                    Block block = forBoneMealing.getBlock();
                    if (block instanceof BonemealableBlock bonemealableBlock && bonemealableBlock.isValidBonemealTarget(serverLevel,
                            growAt,
                            forBoneMealing
                    )) {
                        bonemealableBlock.performBonemeal(serverLevel, level.random, growAt, forBoneMealing);
                    }

                }
            }

        }
    }

    @SuppressWarnings("unchecked")
    private static void applyEffectInRadius(Level level, BlockPos pos, int radius, Supplier<MobEffectInstance>... effects) {
        AABB boxToCheck = AABB.ofSize(Vec3.atLowerCornerOf(pos), 1 + 2 * radius, 1 + 2 * radius, 1 + 2 * radius);
        List<Entity> toCheck = level.getEntities(null, boxToCheck);
        int radiusSqr = radius * radius;
        Vec3 center = Vec3.atCenterOf(pos);

        // Iterate and check
        for (Entity e : toCheck) {
            // Constants.LOG.warn("Checking entity {}", e);
            if (e instanceof LivingEntity le) {
                // Constants.LOG.warn("Entity {} is living at {}", le, le.blockPosition());
                // Distance check.
                if (le.distanceToSqr(center.x, center.y, center.z) < radiusSqr) {
                    // Constants.LOG.warn("Entity is in range");
                    // Add the effect.
                    for (Supplier<MobEffectInstance> effect : effects) {
                        // Constants.LOG.warn("Applying effect {}", effect.get());
                        le.addEffect(effect.get());
                    }
                }
            }
        }
    }


    public static int updateRange(Level level, BlockPos pos) {
        int range = 0;

        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                for (int k = -2; k <= 2; ++k) {


                    BlockPos posToCheck = pos.offset(i, j, k);
                    BlockState stateToCheck = level.getBlockState(posToCheck);

                    if (stateToCheck.is(BlockTags.LOGS)) {
                        range++;
                    }

                }
            }
        }

        return range;
    }

    public static boolean updateShape(Level level, BlockPos pos, List<BlockPos> positions) {
        positions.clear();

        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                for (int k = -2; k <= 2; ++k) {
                    int absI = Math.abs(i);
                    int absJ = Math.abs(j);
                    int absK = Math.abs(k);
                    if ((absK == 2 || absI == 2 || absJ == 2)) {
                        BlockPos posToCheck = pos.offset(i, j, k);
                        BlockState stateToCheck = level.getBlockState(posToCheck);

                        if (stateToCheck.is(BlockTags.LOGS)) {
                            positions.add(posToCheck);
                        }
                    }
                }
            }
        }

        return positions.size() >= 16;
    }

    /**
     * Generates a random integer position within a sphere of radius r.
     * By ChatGPT.
     *
     * @param radius The radius of the sphere.
     * @return An array of size 3 containing the random {x, y, z} position.
     */
    public static int[] randomPointInSphere(RandomSource random, int radius) {
        int radiusSquared = radius * radius; // Precompute r^2
        // Constants.LOG.warn("Getting random point in sphere. Radius is {}", radius);

        while (true) {
            // Generate random offsets within the bounding cube
            int dx = random.nextInt(2 * radius + 1) - radius; // Range: [-radius, radius]
            int dy = random.nextInt(2 * radius + 1) - radius;
            int dz = random.nextInt(2 * radius + 1) - radius;
            // Check if the point lies within the sphere
            if (dx * dx + dy * dy + dz * dz <= radiusSquared) {
                // Translate to the sphere's center and return
                return new int[]{dx, dy, dz};
            }
        }
    }

    private static void applyEffects(Level level, BlockPos pos, int radius) {
        applyEffectInRadius(level, pos, radius, VERDANT_ENERGY);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public float getActiveRotation(float partialTick) {
        return (this.animationTickCount + partialTick) * -0.0375F;
    }


}

