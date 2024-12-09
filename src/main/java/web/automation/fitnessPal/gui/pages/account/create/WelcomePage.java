package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.automation.fitnessPal.Utils;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public class WelcomePage extends AbstractPage {
    @FindBy(xpath = "//main/div[3]/button")
    private ExtendedWebElement continueBtn;

    public WelcomePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/account/create/welcome");
    }

    @Override
    public boolean isPageOpened() {
        System.out.println("isPageOpened(): " + getCurrentUrl());
        return getCurrentUrl().equals("https://www.myfitnesspal.com/account/create/welcome");
    }

    public FirstNameInputPage openCreateAccount() {
        this.continueBtn.click(5);
        Utils.LOGGER.info("Navigating to Create Account...");

        return new FirstNameInputPage(getDriver());
    }
}