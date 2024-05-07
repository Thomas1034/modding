package com.thomas.verdant.util;

@FunctionalInterface
public interface TriFunction<R, S, T, U> {
	public U apply(R r, S s, T t);
}
