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
        waitForPageLoad();
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
        log.info("User hit the submit button");
        click(mainPage.getSearchButton());
        waitForPageLoad();
        if (!promoCode.isEmpty()) {
            //Verifying the promotional code
            verifySearchResultsPromoCode(expectedResults, Utility.buildExpectedPromoResult(promoCode));
        } else {
            //Verifying without the promotional code
            verifySearchResults(expectedResults);

        }
        click(searchResultPage.getBackLink());
        waitForPageLoad();

        softAssert.assertAll();
    }

    private void verifySearchResults(String expectedResult) {
        searchResultPage = new SearchResultPage(driver);
        waitForPageLoad();
        log.info("Verifying Search result page output");
        Assert.assertEquals(searchResultPage.getMainHeader().getText(), "Search Results");
        log.info("Validating search result page");
        softAssert.assertEquals(searchResultPage.getResultText().getText(), expectedResult);

    }

    private void verifySearchResultsPromoCode(String expectedResult, String expectedPromoResult) {
        verifySearchResults(expectedResult);
        log.info("Validating promotional code message");
        softAssert.assertEquals(searchResultPage.getResultSecondaryText().getText(), expectedPromoResult);
    }

    @AfterClass(alwaysRun = true)
    public void quit() {
        log.info("Test is being closed");
        Driver.quitDriver();
    }
}
