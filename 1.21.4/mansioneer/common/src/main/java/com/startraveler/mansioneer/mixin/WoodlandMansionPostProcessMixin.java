package com.startraveler.mansioneer.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.mansioneer.util.biomemapping.BiomeMapping;
import com.startraveler.mansioneer.util.blocktransformer.BlockTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(WoodlandMansionStructure.class)
public class WoodlandMansionPostProcessMixin {
    @Unique
    protected final Map<Holder<Biome>, BlockTransformer> mansioneer$cachedTransformerMap = new HashMap<>();

    @WrapOperation(method = "afterPlace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    public boolean changeFoundation(WorldGenLevel instance, BlockPos pos, BlockState state, int i, Operation<Boolean> original, @Local(argsOnly = true) PiecesContainer container) {
        BlockPos origin = container.pieces().getFirst().getLocatorPosition();
        BlockTransformer transformer = mansioneer$getTransformer(instance, origin);
        return transformer == null ? original.call(instance, pos, state, i) : original.call(
                instance,
                pos,
                transformer.get(state, instance.registryAccess(), instance.getRandom()),
                i
        );
    }

    // Notes: runs on a chunk-by-chunk basis!
    @Inject(method = "afterPlace", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(III)Lnet/minecraft/core/BlockPos$MutableBlockPos;", ordinal = 0, shift = At.Shift.AFTER))
    public void addIterativeProcessor(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, ChunkPos whichChunkThisIs, PiecesContainer container, CallbackInfo ci, @Local BlockPos.MutableBlockPos mutableBlockPos) {
        // First, calculate the biome position.
        // This is based on the first structure piece in the list.
        // May change later.
        BlockPos origin = container.pieces().getFirst().getLocatorPosition();
        // Then, get the block transformer.
        BlockTransformer transformer = this.mansioneer$getTransformer(level, origin);

        // If a block transformer was found, update the entire vertical column within the structure at this position.
        if (null != transformer) {
            int minY = boundingBox.minY();
            int maxY = boundingBox.maxY();
            int oldX = mutableBlockPos.getX();
            int oldY = mutableBlockPos.getY();
            int oldZ = mutableBlockPos.getZ();
            for (int i = minY; i < maxY; i++) {
                mutableBlockPos.setY(i);
                if (!(boundingBox.isInside(mutableBlockPos) && container.isInsidePiece(mutableBlockPos))) {
                    continue;
                }
                BlockState oldState = level.getBlockState(mutableBlockPos);

                BlockState newState = transformer.get(oldState, level.registryAccess(), random);
                level.setBlock(mutableBlockPos, newState, Block.UPDATE_CLIENTS);
            }
            // Clean up; reset the mutable block pos.
            mutableBlockPos.set(oldX, oldY, oldZ);
        }
    }

    @Unique
    public BlockTransformer mansioneer$getTransformer(WorldGenLevel level, BlockPos pos) {
        // Then, get the biome.
        Holder<Biome> biome = level.getLevel()
                .getChunkSource()
                .getGenerator()
                .getBiomeSource()
                .getNoiseBiome(
                        pos.getX() / 4,
                        pos.getY() / 4,
                        pos.getZ() / 4,
                        level.getLevel().getChunkSource().randomState().sampler()
                );
        // Get the block transformer from the cache.
        BlockTransformer transformer = this.mansioneer$cachedTransformerMap.get(biome);
        // If it's not in the cache, calculate it.
        // Get the list of biome mappings
        Registry<BiomeMapping> biomeMappings = level.registryAccess().lookupOrThrow(BiomeMapping.KEY);
        int smallestSizeSoFar = Integer.MAX_VALUE;
        if (null == transformer) {
            for (BiomeMapping mapping : biomeMappings) {
                int mappingSize = mapping.getSize(level.registryAccess());
                if (mapping.matches(biome) && mappingSize < smallestSizeSoFar) {
                    transformer = mapping.getTransformer(level.registryAccess());
                    smallestSizeSoFar = mappingSize;
                }
            }
        }
        this.mansioneer$cachedTransformerMap.put(biome, transformer);
        return transformer;
    }


}
