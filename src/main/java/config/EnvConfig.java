package config;

public class EnvConfig {
    public static final String BROWSER = ConfigManager.get("browser");
    public static final String URL = ConfigManager.get("url");
    public static final boolean HEADLESS = ConfigManager.getBoolean("headless");
}
