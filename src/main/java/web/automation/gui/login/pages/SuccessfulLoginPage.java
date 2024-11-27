package web.automation.gui.login.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SuccessfulLoginPage extends AbstractPage {

    @FindBy(xpath = "//*[@id=\"loop-container\"]/div/article/div[1]/h1")
    public ExtendedWebElement successTitle;

    public SuccessfulLoginPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(successTitle);
    }

    @Override
    public void open() {
        getDriver().navigate().to("https://practicetestautomation.com/logged-in-successfully/");
    }

    @FindBy(xpath = "//*[@id=\"loop-container\"]/div/article/div[2]/div/div/div/a")
    public ExtendedWebElement logoutBtn;

}
