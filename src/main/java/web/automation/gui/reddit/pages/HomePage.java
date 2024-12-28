package web.automation.gui.reddit.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends AbstractPage {
    public HomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(carousel);
    }

    @FindBy(tagName = "search-dynamic-id-cache-controller")
    ExtendedWebElement carousel;

    @FindBy(xpath = "//a[@data-testid=\"subreddit-name\"]")
    public List<ExtendedWebElement> communitiesNameList;

}
