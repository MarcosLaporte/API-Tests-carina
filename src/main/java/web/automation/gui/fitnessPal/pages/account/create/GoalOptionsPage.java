package web.automation.gui.fitnessPal.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web.automation.gui.fitnessPal.AccountFieldValidator;
import web.automation.gui.fitnessPal.Utils;
import web.automation.gui.fitnessPal.pages.account.common.SelectionPageBase;
import web.automation.gui.fitnessPal.objects.account.enums.GoalOptions;
import web.automation.gui.fitnessPal.objects.account.enums.Goals.Goal;

import java.util.Set;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = SelectionPageBase.class)
public class GoalOptionsPage<T extends GoalOptions> extends SelectionPageBase<T, GoalAffirmationPage> {
    public final Goal goal;

    public GoalOptionsPage(WebDriver driver, Goal goal) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/goals/" + goal.relUrl + "/options");

        this.goal = goal;
    }

    @Override
    public boolean isPageOpened() {
        return this.getCurrentUrl().contains("/account/create/goals");
    }

    @Override
    public GoalAffirmationPage selectAndContinue(Set<T> options) throws IllegalArgumentException {
        AccountFieldValidator.validateGoalOptions(this.goal, options);

        for (T option : options) {
            ExtendedWebElement currOptionBtn = this.buttonEl.format(option.getIndex());
            Utils.scrollTo(getDriver(), currOptionBtn);
            currOptionBtn.click();
        }

        if (goal != Goal.INCREASE_STEP_COUNT &&
                options.size() < this.btnsContainer.findElements(By.xpath(".//button")).size())
            this.clickNext();

        return new GoalAffirmationPage(getDriver(), goal, GoalAffirmationPage.AffirmationRelUrl.AFFIRMATION);
    }
}