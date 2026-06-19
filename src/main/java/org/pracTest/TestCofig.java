package org.pracTest;

import config.EnvConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

@Slf4j
public class TestCofig {
    private final String URL = EnvConfig.URL;
    WebDriver driver;

    @Test
    public void testing1() {
        log.info("Test");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.get(URL);
        log.info("Test is being initiated");
        driver = chromeDriver;
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Mars Airlines: Home");

    }

    @AfterTest
    public void quit() {
        log.info("Test is being closed");
        driver.quit();
    }
}
