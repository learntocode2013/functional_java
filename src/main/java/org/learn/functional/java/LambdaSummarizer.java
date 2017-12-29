package org.learn.functional.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaSummarizer {
	private static final Logger logger = LoggerFactory.getLogger("LambdaSummarizer");
	private static final String[] BASKET = {
			"Bright orange bananas",
			"Deep green ridge gourd",
			"",
			"Bug free leafy cabbage",
			"Blood red tomato",
			"Red juicy fuji apple"
	};

	private static final Predicate<String>  NON_EMPTY = (input) -> null != input &&
																   input.trim().length() >= 1;

	private static final BinaryOperator<String> chooseLast = (allSoFar, nextElement) -> nextElement;
	private static final Function<String,String> lastWord = (phrase) ->
			Arrays.asList(phrase.split("\\s+"))
					.stream()
					.reduce(chooseLast)
					.orElse("");

	public static void main(String[] args) {
		String allTogether = summarize(BASKET);
		logger.info(allTogether);
	}

	private static String summarize(String[] descriptions) {
		return Arrays.asList(descriptions)
				.stream()
				.peek(str -> logger.info("About to filter: " + str))
				.filter(NON_EMPTY)
				.peek(str -> logger.info("Transforming phrase " + str))
				.map(lastWord)
				.peek(str -> logger.info("Reducing string: " + str))
				.reduce(joinOn(" & "))
				.orElse("");

	}

	private static BinaryOperator<String> joinOn(String connector) {
		return (allSoFar, nextElement) -> allSoFar + connector + nextElement;
	}
}
