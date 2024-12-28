package web.automation.gui.reddit.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CommunityPage extends AbstractPage {
    final String community;
    public CommunityPage(WebDriver driver, String community) {
        super(driver);
        this.community = community;

        setPageURL(community);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @FindBy(tagName = "h1")
    public ExtendedWebElement header;
}
