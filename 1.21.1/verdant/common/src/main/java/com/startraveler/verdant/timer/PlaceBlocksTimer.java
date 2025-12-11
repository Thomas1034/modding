package com.startraveler.verdant.timer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.Constants;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// TODO find why this crashes on exiting. Later.
public class PlaceBlocksTimer extends BaseTimer {

    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "place_blocks_timer"
    );
    private static final String BLOCKSTATE_AND_POS_LIST_KEY = "to_place";

    public static final MapCodec<PlaceBlocksTimer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.list(
                    BlockPlaceDirective.CODEC).fieldOf(BLOCKSTATE_AND_POS_LIST_KEY).forGetter(PlaceBlocksTimer::toPlace))
            .and(baseData(instance))
            .apply(instance, PlaceBlocksTimer::new));
    protected final List<BlockPlaceDirective> toPlace;

    protected PlaceBlocksTimer(List<BlockPlaceDirective> toPlace, long timeRemaining, ResourceLocation type) {
        super(timeRemaining, type);
        this.toPlace = Objects.requireNonNullElse(toPlace, new ArrayList<>());
        ;
    }

    public PlaceBlocksTimer(long timeRemaining, List<BlockPlaceDirective> toPlace) {
        this(toPlace, timeRemaining, TYPE);
    }

    public PlaceBlocksTimer(long timeRemaining, BlockPlaceDirective... toPlace) {
        this(timeRemaining, Arrays.asList(toPlace));
    }

    public List<BlockPlaceDirective> toPlace() {
        return this.toPlace;
    }

    @Override
    public void onFinish(ServerLevel level) {
        super.onFinish(level);
        for (BlockPlaceDirective directive : this.toPlace) {
            @Nullable BlockInWorld block = new BlockInWorld(level, directive.pos, false);
            if (block.getState() != null && directive.predicate.matches(block)) {
                level.setBlockAndUpdate(directive.pos, directive.state);
            }
        }
    }

    public MapCodec<? extends BaseTimer> codec() {
        return CODEC;
    }

    public record BlockPlaceDirective(BlockState state,
            BlockPos pos,
            BlockPredicate predicate) {
        private static final String BLOCKSTATE_KEY = "state";
        private static final String BLOCKPOS_KEY = "pos";
        private static final String PREDICATE_KEY = "predicate";
        public static final Codec<BlockPlaceDirective> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockState.CODEC.fieldOf(BLOCKSTATE_KEY).forGetter(BlockPlaceDirective::state),
                BlockPos.CODEC.fieldOf(BLOCKPOS_KEY).forGetter(BlockPlaceDirective::pos),
                BlockPredicate.CODEC.fieldOf(PREDICATE_KEY).forGetter(BlockPlaceDirective::predicate)
        ).apply(instance, BlockPlaceDirective::new));
    }
}
