package org.pracTest;

import driver.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import resuableComponents.CommonCommands;
import webElements.MainPage;
import webElements.SearchResultPage;

@Slf4j
public class TestHomePage extends CommonCommands {
    WebDriver driver;
    SearchResultPage searchResultPage;
    SoftAssert softAssert;

    @DataProvider(name = "flightScheduleData")
    private Object[][] flightScheduleData() {
        return new Object[][]{
                {"Select...", "Select...", "Unfortunately, this schedule is not possible. Please try again."},
                {"July", "July", "Unfortunately, this schedule is not possible. Please try again."},
                {"July", "December", "Unfortunately, this schedule is not possible. Please try again."},
                {"December", "July", "Unfortunately, this schedule is not possible. Please try again."},
                {"December", "December", "Unfortunately, this schedule is not possible. Please try again."},
                {"December", "July (next year)", "Unfortunately, this schedule is not possible. Please try again."}
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
        softAssert = new SoftAssert();
        wait(driver).until(ExpectedConditions.elementToBeClickable(mainPage.dropdown_departing()));
        selectDropDown(mainPage.dropdown_departing(), departing);
        selectDropDown(mainPage.dropdown_returning(), returning);
        click(mainPage.button_search());
        verifySearchResults(expectedResults);
        softAssert.assertAll();
    }

    public void verifySearchResults(String expectedResult) {
        searchResultPage = new SearchResultPage(driver);
        wait(driver).until(ExpectedConditions.visibilityOf(searchResultPage.mainHeader()));
        Assert.assertEquals(searchResultPage.mainHeader().getText(), "Search Results");
        softAssert.assertEquals(searchResultPage.textbox_Result().getText(), expectedResult);
        wait(driver).until(ExpectedConditions.visibilityOf(searchResultPage.linkbutton_back()));
        wait(driver).until(ExpectedConditions.elementToBeClickable(searchResultPage.linkbutton_back()));
        click(searchResultPage.linkbutton_back());
    }

    @AfterClass(alwaysRun = true)
    public void quit() {
        log.info("Test is being closed");
        Driver.quitDriver();
    }
}
