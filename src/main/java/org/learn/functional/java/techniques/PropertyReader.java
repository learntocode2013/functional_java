package org.learn.functional.java.techniques;

import org.learn.functional.java.types.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

class PropertyReader {
    private final Logger logger = LoggerFactory.getLogger(PropertyReader.class);
    private Result<Properties> properties;

    PropertyReader(Path cfgFilePath) {
        this.properties = readProperties(cfgFilePath);
        logger.info("Attempt to load properties from file: {} was a {}", cfgFilePath, properties);
    }

    private Result<Properties> readProperties(Path cfgFilePath) {
        try {
            Properties props = new Properties();
            props.load(Files.newBufferedReader(cfgFilePath));
            return Result.of(props);
        } catch (Exception cause) {
            logger.error("Failed to read properties from: {}", cfgFilePath);
            var errMsg = String.format("Failed to read properties from file: %s", cfgFilePath);
            return Result.failure(errMsg, cause);
        }
    }

    Result<String> readProperty(String key) {
        return properties.flatMap(props -> readProperty(props, key));
    }

    private Result<String> readProperty(Properties properties, String key) {
        return Result.of(properties.getProperty(key))
                .mapFailure(String.format("Property \"%s\" was not found %n",key));
    }
}
