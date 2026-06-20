package config;

public class EnvConfig {
    public static final String BROWSER = ConfigManager.get("browser");
    public static final String URL = ConfigManager.get("url");
    public static final boolean HEADLESS = ConfigManager.getBoolean("headless");
    public static final int EXPLICIT_WAIT = ConfigManager.getInt("explicit_wait");
    public static final int POLLING_MS = ConfigManager.getInt("polling_ms");
}
