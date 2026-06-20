package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement dropdown_departing() {
        return driver.findElement(By.id("departing"));
    }

    public WebElement dropdown_returning() {
        return driver.findElement(By.id("returning"));
    }

    public WebElement textBox_promotionalCode() {
        return driver.findElement(By.id("promotional_code"));
    }

    public WebElement button_search() {
        return driver.findElement(By.cssSelector("input[type='submit']"));
    }

    public WebElement mainHeader() {
        return driver.findElement(By.cssSelector("h2"));
    }

    public WebElement subHeader() {
        return driver.findElement(By.cssSelector("h3"));
    }
}
