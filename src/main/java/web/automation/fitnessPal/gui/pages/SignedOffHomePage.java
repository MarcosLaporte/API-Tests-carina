package web.automation.fitnessPal.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.automation.fitnessPal.gui.pages.account.common.HomePageParent;
import web.automation.fitnessPal.gui.pages.account.create.WelcomePage;

import static web.automation.fitnessPal.Utils.LOGGER;

public class SignedOffHomePage extends HomePageParent {
    @FindBy(xpath = "//*[@id=\"__next\"]/section[2]/div/div[1]/a")
    private ExtendedWebElement startBtn;

    public SignedOffHomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL_AND_ELEMENT);
        setUiLoadedMarker(startBtn);
    }

    public WelcomePage clickStart() {
        this.startBtn.click();
        LOGGER.info("Starting registration process...");

        return new WelcomePage(getDriver());
    }
}
