package driver;

import config.EnvConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Driver {
    private static final String BROWSER = EnvConfig.BROWSER;
    private static final String URL = EnvConfig.URL;

    public static WebDriver getDriver() {
        return switch (BROWSER) {
            case "firefox" -> getFirefoxDriver();
            default -> getChromeDriver();
        };
    }

    static WebDriver getChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.get(URL);
        return chromeDriver;
    }

    static WebDriver getFirefoxDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("start-maximized");
        FirefoxDriver firefoxDriver = new FirefoxDriver(firefoxOptions);
        firefoxDriver.get(URL);
        return firefoxDriver;
    }
}
