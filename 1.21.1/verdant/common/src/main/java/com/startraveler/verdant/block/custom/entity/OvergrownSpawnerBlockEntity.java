package com.startraveler.verdant.block.custom.entity;

import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class OvergrownSpawnerBlockEntity extends BlockEntity implements Spawner {
    protected static final int OVERGROWN_MIN_SPAWN_DELAY = 40;
    protected static final int OVERGROWN_MAX_SPAWN_DELAY = 80;
    protected static final int OVERGROWN_SPAWN_COUNT = 4;
    protected static final int OVERGROWN_MAX_NEARBY_ENTITIES = 6*16;
    protected static final int OVERGROWN_REQUIRED_PLAYER_RANGE = 16;
    protected static final int OVERGROWN_SPAWN_RANGE = 24;
    protected final BaseSpawner spawner = new BaseSpawner() {
        public void setNextSpawnData(@Nullable Level level, @NotNull BlockPos pos, @NotNull SpawnData spawnData) {
            super.setNextSpawnData(level, pos, spawnData);
            if (level != null) {
                BlockState blockstate = level.getBlockState(pos);
                level.sendBlockUpdated(pos, blockstate, blockstate, 260);
            }
        }

        public void broadcastEvent(Level level, @NotNull BlockPos pos, int event) {
            level.blockEvent(pos, Blocks.SPAWNER, event, 0);
        }
    };

    public OvergrownSpawnerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityTypeRegistry.OVERGROWN_SPAWNER.get(), pos, blockState);
        this.getSpawner().minSpawnDelay = OVERGROWN_MIN_SPAWN_DELAY;
        this.getSpawner().maxSpawnDelay = OVERGROWN_MAX_SPAWN_DELAY;
        this.getSpawner().spawnCount = OVERGROWN_SPAWN_COUNT;
        this.getSpawner().maxNearbyEntities = OVERGROWN_MAX_NEARBY_ENTITIES;
        this.getSpawner().requiredPlayerRange = OVERGROWN_REQUIRED_PLAYER_RANGE;
        this.getSpawner().spawnRange = OVERGROWN_SPAWN_RANGE;
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, OvergrownSpawnerBlockEntity blockEntity) {
        blockEntity.spawner.clientTick(level, pos);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, OvergrownSpawnerBlockEntity blockEntity) {
        blockEntity.spawner.serverTick((ServerLevel) level, pos);
    }

    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.spawner.load(this.level, this.worldPosition, input);
    }

    protected void saveAdditional(@NotNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        this.spawner.save(valueOutput);
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
        return this.spawner.onEventTriggered(this.level, id) || super.triggerEvent(id, type);
    }

    public void setEntityId(@NotNull EntityType<?> type, @NotNull RandomSource random) {
        this.spawner.setEntityId(type, this.level, random, this.worldPosition);
        this.setChanged();
    }

    public BaseSpawner getSpawner() {
        return this.spawner;
    }
}
