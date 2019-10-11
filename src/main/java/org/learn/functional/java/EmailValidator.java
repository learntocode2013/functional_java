package org.learn.functional.java;

import org.learn.functional.java.types.Effect;
import org.learn.functional.java.types.Executable;
import org.learn.functional.java.types.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.regex.Pattern;

import static org.learn.functional.java.types.Case.*;

class EmailValidator {
    private static final Logger logger = LoggerFactory.getLogger(EmailValidator.class);
    private static final Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    static final Function<String, Result> EMAIL_CHECKER = email -> match(
            defaultCase(() -> Result.success("email " + email + " is valid")),
            newCase(() -> email == null, () -> Result.failure("email must be specified")),
            newCase(() -> email.isEmpty(), () -> Result.failure("email cannot be empty")),
            newCase(() -> !emailPattern.matcher(email).find(), () -> Result.failure("email " + email + " is invalid"))
    );

    public static final Effect<String> onSuccess = email  -> sendEmailNotification(email);
    public static final Effect<String> onFailure = errMsg -> logError(errMsg);

    static Executable validate(String email) {
        Result result = EMAIL_CHECKER.apply(email);
        return result.isSuccess() ? () -> sendEmailNotification(email):
                () -> logError(result.failureValue().getMessage());
    }

    private static void sendEmailNotification(String email) {
        logger.info("Email successfully sent to {}", email);
    }

    private static void logError(String errMsg) {
        logger.error("Error logged - {}", errMsg);
    }
}
