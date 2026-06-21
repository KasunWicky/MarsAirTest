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

    /**
     * Waits for element to be visible and clickable, then clicks it.
     * @param element the element to click
     */
    public static void click(WebElement element) {
        until(element, Until.VISIBLE);
        until(element, Until.CLICKABLE);
        log.info("Clicking on button");
        element.click();
    }

    /**
     * Picks an option from a dropdown by its visible text.
     * @param element the dropdown element
     * @param selectText the option text to select
     */
    public static void selectDropDown(WebElement element, String selectText) {
        until(element, Until.VISIBLE);
        until(element, Until.ENABLED);
        Select dropDown = new Select(element);
        dropDown.selectByVisibleText(selectText);
        log.info("Drop down select value '{}'", selectText);
    }

    /** Blocks until the page is fully loaded. Uses JS readyState check. */
    public static void waitForPageLoad() {
        buildWait(Driver.getDriver())
                .until(driver -> ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Clears the field and types the given text into it.
     * @param element the input field
     * @param textToType text to enter
     */
    public static void type(WebElement element, String textToType) {
        until(element, Until.VISIBLE);
        until(element, Until.CLICKABLE);
        element.clear();
        log.info("Entering '{}' text into the field", textToType);
        element.sendKeys(textToType);
    }

    public enum Until {
        VISIBLE,      // element is displayed on the page
        CLICKABLE,    // element is displayed AND enabled
        INVISIBLE,    // element is hidden or removed from DOM
        ENABLED       // element is enabled (e.g. button not disabled)
    }

    /**
     * Waits for the element to meet the given condition before returning it.
     * @param element the element to wait on
     * @param condition the expected state to wait for
     */
    public static WebElement until(WebElement element, Until condition) {
        FluentWait<WebDriver> wait = buildWait(Driver.getDriver());

        switch (condition) {
            case VISIBLE:
                return wait.until(ExpectedConditions.visibilityOf(element));

            case CLICKABLE:
                return wait.until(ExpectedConditions.elementToBeClickable(element));

            case INVISIBLE:
                wait.until(ExpectedConditions.invisibilityOf(element));
                return element;

            case ENABLED:
                return wait.until(driver -> element.isEnabled() ? element : null);

            default:
                throw new IllegalArgumentException("Unsupported condition: " + condition);
        }
    }

    /**
     * Returns a FluentWait configured with the default timeout and polling interval.
     * @param driver the active WebDriver instance
     */
     static FluentWait<WebDriver> buildWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))    // Max wait time
                .pollingEvery(Duration.ofMillis(DEFAULT_POLLING_MS))   // Frequency of DOM checks
                .ignoring(NoSuchElementException.class);
    }

}