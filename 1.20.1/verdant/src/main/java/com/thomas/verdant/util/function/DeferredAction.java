package com.thomas.verdant.util.function;

import java.awt.EventQueue;
import java.util.function.BooleanSupplier;

// Executes the supplied Runnable when the given BooleanSupplier returns true.
public class DeferredAction {

	// Constants defining wait times in milliseconds
	public static final long WAIT_TIME_SHORT = 100;
	public static final long WAIT_TIME_MEDIUM_SHORT = 250;
	public static final long WAIT_TIME_MEDIUM = 500;
	public static final long WAIT_TIME_LONG = 1000;
	public static final long WAIT_TIME_EXTRA_LONG = 2000;

	public static void waitFor(BooleanSupplier condition, Runnable action, long checkIntervalMillis) {
		Thread runner = new Thread(() -> {
			while (!condition.getAsBoolean()) {
				try {
					Thread.sleep(checkIntervalMillis);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
			action.run();
		});

		runner.start();
	}

	public static void waitFor(BooleanSupplier condition, Runnable action) {
		DeferredAction.waitFor(condition, action, WAIT_TIME_MEDIUM);
	};

}
