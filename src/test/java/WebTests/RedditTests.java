package WebTests;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import web.automation.gui.reddit.pages.CommunityPage;
import web.automation.gui.reddit.pages.HomePage;

public class RedditTests implements IAbstractTest {
    static {
        R.CONFIG.put("url", "https://www.reddit.com/", true);
        R.CONFIG.put("platform", "DESKTOP", true);
    }

    @DataProvider
    Object[][] postNumbers() {
        return new Object[][]{
                {0},
                {1},
                {2}
        };
    }

    @Test(
            description = "Selects a community from posts and checks the URL and header of the page match the name of the community selected.",
            dataProvider = "postNumbers"
    )
    public void selectRandomCommunityCheckName(int postIndex) {
        WebDriver driver = getDriver();
        HomePage home = new HomePage(driver);
        home.open();
        home.assertPageOpened();

        ExtendedWebElement commEl = home.communitiesNameList.get(postIndex);
        commEl.scrollTo();
        commEl.hover();
        String commName = commEl.findExtendedWebElement(By.tagName("span")).getText();

        commEl.click();
        CommunityPage communityPage = new CommunityPage(driver, commName);
        communityPage.assertPageOpened();

        String communityHeader = communityPage.header.getText();
        Assert.assertEquals(commName, communityHeader, "Names are not the same.");
    }
}
