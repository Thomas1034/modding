package com.startraveler.verdant.timer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.Converter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// TODO find why this crashes on exiting. Later.
public class BlockTransformerTimer extends BaseTimer implements Converter {

    public static final Identifier TYPE = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "transformer_timer"
    );
    private static final String POS_LIST_KEY = "to_convert";
    private static final String TRANSFORMER_KEY = "block_transformer";
    public static final MapCodec<BlockTransformerTimer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.list(
                            BlockPos.CODEC).fieldOf(POS_LIST_KEY).forGetter(BlockTransformerTimer::toConvert),
                    Identifier.CODEC.fieldOf(TRANSFORMER_KEY).forGetter(BlockTransformerTimer::getTransformer)
            )
            .and(baseData(instance))
            .apply(instance, BlockTransformerTimer::new));
    protected final List<BlockPos> toConvert;
    protected final Identifier transformer;

    protected BlockTransformerTimer(List<BlockPos> toConvert, Identifier transformer, long timeRemaining, Identifier type) {
        super(timeRemaining, type);
        this.toConvert = Objects.requireNonNullElse(toConvert, new ArrayList<>());
        this.transformer = transformer;
    }

    public BlockTransformerTimer(long timeRemaining, Identifier transformer, List<BlockPos> toConvert) {
        this(toConvert, transformer, timeRemaining, TYPE);
    }

    public BlockTransformerTimer(long timeRemaining, Identifier transformer, BlockPos... toConvert) {
        this(timeRemaining, transformer, Arrays.asList(toConvert));
    }

    public List<BlockPos> toConvert() {
        return this.toConvert;
    }

    @Override
    public void onFinish(ServerLevel level) {
        super.onFinish(level);
        for (BlockPos pos : this.toConvert) {
            this.convert(level, pos);
        }
    }

    public MapCodec<? extends BaseTimer> codec() {
        return CODEC;
    }

    @Override
    public Identifier getTransformer() {
        return this.transformer;
    }
}
