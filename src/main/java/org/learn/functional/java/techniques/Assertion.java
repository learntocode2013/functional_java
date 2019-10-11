package org.learn.functional.java.techniques;

import org.learn.functional.java.types.Result;

public class Assertion {
    public static boolean isPositive(int i) {
        return i >= 0;
    }

    public static boolean isValidName(String name) {
        return null != name && name.length() != 0
                && name.charAt(0) >= 65 && name.charAt(0) <= 91;
    }

    public static Result<Integer> assertPositive(int i) {
        return assertPositive(i, "Assertion error: value %s must be positive");
    }

    public static Result<Integer> assertPositive(int i, String message) {
        return Result.of(Assertion::isPositive, i, message);
    }

    public static Result<String> assertValidName(String name, String message) {
        return Result.of(Assertion::isValidName, name, message);
    }

}
