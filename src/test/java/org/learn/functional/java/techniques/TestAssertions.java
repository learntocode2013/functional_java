package org.learn.functional.java.techniques;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.learn.functional.java.types.Result;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.learn.functional.java.techniques.Assertion.*;

class TestAssertions {
    static private final Map<String, String> invalidKeyVals = new HashMap<>(){{
        put("personId","-1");
        put("firstName","00seven");
        put("lastName","B0nd");
    }};

    static private final Map<String, String> validKeyVals = new HashMap<>(){{
        put("personId","864033");
        put("firstName","Dibakar");
        put("lastName","Sen");
    }};

    @Test
    @DisplayName("Demonstrate functional approach to input validation")
    void demoInputValidation() {
        // With invalid inputs
        final Result<Integer> rId = assertPositive(getInvalidInt("personId"), "Id cannot be negative");
        final Result<String> rfname = assertValidName(getInvalidString("firstName"), "Firstname must start with an alphabet");
        final Result<String> rlname = assertValidName(getInvalidString("lastName"), "Lastname must contain all alphabets");

        Result<Employee> rEmp = rId.flatMap(id -> rfname.flatMap(
                firstName -> rlname.map(
                        lastName -> new Employee(id, firstName, lastName)
                )
        ));

        assertTrue(rEmp.isFailure(), "Expected a failed result for invalid input");

        // With valid inputs
        final Result<Integer> validIdResult = assertPositive(getInt("personId"), "Id cannot be negative");
        final Result<String> validfnameResult = assertValidName(getString("firstName"), "Firstname must start with an alphabet");
        final Result<String> validlnameResult = assertValidName(getString("lastName"), "Lastname must contain all alphabets");

        Result<Employee> validEmpResult = validIdResult.flatMap(id -> validfnameResult.flatMap(
                firstName -> validlnameResult.map(lastName -> new Employee(id, firstName, lastName))
        ));

        assertTrue(validEmpResult.isSuccess(), "Expected a successful result for valid input");
    }

    private int getInvalidInt(String id) {
        return Integer.parseInt(invalidKeyVals.get(id));
    }

    private int getInt(String id) {
        return Integer.parseInt(validKeyVals.get(id));
    }

    private String getString(String key) {
        return validKeyVals.get(key);
    }


    private String getInvalidString(String key) {
        return invalidKeyVals.get(key);
    }
}
