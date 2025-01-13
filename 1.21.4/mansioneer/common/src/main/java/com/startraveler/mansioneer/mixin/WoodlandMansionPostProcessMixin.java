package com.startraveler.mansioneer.mixin;

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

    // Notes: runs on a chunk-by-chunk basis!
    @Inject(method = "afterPlace", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(III)Lnet/minecraft/core/BlockPos$MutableBlockPos;", ordinal = 0, shift = At.Shift.AFTER))
    public void addIterativeProcessor(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, ChunkPos whichChunkThisIs, PiecesContainer container, CallbackInfo ci, @Local BlockPos.MutableBlockPos mutableBlockPos) {
        // First, calculate the biome position.
        // This is based on the first structure piece in the list.
        // May change later.
        BlockPos origin = container.pieces().getFirst().getLocatorPosition();
        // Then, get the biome.
        // TODO: Gets the wrong biome. Not sure why.
        // Maybe it has the wrong seed or something?
        Holder<Biome> biome = level.getLevel().getChunkSource().getGenerator().getBiomeSource().getNoiseBiome(
                origin.getX() / 4,
                origin.getY() / 4,
                origin.getZ() / 4,
                level.getLevel().getChunkSource().randomState().sampler()
        );
        // Print out the biome holder and its position for debugging.
        // Constants.LOG.warn("The biome holder is {} at {}", biome, origin);
        // Get the block transformer from the cache.
        BlockTransformer transformer = this.mansioneer$cachedTransformerMap.get(biome);
        // If it's not in the cache, calculate it.
        // Get the list of biome mappings
        Registry<BiomeMapping> biomeMappings = level.registryAccess().lookupOrThrow(BiomeMapping.KEY);
        if (null == transformer) {
            for (BiomeMapping mapping : biomeMappings) {
                if (mapping.matches(biome)) {
                    transformer = mapping.getTransformer(level.registryAccess());
                    this.mansioneer$cachedTransformerMap.put(biome, transformer);
                    break;
                }
            }
        }
        // If a block transformer was found, update the entire vertical column within the structure at this position.
        if (null != transformer) {
            int minY = boundingBox.minY();
            int maxY = boundingBox.maxY();
            int oldX = mutableBlockPos.getX();
            int oldY = mutableBlockPos.getY();
            int oldZ = mutableBlockPos.getZ();
            for (int i = minY; i < maxY; i++) {
                mutableBlockPos.setY(i);
                if (!(boundingBox.isInside(mutableBlockPos)) && container.isInsidePiece(mutableBlockPos)) {
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


}
