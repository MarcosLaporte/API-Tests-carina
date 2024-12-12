import android.automation.aptoide.gui.pages.*;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class AptoideTests implements IAbstractTest {
    @Test
    public void checkBottomNavBar() {
        WebDriver driver = getDriver();
        SoftAssert softAssert = new SoftAssert();

        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.assertPageOpened(5);

        HomePage homePage = welcomePage.skipWelcomePage();
        softAssert.assertTrue(homePage.isPageOpened(5), "HOME page was not opened.");

        EditorialPage editorialPage = homePage.navigateTo(BasePage.Section.EDITORIAL);
        softAssert.assertTrue(editorialPage.isPageOpened(5), "EDITORIAL page was not opened.");

        SearchPage searchPage = editorialPage.navigateTo(BasePage.Section.SEARCH);
        softAssert.assertTrue(searchPage.isPageOpened(5), "SEARCH page was not opened.");

        StoresPage storesPage = searchPage.navigateTo(BasePage.Section.STORES);
        softAssert.assertTrue(storesPage.isPageOpened(5), "STORES page was not opened.");

        AppsPage appsPage = storesPage.navigateTo(BasePage.Section.APPS);
        softAssert.assertTrue(appsPage.isPageOpened(5), "APPS page was not opened.");

        softAssert.assertAll();
    }

    @DataProvider(name = "searchQuery")
    public Object[][] getAccount() {
        return new Object[][]{
                {"clash"},
                {"kingdom"},
                {"candy"}
        };
    }

    @Test(dataProvider = "searchQuery")
    public void searchForApp(String query) {
        WebDriver driver = getDriver();
        SoftAssert softAssert = new SoftAssert();

        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.assertPageOpened(5);

        HomePage homePage = welcomePage.skipWelcomePage();
        homePage.assertPageOpened(5);

        SearchPage searchPage = homePage.navigateTo(BasePage.Section.SEARCH);
        searchPage.assertPageOpened(5);

        List<ExtendedWebElement> results = searchPage.searchFor(query, 3);

        for (ExtendedWebElement result : results) {
            String appName = SearchPage.getAppName(result);
            softAssert.assertTrue(appName.toLowerCase().contains(query.toLowerCase()), String.format("'%s' is not present in '%s'", query, appName));
        }

        softAssert.assertAll("Some searches failed:");

        ExtendedWebElement selectedAppEl = results.getFirst();
        String appName = SearchPage.getAppName(selectedAppEl);

        AppPage appPage = searchPage.openResult(selectedAppEl);
        Assert.assertEquals(appPage.appNameEl.getText(), appName, "App names don't match.");
    }

    @Test
    public void checkHomePage() {
        WebDriver driver = getDriver();
        SoftAssert softAssert = new SoftAssert();

        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.assertPageOpened(5);

        HomePage homePage = welcomePage.skipWelcomePage();
        homePage.assertPageOpened(5);

        softAssert.assertTrue(homePage.gamesChipBtn.isVisible(Duration.ofMillis(2500)), "Games Chip Button is not visible.");
        softAssert.assertTrue(homePage.appsChipBtn.isVisible(Duration.ofMillis(2500)), "Apps Chip Button is not visible.");
        softAssert.assertTrue(homePage.featuredAppList.isVisible(Duration.ofMillis(2500)), "Featured App List is not visible.");
        softAssert.assertTrue(homePage.gotwCardEl.isVisible(Duration.ofMillis(2500)), "Game of the Week Card is not visible.");
        softAssert.assertTrue(homePage.featuredAppCoinList.isVisible(Duration.ofMillis(2500)), "Featured App Coin List is not visible.");

        softAssert.assertAll();
    }

    @Test
    public void checkStorePage() {
        WebDriver driver = getDriver();
        SoftAssert softAssert = new SoftAssert();

        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.assertPageOpened(5);

        HomePage homePage = welcomePage.skipWelcomePage();
        homePage.assertPageOpened(5);

        StoresPage storesPage = homePage.navigateTo(BasePage.Section.STORES);
        storesPage.assertPageOpened(5);

        for (ExtendedWebElement header : storesPage.headerList) {
            softAssert.assertTrue(header.isVisible(Duration.ofMillis(2500)), "Header not visible.");
        }

        for (ExtendedWebElement recommendedStore : storesPage.recommendedStores) {
            softAssert.assertTrue(recommendedStore.isVisible(Duration.ofMillis(2500)), "Store not visible.");
        }

        softAssert.assertAll();
    }

    @Test
    public void checkAppsPage() {
        WebDriver driver = getDriver();
        SoftAssert softAssert = new SoftAssert();

        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.assertPageOpened(5);

        HomePage homePage = welcomePage.skipWelcomePage();
        homePage.assertPageOpened(5);

        AppsPage appsPage = homePage.navigateTo(BasePage.Section.APPS);
        appsPage.assertPageOpened(5);

        for (ExtendedWebElement header : appsPage.headerList) {
            softAssert.assertTrue(header.isVisible(Duration.ofMillis(2500)), "Header not visible.");
        }

        softAssert.assertTrue(appsPage.aptoideAppEl.isPresent(), "Aptoide app is not present.");

    }
}
