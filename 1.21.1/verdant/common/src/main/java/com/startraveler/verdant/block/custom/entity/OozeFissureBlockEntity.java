package com.startraveler.verdant.block.custom.entity;

import com.startraveler.verdant.block.custom.OozeFissureBlock;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CreakingHeartBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.CreakingHeartState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class OozeFissureBlockEntity extends BlockEntity implements Spawner {
    protected static final int OOZE_FISSURE_MIN_SPAWN_DELAY = 80;
    protected static final int OOZE_FISSURE_MAX_SPAWN_DELAY = 160;
    protected static final int OOZE_FISSURE_SPAWN_COUNT = 2;
    protected static final int OOZE_FISSURE_MAX_NEARBY_ENTITIES = 6;
    protected static final int OOZE_FISSURE_REQUIRED_PLAYER_RANGE = 16;
    protected static final int OOZE_FISSURE_SPAWN_RANGE = 4;
    protected static final int DELAY_FACTOR_IF_NOT_NATURAL = 20;
    protected final BaseSpawner spawner = new BaseSpawner() {
        @Override
        public void setNextSpawnData(@Nullable Level level, @NotNull BlockPos pos, @NotNull SpawnData spawnData) {
            super.setNextSpawnData(level, pos, spawnData);
            if (level != null) {
                BlockState blockstate = level.getBlockState(pos);
                level.sendBlockUpdated(pos, blockstate, blockstate, 260);
            }
        }

        @Override
        public void broadcastEvent(Level level, @NotNull BlockPos pos, int event) {

            level.blockEvent(pos, Blocks.SPAWNER, event, 0);
        }
    };
    private int outputSignal = 0;

    public OozeFissureBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityTypeRegistry.OOZE_FISSURE_BLOCK_ENTITY.get(), pos, blockState);
        this.spawner.minSpawnDelay = OOZE_FISSURE_MIN_SPAWN_DELAY;
        this.spawner.maxSpawnDelay = OOZE_FISSURE_MAX_SPAWN_DELAY;
        this.spawner.spawnCount = OOZE_FISSURE_SPAWN_COUNT;
        this.spawner.maxNearbyEntities = OOZE_FISSURE_MAX_NEARBY_ENTITIES;
        this.spawner.requiredPlayerRange = OOZE_FISSURE_REQUIRED_PLAYER_RANGE;
        this.spawner.spawnRange = OOZE_FISSURE_SPAWN_RANGE;
    }


    public static void clientTick(Level level, BlockPos pos, BlockState state, OozeFissureBlockEntity blockEntity) {
        if (state.getOptionalValue(OozeFissureBlock.STATE)
                .orElse(CreakingHeartState.UPROOTED) == CreakingHeartState.AWAKE) {
            blockEntity.spawner.clientTick(level, pos);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, OozeFissureBlockEntity blockEntity) {
        if (level instanceof ServerLevel serverLevel) {
            int outputSignal = blockEntity.computeAnalogOutputSignal();
            if (blockEntity.outputSignal != outputSignal) {
                blockEntity.outputSignal = outputSignal;
                level.updateNeighbourForOutputSignal(pos, Blocks.CREAKING_HEART);
            }
            BlockState updatedFissureState = OozeFissureBlockEntity.updateFissureState(level, state, pos);

            if (updatedFissureState != state) {
                state = updatedFissureState;
                level.setBlockAndUpdate(pos, state);
            }

            if (!state.getOptionalValue(OozeFissureBlock.NATURAL).orElse(false)) {
                blockEntity.spawner.minSpawnDelay = OOZE_FISSURE_MIN_SPAWN_DELAY * DELAY_FACTOR_IF_NOT_NATURAL;
                blockEntity.spawner.maxSpawnDelay = OOZE_FISSURE_MAX_SPAWN_DELAY * DELAY_FACTOR_IF_NOT_NATURAL;
            } else {
                blockEntity.spawner.minSpawnDelay = OOZE_FISSURE_MIN_SPAWN_DELAY;
                blockEntity.spawner.maxSpawnDelay = OOZE_FISSURE_MAX_SPAWN_DELAY;
            }

            if (state.getOptionalValue(OozeFissureBlock.STATE)
                    .orElse(CreakingHeartState.UPROOTED) == CreakingHeartState.AWAKE) {
                blockEntity.spawner.serverTick(serverLevel, pos);
            }


            if (blockEntity.spawner.getOrCreateDisplayEntity(level, pos) == null) {
                blockEntity.setEntityId(EntityTypeRegistry.OOZE.get(), serverLevel.random);
            }


        }

    }


    @SuppressWarnings("ConstantValue")
    public static BlockState updateFissureState(Level level, BlockState state, BlockPos pos) {
        if (!(state.getBlock() instanceof OozeFissureBlock oozeFissureBlock)) {
            return state;
        }
        if (!oozeFissureBlock.hasRequiredLogs(state, level, pos)) {
            return state.setValue(CreakingHeartBlock.STATE, CreakingHeartState.UPROOTED);
        } else {
            boolean timeAgreeing = OozeFissureBlock.timeAgreeing(level);
            return state.setValue(
                    CreakingHeartBlock.STATE,
                    timeAgreeing ? CreakingHeartState.AWAKE : CreakingHeartState.DORMANT
            );
        }
    }

    protected int computeAnalogOutputSignal() {
        return 15;
    }


    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag compoundtag = this.saveCustomOnly(registries);
        compoundtag.remove("SpawnPotentials");
        return compoundtag;
    }

    public boolean triggerEvent(int id, int type) {
        if (this.level != null) {
            return this.spawner.onEventTriggered(this.level, id) || super.triggerEvent(id, type);
        }
        return super.triggerEvent(id, type);
    }

    public void setEntityId(@NotNull EntityType<?> type, @NotNull RandomSource random) {
        this.spawner.setEntityId(type, this.level, random, this.worldPosition);
        this.setChanged();
    }

    public int getAnalogOutputSignal() {
        return this.outputSignal;
    }
}
