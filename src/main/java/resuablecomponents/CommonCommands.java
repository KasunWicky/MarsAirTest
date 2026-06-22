package resuablecomponents;

import config.EnvConfig;
import driver.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

import runner.ScreenshotListener;
import org.testng.annotations.Listeners;

@Slf4j
@Listeners(ScreenshotListener.class)
public class CommonCommands {
    private static final int DEFAULT_TIMEOUT_SECONDS = EnvConfig.EXPLICIT_WAIT;
    private static final int DEFAULT_POLLING_MS = EnvConfig.POLLING_MS;
    WebDriver driver = Driver.getDriver();

    /**
     * Waits for element to be visible and clickable, then clicks it.
     *
     * @param locator the element to click
     */
    public void click(By locator) {
        until(locator, Until.VISIBLE);
        until(locator, Until.CLICKABLE);
        log.info("Clicking on button");
        driver.findElement(locator).click();
    }

    public String getText(By locator) {
        until(locator, Until.VISIBLE);
        log.info("Extracting text on element");
        return driver.findElement(locator).getText();
    }


    /**
     * Picks an option from a dropdown by its visible text.
     *
     * @param locator    the dropdown element
     * @param selectText the option text to select
     */
    public void selectDropDown(By locator, String selectText) {
        until(locator, Until.VISIBLE);
        until(locator, Until.ENABLED);
        Select dropDown = new Select(driver.findElement(locator));
        dropDown.selectByVisibleText(selectText);
        log.info("Drop down select value '{}'", selectText);
    }

    /**
     * Blocks until the page is fully loaded. Uses JS readyState check.
     */
    public static void waitForPageLoad() {
        buildWait(Driver.getDriver())
                .until(driver -> ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Clears the field and types the given text into it.
     *
     * @param locator    the input field
     * @param textToType text to enter
     */
    public void type(By locator, String textToType) {
        until(locator, Until.VISIBLE);
        until(locator, Until.CLICKABLE);
        driver.findElement(locator).clear();
        log.info("Entering '{}' text into the field", textToType);
        driver.findElement(locator).sendKeys(textToType);
    }

    public enum Until {
        VISIBLE,      // element is displayed on the page
        CLICKABLE,    // element is displayed AND enabled
        INVISIBLE,    // element is hidden or removed from DOM
        ENABLED       // element is enabled (e.g. button not disabled)
    }

    /**
     * Waits for the element to meet the given condition before returning it.
     *
     * @param locator   the element to wait on
     * @param condition the expected state to wait for
     */
    public WebElement until(By locator, Until condition) {
        FluentWait<WebDriver> wait = buildWait(driver);

        switch (condition) {
            case VISIBLE:
                return wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));

            case CLICKABLE:
                return wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));

            case INVISIBLE:
                wait.until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
                return driver.findElement(locator);

            case ENABLED:
                return wait.until(driver -> driver.findElement(locator).isEnabled() ? driver.findElement(locator) : null);
            default:
                throw new IllegalArgumentException("Unsupported condition: " + condition);
        }
    }

    /**
     * Returns a FluentWait configured with the default timeout and polling interval.
     *
     * @param driver the active WebDriver instance
     */
    static FluentWait<WebDriver> buildWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))    // Max wait time
                .pollingEvery(Duration.ofMillis(DEFAULT_POLLING_MS))   // Frequency of DOM checks
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

}