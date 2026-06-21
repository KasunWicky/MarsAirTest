package testsCases;

import driver.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import resuableComponents.CommonCommands;
import utils.Utility;
import webElements.MainPage;
import webElements.SearchResultPage;

@Slf4j
public class BookingFlightTest extends CommonCommands {
    WebDriver driver;
    SearchResultPage searchResultPage;
    SoftAssert softAssert;

    @DataProvider(name = "flightScheduleData")
    private Object[][] flightScheduleData() {
        return Utility.readCsv("testdata/flightCombinationData.csv");
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
        log.info("Verified user is Home page");
    }

    @Test(dependsOnMethods = "verifyingHomePage", dataProvider = "flightScheduleData")
    public void bookFlight(String departing, String returning, String promoCode, String expectedResults) {
        MainPage mainPage = new MainPage(driver);
        until(mainPage.getDepartingDropdown(), Until.VISIBLE);
        softAssert = new SoftAssert();
        log.info("Selecting 'Departing' DpDwn");
        selectDropDown(mainPage.getDepartingDropdown(), departing);
        log.info("Selecting 'Returning' DpDwn");
        selectDropDown(mainPage.getReturningDropdown(), returning);
        if (!promoCode.isEmpty()) {
            log.info("Promotional code :'{}' is present", promoCode);
            type(mainPage.getPromotionalCodeField(), promoCode);
        }
        log.info("User hit submit button");
        click(mainPage.getSearchButton());
        verifySearchResults(expectedResults);
        softAssert.assertAll();
    }

    private void verifySearchResults(String expectedResult) {
        searchResultPage = new SearchResultPage(driver);
        log.info("Verifying Search result page output");
        Assert.assertEquals(searchResultPage.getMainHeader().getText(), "Search Results");
        log.info("Validating search result page");
        softAssert.assertEquals(searchResultPage.getResultText().getText(), expectedResult);
        //Validate Assert equals with promocode message
        click(searchResultPage.getBackLink());
    }

    @AfterClass(alwaysRun = true)
    public void quit() {
        log.info("Test is being closed");
        Driver.quitDriver();
    }
}
