package org.learn.functional.java;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class GuavaSummarizer {
	private static final Logger logger = LoggerFactory.getLogger("GuavaSummarizer");
	private static final String[] BASKET = {
		"Bright orange bananas",
		"Deep green ridge gourd",
		"",
		"Bug free leafy cabbage",
		"Blood red tomato",
		"Red juicy fuji apple"
	};

	private static final Predicate<String> NON_EMPTY = str -> str != null && str.length() > 0;

	private static final Function<String, String> lastWord = new Function<String, String>() {
		@Override
		public String apply(String phrase) {
			final String[] words = phrase.split("\\s+");
			return Iterables.getLast(Arrays.asList(words));
		}
	};

	public static void main(String[] args) {
		logger.info(summarize(BASKET).get());
	}

	private static Optional<String> summarize(String[] elements) {
		if(null == elements) {
			return Optional.absent();
		}

		final String finalPhrase = FluentIterable.from(elements)
				.filter(NON_EMPTY)
				.transform(lastWord)
				.join(Joiner.on(" & "))
				.toString();

		return Optional.of(finalPhrase);
	}

	private static Iterable<String> getNonEmpties(String[] elements) {
		return Iterables.filter(Arrays.asList(elements), NON_EMPTY);
	}
}
