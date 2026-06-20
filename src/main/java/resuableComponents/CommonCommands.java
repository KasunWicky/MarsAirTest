package resuableComponents;

import config.EnvConfig;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import runner.ScreenshotListener;
import org.testng.annotations.Listeners;

@Listeners(ScreenshotListener.class)
public class CommonCommands {
    private static final int DEFAULT_TIMEOUT_SECONDS = EnvConfig.EXPLICIT_WAIT;
    private static final int DEFAULT_POLLING_MS = EnvConfig.POLLING_MS;

    /**
     *
     * @param element
     */
    public static void click(WebElement element) {
        element.click();
    }

    /**
     *
     * @param element
     * @param selectText
     */
    public static void selectDropDown(WebElement element, String selectText) {
        Select dropDown = new Select(element);
        dropDown.selectByVisibleText(selectText);
    }

    public static FluentWait<WebDriver> wait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))    // Max wait time
                .pollingEvery(Duration.ofMillis(DEFAULT_POLLING_MS))   // Frequency of DOM checks
                .ignoring(NoSuchElementException.class);// Disregard this error during loops
    }
}
