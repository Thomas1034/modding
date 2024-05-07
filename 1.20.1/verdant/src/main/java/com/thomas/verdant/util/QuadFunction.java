package com.thomas.verdant.util;

@FunctionalInterface
public interface QuadFunction<Q, R, S, T, U> {
	public U apply(Q q, R r, S s, T t);
}
