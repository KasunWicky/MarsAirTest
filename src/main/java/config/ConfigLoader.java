package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

class ConfigLoader {
    Properties loadPropertiesFile(String filePath) {
        Properties properties = new Properties();
        Path path = Paths.get("", filePath);
        try {
            FileInputStream fis = new FileInputStream(path.toFile());
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Target properties file not found at: " + filePath, e);
        }
        return properties;
    }

}