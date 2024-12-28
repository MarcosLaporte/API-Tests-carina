package web.automation.gui.fitnessPal.pages.account.create;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import web.automation.gui.fitnessPal.AccountFieldValidator;
import web.automation.gui.fitnessPal.Utils;
import web.automation.gui.fitnessPal.pages.account.common.SelectionPageBase;
import web.automation.gui.fitnessPal.objects.account.enums.Goals.Goal;

import java.util.Set;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = SelectionPageBase.class)
public class GoalSelectionPage extends SelectionPageBase<Goal, GoalAffirmationPage> {
    public GoalSelectionPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
        setPageURL("/goals");
    }

    @Override
    public boolean isPageOpened() {
        return super.isPageOpened() && this.getCurrentUrl().contains("/goals");
    }

    @Override
    public GoalAffirmationPage selectAndContinue(Set<Goal> goals) throws IllegalArgumentException {
        AccountFieldValidator.validateGoals(goals);

        for (Goal goal : goals) {
            ExtendedWebElement currGoalBtn = this.buttonEl.format(goal.getIndex());
            Utils.scrollTo(getDriver(), currGoalBtn);
            currGoalBtn.click();
        }

        if (goals.size() < 3)
            this.clickNext();

        return new GoalAffirmationPage(getDriver(), GoalAffirmationPage.AffirmationRelUrl.BIG_STEP);
    }

}