package config;

import java.util.Properties;

class ConfigManager {
    private static final String PROPERTY_FILE_PATH = "src/main/resources/config.properties";
    private static final String ENV_FILE_PATH = "src/main/resources/%s.properties";
    private static final ConfigLoader loader = new ConfigLoader();

    private static final Properties finalProperties = new Properties();

    static {
        Properties baseProps = loader.loadPropertiesFile(PROPERTY_FILE_PATH);
        finalProperties.putAll(baseProps);

        String environment = System.getProperty("env", "test").toLowerCase();
        String envFilePath = String.format(ENV_FILE_PATH, environment);

        Properties envProps = loader.loadPropertiesFile(envFilePath);
        finalProperties.putAll(envProps);
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

    public static boolean getBoolean(String key) {
        String value;
        try {
            value = get(key);
        } catch (RuntimeException e) {
            // Catching the exception from get() if the key doesn't exist at all
            return false;
        }
        // Handle cases where the key exists but has no value (e.g., "browser.headless=")
        if (value.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static int getInt(String key) {
        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Configuration key '" + key + "' value cannot be parsed to an Integer.", e);
        }
    }

    public static float getFloat(String key) {
        try {
            return Float.parseFloat(get(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Configuration key '" + key + "' value cannot be parsed to a Float.", e);
        }
    }

}