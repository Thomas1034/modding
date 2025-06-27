package com.startraveler.verdant.timer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.Constants;
import it.unimi.dsi.fastutil.objects.ObjectImmutableList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TimerListSavedData extends SavedData {
    public static final String SAVED_DATA_KEY = Constants.MOD_ID + "_" + "timer_list";
    public static final String TIMER_KEY = "timers";
    public static final Codec<TimerListSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.list(
                    (Codec<BaseTimer>) BaseTimer.CODEC_REGISTRY.forDispatch().dispatch(BaseTimer::codec, Function.identity()))
                    .fieldOf(TIMER_KEY)
                    .forGetter(
                            TimerListSavedData::getTimers))
            .apply(instance, TimerListSavedData::new));
    public static final SavedDataType<TimerListSavedData> TYPE = new SavedDataType<>(
            SAVED_DATA_KEY,
            TimerListSavedData::new,
            CODEC,
            null
    );
    private final List<BaseTimer> timers = new ArrayList<>();

    public TimerListSavedData() {
    }

    public TimerListSavedData(@NotNull List<BaseTimer> timers) {
        this();
        timers.forEach(this::addTimer);
    }

    public static TimerListSavedData fetch(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public static void save(ServerLevel level, TimerListSavedData data) {
        level.getDataStorage().set(TYPE, data);
    }

    public static void addTimer(ServerLevel level, BaseTimer timer) {
        TimerListSavedData list = fetch(level);
        list.addTimer(timer);
        save(level, list);

    }

    public static void removeTimer(ServerLevel level, BaseTimer timer) {
        TimerListSavedData list = fetch(level);
        if (list != null) {
            list.removeTimer(timer);
            save(level, list);
        }
    }

    public void addTimer(BaseTimer timer) {
        this.timers.add(timer);
    }

    public void removeTimer(BaseTimer timer) {
        this.timers.remove(timer);
    }

    public @NotNull List<BaseTimer> getTimers() {
        return new ObjectImmutableList<>(this.timers);
    }
}
