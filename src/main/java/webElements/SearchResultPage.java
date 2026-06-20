package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultPage {

    private WebDriver driver;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getMainHeader() {
        return driver.findElement(By.cssSelector("h2"));
    }

    public WebElement getBackLink() {
        return driver.findElement(By.xpath("//a[text()=' Back']"));
    }

    public WebElement getResultText() {
        return driver.findElement(By.cssSelector("div#content>p:nth-of-type(1)"));
    }
}
