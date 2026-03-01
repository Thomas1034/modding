package com.startraveler.verdant.timer;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.util.CodecRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;

public class BaseTimer {
    public static final CodecRegistry<BaseTimer> CODEC_REGISTRY = new CodecRegistry<>();
    public static final String TIME_REMAINING_STRING = "time_remaining";
    public static final String TYPE_STRING = "type";
    public static final MapCodec<BaseTimer> CODEC = RecordCodecBuilder.mapCodec(instance -> baseData(instance).apply(
            instance,
            BaseTimer::new
    ));
    private final Identifier type;
    private long timeRemaining;

    protected BaseTimer(long timeRemaining, Identifier type) {
        this.timeRemaining = timeRemaining;
        this.type = type;
    }

    protected static <T extends BaseTimer> Products.P2<RecordCodecBuilder.Mu<T>, Long, Identifier> baseData(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(
                Codec.LONG.fieldOf(TIME_REMAINING_STRING).forGetter(BaseTimer::getTimeRemaining),
                Identifier.CODEC.fieldOf(TYPE_STRING).forGetter(BaseTimer::getType)
        );
    }

    private Long getTimeRemaining() {
        return this.timeRemaining;
    }

    // Runs every tick; returns false to cancel the timer.
    public boolean handleTick(ServerLevel level) {
        if (this.timeRemaining > 0) {
            this.timeRemaining--;
        } else {
            this.timeRemaining = 0;
        }
        if (!this.onTick(level, this.timeRemaining)) {
            return false;
        }
        if (this.timeRemaining == 0) {
            this.onFinish(level);
            return false;
        }
        return true;
    }

    // Return false to cancel timer.
    protected boolean onTick(ServerLevel level, long ticksRemaining) {
        return true;
    }

    // Called when the timer finishes without being canceled.
    public void onFinish(ServerLevel level) {

    }

    public Identifier getType() {
        return type;
    }

    public MapCodec<? extends BaseTimer> codec() {
        return CODEC;
    }
}
