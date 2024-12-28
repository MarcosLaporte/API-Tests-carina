package web.automation.gui.login.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends AbstractPage {
    @FindBy(xpath = "//*[@id=\"login\"]/h2")
    private ExtendedWebElement testTitle;

    public SignInPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL_AND_ELEMENT);
        setUiLoadedMarker(testTitle);
    }

    @Override
    public boolean isPageOpened() {
        return getCurrentUrl().contains("/practice-test-login");
    }

    @FindBy(xpath = "//*[@id=\"username\"]")
    public ExtendedWebElement userInput;

    @FindBy(xpath = "//*[@id=\"password\"]")
    public ExtendedWebElement passInput;

    @FindBy(xpath = "//*[@id=\"submit\"]")
    public ExtendedWebElement submitBtn;

    @FindBy(xpath = "//*[@id=\"error\"]")
    public ExtendedWebElement errorMsg;
}
