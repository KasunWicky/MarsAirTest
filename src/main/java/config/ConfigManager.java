package config;

import java.util.Properties;

class ConfigManager {
    private static final String PROPERTY_FILE_PATH = "src/main/resources/config.properties";
    private static final ConfigLoader loader = new ConfigLoader();

    private static final Properties finalProperties = new Properties();

    static {
        Properties baseProps = loader.loadPropertiesFile(PROPERTY_FILE_PATH);
        finalProperties.putAll(baseProps);
    }
    static String get(String key) {
        String systemOverride = System.getProperty(key);
        if (systemOverride != null) {
            return systemOverride.trim();
        }

        String value = finalProperties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Configuration key '" + key + "' not found in global configs.");
        }
        return value.trim();
    }
}