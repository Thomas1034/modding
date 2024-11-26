package com.thomas.verdant.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OptionalDirection implements StringRepresentable {

    EMPTY("empty"),
    UP("up", Direction.UP),
    DOWN("down", Direction.DOWN),
    NORTH("north", Direction.NORTH),
    EAST("east", Direction.EAST),
    SOUTH("south", Direction.SOUTH),
    WEST("west", Direction.WEST);

    private static final BiMap<Direction, OptionalDirection> MAP = HashBiMap.create();

    static {
        MAP.putAll(Arrays.stream(OptionalDirection.values()).collect(Collectors.toMap(OptionalDirection::direction, Function.identity())));
    }

    private final Direction direction;
    private final String name;

    OptionalDirection(String name, Direction d) {
        this.name = name;
        this.direction = d;
    }

    OptionalDirection(String name) {
        this.name = name;
        this.direction = null;
    }

    public static OptionalDirection of(Direction d) {
        return MAP.get(d);
    }

    private Direction direction() {
        return this.direction;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }
}
