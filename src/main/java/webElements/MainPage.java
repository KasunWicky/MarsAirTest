package webElements;

import driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }


    public By getDepartingDropdown() {
        return By.id("departing");
    }
    public By getReturningDropdown() {
        return By.id("returning");
    }

    public By getPromotionalCodeField() {
        return By.id("promotional_code");
    }

    public By getSearchButton() {
        return By.cssSelector("input[type='submit']");
    }

    public By getMainHeader() {
        return By.cssSelector("h2");
    }

    public By getSubHeader() {
        return By.cssSelector("h3");
    }
}
