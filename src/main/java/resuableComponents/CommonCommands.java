package resuableComponents;

import config.EnvConfig;
import driver.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
     *
     * @param element
     */
    public static void click(WebElement element) {
        until(element, Until.VISIBLE);
        until(element, Until.CLICKABLE);
        log.info("Clicking on button");
        element.click();
    }

    /**
     *
     * @param element
     * @param selectText
     */
    public static void selectDropDown(WebElement element, String selectText) {
        until(element, Until.VISIBLE);
        until(element, Until.ENABLED);
        Select dropDown = new Select(element);
        dropDown.selectByVisibleText(selectText);
        log.info("Drop down select value '{}'", selectText);
    }

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

    public static FluentWait<WebDriver> buildWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))    // Max wait time
                .pollingEvery(Duration.ofMillis(DEFAULT_POLLING_MS))   // Frequency of DOM checks
                .ignoring(NoSuchElementException.class);// Disregard this error during loops
    }

}