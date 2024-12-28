package web.automation.gui.fitnessPal.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.automation.gui.fitnessPal.pages.account.common.HomePageParent;

public class SignedInHomePage extends HomePageParent {
    @FindBy(xpath = "//*[@id=\"__next\"]//header")
    private ExtendedWebElement header;

    public SignedInHomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL_AND_ELEMENT);
        setUiLoadedMarker(header);
    }

}
