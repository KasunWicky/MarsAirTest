package testsCases;

import driver.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import resuablecomponents.CommonCommands;
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
        softAssert = new SoftAssert();
        MainPage mainPage = new MainPage(driver);
        fillAndSubmitSearchForm(mainPage, departing, returning, promoCode);
        verifyResults(promoCode, expectedResults);
        navigateBack();
        softAssert.assertAll();

    }

    private void fillAndSubmitSearchForm(MainPage mainPage, String departing, String returning, String promoCode) {
        waitForPageLoad();
        until(mainPage.getDepartingDropdown(), Until.VISIBLE);
        log.info("Selecting 'Departing' DropDown");
        selectDropDown(mainPage.getDepartingDropdown(), departing);
        log.info("Selecting 'Returning' DropDown");
        selectDropDown(mainPage.getReturningDropdown(), returning);
        if (!promoCode.isEmpty()) {
            log.info("Promotional code: '{}' is present", promoCode);
            type(mainPage.getPromotionalCodeField(), promoCode);
        }
        log.info("User hit the submit button");
        click(mainPage.getSearchButton());
        waitForPageLoad();
    }

    private void verifyResults(String promoCode, String expectedResults) {
        if (!promoCode.isEmpty()) {
            verifySearchResultsPromoCode(expectedResults, Utility.buildExpectedPromoResult(promoCode));
        } else {
            verifySearchResults(expectedResults);
        }

    }

    private void navigateBack() {
        click(searchResultPage.getBackLink());
        waitForPageLoad();
    }

    private void verifySearchResults(String expectedResult) {
        searchResultPage = new SearchResultPage(driver);
        log.info("Verifying Search result page output");
        waitForPageLoad();
        softAssert.assertEquals(getText(searchResultPage.getMainHeader()), "Search Results");
        log.info("Validating search result page");
        softAssert.assertEquals(getText(searchResultPage.getResultText()), expectedResult);
    }

    private void verifySearchResultsPromoCode(String expectedResult, String expectedPromoResult) {
        verifySearchResults(expectedResult);
        log.info("Validating promotional code message");
        softAssert.assertEquals(getText(searchResultPage.getResultSecondaryText()), expectedPromoResult);
    }

    @AfterClass(alwaysRun = true)
    public void quit() {
        log.info("Test is being closed");
        Driver.quitDriver();
    }
}
