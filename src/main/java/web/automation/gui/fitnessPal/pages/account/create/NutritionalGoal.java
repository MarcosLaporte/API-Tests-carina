package web.automation.gui.fitnessPal.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.automation.gui.fitnessPal.pages.SignedInHomePage;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AbstractPage.class)
public class NutritionalGoal extends AbstractPage {
    public NutritionalGoal(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/account/create/nutrition-goal");
    }

    @FindBy(xpath = "//main//h4[text()=\"Sign up for emails\"]/..")
    private ExtendedWebElement emailsCheckbox;

    @FindBy(xpath = "//main//button/span[text()=\"Explore MyFitnessPal\"]/..")
    private ExtendedWebElement continueBtn;

    @Override
    public boolean isPageOpened() {
        return getCurrentUrl().contains("/account/create/nutrition-goal");
    }

    public SignedInHomePage unsubscribeAndFinish() {
        this.emailsCheckbox.click();
        this.continueBtn.click();

        return new SignedInHomePage(getDriver());
    }
}
