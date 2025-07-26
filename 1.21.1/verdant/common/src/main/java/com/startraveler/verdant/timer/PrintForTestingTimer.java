package com.startraveler.verdant.timer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.rootbound.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class PrintForTestingTimer extends BaseTimer {

    public static final ResourceLocation TYPE = Constants.location("print_for_testing");
    private static final String TO_PRINT_STRING = "to_print";

    public static final MapCodec<PrintForTestingTimer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf(TO_PRINT_STRING).forGetter(PrintForTestingTimer::toPrint)).and
            (baseData(instance)).apply(instance, PrintForTestingTimer::new));


    protected final String toPrint;

    protected PrintForTestingTimer(String toPrint, long timeRemaining, ResourceLocation type) {
        super(timeRemaining, type);
        this.toPrint = toPrint;
    }

    public PrintForTestingTimer(String toPrint, long timeRemaining) {
        this(toPrint, timeRemaining, TYPE);
    }

    protected String toPrint() {
        return this.toPrint;
    }

    @Override
    // Return false to cancel timer.
    protected boolean onTick(ServerLevel level, long ticksRemaining) {
        System.out.println(ticksRemaining + " ticks remaining.");
        return super.onTick(level, ticksRemaining);
    }

    @Override
    public void onFinish(ServerLevel level) {
        super.onFinish(level);
        System.out.println(this.toPrint);
    }

    public MapCodec<? extends BaseTimer> codec() {
        return CODEC;
    }
}
