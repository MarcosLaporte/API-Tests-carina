package web.automation.gui.fitnessPal.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import web.automation.gui.fitnessPal.AccountFieldValidator;
import web.automation.gui.fitnessPal.pages.account.common.SingleInputPageBase;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = SingleInputPageBase.class)
public class FirstNameInputPage extends SingleInputPageBase {
    public FirstNameInputPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/input-name");
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/input-name");
    }

    public GoalSelectionPage setNameInputAndContinue(String name) {
        AccountFieldValidator.validateFirstName(name);

        this.inputEl.type(name);
        this.clickNext();

        return new GoalSelectionPage(getDriver());
    }
}
