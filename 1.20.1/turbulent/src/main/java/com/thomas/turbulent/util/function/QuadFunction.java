package com.thomas.turbulent.util.function;

@FunctionalInterface
public interface QuadFunction<Q, R, S, T, U> {
	public U apply(Q q, R r, S s, T t);
}
