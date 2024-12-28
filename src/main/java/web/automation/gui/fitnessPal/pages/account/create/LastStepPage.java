package web.automation.gui.fitnessPal.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import web.automation.gui.fitnessPal.pages.account.common.CreatePageBase;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CreatePageBase.class)
public class LastStepPage extends CreatePageBase {
    public LastStepPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/consents");
    }

    @Context(dependsOn = "formElement")
    @FindBy(xpath = ".//input[@aria-label=\"Accept All\"]")
    public ExtendedWebElement acceptAllBtn;

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/consents");
    }

    public NutritionalGoal acceptAllAndFinish() {
        this.acceptAllBtn.click();
        this.clickNext();

        return new NutritionalGoal(getDriver());
    }
}
