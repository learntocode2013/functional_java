package org.learn.functional.java.concepts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Function;

public class Applicative {
    private final Logger logger = LoggerFactory.getLogger(Applicative.class.getSimpleName());
    private Optional<Function<String, String>> applicativeOrNothing = Optional.empty();

    Applicative() {
    }

    Applicative(Function<String, String> customFunction) {
        this.applicativeOrNothing = Optional.of(customFunction);
    }

    public String applyDelimiter(String... parts) {
        String spaceDelimitedInput = String.join(" ", parts);
        return applicativeOrNothing.or(this::getDefaultApplicative)
                .get()
                .apply(spaceDelimitedInput);
    }

    private Optional<Function<String, String>> getDefaultApplicative() {
        logger.info("Default applicative will be used since none was specified...");
        return Optional.of(Function.identity());
    }
}
