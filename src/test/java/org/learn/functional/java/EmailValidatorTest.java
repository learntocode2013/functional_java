package org.learn.functional.java;

import org.junit.jupiter.api.Test;
import org.learn.functional.java.types.Effect;
import org.learn.functional.java.types.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.base.Strings.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.learn.functional.java.EmailValidator.EMAIL_CHECKER;

class EmailValidatorTest {
    private final Logger logger = LoggerFactory.getLogger("EmailValidatorTest");

    @Test
    void testValidation() {
        var emailIds = asList("disen@cisco.com", null, "disen.cisco.com", "");
        List<Executable> resultantActions = emailIds.stream()
                .map(EmailValidator::validate)
                .collect(toList());
        assertNotNull(resultantActions, "Expected list of executable actions");
        resultantActions.forEach(Executable::exec);
    }

    @Test
    void testValidationWithCustomEffect() {
        // Desired effect which needs to be applied if validation is successful
        Effect<String> assertSuccess = result -> {
            logger.info("Inside success callback effect");
            assertFalse(isNullOrEmpty(result));
        };
        Effect<String> assertFailure = result -> {
            logger.error("Inside failure callback effect");
            assertFalse(result.contains("@"));
        };
        var emailIds = asList("disen@cisco.com", null, "");
        emailIds.forEach(email -> EMAIL_CHECKER.apply(email).bind(assertSuccess, assertFailure));
    }
}
