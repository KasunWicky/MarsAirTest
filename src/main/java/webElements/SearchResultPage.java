package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultPage {

    private WebDriver driver;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public By getMainHeader() {
        return By.tagName("h2");
    }

    public By getBackLink() {
        return By.xpath("//a[text()=' Back']");
    }

    public By getResultText() {
        return By.cssSelector("div#content>p:nth-of-type(1)");
    }

    public By getResultSecondaryText() {
        return By.cssSelector("div#content>p:nth-of-type(2)");
    }

    public By getCallNowResultText() {
        return By.cssSelector("div#content>p:nth-of-type(3)");
    }
}
