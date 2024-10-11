package com.thomas.verdant.util.function;

@FunctionalInterface
public interface PentaPredicate<Q, R, S, T, U> {

	public boolean test(Q q, R r, S s, T t, U u);
}
