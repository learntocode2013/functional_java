package org.learn.functional.java;

import java.time.LocalTime;
import java.util.function.Consumer;
import java.util.function.Supplier;

class Timing {
	static <T> T timed(String description, Supplier<T> action) {
		Consumer<String> output = System.out::println;
		return timed(description, output, action);
	}

	static <T> T timed(String description,
	                   Consumer<String> output,
	                   Supplier<T> action) {
		LocalTime before = LocalTime.now();
		final T result = action.get();
		LocalTime after = LocalTime.now();
		final int timeTaken = after.getSecond() - before.getSecond();
		// Notice how the logging decision is offloaded to the caller, thereby
		// making this function truly data-in/data-out
		output.accept(String.format("%s took %d seconds %n",description,timeTaken));
		return result;
	}
}
