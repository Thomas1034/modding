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
package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.Converter;
import com.startraveler.verdant.registry.BlockTransformerRegistry;
import com.startraveler.verdant.registry.DamageSourceRegistry;
import com.startraveler.verdant.timer.BlockTransformerTimer;
import com.startraveler.verdant.timer.TimerListSavedData;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ToxicAshItem extends Item implements Converter {
    public static final int STEP = 3;
    public static final int DELAY_PER_STEP = 10;
    public static final int BASE_DELAY = 20;
    protected final int range;
    protected final int randomRange;

    public ToxicAshItem(Properties properties, int range, int randomRange) {
        super(properties);
        this.range = range;
        this.randomRange = randomRange;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (level instanceof ServerLevel serverLevel && (state.is(VerdantTags.Blocks.ALLOWS_ASH_SPREAD) || this.canConvert(
                state,
                serverLevel
        ))) {

            this.convertInRadius(context.getPlayer(), serverLevel, pos, this.getRadius(serverLevel.random));

            ItemStack stack = context.getItemInHand();
            Player player = context.getPlayer();
            if (player != null) {
                player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
            }
            if (stack.getMaxStackSize() != 1) {
                stack.shrink(1);
                return InteractionResult.SUCCESS_SERVER;
            } else {
                ItemStack result = this.getEmptySuccessItem(stack, player);
                if (player != null) {
                    player.setItemInHand(context.getHand(), result);
                }
                return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(result);
            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        @NotNull ItemStack itemStack = player.getItemInHand(hand);

        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (!level.mayInteract(player, blockPos)) {
                return InteractionResult.PASS;
            }

            if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                if (level instanceof ServerLevel serverLevel) {

                    this.convertInRadius(player, serverLevel, blockPos, this.getRadius(serverLevel.random));

                    player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                    if (itemStack.getMaxStackSize() != 1) {
                        itemStack.shrink(1);
                        return InteractionResult.SUCCESS_SERVER;
                    } else {
                        return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(this.getEmptySuccessItem(
                                itemStack,
                                player
                        ));
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    protected void convertInRadius(@Nullable final Player player, final ServerLevel level, final BlockPos original, final int radius) {
        final int radiusSqr = radius * radius;
        Deque<BlockPos> stack = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();

        stack.push(original);

        while (!stack.isEmpty()) {
            BlockPos current = stack.pop();

            // Skip if already visited or out of radius
            if (!visited.add(current) || current.distSqr(original) > radiusSqr) {
                continue;
            }

            BlockState state = level.getBlockState(current);

            boolean blocksSpread = state.is(VerdantTags.Blocks.BLOCKS_ASH_SPREAD);

            if (blocksSpread) {
                continue;
            }

            boolean convertible = this.canConvert(state, level);
            boolean passable = state.is(VerdantTags.Blocks.ALLOWS_ASH_SPREAD);

            if (!convertible && !passable) {
                continue;
            }

            // Add neighboring positions to stack
            stack.push(current.above());
            stack.push(current.below());
            stack.push(current.north());
            stack.push(current.south());
            stack.push(current.east());
            stack.push(current.west());
        }

        Map<Integer, List<BlockPos>> positionsByDelay = new HashMap<>();
        for (BlockPos pos : visited) {
            int dist = (int) Math.ceil(Math.sqrt(pos.distSqr(original)));
            positionsByDelay.computeIfAbsent(dist, i -> new ArrayList<>()).add(pos);
            // this.convert(level, pos);
        }

        positionsByDelay.forEach((distance, blockPosList) -> {
            Constants.LOG.warn(
                    "Adding {} blocks at distance {} with delay {}.",
                    blockPosList.size(),
                    distance,
                    ((long) DELAY_PER_STEP * (distance / STEP)) + BASE_DELAY
            );
            TimerListSavedData.addTimer(
                    level,
                    new BlockTransformerTimer(
                            ((long) DELAY_PER_STEP * (distance / STEP)) + BASE_DELAY,
                            this.getTransformer(),
                            blockPosList
                    )
            );
        });

        // Handle entity damage within the affected radius
        AABB bounds = new AABB(
                original.offset(-radius - 1, -radius - 1, -radius - 1).getCenter(),
                original.offset(radius + 1, radius + 1, radius + 1).getCenter()
        );
        List<LivingEntity> affectedEntities = level.getEntitiesOfClass(LivingEntity.class, bounds);
        for (LivingEntity entity : affectedEntities) {
            if (visited.contains(entity.blockPosition()) || visited.contains(entity.getOnPos())) {
                this.damageEntity(level, entity, player instanceof ServerPlayer serverPlayer ? serverPlayer : null);
            }
        }
    }

    protected void damageEntity(ServerLevel level, LivingEntity entity, @Nullable ServerPlayer player) {
        if (entity.getType().is(VerdantTags.EntityTypes.TOXIC_ASH_DAMAGES)) {
            Holder<DamageType> type = DamageSourceRegistry.get(level.registryAccess(), DamageSourceRegistry.TOXIC_ASH);
            DamageSource source = new DamageSource(type, player);
            entity.hurtServer(level, source, 20);
        }
    }

    protected int getRadius(RandomSource random) {
        return this.range + random.nextInt(this.randomRange);
    }


    public ItemStack getEmptySuccessItem(ItemStack stack, Player player) {
        return (player != null && !player.hasInfiniteMaterials()) ? stack.has(DataComponents.USE_REMAINDER) ? stack.getOrDefault(
                        DataComponents.USE_REMAINDER,
                        new UseRemainder(ItemStack.EMPTY)
                )
                .convertInto() : ItemStack.EMPTY : stack;
    }

    @Override
    public Identifier getTransformer() {
        return BlockTransformerRegistry.TOXIC_ASH;
    }
}

