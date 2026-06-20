package testsCases;

import driver.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import resuableComponents.CommonCommands;
import webElements.MainPage;
import webElements.SearchResultPage;

@Slf4j
public class BookingFlightTest extends CommonCommands {
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
        until(mainPage.getDepartingDropdown(), Until.VISIBLE);
        softAssert = new SoftAssert();
        selectDropDown(mainPage.getDepartingDropdown(), departing);
        selectDropDown(mainPage.getReturningDropdown(), returning);
        click(mainPage.getSearchButton());
        verifySearchResults(expectedResults);
        softAssert.assertAll();
    }

    private void verifySearchResults(String expectedResult) {
        searchResultPage = new SearchResultPage(driver);
        Assert.assertEquals(searchResultPage.getMainHeader().getText(), "Search Results");
        softAssert.assertEquals(searchResultPage.getResultText().getText(), expectedResult);
        click(searchResultPage.getBackLink());
    }

    @AfterClass(alwaysRun = true)
    public void quit() {
        log.info("Test is being closed");
        Driver.quitDriver();
    }
}
