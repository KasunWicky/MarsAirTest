package org.pracTest;

import driver.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import webElements.MainPage;
import webElements.SearchResultPage;

@Slf4j
public class TestHomePage {
    WebDriver driver;

    @DataProvider(name = "flightScheduleData")
    private Object[][] flightScheduleData() {
        return new Object[][]{
                {"Select...", "Select...", "Unfortunately, this schedule is not possible. Please try again."},
                {"July", "July", "Unfortunately, this schedule is not possible. Please try again."}
        };
    }

    @BeforeClass
    public void setupInvocation() {
        driver = Driver.getDriver();
    }

    @Test
    public void verifyingHomePage() {
        log.info("Test is being initiated");
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Mars Airlines: Home");
    }

    @Test(dependsOnMethods = "verifyingHomePage", dataProvider = "flightScheduleData")
    public void bookFlight(String departing, String returning, String expectedResults) {
        MainPage mainPage = new MainPage(driver);

        Select departingDropDown = new Select(mainPage.dropdown_departing());
        departingDropDown.selectByVisibleText(departing);
        Select returningDropDown = new Select(mainPage.dropdown_returning());
        returningDropDown.selectByVisibleText(returning);
        mainPage.button_search().click();
        //wait for submission to load  for
        verifySearchResults(expectedResults);

    }

    // @Test(dependsOnMethods = "bookFlight")
    protected void verifySearchResults(String expectedResult) {
        //String expectedResult = "Unfortunately, this schedule is not possible. Please try again.";
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        Assert.assertEquals(searchResultPage.mainHeader().getText(), "Search Results");
        Assert.assertEquals(searchResultPage.textbox_Result().getText(), expectedResult);
    }
    public void bookFlight()
    @AfterClass
    public void quit() {
        log.info("Test is being closed");
        driver.quit();
    }
}
