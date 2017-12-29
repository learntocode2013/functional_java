package org.learn.functional.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimingTest {
	@Test
	@Tag("Sanity")
	@DisplayName("Verifies that the output from timed function contains passed in description")
	void outputHasDescription() {
		final String description = "Has description";
		AtomicReference<String> output = new AtomicReference<>("");
		Timing.timed(description, desc -> output.set(desc), () -> 1.0);
		assertTrue(output.get().contains(description),
				"Passed in description was not provided to output!");
	}
}
