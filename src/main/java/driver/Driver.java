package driver;

import config.EnvConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

@Slf4j
public class Driver {
    private static final String BROWSER = EnvConfig.BROWSER;
    private static final String URL = EnvConfig.URL;
    private static final boolean HEADLESS = EnvConfig.HEADLESS;


    public static WebDriver getDriver() {
        log.info("The browser is being invoked on URL:{}", URL);
        return switch (BROWSER.toLowerCase()) {
            case "firefox" -> getFirefoxDriver();
            case "safari" -> getSafariDriver();
            default -> getChromeDriver();
        };
    }

    static WebDriver getChromeDriver() {
        log.info("Chrome browser is being invoked");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        if (HEADLESS) {
            log.info("Enabling headless mode");
            chromeOptions.addArguments("--headless=new");
        }
        ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.get(URL);
        return chromeDriver;
    }

    static WebDriver getFirefoxDriver() {
        log.info("Firefox browser is being invoked");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("start-maximized");
        if (HEADLESS) {
            log.info("Enabling headless mode");
            firefoxOptions.addArguments("--headless");
        }
        FirefoxDriver firefoxDriver = new FirefoxDriver(firefoxOptions);
        firefoxDriver.get(URL);
        return firefoxDriver;
    }

    static WebDriver getSafariDriver() {
        log.info("Safari browser is being invoked");
        if (HEADLESS) {
            log.warn("Safari browser is not supporting headless mode. Continuing with the head mode");
        }
        SafariDriver safariDriver = new SafariDriver();
        safariDriver.get(URL);
        safariDriver.manage().window().fullscreen();
        return safariDriver;
    }
}
