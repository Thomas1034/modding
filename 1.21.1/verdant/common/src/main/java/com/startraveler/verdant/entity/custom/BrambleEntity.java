package com.startraveler.verdant.entity.custom;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.VerdantGrower;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.BlockTransformerRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.timer.BlockTransformerTimer;
import com.startraveler.verdant.timer.PlaceBlocksTimer;
import com.startraveler.verdant.timer.PrintForTestingTimer;
import com.startraveler.verdant.timer.TimerListSavedData;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrambleEntity extends AbstractGolem implements Enemy, VerdantGrower {

    public static final int BASE_INVULNERABLE_TICKS = 20 * 5;
    public static final byte HEALTH_STAGES = 3;
    protected static final EntityDataAccessor<Byte> DATA_HEALTH_ID = SynchedEntityData.defineId(
            BrambleEntity.class,
            EntityDataSerializers.BYTE
    );
    protected static final String DATA_HEALTH_NAME = "HealthStage";
    protected static final EntityDataAccessor<Integer> DATA_INVULNERABLE_TICKS_ID = SynchedEntityData.defineId(
            BrambleEntity.class,
            EntityDataSerializers.INT
    );
    protected static final String DATA_INVULNERABLE_TICKS_NAME = "InvulnerableTicks";

    public BrambleEntity(EntityType<? extends AbstractGolem> type, Level level) {
        super(type, level);
        this.setHealthStage(HEALTH_STAGES);
        this.setInvulnerableTicks(0);
    }


    @Override
    protected void registerGoals() {
        // TODO

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0);
    }

    public static void emitConversionSphere(ServerLevel level, BlockPos pos, int maxRadius, Identifier transformer, int delay, int step) {

        List<List<BlockPos>> positions = new ArrayList<>(Stream.generate(ArrayList<BlockPos>::new)
                .limit(maxRadius)
                .toList());
        for (int i = -maxRadius; i <= maxRadius; i++) {
            for (int j = -maxRadius; j <= maxRadius; j++) {
                for (int k = -maxRadius; k <= maxRadius; k++) {
                    int r = (int) Math.floor(Math.sqrt(i * i + j * j + k * k));
                    if (r < maxRadius) {
                        positions.get(r).add(pos.offset(i, j, k));
                    }
                }
            }
        }

        for (int i = 0; i < maxRadius; i++) {
            List<BlockPos> circlePositions = positions.get(i);
            TimerListSavedData.addTimer(
                    level,
                    new BlockTransformerTimer(((long) step * i) + delay, transformer, circlePositions)
            );
        }
    }

    public static List<PlaceBlocksTimer.BlockPlaceDirective> buildShell(ServerLevel level, BlockPos center) {
        return BrambleEntityPatterns.offsetMap(BrambleEntityPatterns.shellStates(level.random), center)
                .entrySet()
                .stream()
                .map(entry -> new PlaceBlocksTimer.BlockPlaceDirective(
                        entry.getValue(),
                        entry.getKey(),
                        BlockPredicate.Builder.block()
                                .of(BuiltInRegistries.BLOCK, VerdantTags.Blocks.REPLACEABLE_BY_BRAMBLE)
                                .build()
                ))
                .toList();
    }

    public static Integer sortByY(BlockPos center, BlockPos each) {
        return 2 * each.getY();
    }

    @SuppressWarnings("unused")
    public static Integer sortByZ(BlockPos center, BlockPos each) {
        return 2 * each.getZ();
    }

    public static void emitBlocks(ServerLevel level, BlockPos pos, List<PlaceBlocksTimer.BlockPlaceDirective> directives, BiFunction<BlockPos, BlockPos, Integer> timingGetter) {
        Map<Integer, List<PlaceBlocksTimer.BlockPlaceDirective>> grouped = new HashMap<>();
        int min = Integer.MAX_VALUE;
        for (PlaceBlocksTimer.BlockPlaceDirective directive : directives) {
            Integer key = timingGetter.apply(pos, directive.pos());
            if (key < min) {
                min = key;
            }
            // Constants.LOG.warn("Adding {} to {}", directive.pos(), key);
            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(directive);
        }

        for (Map.Entry<Integer, List<PlaceBlocksTimer.BlockPlaceDirective>> entry : grouped.entrySet()) {
            // Constants.LOG.warn("Placing {} blocks at time {}", entry.getValue().size(), entry.getKey() - min);
            TimerListSavedData.addTimer(level, new PlaceBlocksTimer(entry.getKey() - min, entry.getValue()));
        }
    }

    public static void renewArenaFloor(ServerLevel level, BlockPos pos, int maxRadius, Block block, Either<Block, TagKey<Block>> toReplace, int delay, int step) {

        List<List<PlaceBlocksTimer.BlockPlaceDirective>> directives = new ArrayList<>(Stream.generate(ArrayList<PlaceBlocksTimer.BlockPlaceDirective>::new)
                .limit(maxRadius)
                .toList());
        for (int i = -maxRadius; i <= maxRadius; i++) {
            for (int j = -maxRadius; j <= 0; j++) {
                for (int k = -maxRadius; k <= maxRadius; k++) {
                    int r = (int) Math.floor(Math.sqrt(i * i + j * j + k * k));
                    if (r < maxRadius) {
                        int finalI = i;
                        int finalJ = j;
                        int finalK = k;
                        toReplace.ifRight(blockToReplace -> directives.get(r)
                                .add(new PlaceBlocksTimer.BlockPlaceDirective(
                                        block.defaultBlockState(),
                                        pos.offset(finalI, finalJ, finalK),
                                        BlockPredicate.Builder.block()
                                                .of(BuiltInRegistries.BLOCK, blockToReplace)
                                                .build()
                                )));
                        toReplace.ifLeft(tagToReplace -> directives.get(r).add(new PlaceBlocksTimer.BlockPlaceDirective(
                                block.defaultBlockState(),
                                pos.offset(finalI, finalJ, finalK),
                                BlockPredicate.Builder.block().of(BuiltInRegistries.BLOCK, tagToReplace).build()
                        )));
                    }
                }
            }
        }

        for (int i = 0; i < maxRadius; i++) {
            List<PlaceBlocksTimer.BlockPlaceDirective> circleDirectives = directives.get(i);
            TimerListSavedData.addTimer(level, new PlaceBlocksTimer(((long) step * i) + delay, circleDirectives));
        }

    }


    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_HEALTH_ID, (byte) 0);
        builder.define(DATA_INVULNERABLE_TICKS_ID, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {

            // Clear all effects.
            this.removeAllEffects();

            // Restore all health.
            byte healthStage = this.getHealthStage();
            float health = this.getHealth();
            float maxHealth = this.getMaxHealth();
            if (health < maxHealth && healthStage > 0) {
                this.setHealth(maxHealth);
                this.markHurt();
            }

            // Handle invulnerability ticks.
            int invulnerabilityTicks = this.getInvulnerableTicks();
            if (invulnerabilityTicks > 0) {
                invulnerabilityTicks--;
                this.setInvulnerableTicks(invulnerabilityTicks);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte(DATA_HEALTH_NAME, this.getHealthStage());
        compound.putInt(DATA_INVULNERABLE_TICKS_NAME, this.getInvulnerableTicks());
    }

    @Override
    public void readAdditionalSaveData(@NotNull ValueInput compound) {
        super.readAdditionalSaveData(compound);
        this.setHealthStage(compound.getByteOr(DATA_HEALTH_NAME, HEALTH_STAGES));
        this.setInvulnerableTicks(compound.getIntOr(DATA_INVULNERABLE_TICKS_NAME, 0));
    }

    @Override
    public boolean hurtServer(@NotNull ServerLevel level, DamageSource source, float damage) {
        // TODO change so that it only takes one damage for all hits (i.e. pass damage = 1 to super)
        // then just track the health as the stage.
        if (source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return super.hurtServer(level, source, damage);
        } else {
            if (this.isInvulnerable()) {
                return false;
            }
            damage = Mth.clamp(damage, 0, this.getMaxHealth() / 2);
            boolean result = super.hurtServer(level, source, damage);

            byte healthStage = this.getHealthStage();
            if (result && healthStage > 0 && damage > 0) {
                healthStage--;
                this.setHealthStage(healthStage);
                this.triggerStageUpdate();
                this.resetInvulnerableTicks();
            }
            return result;
        }
    }

    // Do death effects like blast and beams of light?
    public void die(@NotNull DamageSource cause) {
        super.die(cause);
    }

    public void triggerStageUpdate() {
        Constants.LOG.warn("Updating to stage {}", this.getHealthStage());


        if (this.level() instanceof ServerLevel serverLevel) {
            int maxRadius = 15;
            int delayBetweenRings = 2;
            int numberOfErosions = 5;

            emitBlocks(serverLevel, this.getOnPos(), buildShell(serverLevel, this.getOnPos()), BrambleEntity::sortByY);

            //            for (int i = -8; i <= 8; i++) {
            //                for (int j = -8; j <= 8; j++) {
            //                    for (int k = -8; k <= 8; k++) {
            //                        BlockState state = serverLevel.getBlockState(this.getOnPos().offset(i, j, k));
            //                        if (!state.isAir()) {
            //                            Constants.LOG.warn("{} | {}", new BlockPos(i, j, k), state);
            //                        }
            //
            //                    }
            //                }
            //            }

            renewArenaFloor(
                    serverLevel,
                    this.getOnPos(),
                    maxRadius,
                    BlockRegistry.VERDANT_ROOTED_DIRT.get(),
                    Either.right(BlockTags.REPLACEABLE_BY_TREES),
                    0,
                    delayBetweenRings
            );
            renewArenaFloor(
                    serverLevel,
                    this.getOnPos(),
                    maxRadius,
                    BlockRegistry.VERDANT_ROOTED_DIRT.get(),
                    Either.left(Blocks.AIR),
                    0,
                    delayBetweenRings
            );
            renewArenaFloor(
                    serverLevel,
                    this.getOnPos(),
                    maxRadius,
                    BlockRegistry.SAP_BLOCK.get(),
                    Either.left(Blocks.WATER),
                    0,
                    delayBetweenRings
            );
            for (int i = 0; i < numberOfErosions; i++) {
                emitConversionSphere(
                        serverLevel,
                        this.getOnPos(),
                        maxRadius,
                        BlockTransformerRegistry.EROSION,
                        maxRadius * delayBetweenRings * i,
                        delayBetweenRings
                );
                emitConversionSphere(
                        serverLevel,
                        this.getOnPos(),
                        maxRadius,
                        BlockTransformerRegistry.VERDANT_ROOTS,
                        maxRadius * delayBetweenRings * i + delayBetweenRings * 3,
                        delayBetweenRings
                );
            }

            @SuppressWarnings("unused")
            int currentDelay = maxRadius * delayBetweenRings * numberOfErosions + delayBetweenRings * 3;

            TimerListSavedData.addTimer(
                    serverLevel,
                    new PrintForTestingTimer("Finished timer for phase " + this.getHealthStage(), 200L)
            );
        }
    }

    @Override
    public boolean canBeCollidedWith(Entity entity) {
        return this.isAlive();
    }

    @Override
    public boolean isInvulnerable() {
        return super.isInvulnerable() || this.getInvulnerableTicks() > 0;
    }


    @Override
    public @NotNull Vec3 getDeltaMovement() {
        return Vec3.ZERO;
    }

    @Override
    public void setDeltaMovement(@NotNull Vec3 p_149804_) {
    }

    public byte getHealthStage() {
        return this.entityData.get(DATA_HEALTH_ID);
    }

    public void setHealthStage(byte health) {
        this.entityData.set(DATA_HEALTH_ID, health);
    }

    public int getInvulnerableTicksForCurrentDifficulty() {
        return this.level().getDifficulty().getId() * BASE_INVULNERABLE_TICKS;
    }

    public int getInvulnerableTicks() {
        return this.entityData.get(DATA_INVULNERABLE_TICKS_ID);
    }

    public void setInvulnerableTicks(int ticks) {
        this.entityData.set(DATA_INVULNERABLE_TICKS_ID, ticks);
    }

    public void resetInvulnerableTicks() {
        this.setInvulnerableTicks(this.getInvulnerableTicksForCurrentDifficulty());
    }

    public BlockState getModelForCurrentStage() {
        return BlockRegistry.BRAMBLE_FRAME.get()
                .defaultBlockState()
                .setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
    }

    public BlockState getShellModelForCurrentStage() {
        return BlockRegistry.SAP_BLOCK.get().defaultBlockState();
    }

    protected static class BrambleEntityPatterns {
        private BrambleEntityPatterns() {
        }

        public static Map<BlockPos, BlockState> offsetMap(Map<BlockPos, BlockState> oldMap, BlockPos center) {
            return oldMap.entrySet()
                    .stream()
                    .map(entry -> Pair.of(entry.getKey().offset(center), entry.getValue()))
                    .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        }

        public static Map<BlockPos, BlockState> shellStates(RandomSource random) {
            return shell().entrySet().stream().map(entry -> {
                BlockState state = entry.getValue()
                        .defaultBlockState()
                        .trySetValue(BlockStateProperties.PERSISTENT, true)
                        .trySetValue(BlockStateProperties.AXIS, Direction.Axis.getRandom(random));
                return Pair.of(entry.getKey(), state);
            }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        }

        public static Map<BlockPos, Block> shell() {
            Map<BlockPos, Block> map = new HashMap<>();

            for (int i = -5; i <= 5; i++) {
                for (int j = -3; j <= 4; j++) {
                    for (int k = -5; k <= 5; k++) {
                        if (i * i + j * j + k * k <= (5 * 5 + 1)) {

                            map.put(new BlockPos(i, j, k), Blocks.AIR);
                        }
                    }
                }
            }
            map.put(new BlockPos(-6, -2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, -2, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, -2, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, -2, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, -2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, -1, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, -1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-6, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-6, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-6, -1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, -1, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 0, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 0, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 0, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 0, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 0, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 0, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 0, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 1, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 1, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 1, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 1, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 1, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 2, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 2, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 2, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-6, 3, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 3, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 3, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-6, 3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -2, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -2, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -2, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -1, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -1, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, -1, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, -1, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, -1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 0, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 0, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 0, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 0, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 0, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 0, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 0, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 0, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 0, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 2, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 2, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 2, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 2, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 2, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 3, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 3, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 3, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-5, 3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 4, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 4, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 4, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 4, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-5, 4, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 4, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-5, 4, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -3, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -3, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -3, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -1, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, -1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, -1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, -1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, -1, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 0, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 0, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 0, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 0, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 0, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 0, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 0, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 0, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 0, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 0, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 0, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 1, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 1, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 3, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 3, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 3, -1), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-4, 3, 1), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-4, 3, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 3, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 4, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 4, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 4, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 4, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 4, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 4, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 4, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-4, 4, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 4, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 5, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 5, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 5, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 5, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 5, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 5, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-4, 5, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -3, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -3, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -3, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -2, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -2, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -2, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -2, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -2, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -1, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -1, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -1, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -1, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -1, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -1, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, -1, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, -1, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 0, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 0, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 0, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 0, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 0, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-3, 0, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-3, 0, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-3, 0, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-3, 0, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-3, 0, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 0, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 0, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 0, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 1, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 1, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 1, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 1, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 1, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 1, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 2, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 2, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 2, -2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-3, 2, 2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-3, 2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 2, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 2, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 3, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 3, -2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-3, 3, 2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-3, 3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 3, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 4, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 4, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 4, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 4, -2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-3, 4, -1), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-3, 4, 0), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-3, 4, 1), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-3, 4, 2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-3, 4, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 4, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 4, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 5, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 5, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 5, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 5, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 5, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 5, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 5, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 5, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 5, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 6, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 6, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 6, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 6, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-3, 6, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 7, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 7, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-3, 7, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -4, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -4, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -4, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -3, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -3, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -3, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -2, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -2, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -2, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -2, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -2, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -2, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -2, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -2, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -2, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -1, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -1, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -1, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, -1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, -1, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 0, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 0, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, 0, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 0, -3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 0, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 0, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 0, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 0, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 0, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 0, 3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 0, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 0, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, 0, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 1, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 1, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 1, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 1, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 2, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 2, -3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-2, 2, 3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-2, 2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 2, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 3, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 3, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 3, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, 3, -3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-2, 3, 3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-2, 3, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, 3, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 3, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 4, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 4, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, 4, -3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-2, 4, -2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-2, 4, 2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-2, 4, 3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-2, 4, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, 4, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 5, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 5, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, 5, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 5, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 5, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 5, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 5, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 5, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-2, 5, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 6, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 6, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 6, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 6, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 6, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 6, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 6, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 7, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-2, 7, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 7, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 7, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-2, 7, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -4, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -4, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -4, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -4, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -4, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -3, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -3, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -3, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -2, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -2, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -2, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -2, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -2, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -2, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -2, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -2, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -2, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, -1, -6), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, -1, 6), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 0, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 0, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 0, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, 0, -3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 0, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 0, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 0, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 0, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 0, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 0, 3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 0, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, 0, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 0, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 1, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 1, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 2, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 2, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 2, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 2, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 3, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 3, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 3, -4), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-1, 3, 4), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-1, 3, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 3, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 4, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, 4, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 4, -3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-1, 4, 3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(-1, 4, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 4, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, 5, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, 5, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 5, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, 5, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 5, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 5, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 5, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, 5, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 5, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, 6, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 6, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 6, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 6, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 6, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 6, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 6, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(-1, 7, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(-1, 7, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 7, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(-1, 7, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -4, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -4, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -4, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -4, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -4, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -3, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -3, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -3, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -2, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -2, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -2, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -2, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -2, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -2, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -2, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -2, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -2, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, -1, -6), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, -1, 6), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 0, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 0, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 0, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, 0, -3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 0, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 0, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 0, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 0, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 0, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 0, 3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 0, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, 0, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 0, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 1, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 1, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 2, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 2, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 2, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 2, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 3, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 3, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 3, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 3, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 4, -5), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 4, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 4, -3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(0, 4, 3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(0, 4, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 4, 5), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 5, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, 5, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 5, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, 5, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 5, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 5, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 5, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, 5, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 5, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, 6, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 6, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 6, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 6, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 6, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 6, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 6, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(0, 7, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(0, 7, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 7, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(0, 7, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -4, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -4, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -4, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -4, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -4, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -3, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -3, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -3, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -2, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -2, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -2, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -2, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -2, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -2, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -2, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -2, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -2, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, -1, -6), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, -1, 6), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 0, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 0, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 0, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, 0, -3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 0, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 0, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 0, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 0, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 0, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 0, 3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 0, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, 0, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 0, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 1, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 1, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 2, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 2, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 2, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 2, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 3, -6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 3, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 3, -4), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(1, 3, 4), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(1, 3, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 3, 6), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 4, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, 4, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 4, -3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(1, 4, 3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(1, 4, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 4, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, 5, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, 5, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 5, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, 5, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 5, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 5, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 5, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, 5, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 5, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, 6, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 6, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 6, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 6, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 6, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 6, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 6, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(1, 7, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(1, 7, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 7, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(1, 7, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -4, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -4, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -4, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -3, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -3, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -3, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -2, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -2, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -2, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -2, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -2, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -2, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -2, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -2, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -2, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -1, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -1, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -1, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, -1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, -1, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 0, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 0, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, 0, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 0, -3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 0, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 0, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 0, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 0, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 0, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 0, 3), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 0, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 0, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, 0, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 1, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 1, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 1, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 1, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 2, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 2, -3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(2, 2, 3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(2, 2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 2, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 3, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 3, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 3, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, 3, -3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(2, 3, 3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(2, 3, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, 3, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 3, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 4, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 4, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, 4, -3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(2, 4, -2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(2, 4, 2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(2, 4, 3), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(2, 4, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, 4, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 5, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 5, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, 5, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 5, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 5, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 5, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 5, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 5, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(2, 5, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 6, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 6, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 6, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 6, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 6, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 6, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 6, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 7, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(2, 7, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 7, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 7, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(2, 7, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -3, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -3, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -3, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -2, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -2, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -2, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -2, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -2, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -1, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -1, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -1, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -1, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -1, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -1, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, -1, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, -1, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 0, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 0, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 0, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 0, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 0, -2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(3, 0, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(3, 0, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(3, 0, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(3, 0, 2), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(3, 0, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 0, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 0, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 0, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 1, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 1, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 1, -4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 1, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 1, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 1, 4), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 1, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 1, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 2, -6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 2, -5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 2, -2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(3, 2, 2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(3, 2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 2, 5), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 2, 6), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 3, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 3, -2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(3, 3, 2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(3, 3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 3, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 4, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 4, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 4, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 4, -2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(3, 4, -1), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(3, 4, 0), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(3, 4, 1), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(3, 4, 2), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(3, 4, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 4, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 4, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 5, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 5, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 5, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 5, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 5, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 5, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 5, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 5, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 5, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 6, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 6, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 6, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 6, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(3, 6, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 7, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 7, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(3, 7, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -3, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -3, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -3, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -1, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, -1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, -1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, -1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, -1, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 0, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 0, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 0, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 0, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 0, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 0, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 0, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 0, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 0, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 0, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 0, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 1, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 1, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 2, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 2, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 3, -5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 3, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 3, -1), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(4, 3, 1), BlockRegistry.WILTED_STRANGLER_LEAVES.get());
            map.put(new BlockPos(4, 3, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 3, 5), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 4, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 4, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 4, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 4, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 4, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 4, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 4, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(4, 4, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 4, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 5, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 5, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 5, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 5, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 5, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 5, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(4, 5, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -2, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -2, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -2, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -1, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -1, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, -1, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, -1, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, -1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 0, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 0, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 0, -2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 0, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 0, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 0, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 0, 2), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 0, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 0, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 1, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 1, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 1, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 1, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 2, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 2, -3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 2, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 2, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 2, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 2, 3), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 2, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 3, -4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 3, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 3, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 3, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 3, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(5, 3, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 3, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 3, 4), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 4, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 4, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 4, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 4, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(5, 4, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 4, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(5, 4, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, -2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, -2, -1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, -2, 0), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, -2, 1), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, -2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, -1, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, -1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, -1, -1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(6, -1, 0), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(6, -1, 1), WoodSets.HEARTWOOD.getWood().get());
            map.put(new BlockPos(6, -1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, -1, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 0, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 0, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 0, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 0, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 0, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 0, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 0, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 1, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 1, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 1, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 1, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 1, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 1, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 1, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 2, -3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 2, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 2, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 2, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 2, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 2, 2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 2, 3), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 3, -2), WoodSets.STRANGLER.getWood().get());
            map.put(new BlockPos(6, 3, -1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 3, 0), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 3, 1), BlockRegistry.SAP_BLOCK.get());
            map.put(new BlockPos(6, 3, 2), WoodSets.STRANGLER.getWood().get());
            return map;
        }
    }
}
