package web.automation.fitnessPal.gui.pages.account.create;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import web.automation.fitnessPal.AccountFieldValidator;
import web.automation.fitnessPal.gui.pages.account.common.SingleInputPageBase;

public class FirstNameInputPage extends SingleInputPageBase {
    public FirstNameInputPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/input-name");
    }

    public GoalSelectionPage setNameInputAndContinue(String name) {
        AccountFieldValidator.validateFirstName(name);

        this.inputEl.sendKeys(name);
        this.clickNext();

        return new GoalSelectionPage(getDriver());
    }
}
