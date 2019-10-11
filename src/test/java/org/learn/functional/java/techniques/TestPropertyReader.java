package org.learn.functional.java.techniques;

import org.junit.jupiter.api.Test;
import org.learn.functional.java.types.Result;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TestPropertyReader {
    @Test
    void canReadPropertiesFromAFile() {
        Path pathToFile = Paths.get("src/main/resources/config.properties");
        PropertyReader propertyReader = new PropertyReader(pathToFile);
        assertEquals("Success",propertyReader.readProperty("host").getClass().getSimpleName());
        assertTrue(propertyReader.readProperty("host").isSuccess());
        Result<String> resultantYear = propertyReader.readProperty("year");
        resultantYear.forEachOrFail(System.out::println).forEach(System.out::println);
    }

    @Test
    void noErrorsThrownDueToFileRead() {
        Path pathToFile = Paths.get("src/main/resources/bogus.properties");
        PropertyReader propertyReader = new PropertyReader(pathToFile);
        assertEquals("Failure", propertyReader.readProperty("host").getClass().getSimpleName());
        assertTrue(propertyReader.readProperty("host").isFailure());
    }
}
