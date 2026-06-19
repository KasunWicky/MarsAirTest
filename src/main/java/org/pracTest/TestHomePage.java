package org.pracTest;

import config.EnvConfig;
import driver.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

@Slf4j
public class TestHomePage {
    WebDriver driver;

    @Test
    public void verifyingHomePage() {
        log.info("Test is being initiated");
        driver = Driver.getDriver();
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Mars Airlines: Home");

    }

    @AfterTest
    public void quit() {
        log.info("Test is being closed");
        driver.quit();
    }
}
