package com.thomas.verdant.util.function;

@FunctionalInterface
public interface QuadPredicate<Q, R, S, T> {
	
	public boolean test(Q q, R r, S s, T t);
}
