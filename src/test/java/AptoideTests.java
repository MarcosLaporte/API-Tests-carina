import android.automation.aptoide.gui.pages.*;
import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
}
