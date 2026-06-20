package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getDepartingDropdown() {
        return driver.findElement(By.id("departing"));
    }

    public WebElement getReturningDropdown() {
        return driver.findElement(By.id("returning"));
    }

    public WebElement getPromotionalCodeField() {
        return driver.findElement(By.id("promotional_code"));
    }

    public WebElement getSearchButton() {
        return driver.findElement(By.cssSelector("input[type='submit']"));
    }

    public WebElement getMainHeader() {
        return driver.findElement(By.cssSelector("h2"));
    }

    public WebElement getSubHeader() {
        return driver.findElement(By.cssSelector("h3"));
    }
}
