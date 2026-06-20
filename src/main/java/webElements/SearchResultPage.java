package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultPage {

    private WebDriver driver;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement mainHeader() {
        return driver.findElement(By.cssSelector("h2"));
    }

    public WebElement linkbutton_back() {
        return driver.findElement(By.xpath("//a[text()=' Back']"));
    }

    public WebElement textbox_Result() {
        return driver.findElement(By.cssSelector("div#content>p:nth-of-type(1)"));
    }
}
