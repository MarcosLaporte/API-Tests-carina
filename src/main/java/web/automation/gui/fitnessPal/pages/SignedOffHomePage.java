package web.automation.gui.fitnessPal.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.automation.gui.fitnessPal.Utils;
import web.automation.gui.fitnessPal.pages.account.LogInPage;
import web.automation.gui.fitnessPal.pages.account.common.HomePageParent;
import web.automation.gui.fitnessPal.pages.account.create.WelcomePage;

public class SignedOffHomePage extends HomePageParent {
    @FindBy(xpath = "//*[@id=\"__next\"]/section[2]/div/div[1]/a")
    private ExtendedWebElement startBtn;

    public SignedOffHomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL_AND_ELEMENT);
        setUiLoadedMarker(startBtn);
    }

    @FindBy(xpath = "//*[@id=\"__next\"]/section[1]/div/a")
    public ExtendedWebElement accountHeaderBtn;

    public WelcomePage clickStart() {
        this.startBtn.click();
        Utils.LOGGER.info("Starting registration process...");

        return new WelcomePage(getDriver());
    }

    public LogInPage openLogin() {
        this.accountHeaderBtn.click();
        Utils.LOGGER.info("Opening log in page...");

        return new LogInPage(getDriver());
    }
}
